package com.model;

public class FeatureVO {

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public double getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double featureVector) {
		this.featureVector = featureVector;
	}

	public double getIdft() {
		return idft;
	}

	public void setIdft(double idft) {
		this.idft = idft;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getNoOfDocs() {
		return noOfDocs;
	}

	public void setNoOfDocs(int noOfDocs) {
		this.noOfDocs = noOfDocs;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	private int featureId;

	private String tokenName;

	private int freq;

	private double featureVector;

	private double idft;

	private int noOfDocs;

	private String userId;

	private String query;

	private String desc;
	
	private String articleName;

}
