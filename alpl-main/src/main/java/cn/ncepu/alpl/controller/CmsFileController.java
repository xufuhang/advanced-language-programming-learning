package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.config.UploadConfig;
import cn.ncepu.alpl.service.CmsFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
@author xufuhang
@date 2022/3/20-19:59
*/
@RestController
@RequestMapping("/cms")
@Slf4j
public class CmsFileController {

    @Autowired
    UploadConfig uploadConfig;
    @Autowired
    CmsFileService cmsFileService;

    @PostMapping("/video/create")
    public CommonResult<String> createVideo(MultipartFile video) {
        String videoId = cmsFileService.handleUploadVideo(video);
        if (videoId == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(videoId);
    }

    @PostMapping("/image/create")
    public String createImage(MultipartFile image) {
        String id = cmsFileService.handleUploadImage(image);
        return id;
    }

    @DeleteMapping("/video/delete")
    public CommonResult<Void> deleteVideo(String videoId) {
        boolean delete = cmsFileService.deleteVideo(videoId);
        if (!delete) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @GetMapping("/video/query/{videoId}")
    public void getVideo(@PathVariable("videoId") String videoId) {
        cmsFileService.writeVideo(videoId);
    }

    @GetMapping(value = "/image/query/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("imageId") String imageId) {
        byte[] bytes = cmsFileService.getImageBytes(imageId);
        return bytes;
    }

}
