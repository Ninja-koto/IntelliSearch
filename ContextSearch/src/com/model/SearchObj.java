package com.model;

import java.io.Serializable;
import java.util.List;

public class SearchObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHiddenUrl() {
		return hiddenUrl;
	}

	public void setHiddenUrl(String hiddenUrl) {
		this.hiddenUrl = hiddenUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSearchId() {
		return searchId;
	}

	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}

	public double getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	private String userId;

	private String query;

	private String url;

	private String hiddenUrl;

	private String title;

	private String desc;

	private int searchId;

	private double featureVector;

	private List<String> keywords;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<String> authorList) {
		this.authorList = authorList;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Object getArticleId() {
		return articleId;
	}

	public void setArticleId(Object articleId) {
		this.articleId = articleId;
	}

	private List<String> authorList;

	private String publisher;
	
	private Object articleId;

}
