package cn.ncepu.alpl.mapper;

import cn.ncepu.alpl.model.DmsUserCommentStarsRelation;
import cn.ncepu.alpl.model.DmsUserCommentStarsRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmsUserCommentStarsRelationMapper {
    long countByExample(DmsUserCommentStarsRelationExample example);

    int deleteByExample(DmsUserCommentStarsRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmsUserCommentStarsRelation record);

    int insertSelective(DmsUserCommentStarsRelation record);

    List<DmsUserCommentStarsRelation> selectByExample(DmsUserCommentStarsRelationExample example);

    DmsUserCommentStarsRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmsUserCommentStarsRelation record, @Param("example") DmsUserCommentStarsRelationExample example);

    int updateByExample(@Param("record") DmsUserCommentStarsRelation record, @Param("example") DmsUserCommentStarsRelationExample example);

    int updateByPrimaryKeySelective(DmsUserCommentStarsRelation record);

    int updateByPrimaryKey(DmsUserCommentStarsRelation record);
}