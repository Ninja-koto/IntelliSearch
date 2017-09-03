package com.model;

public class FrequencyVO {

	private int freqId;

	public int getFreqId() {
		return freqId;
	}

	public void setFreqId(int freqId) {
		this.freqId = freqId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	private String articleName;

	private String tokenName;

	private int freq;

}
