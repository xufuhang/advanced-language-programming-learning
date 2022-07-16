package cn.ncepu.alpl.component;

import cn.hutool.core.thread.ThreadUtil;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.model.CmsContent;
import cn.ncepu.alpl.model.DmsComment;
import cn.ncepu.alpl.model.DmsCommentReply;
import cn.ncepu.alpl.model.UmsUser;
import cn.ncepu.alpl.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.ncepu.alpl.constant.FileClearConstant.FILE_NAME_PATTERN_GROUP;
import static cn.ncepu.alpl.constant.FileClearConstant.FILE_NAME_REG_EXP;

/**
 * The type File clear worker.
 *
 * @author xufuhang
 * @date 2022 /4/24-0:20
 */
@Component
@Slf4j
public class FileClearWorker {

    private Thread thread;
    @Value("${fileClear.intervalTimeHour:24}")
    private int fileClearIntervalTimeHour;
    private long fileClearIntervalTime;
    @Value("${fileClear.queryPageSize:10}")
    private int fileClearQueryPageSize;
    @Autowired
    private Pattern fileNamePattern;
    @Autowired
    private FmsFileService fileService;
    @Autowired
    private CmsSectionService sectionService;
    @Autowired
    private DmsCommentService commentService;
    @Autowired
    private DmsCommentReplyService commentReplyService;
    @Autowired
    private UmsUserService userService;

    @Bean
    Pattern fileNamePattern() {
        return Pattern.compile(FILE_NAME_REG_EXP);
    }

    /**
     * 启动定期扫描线程
     */
    @PostConstruct
    public void startWorker() {
        fileClearIntervalTime = (long) fileClearIntervalTimeHour * 60 * 60 * 1000;
        thread = new Thread(this::work);
        thread.start();
    }

    private void work() {
        while (true) {
            long timeMillis = System.currentTimeMillis();
            clearOnce();
            long executeTime = System.currentTimeMillis() - timeMillis;
            long sleepTime = Math.max(fileClearIntervalTime - executeTime, 0);
            ThreadUtil.sleep(sleepTime);
        }
    }

    /**
     * 进行一次磁盘扫描，并清理无用文件
     */
    private void clearOnce() {
        List<String> allFilenameList = fileService.getAllFileList();
        Set<String> filesUsedSet = getFilesUsedSet();
        deleteUnusedFiles(allFilenameList, filesUsedSet);
    }

    /**
     * 获取在所有内容中使用到的文件
     * @return 已使用的文件集合
     */
    private Set<String> getFilesUsedSet() {
        Set<String> filesUsedSet = new HashSet<>();
        scanSection(filesUsedSet);
        scanComment(filesUsedSet);
        scanCommentReply(filesUsedSet);
        scanUserAvatar(filesUsedSet);
        return filesUsedSet;
    }

    private void scanUserAvatar(Set<String> filesUsedSet) {
        int userPageNum = 0;
        while (true) {
            CommonPage<UmsUser> page = userService.queryList(userPageNum, fileClearQueryPageSize);
            userPageNum++;

            for (UmsUser user : page.getList()) {
                String avatar = user.getAvatar();
                searchFileFromAvatar(filesUsedSet, avatar);
            }

            Integer pages = page.getPages();
            if (userPageNum > pages) {
                break;
            }
        }
    }

    private void searchFileFromAvatar(Set<String> filesUsedSet, String avatar) {
        if (avatar == null) {
            return;
        }
        int index = avatar.lastIndexOf('/') + 1;
        String filename = avatar.substring(index);
        filesUsedSet.add(filename);
    }

    private void scanCommentReply(Set<String> filesUsedSet) {
        int commentReplyPageNum = 0;
        while (true) {
            CommonPage<DmsCommentReply> page
                    = commentReplyService.fetchList(commentReplyPageNum, fileClearQueryPageSize);
            commentReplyPageNum++;

            for (DmsCommentReply commentReply : page.getList()) {
                String contentStr = commentReply.getContent();
                searchFile(filesUsedSet, contentStr);
            }

            Integer pages = page.getPages();
            if (commentReplyPageNum > pages) {
                break;
            }
        }
    }

    private void scanComment(Set<String> filesUsedSet) {
        int commentPageNum = 0;
        while (true) {
            CommonPage<DmsComment> page = commentService.fetchList(commentPageNum, fileClearQueryPageSize);
            commentPageNum++;

            for (DmsComment comment : page.getList()) {
                String contentStr = comment.getContent();
                searchFile(filesUsedSet, contentStr);
            }

            Integer pages = page.getPages();
            if (commentPageNum > pages) {
                break;
            }
        }
    }

    private void scanSection(Set<String> filesUsedSet) {
        int contentPageNum = 0;
        while (true) {
            // 获取fileClearPageSize个小节内容，进行扫描
            CommonPage<CmsContent> page
                    = sectionService.queryContentPage(contentPageNum, fileClearQueryPageSize);
            contentPageNum++;

            for (CmsContent cmsContent : page.getList()) {
                String contentStr = cmsContent.getContent();
                searchFile(filesUsedSet, contentStr);
            }

            Integer pages = page.getPages();
            if (contentPageNum > pages) {
                break;
            }
        }
    }

    private void searchFile(Set<String> filesUsedSet, String contentStr) {
        if (contentStr == null) {
            return;
        }
        Matcher matcher = fileNamePattern.matcher(contentStr);
        while (matcher.find()) {
            String objectName = matcher.group(FILE_NAME_PATTERN_GROUP);
            filesUsedSet.add(objectName);
        }
    }

    /**
     * 对比两个集合，不一致的即为无用文件
     * @param allFileList 所有文件名列表
     * @param fileUsedSet 使用的文件名集合
     */
    private void deleteUnusedFiles(List<String> allFileList, Set<String> fileUsedSet) {
        for (String filename : allFileList) {
            if (fileUsedSet.contains(filename)) {
                continue;
            }
            try {
                fileService.deleteFile(filename);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("文件删除异常");
            }
        }
    }

    /**
     * 在扫描内容中文件时，可能会新增新文件，此时要把新文件加入到“已使用文件集合”中
     * @param objectName 访问文件名
     */
//    public void addNewFile(String objectName) {
//        filesUsedSet.add(objectName);
//    }


}
