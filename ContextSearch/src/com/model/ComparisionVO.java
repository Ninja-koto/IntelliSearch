package com.model;

import java.io.Serializable;

public class ComparisionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int iterationNo;
	

	private double proposedTime;

	private double previousAccuracy;

	private double proposedAccuracy;

	public int getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(int iterationNo) {
		this.iterationNo = iterationNo;
	}

	private double previousTime;

	public double getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(double previousTime) {
		this.previousTime = previousTime;
	}

	public double getProposedTime() {
		return proposedTime;
	}

	public void setProposedTime(double proposedTime) {
		this.proposedTime = proposedTime;
	}

	public double getPreviousAccuracy() {
		return previousAccuracy;
	}

	public void setPreviousAccuracy(double previousAccuracy) {
		this.previousAccuracy = previousAccuracy;
	}

	public double getProposedAccuracy() {
		return proposedAccuracy;
	}

	public void setProposedAccuracy(double proposedAccuracy) {
		this.proposedAccuracy = proposedAccuracy;
	}


}
