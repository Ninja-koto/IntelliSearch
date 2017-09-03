package com.model;

import java.io.Serializable;
import java.util.List;

public class BinaryCompareObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Long getTimeTakenBinary() {
		return timeTakenBinary;
	}

	public void setTimeTakenBinary(Long timeTakenBinary) {
		this.timeTakenBinary = timeTakenBinary;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public List<Integer> getCountSameList() {
		return countSameList;
	}

	public void setCountSameList(List<Integer> countSameList) {
		this.countSameList = countSameList;
	}

	public int getCountSame() {
		return countSame;
	}

	public void setCountSame(int countSame) {
		this.countSame = countSame;
	}

	public int getCountNotSame() {
		return countNotSame;
	}

	public void setCountNotSame(int countNotSame) {
		this.countNotSame = countNotSame;
	}

	public List<String> getTokenListRight() {
		return tokenListRight;
	}

	public void setTokenListRight(List<String> tokenListRight) {
		this.tokenListRight = tokenListRight;
	}

	public List<String> getTokenListLeft() {
		return tokenListLeft;
	}

	public void setTokenListLeft(List<String> tokenListLeft) {
		this.tokenListLeft = tokenListLeft;
	}

	private List<Integer> countSameList;

	private Long timeTakenBinary;

	private double accuracy;

	private int countSame;

	private int countNotSame;
	
	private List<String> tokenListRight;
	
	private List<String> tokenListLeft;

}
