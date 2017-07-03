/**
 * <p>Title: ${file_name}</p>
 * <p>Description:${project_name} </p>
 * <p>Copyright:${date} </p>
 * <p>Company: rongshu</p>
 * <p>author: ${user}</p>
 * <p>package: ${package_name}</p>
 * @version v1.0.0
 */
package com.rongcapital.usercenter.provider.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UcOrgMappingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UcOrgMappingExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNull() {
            addCriterion("LOGIN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("LOGIN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("LOGIN_NAME =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("LOGIN_NAME <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("LOGIN_NAME >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("LOGIN_NAME >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("LOGIN_NAME <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("LOGIN_NAME <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("LOGIN_NAME like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("LOGIN_NAME not like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("LOGIN_NAME in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("LOGIN_NAME not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("LOGIN_NAME between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("LOGIN_NAME not between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualIsNull() {
            addCriterion("ORG_CODE_ACTUAL is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualIsNotNull() {
            addCriterion("ORG_CODE_ACTUAL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualEqualTo(String value) {
            addCriterion("ORG_CODE_ACTUAL =", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualNotEqualTo(String value) {
            addCriterion("ORG_CODE_ACTUAL <>", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualGreaterThan(String value) {
            addCriterion("ORG_CODE_ACTUAL >", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_ACTUAL >=", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualLessThan(String value) {
            addCriterion("ORG_CODE_ACTUAL <", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_ACTUAL <=", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualLike(String value) {
            addCriterion("ORG_CODE_ACTUAL like", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualNotLike(String value) {
            addCriterion("ORG_CODE_ACTUAL not like", value, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualIn(List<String> values) {
            addCriterion("ORG_CODE_ACTUAL in", values, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualNotIn(List<String> values) {
            addCriterion("ORG_CODE_ACTUAL not in", values, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualBetween(String value1, String value2) {
            addCriterion("ORG_CODE_ACTUAL between", value1, value2, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE_ACTUAL not between", value1, value2, "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualIsNull() {
            addCriterion("ORG_CODE_VIRTUAL is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualIsNotNull() {
            addCriterion("ORG_CODE_VIRTUAL is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualEqualTo(String value) {
            addCriterion("ORG_CODE_VIRTUAL =", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualNotEqualTo(String value) {
            addCriterion("ORG_CODE_VIRTUAL <>", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualGreaterThan(String value) {
            addCriterion("ORG_CODE_VIRTUAL >", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_VIRTUAL >=", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualLessThan(String value) {
            addCriterion("ORG_CODE_VIRTUAL <", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_VIRTUAL <=", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualLike(String value) {
            addCriterion("ORG_CODE_VIRTUAL like", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualNotLike(String value) {
            addCriterion("ORG_CODE_VIRTUAL not like", value, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualIn(List<String> values) {
            addCriterion("ORG_CODE_VIRTUAL in", values, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualNotIn(List<String> values) {
            addCriterion("ORG_CODE_VIRTUAL not in", values, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualBetween(String value1, String value2) {
            addCriterion("ORG_CODE_VIRTUAL between", value1, value2, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE_VIRTUAL not between", value1, value2, "orgCodeVirtual");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("CREATED_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("CREATED_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("CREATED_TIME =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("CREATED_TIME <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("CREATED_TIME >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_TIME >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("CREATED_TIME <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATED_TIME <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("CREATED_TIME in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("CREATED_TIME not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("CREATED_TIME between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATED_TIME not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNull() {
            addCriterion("UPDATED_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNotNull() {
            addCriterion("UPDATED_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeEqualTo(Date value) {
            addCriterion("UPDATED_TIME =", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotEqualTo(Date value) {
            addCriterion("UPDATED_TIME <>", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThan(Date value) {
            addCriterion("UPDATED_TIME >", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATED_TIME >=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThan(Date value) {
            addCriterion("UPDATED_TIME <", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATED_TIME <=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIn(List<Date> values) {
            addCriterion("UPDATED_TIME in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotIn(List<Date> values) {
            addCriterion("UPDATED_TIME not in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATED_TIME between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATED_TIME not between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andLoginNameLikeInsensitive(String value) {
            addCriterion("upper(LOGIN_NAME) like", value.toUpperCase(), "loginName");
            return (Criteria) this;
        }

        public Criteria andOrgCodeActualLikeInsensitive(String value) {
            addCriterion("upper(ORG_CODE_ACTUAL) like", value.toUpperCase(), "orgCodeActual");
            return (Criteria) this;
        }

        public Criteria andOrgCodeVirtualLikeInsensitive(String value) {
            addCriterion("upper(ORG_CODE_VIRTUAL) like", value.toUpperCase(), "orgCodeVirtual");
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