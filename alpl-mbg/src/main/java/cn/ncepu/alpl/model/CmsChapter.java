package cn.ncepu.alpl.model;

import java.io.Serializable;

public class CmsChapter implements Serializable {
    private Integer id;

    /**
     * 创建该章节的用户id
     */
    private Integer createUserId;

    /**
     * 该章节所属的模块id
     */
    private Integer moduleId;

    /**
     * 章节显示的标题
     */
    private String title;

    /**
     * 值越小顺序越靠前
     */
    private Short sort;

    /**
     * 该章节下未被教师查看的新评论的数量
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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
        sb.append(", moduleId=").append(moduleId);
        sb.append(", title=").append(title);
        sb.append(", sort=").append(sort);
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
        CmsChapter other = (CmsChapter) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getNewCommentCount() == null ? other.getNewCommentCount() == null : this.getNewCommentCount().equals(other.getNewCommentCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getNewCommentCount() == null) ? 0 : getNewCommentCount().hashCode());
        return result;
    }
}