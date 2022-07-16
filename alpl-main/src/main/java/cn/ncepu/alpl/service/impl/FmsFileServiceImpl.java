package cn.ncepu.alpl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import cn.ncepu.alpl.config.MinioConfig;
import cn.ncepu.alpl.domain.BucketPolicyConfigDto;
import cn.ncepu.alpl.domain.MinioUploadDto;
import cn.ncepu.alpl.service.FmsFileService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import io.minio.messages.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/22-23:25
 */
@Service
@Slf4j
public class FmsFileServiceImpl implements FmsFileService {

    @Autowired
    MinioConfig minioConfig;

    @Override
    public MinioUploadDto uploadRichTextFile(MultipartFile file){
        String bucketName = minioConfig.getBucketName();
        MinioUploadDto uploadDto = doUpload(file, bucketName);
        return uploadDto;
    }

    @Override
    public MinioUploadDto uploadAvatar(MultipartFile file) {
        String avatarBucket = minioConfig.getAvatarBucket();
        MinioUploadDto uploadDto = doUpload(file, avatarBucket);
        return uploadDto;
    }

    private MinioUploadDto doUpload(MultipartFile file, String bucketName) {
        MinioUploadDto minioUploadDto = null;
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = getMinioClient();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
            if (!isExist) {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                BucketPolicyConfigDto bucketPolicyConfigDto
                        = createBucketPolicyConfigDto(bucketName);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
            // 设置存储对象名称
//        String objectName = getRandomNameAppendFilename(file);
            String objectName = genNewNameWithExt(file);
            // 使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .contentType(file.getContentType())
    //                .contentType("application/octet-stream")
                    .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
            minioClient.putObject(putObjectArgs);
            log.info("文件上传成功!");
            minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(objectName);
            minioUploadDto.setUrl(minioConfig.getEndpoint() + "/" +
                    bucketName + "/" + objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minioUploadDto;
    }

    @Override
    public void deleteFile(String objectName) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        MinioClient minioClient = getMinioClient();
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(objectName).build());
        log.info("删除文件: {}", objectName);
    }

    @Override
    public List<String> getAllFileList() {
        MinioClient minioClient = getMinioClient();
        Iterable<Result<Item>> listObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioConfig.getBucketName())
                .build());
        List<String> filenameList = new ArrayList<>();
        for (Result<Item> result : listObjects) {
            Item item = null;
            try {
                item = result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String objectName = item != null ? item.objectName() : null;
            filenameList.add(objectName);
        }
//        log.info(filenameList.toString());
        Iterable<Result<Upload>> results = minioClient.listIncompleteUploads(ListIncompleteUploadsArgs.builder()
                .bucket(minioConfig.getBucketName())
                .build());
        Iterator<Result<Upload>> iterator = results.iterator();
        while (iterator.hasNext()) {
            try {
                log.error(iterator.next().get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filenameList;
    }

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    private String getRandomNameAppendFilename(MultipartFile file) {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID().toString(true));
        sb.append('-');
        sb.append(file.getOriginalFilename());
        return sb.toString();
    }

    private String genNewNameWithExt(MultipartFile file) {
        String randomStr = UUID.randomUUID().toString(true);
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return randomStr;
        }
        String suffix = filename.substring(filename.lastIndexOf("."));
        return randomStr + suffix;
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::" + bucketName + "/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }

}
