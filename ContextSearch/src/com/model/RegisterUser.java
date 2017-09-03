package com.model;

import java.io.Serializable;
import java.util.List;

public class RegisterUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUSN() {
		return USN;
	}

	public void setUSN(String uSN) {
		USN = uSN;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherNumber() {
		return fatherNumber;
	}

	public void setFatherNumber(String fatherNumber) {
		this.fatherNumber = fatherNumber;
	}

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherNumber() {
		return motherNumber;
	}

	public void setMotherNumber(String motherNumber) {
		this.motherNumber = motherNumber;
	}

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public String getLocalGuardName() {
		return localGuardName;
	}

	public void setLocalGuardName(String localGuardName) {
		this.localGuardName = localGuardName;
	}

	public String getLocalGuardNumber() {
		return localGuardNumber;
	}

	public void setLocalGuardNumber(String localGuardNumber) {
		this.localGuardNumber = localGuardNumber;
	}

	public String getLocalGuardEmail() {
		return localGuardEmail;
	}

	public void setLocalGuardEmail(String localGuardEmail) {
		this.localGuardEmail = localGuardEmail;
	}

	public String getChallanNumber() {
		return challanNumber;
	}

	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}

	public String getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getResidenceStatus() {
		return residenceStatus;
	}

	public void setResidenceStatus(String residenceStatus) {
		this.residenceStatus = residenceStatus;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public List<String> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<String> subjectList) {
		this.subjectList = subjectList;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public List<String> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<String> activityList) {
		this.activityList = activityList;
	}

	public String getSemesterMarks() {
		return semesterMarks;
	}

	public void setSemesterMarks(String semesterMarks) {
		this.semesterMarks = semesterMarks;
	}

	public double getSemesterMarksDouble() {
		return semesterMarksDouble;
	}

	public void setSemesterMarksDouble(double semesterMarksDouble) {
		this.semesterMarksDouble = semesterMarksDouble;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private String name;

	private String password;

	private String email;

	private String gender;

	private String USN;

	private String degree;

	private String specification;

	private int loginType;

	private String address;

	private String city;

	private String state;

	private String pinCode;

	private String fatherName;

	private String fatherNumber;

	private String fatherEmail;

	private String motherName;

	private String motherNumber;

	private String motherEmail;

	private String localGuardName;

	private String localGuardNumber;

	private String localGuardEmail;

	private String admissionType;

	private String challanNumber;

	private String feePaid;

	private String residentialAddress;

	private String residenceStatus;

	private String semesterMarks;

	private String subjects;

	private List<String> subjectList;

	private String activity;

	private List<String> activityList;

	private double semesterMarksDouble;

	private String college;
	
	private String department;

}
