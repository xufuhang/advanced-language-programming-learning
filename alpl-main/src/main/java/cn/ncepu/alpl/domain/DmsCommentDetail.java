package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.DmsComment;
import cn.ncepu.alpl.model.UmsUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
@author xufuhang
@date 2022/3/30-14:20
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class DmsCommentDetail extends DmsComment {

    /**
     * 发布该评论的用户，前端需要显示该用户的昵称
     */
    private UmsUser fromUser;

    /**
     * 当前请求数据的用户是否点赞了该评论
     */
    private Boolean isStars;
}
