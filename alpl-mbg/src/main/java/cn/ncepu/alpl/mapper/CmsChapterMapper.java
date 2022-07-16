package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.model.CmsChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsChapterMapper {
    long countByExample(CmsChapterExample example);

    int deleteByExample(CmsChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmsChapter record);

    int insertSelective(CmsChapter record);

    List<CmsChapter> selectByExample(CmsChapterExample example);

    CmsChapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmsChapter record, @Param("example") CmsChapterExample example);

    int updateByExample(@Param("record") CmsChapter record, @Param("example") CmsChapterExample example);

    int updateByPrimaryKeySelective(CmsChapter record);

    int updateByPrimaryKey(CmsChapter record);
}