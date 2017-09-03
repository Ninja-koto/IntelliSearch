package com.model;

import java.io.Serializable;

public class ArticleModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	private String articleName;
	
	private String articleDesc;
	
	private String userId;
	
	private int articleId;

}
