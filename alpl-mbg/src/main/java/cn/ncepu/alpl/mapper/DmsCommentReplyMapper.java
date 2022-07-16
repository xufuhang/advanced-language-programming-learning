package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.DmsCommentReply;
import cn.ncepu.alpl.model.DmsCommentReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsCommentReplyMapper {
    long countByExample(DmsCommentReplyExample example);

    int deleteByExample(DmsCommentReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmsCommentReply record);

    int insertSelective(DmsCommentReply record);

    List<DmsCommentReply> selectByExample(DmsCommentReplyExample example);

    DmsCommentReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmsCommentReply record, @Param("example") DmsCommentReplyExample example);

    int updateByExample(@Param("record") DmsCommentReply record, @Param("example") DmsCommentReplyExample example);

    int updateByPrimaryKeySelective(DmsCommentReply record);

    int updateByPrimaryKey(DmsCommentReply record);
}