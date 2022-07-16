package cn.ncepu.alpl.service;

import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.model.CmsChapter;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

/**
@author xufuhang
@date 2022/3/13-21:41
*/
public interface CmsChapterService {

    @Transactional(rollbackFor = Exception.class)
    int create(CmsChapter chapter, Principal principal);

    int delete(Integer chapterId);

    List<CmsChapter> queryByCondition(CmsChapter chapter);

    List<CmsChapterDetail> queryChapterDetailListByModuleId(Integer moduleId);

    int updatePosition(CmsChapter chapter);

    int updateByIdSelective(CmsChapter chapter);

    int incrNewCommentCount(Integer chapterId, int count);

    CmsChapter getChapterById(Integer chapterId);
}
