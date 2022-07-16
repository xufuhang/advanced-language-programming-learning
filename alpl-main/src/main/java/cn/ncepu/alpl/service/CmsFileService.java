package cn.ncepu.alpl.service;

/*
@author xufuhang
@date 2022/3/20-22:39
*/

import org.springframework.web.multipart.MultipartFile;

public interface CmsFileService {

    void writeVideo(String videoName);

    String handleUploadVideo(MultipartFile file);

    String handleUploadImage(MultipartFile file);

    byte[] getImageBytes(String imageName);

    boolean deleteVideo(String videoName);
}
