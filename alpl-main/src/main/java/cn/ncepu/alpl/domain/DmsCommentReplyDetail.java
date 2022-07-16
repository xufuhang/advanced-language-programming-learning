package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.DmsCommentReply;
import cn.ncepu.alpl.model.UmsUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
@author xufuhang
@date 2022/4/4-17:32
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class DmsCommentReplyDetail extends DmsCommentReply {

    private UmsUser fromUser;

    private UmsUser toUser;

    private Boolean isStars;

}
