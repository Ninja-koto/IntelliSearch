package com.model;

import java.io.Serializable;

public class GroupingView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private String articleName;

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleNameMain() {
		return articleNameMain;
	}

	public void setArticleNameMain(String articleNameMain) {
		this.articleNameMain = articleNameMain;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public double getRvCoeffcient() {
		return rvCoeffcient;
	}

	public void setRvCoeffcient(double rvCoeffcient) {
		this.rvCoeffcient = rvCoeffcient;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public boolean isSimilarity() {
		return similarity;
	}

	public void setSimilarity(boolean similarity) {
		this.similarity = similarity;
	}

	private String articleNameMain;

	private String userId;

	private String email;

	private int groupId;

	private double rvCoeffcient;

	private double threshold;

	private boolean similarity;
	
	public int getArticleIdChild() {
		return articleIdChild;
	}

	public void setArticleIdChild(int articleIdChild) {
		this.articleIdChild = articleIdChild;
	}

	public int getArticleIdParent() {
		return articleIdParent;
	}

	public void setArticleIdParent(int articleIdParent) {
		this.articleIdParent = articleIdParent;
	}
	
	private int articleIdChild;
	
	private int articleIdParent;

}
