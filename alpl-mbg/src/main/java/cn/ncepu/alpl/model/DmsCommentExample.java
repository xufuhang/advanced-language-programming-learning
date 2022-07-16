package cn.ncepu.alpl.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DmsCommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DmsCommentExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNull() {
            addCriterion("from_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNotNull() {
            addCriterion("from_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdEqualTo(Integer value) {
            addCriterion("from_user_id =", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotEqualTo(Integer value) {
            addCriterion("from_user_id <>", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThan(Integer value) {
            addCriterion("from_user_id >", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("from_user_id >=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThan(Integer value) {
            addCriterion("from_user_id <", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("from_user_id <=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIn(List<Integer> values) {
            addCriterion("from_user_id in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotIn(List<Integer> values) {
            addCriterion("from_user_id not in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdBetween(Integer value1, Integer value2) {
            addCriterion("from_user_id between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("from_user_id not between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andSectionIdIsNull() {
            addCriterion("section_id is null");
            return (Criteria) this;
        }

        public Criteria andSectionIdIsNotNull() {
            addCriterion("section_id is not null");
            return (Criteria) this;
        }

        public Criteria andSectionIdEqualTo(Integer value) {
            addCriterion("section_id =", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdNotEqualTo(Integer value) {
            addCriterion("section_id <>", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdGreaterThan(Integer value) {
            addCriterion("section_id >", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("section_id >=", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdLessThan(Integer value) {
            addCriterion("section_id <", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdLessThanOrEqualTo(Integer value) {
            addCriterion("section_id <=", value, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdIn(List<Integer> values) {
            addCriterion("section_id in", values, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdNotIn(List<Integer> values) {
            addCriterion("section_id not in", values, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdBetween(Integer value1, Integer value2) {
            addCriterion("section_id between", value1, value2, "sectionId");
            return (Criteria) this;
        }

        public Criteria andSectionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("section_id not between", value1, value2, "sectionId");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andStarsIsNull() {
            addCriterion("stars is null");
            return (Criteria) this;
        }

        public Criteria andStarsIsNotNull() {
            addCriterion("stars is not null");
            return (Criteria) this;
        }

        public Criteria andStarsEqualTo(Integer value) {
            addCriterion("stars =", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotEqualTo(Integer value) {
            addCriterion("stars <>", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsGreaterThan(Integer value) {
            addCriterion("stars >", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsGreaterThanOrEqualTo(Integer value) {
            addCriterion("stars >=", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsLessThan(Integer value) {
            addCriterion("stars <", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsLessThanOrEqualTo(Integer value) {
            addCriterion("stars <=", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsIn(List<Integer> values) {
            addCriterion("stars in", values, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotIn(List<Integer> values) {
            addCriterion("stars not in", values, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsBetween(Integer value1, Integer value2) {
            addCriterion("stars between", value1, value2, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotBetween(Integer value1, Integer value2) {
            addCriterion("stars not between", value1, value2, "stars");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountIsNull() {
            addCriterion("comment_reply_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountIsNotNull() {
            addCriterion("comment_reply_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountEqualTo(Integer value) {
            addCriterion("comment_reply_count =", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountNotEqualTo(Integer value) {
            addCriterion("comment_reply_count <>", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountGreaterThan(Integer value) {
            addCriterion("comment_reply_count >", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_reply_count >=", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountLessThan(Integer value) {
            addCriterion("comment_reply_count <", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_reply_count <=", value, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountIn(List<Integer> values) {
            addCriterion("comment_reply_count in", values, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountNotIn(List<Integer> values) {
            addCriterion("comment_reply_count not in", values, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_reply_count between", value1, value2, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andCommentReplyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_reply_count not between", value1, value2, "commentReplyCount");
            return (Criteria) this;
        }

        public Criteria andToppingSortIsNull() {
            addCriterion("topping_sort is null");
            return (Criteria) this;
        }

        public Criteria andToppingSortIsNotNull() {
            addCriterion("topping_sort is not null");
            return (Criteria) this;
        }

        public Criteria andToppingSortEqualTo(Short value) {
            addCriterion("topping_sort =", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortNotEqualTo(Short value) {
            addCriterion("topping_sort <>", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortGreaterThan(Short value) {
            addCriterion("topping_sort >", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortGreaterThanOrEqualTo(Short value) {
            addCriterion("topping_sort >=", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortLessThan(Short value) {
            addCriterion("topping_sort <", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortLessThanOrEqualTo(Short value) {
            addCriterion("topping_sort <=", value, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortIn(List<Short> values) {
            addCriterion("topping_sort in", values, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortNotIn(List<Short> values) {
            addCriterion("topping_sort not in", values, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortBetween(Short value1, Short value2) {
            addCriterion("topping_sort between", value1, value2, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andToppingSortNotBetween(Short value1, Short value2) {
            addCriterion("topping_sort not between", value1, value2, "toppingSort");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameIsNull() {
            addCriterion("from_user_role_name is null");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameIsNotNull() {
            addCriterion("from_user_role_name is not null");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameEqualTo(String value) {
            addCriterion("from_user_role_name =", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameNotEqualTo(String value) {
            addCriterion("from_user_role_name <>", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameGreaterThan(String value) {
            addCriterion("from_user_role_name >", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameGreaterThanOrEqualTo(String value) {
            addCriterion("from_user_role_name >=", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameLessThan(String value) {
            addCriterion("from_user_role_name <", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameLessThanOrEqualTo(String value) {
            addCriterion("from_user_role_name <=", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameLike(String value) {
            addCriterion("from_user_role_name like", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameNotLike(String value) {
            addCriterion("from_user_role_name not like", value, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameIn(List<String> values) {
            addCriterion("from_user_role_name in", values, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameNotIn(List<String> values) {
            addCriterion("from_user_role_name not in", values, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameBetween(String value1, String value2) {
            addCriterion("from_user_role_name between", value1, value2, "fromUserRoleName");
            return (Criteria) this;
        }

        public Criteria andFromUserRoleNameNotBetween(String value1, String value2) {
            addCriterion("from_user_role_name not between", value1, value2, "fromUserRoleName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}