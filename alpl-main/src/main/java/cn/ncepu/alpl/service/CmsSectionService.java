package cn.ncepu.alpl.service;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.model.CmsContent;
import cn.ncepu.alpl.model.CmsSection;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

/**
 * The interface Cms section service.
 */
public interface CmsSectionService {

    @Transactional(rollbackFor = Exception.class)
    int create(CmsSectionDetail sectionResult, Principal principal);

    int delete(Integer sectionId);

    int clearNewCommentCount(Integer sectionId);

    List<CmsSection> queryByCondition(CmsSection section);

    CommonPage<CmsContent> queryContentPage(int fileClearPageNum, int fileClearPageSize);

    int incrCommentCount(Integer sectionId, int count);

    int incrNewCommentCount(Integer sectionId);

    /**
     * 三步：1、目标section_count++而源章节不用变，因为下一个rank_num没有变小，只是中间空了一个,
     * 2、目标位置以后的section修改rankNum, 3、修改section所属章节，以及rankNum
     * 如果sort为空，表示插入到某个章节内，则插到末尾，第一个if表示为该section腾出位置
     *
     * @param section 包含id、sort、chapterId，sort可能为空
     * @return 修改行数
     */
    int updatePosition(CmsSection section);

    int update(CmsSectionDetail sectionResult);

    CmsSectionDetail queryDetailById(Integer id);
}
