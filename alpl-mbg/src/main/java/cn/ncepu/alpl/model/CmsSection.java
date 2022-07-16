package cn.ncepu.alpl.model;

import java.io.Serializable;

public class CmsSection implements Serializable {
    private Integer id;

    /**
     * 创建该小节的用户id
     */
    private Integer createUserId;

    /**
     * 小节所属的章节id
     */
    private Integer chapterId;

    /**
     * 小节显示的标题
     */
    private String title;

    /**
     * 值越小顺序越靠前
     */
    private Short sort;

    /**
     * 小节下的所有评论数量
     */
    private Integer commentCount;

    /**
     * 小节下未被教师查看的新评论的数量
     */
    private Short newCommentCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Short getNewCommentCount() {
        return newCommentCount;
    }

    public void setNewCommentCount(Short newCommentCount) {
        this.newCommentCount = newCommentCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", title=").append(title);
        sb.append(", sort=").append(sort);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", newCommentCount=").append(newCommentCount);
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
        CmsSection other = (CmsSection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getChapterId() == null ? other.getChapterId() == null : this.getChapterId().equals(other.getChapterId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getCommentCount() == null ? other.getCommentCount() == null : this.getCommentCount().equals(other.getCommentCount()))
            && (this.getNewCommentCount() == null ? other.getNewCommentCount() == null : this.getNewCommentCount().equals(other.getNewCommentCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getChapterId() == null) ? 0 : getChapterId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCommentCount() == null) ? 0 : getCommentCount().hashCode());
        result = prime * result + ((getNewCommentCount() == null) ? 0 : getNewCommentCount().hashCode());
        return result;
    }
}