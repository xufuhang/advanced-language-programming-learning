package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.domain.MinioUploadDto;
import cn.ncepu.alpl.service.FmsFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO对象存储管理Controller
 * Created by macro on 2019/12/25.
 */
@RestController
@RequestMapping("/fms")
@Slf4j
public class FmsMinioController {

    @Autowired
    FmsFileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CommonResult<MinioUploadDto> upload(@RequestParam("file") MultipartFile file) {
        try {
            MinioUploadDto minioUploadDto = fileService.uploadRichTextFile(file);
            return CommonResult.success(minioUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<Void> delete(@RequestParam("objectName") String objectName) {
        try {
            fileService.deleteFile(objectName);
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }

}
