package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.DmsUserCommentReplyStarsRelation;
import cn.ncepu.alpl.model.DmsUserCommentReplyStarsRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsUserCommentReplyStarsRelationMapper {
    long countByExample(DmsUserCommentReplyStarsRelationExample example);

    int deleteByExample(DmsUserCommentReplyStarsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmsUserCommentReplyStarsRelation record);

    int insertSelective(DmsUserCommentReplyStarsRelation record);

    List<DmsUserCommentReplyStarsRelation> selectByExample(DmsUserCommentReplyStarsRelationExample example);

    DmsUserCommentReplyStarsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmsUserCommentReplyStarsRelation record, @Param("example") DmsUserCommentReplyStarsRelationExample example);

    int updateByExample(@Param("record") DmsUserCommentReplyStarsRelation record, @Param("example") DmsUserCommentReplyStarsRelationExample example);

    int updateByPrimaryKeySelective(DmsUserCommentReplyStarsRelation record);

    int updateByPrimaryKey(DmsUserCommentReplyStarsRelation record);
}