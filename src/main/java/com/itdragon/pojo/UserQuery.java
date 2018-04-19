package com.itdragon.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询专用对象
 * @author sang
 *
 */
public class UserQuery{

	private Integer userId;
	private String wxOpenId;
	private String wxName;
	private String account;
	private String passWord;
	private String email;
	private String code;
	private Integer role;
	private String name;
	private Integer age;
	private String img;
	private Integer gender;
	private String evaluation;
	private String identityId;
	private String bankCard;
	private String telephone;
	private Integer isValid;
	private String comment;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getWxName() {
		return wxName;
	}
	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


	/***********查询字段指定*************************************/
	private String fields;
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}

	/***********查询字段Like*************************************/
	private boolean userIdLike;
	public boolean isUserIdLike() {
		return userIdLike;
	}
	public void setUserIdLike(boolean userIdLike) {
		this.userIdLike = userIdLike;
	}
	private boolean wxOpenIdLike;
	public boolean isWxOpenIdLike() {
		return wxOpenIdLike;
	}
	public void setWxOpenIdLike(boolean wxOpenIdLike) {
		this.wxOpenIdLike = wxOpenIdLike;
	}
	private boolean wxNameLike;
	public boolean isWxNameLike() {
		return wxNameLike;
	}
	public void setWxNameLike(boolean wxNameLike) {
		this.wxNameLike = wxNameLike;
	}
	private boolean accountLike;
	public boolean isAccountLike() {
		return accountLike;
	}
	public void setAccountLike(boolean accountLike) {
		this.accountLike = accountLike;
	}
	private boolean passWordLike;
	public boolean isPassWordLike() {
		return passWordLike;
	}
	public void setPassWordLike(boolean passWordLike) {
		this.passWordLike = passWordLike;
	}
	private boolean roleLike;
	public boolean isRoleLike() {
		return roleLike;
	}
	public void setRoleLike(boolean roleLike) {
		this.roleLike = roleLike;
	}
	private boolean nameLike;
	public boolean isNameLike() {
		return nameLike;
	}
	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}
	private boolean ageLike;
	public boolean isAgeLike() {
		return ageLike;
	}
	public void setAgeLike(boolean ageLike) {
		this.ageLike = ageLike;
	}
	private boolean imgLike;
	public boolean isImgLike() {
		return imgLike;
	}
	public void setImgLike(boolean imgLike) {
		this.imgLike = imgLike;
	}
	private boolean genderLike;
	public boolean isGenderLike() {
		return genderLike;
	}
	public void setGenderLike(boolean genderLike) {
		this.genderLike = genderLike;
	}
	private boolean evaluationLike;
	public boolean isEvaluationLike() {
		return evaluationLike;
	}
	public void setEvaluationLike(boolean evaluationLike) {
		this.evaluationLike = evaluationLike;
	}
	private boolean identityIdLike;
	public boolean isIdentityIdLike() {
		return identityIdLike;
	}
	public void setIdentityIdLike(boolean identityIdLike) {
		this.identityIdLike = identityIdLike;
	}
	private boolean bankCardLike;
	public boolean isBankCardLike() {
		return bankCardLike;
	}
	public void setBankCardLike(boolean bankCardLike) {
		this.bankCardLike = bankCardLike;
	}
	private boolean telephoneLike;
	public boolean isTelephoneLike() {
		return telephoneLike;
	}
	public void setTelephoneLike(boolean telephoneLike) {
		this.telephoneLike = telephoneLike;
	}
	private boolean isValidLike;
	public boolean isIsValidLike() {
		return isValidLike;
	}
	public void setIsValidLike(boolean isValidLike) {
		this.isValidLike = isValidLike;
	}
	private boolean commentLike;
	public boolean isCommentLike() {
		return commentLike;
	}
	public void setCommentLike(boolean commentLike) {
		this.commentLike = commentLike;
	}
	
	/***********order by *************************************/
	public class FieldOrder{
		private String field;  //id , name  imgUrl
		private String order;  // desc asc
		
		public FieldOrder(String field, String order) {
			super();
			this.field = field;
			this.order = order;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		
	}
	//orderby 集合
	private List<FieldOrder> fieldOrders = new ArrayList<FieldOrder>();
	
	//按照Id排序
	public void orderbyId(boolean isAsc){
		fieldOrders.add(new FieldOrder("id",isAsc == true ? "asc" : "desc"));
	}

}
