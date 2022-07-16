package cn.ncepu.alpl.model;

import java.io.Serializable;
import java.util.Date;

public class DmsComment implements Serializable {
    private Integer id;

    /**
     * 发布该评论的用户id
     */
    private Integer fromUserId;

    /**
     * 评论所在的小节id
     */
    private Integer sectionId;

    /**
     * 评论内容
     */
    private String content;

    private Date createTime;

    private Integer stars;

    /**
     * 评论的回复数量
     */
    private Integer commentReplyCount;

    /**
     * 置顶排序，值越大则位置越靠前，默认为0
     */
    private Short toppingSort;

    /**
     * 用于界面显示标签
     */
    private String fromUserRoleName;

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

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
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

    public Integer getCommentReplyCount() {
        return commentReplyCount;
    }

    public void setCommentReplyCount(Integer commentReplyCount) {
        this.commentReplyCount = commentReplyCount;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", stars=").append(stars);
        sb.append(", commentReplyCount=").append(commentReplyCount);
        sb.append(", toppingSort=").append(toppingSort);
        sb.append(", fromUserRoleName=").append(fromUserRoleName);
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
        DmsComment other = (DmsComment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getSectionId() == null ? other.getSectionId() == null : this.getSectionId().equals(other.getSectionId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStars() == null ? other.getStars() == null : this.getStars().equals(other.getStars()))
            && (this.getCommentReplyCount() == null ? other.getCommentReplyCount() == null : this.getCommentReplyCount().equals(other.getCommentReplyCount()))
            && (this.getToppingSort() == null ? other.getToppingSort() == null : this.getToppingSort().equals(other.getToppingSort()))
            && (this.getFromUserRoleName() == null ? other.getFromUserRoleName() == null : this.getFromUserRoleName().equals(other.getFromUserRoleName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getSectionId() == null) ? 0 : getSectionId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStars() == null) ? 0 : getStars().hashCode());
        result = prime * result + ((getCommentReplyCount() == null) ? 0 : getCommentReplyCount().hashCode());
        result = prime * result + ((getToppingSort() == null) ? 0 : getToppingSort().hashCode());
        result = prime * result + ((getFromUserRoleName() == null) ? 0 : getFromUserRoleName().hashCode());
        return result;
    }
}