package com.model;

public class LogLikelihoodVO {
	
	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public int getLogLikelihoodId() {
		return logLikelihoodId;
	}

	public void setLogLikelihoodId(int logLikelihoodId) {
		this.logLikelihoodId = logLikelihoodId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public double getExpectedFrequency() {
		return expectedFrequency;
	}

	public void setExpectedFrequency(double expectedFrequency) {
		this.expectedFrequency = expectedFrequency;
	}

	public double getLogLikelihood() {
		return logLikelihood;
	}

	public void setLogLikelihood(double logLikelihood) {
		this.logLikelihood = logLikelihood;
	}

	public double getFreq() {
		return freq;
	}

	public void setFreq(double freq) {
		this.freq = freq;
	}

	private String tokenName;
	
	private int logLikelihoodId;
	
	private String articleName;
	
	private double expectedFrequency;
	
	private double logLikelihood;
	
	private double freq;
	
	
	

}
