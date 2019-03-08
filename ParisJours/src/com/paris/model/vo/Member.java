package com.paris.model.vo;


import javafx.beans.property.StringProperty;

public class Member {
	
	private String mNum;
	private String id;
	private String pass;
	private String mName;
	private String mAge;
	private String mPhone;
	private String mEmail;
	private String mAddress;
	private String mDate;
	private String mPoint;

	
	private StringProperty mNum2;
    private StringProperty mName2;
    private StringProperty mAge2;
    private StringProperty mPhone2;
    private StringProperty mEmail2;
    private StringProperty mAddress2;
    private StringProperty mDate2;
    private StringProperty mPoint2;
	
    public Member() {
		// TODO Auto-generated constructor stub
	}
    
	// 관리자
	public Member(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}
	
	
	// 회원등록
	public Member(String mNum, String mName, String mAge, String mPhone, String mEmail, String mAddress, String mDate,
			String mPoint) {
		super();
		this.mNum = mNum;
		this.mName = mName;
		this.mAge = mAge;
		this.mPhone = mPhone;
		this.mEmail = mEmail;
		this.mAddress = mAddress;
		this.mDate = mDate;
		this.mPoint = mPoint;
	}
	
	public Member(StringProperty mNum2, StringProperty mName2, StringProperty mAge2, StringProperty mPhone2,
			StringProperty mEmail2, StringProperty mAddress2, StringProperty mDate2, StringProperty mPoint2) {
		super();
		this.mNum2 = mNum2;
		this.mName2 = mName2;
		this.mAge2 = mAge2;
		this.mPhone2 = mPhone2;
		this.mEmail2 = mEmail2;
		this.mAddress2 = mAddress2;
		this.mDate2 = mDate2;
		this.mPoint2 = mPoint2;
	}

	public Member(String string, String string2, String string3, String string4, int i, String string5, String string6,
			boolean b) {
		// TODO Auto-generated constructor stub
	}

	public String getmNum() {
		return mNum;
	}

	public void setmNum(String mNum) {
		this.mNum = mNum;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmAge() {
		return mAge;
	}

	public void setmAge(String mAge) {
		this.mAge = mAge;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmPoint() {
		return mPoint;
	}

	public void setmPoint(String mPoint) {
		this.mPoint = mPoint;
	}

	public StringProperty getmNum2() {
		return mNum2;
	}

	public void setmNum2(StringProperty mNum2) {
		this.mNum2 = mNum2;
	}

	public StringProperty getmName2() {
		return mName2;
	}

	public void setmName2(StringProperty mName2) {
		this.mName2 = mName2;
	}

	public StringProperty getmAge2() {
		return mAge2;
	}

	public void setmAge2(StringProperty mAge2) {
		this.mAge2 = mAge2;
	}

	public StringProperty getmPhone2() {
		return mPhone2;
	}

	public void setmPhone2(StringProperty mPhone2) {
		this.mPhone2 = mPhone2;
	}

	public StringProperty getmEmail2() {
		return mEmail2;
	}

	public void setmEmail2(StringProperty mEmail2) {
		this.mEmail2 = mEmail2;
	}

	public StringProperty getmAddress2() {
		return mAddress2;
	}

	public void setmAddress2(StringProperty mAddress2) {
		this.mAddress2 = mAddress2;
	}

	public StringProperty getmDate2() {
		return mDate2;
	}

	public void setmDate2(StringProperty mDate2) {
		this.mDate2 = mDate2;
	}

	public StringProperty getmPoint2() {
		return mPoint2;
	}

	public void setmPoint2(StringProperty mPoint2) {
		this.mPoint2 = mPoint2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
