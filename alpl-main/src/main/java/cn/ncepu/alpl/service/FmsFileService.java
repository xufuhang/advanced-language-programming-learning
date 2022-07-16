package cn.ncepu.alpl.service;

import cn.ncepu.alpl.domain.MinioUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/22-23:25
 */
public interface FmsFileService {

    MinioUploadDto uploadRichTextFile(MultipartFile file) throws Exception;

    void deleteFile(String objectName) throws Exception;

    List<String> getAllFileList();

    MinioUploadDto uploadAvatar(MultipartFile file);
}
