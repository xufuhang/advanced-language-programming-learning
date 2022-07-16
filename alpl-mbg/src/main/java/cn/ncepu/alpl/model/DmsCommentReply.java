package cn.ncepu.alpl.model;

import java.io.Serializable;
import java.util.Date;

public class DmsCommentReply implements Serializable {
    private Integer id;

    /**
     * 发布回复的用户
     */
    private Integer fromUserId;

    /**
     * 回复的目标用户
     */
    private Integer toUserId;

    /**
     * 回复所在的评论的id
     */
    private Integer commentId;

    /**
     * 回复的内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 点赞数
     */
    private Integer stars;

    /**
     * 置顶排序，值越大则位置越靠前，默认为0
     */
    private Short toppingSort;

    /**
     * 同评论表
     */
    private String fromUserRoleName;

    /**
     * 用于页面显示被回复用户的角色名
     */
    private String toUserRoleName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Short getToppingSort() {
        return toppingSort;
    }

    public void setToppingSort(Short toppingSort) {
        this.toppingSort = toppingSort;
    }

    public String getFromUserRoleName() {
        return fromUserRoleName;
    }

    public void setFromUserRoleName(String fromUserRoleName) {
        this.fromUserRoleName = fromUserRoleName;
    }

    public String getToUserRoleName() {
        return toUserRoleName;
    }

    public void setToUserRoleName(String toUserRoleName) {
        this.toUserRoleName = toUserRoleName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", commentId=").append(commentId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", stars=").append(stars);
        sb.append(", toppingSort=").append(toppingSort);
        sb.append(", fromUserRoleName=").append(fromUserRoleName);
        sb.append(", toUserRoleName=").append(toUserRoleName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DmsCommentReply other = (DmsCommentReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStars() == null ? other.getStars() == null : this.getStars().equals(other.getStars()))
            && (this.getToppingSort() == null ? other.getToppingSort() == null : this.getToppingSort().equals(other.getToppingSort()))
            && (this.getFromUserRoleName() == null ? other.getFromUserRoleName() == null : this.getFromUserRoleName().equals(other.getFromUserRoleName()))
            && (this.getToUserRoleName() == null ? other.getToUserRoleName() == null : this.getToUserRoleName().equals(other.getToUserRoleName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStars() == null) ? 0 : getStars().hashCode());
        result = prime * result + ((getToppingSort() == null) ? 0 : getToppingSort().hashCode());
        result = prime * result + ((getFromUserRoleName() == null) ? 0 : getFromUserRoleName().hashCode());
        result = prime * result + ((getToUserRoleName() == null) ? 0 : getToUserRoleName().hashCode());
        return result;
    }
}