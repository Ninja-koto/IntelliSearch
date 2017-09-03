package com.model;

import java.util.List;

public class CompareArticleRVVO {

	private List<String> intersectionWords;

	public List<String> getIntersectionWords() {
		return intersectionWords;
	}

	public void setIntersectionWords(List<String> intersectionWords) {
		this.intersectionWords = intersectionWords;
	}

	public int getSumLeftArticle() {
		return sumLeftArticle;
	}

	public void setSumLeftArticle(int sumLeftArticle) {
		this.sumLeftArticle = sumLeftArticle;
	}

	public int getSumRightArticle() {
		return sumRightArticle;
	}

	public void setSumRightArticle(int sumRightArticle) {
		this.sumRightArticle = sumRightArticle;
	}

	public double getMeanLeftArticle() {
		return meanLeftArticle;
	}

	public void setMeanLeftArticle(double meanLeftArticle) {
		this.meanLeftArticle = meanLeftArticle;
	}

	public double getMeanRightArticle() {
		return meanRightArticle;
	}

	public void setMeanRightArticle(double meanRightArticle) {
		this.meanRightArticle = meanRightArticle;
	}

	public double getStandardDevLeftArticle() {
		return standardDevLeftArticle;
	}

	public void setStandardDevLeftArticle(double standardDevLeftArticle) {
		this.standardDevLeftArticle = standardDevLeftArticle;
	}

	public double getStandardDevRightArtilce() {
		return standardDevRightArtilce;
	}

	public void setStandardDevRightArtilce(double standardDevRightArtilce) {
		this.standardDevRightArtilce = standardDevRightArtilce;
	}

	public double getRvCoeffcient() {
		return rvCoeffcient;
	}

	public void setRvCoeffcient(double rvCoeffcient) {
		this.rvCoeffcient = rvCoeffcient;
	}

	public boolean isSmilar() {
		return isSmilar;
	}

	public void setSmilar(boolean isSmilar) {
		this.isSmilar = isSmilar;
	}

	public List<String> getTokenNamesLeft() {
		return tokenNamesLeft;
	}

	public void setTokenNamesLeft(List<String> tokenNamesLeft) {
		this.tokenNamesLeft = tokenNamesLeft;
	}

	public List<String> getTokenNamesRight() {
		return tokenNamesRight;
	}

	public void setTokenNamesRight(List<String> tokenNamesRight) {
		this.tokenNamesRight = tokenNamesRight;
	}

	public BinaryCompareObject getBinaryCompareObject() {
		return binaryCompareObject;
	}

	public void setBinaryCompareObject(BinaryCompareObject binaryCompareObject) {
		this.binaryCompareObject = binaryCompareObject;
	}

	public Long getTimeTakenProposed() {
		return timeTakenProposed;
	}

	public void setTimeTakenProposed(Long timeTakenProposed) {
		this.timeTakenProposed = timeTakenProposed;
	}

	private int sumLeftArticle;

	private int sumRightArticle;

	private double meanLeftArticle;

	private double meanRightArticle;

	private double standardDevLeftArticle;
	private double standardDevRightArtilce;

	private double rvCoeffcient;

	private boolean isSmilar;

	private List<String> tokenNamesLeft;

	private List<String> tokenNamesRight;

	private BinaryCompareObject binaryCompareObject;

	private Long timeTakenProposed;

	private Long timeTakenPrevious;

	public Long getTimeTakenPrevious() {
		return timeTakenPrevious;
	}

	public void setTimeTakenPrevious(Long timeTakenPrevious) {
		this.timeTakenPrevious = timeTakenPrevious;
	}

	public double getAccuracyProposed() {
		return accuracyProposed;
	}

	public void setAccuracyProposed(double accuracyProposed) {
		this.accuracyProposed = accuracyProposed;
	}

	public double getAccuracyOld() {
		return accuracyOld;
	}

	public void setAccuracyOld(double accuracyOld) {
		this.accuracyOld = accuracyOld;
	}

	private double accuracyProposed;

	private double accuracyOld;

}
