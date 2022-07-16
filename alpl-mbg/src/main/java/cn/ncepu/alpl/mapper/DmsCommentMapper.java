package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.DmsComment;
import cn.ncepu.alpl.model.DmsCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsCommentMapper {
    long countByExample(DmsCommentExample example);

    int deleteByExample(DmsCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmsComment record);

    int insertSelective(DmsComment record);

    List<DmsComment> selectByExample(DmsCommentExample example);

    DmsComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmsComment record, @Param("example") DmsCommentExample example);

    int updateByExample(@Param("record") DmsComment record, @Param("example") DmsCommentExample example);

    int updateByPrimaryKeySelective(DmsComment record);

    int updateByPrimaryKey(DmsComment record);
}