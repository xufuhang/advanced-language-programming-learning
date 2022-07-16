package cn.ncepu.alpl.service.impl;

import cn.ncepu.alpl.config.UploadConfig;
import cn.ncepu.alpl.service.CmsFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
@author xufuhang
@date 2022/3/20-22:40
*/
@Service
@Slf4j
public class CmsFileServiceImpl implements CmsFileService {

    @Autowired
    UploadConfig uploadConfig;
    @Autowired
    HttpServletResponse response;

    @Override
    public void writeVideo(String videoName) {
        String filePath = uploadConfig.getVideoPath() + videoName;
        OutputStream outputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] data = new byte[fileInputStream.available()];
            fileInputStream.read(data);

            String fileName = "final.mp4";
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setHeader("Content-Disposition", "attachment;");
            response.setHeader("Content-Range", "" + (data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentType("video/mp4");
            response.setContentLength(data.length);

            outputStream = response.getOutputStream();
            outputStream.write(data);

            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public String handleUploadVideo(MultipartFile file) {
        if (file == null) {
            return null;
        }

        File folder = new File(uploadConfig.getVideoPath());
        if (!folder.exists()) {
            folder.mkdir();
        }

        String newFileName = getRandomFileName(file);
        File destFile = new File(folder, newFileName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;
    }

    @Override
    public String handleUploadImage(MultipartFile file) {
        if (file == null) {
            return null;
        }

        File folder = new File(uploadConfig.getImagePath());
        if (!folder.exists()) {
            folder.mkdir();
        }

        String newFileName = getRandomFileName(file);
        File destFile = new File(folder, newFileName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;
    }

    @Override
    public byte[] getImageBytes(String imageName) {
        byte[] bytes = new byte[0];
        try {
            String filePath = uploadConfig.getImagePath() + imageName;
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public boolean deleteVideo(String videoName) {
        String path = uploadConfig.getVideoPath() + videoName;
        boolean delete = deleteFileByPath(path);
        return delete;
    }

    private String getRandomFileName(MultipartFile file) {
        //String newFileName = getRandomString() + file.getOriginalFilename();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = getRandomString() + suffix;
        return newFileName;
    }

    private String getRandomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private boolean deleteFileByPath(String path) {
        File file = new File(path);
        File[] children = file.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                deleteFileByPath(child.getPath());
            } else {
                child.delete();
            }
        }
        file.delete();
        return true;
    }
}
