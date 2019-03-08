package com.paris.model.vo;

import javafx.beans.property.StringProperty;

public class Product {
	
	private String pNum;
	private String pCate;
	private String pName;
	private String pImage;
	private String pDate;
	private String pPrice;
	private String pContent;
	private String pAmount;
	private String sumPay;
	
	private String payNum;
	private String payDate;
	private String payCate;
	private String payTotal;
	
	
	private StringProperty pNum2;
    private StringProperty pCate2;
    private StringProperty pName2;
    private StringProperty pImage2;
    private StringProperty pDate2;
    private StringProperty pPrice2;
    private StringProperty pContent2;
    private StringProperty pAmount2;
	
    private StringProperty payNum2;
    private StringProperty payDate2;
    private StringProperty payCate2;
    private StringProperty payTotal2;
    
	public String getSumPay() {
		return sumPay;
	}

	public void setSumPay(String sumPay) {
		this.sumPay = sumPay;
	}

	public Product() {
	}
	
	//결제리스트 필요한 생성자
	public Product(StringProperty pCate2, StringProperty pName2, StringProperty pAmount2, StringProperty pPrice2) {
		super();
		this.pCate2 = pCate2;
		this.pName2 = pName2;
		this.pPrice2 = pPrice2;
		this.pAmount2 = pAmount2;
	}

	// 결제 시 필요한 생성자
	public Product(String pCate, String pName, String pAmount, String pPrice) {
		super();
		this.pCate = pCate;
		this.pName = pName;
		this.pPrice = pPrice;
		this.pAmount = pAmount;
	}
	// <----------------------------- End
	
	

	public Product(String pNum, String pCate, String pName, String pImage, String pDate, String pPrice, String pContent,
			String pAmount) {
		super();
		this.pNum = pNum;
		this.pCate = pCate;
		this.pName = pName;
		this.pImage = pImage;
		this.pDate = pDate;
		this.pPrice = pPrice;
		this.pContent = pContent;
		this.pAmount = pAmount;
	}

	public Product(StringProperty pNum2, StringProperty pCate2, StringProperty pName2, StringProperty pImage2,
			StringProperty pDate2, StringProperty pPrice2, StringProperty pContent2, StringProperty pAmount2) {
		super();
		this.pNum2 = pNum2;
		this.pCate2 = pCate2;
		this.pName2 = pName2;
		this.pImage2 = pImage2;
		this.pDate2 = pDate2;
		this.pPrice2 = pPrice2;
		this.pContent2 = pContent2;
		this.pAmount2 = pAmount2;
	}

	public String getpNum() {
		return pNum;
	}

	public void setpNum(String pNum) {
		this.pNum = pNum;
	}

	public String getpCate() {
		return pCate;
	}

	public void setpCate(String pCate) {
		this.pCate = pCate;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpImage() {
		return pImage;
	}

	public void setpImage(String pImage) {
		this.pImage = pImage;
	}

	public String getpDate() {
		return pDate;
	}

	public void setpDate(String pDate) {
		this.pDate = pDate;
	}

	public String getpPrice() {
		return pPrice;
	}

	public void setpPrice(String pPrice) {
		this.pPrice = pPrice;
	}

	public String getpContent() {
		return pContent;
	}

	public void setpContent(String pContent) {
		this.pContent = pContent;
	}

	public String getpAmount() {
		return pAmount;
	}

	public void setpAmount(String pAmount) {
		this.pAmount = pAmount;
	}

	public StringProperty getpNum2() {
		return pNum2;
	}

	public void setpNum2(StringProperty pNum2) {
		this.pNum2 = pNum2;
	}

	public StringProperty getpCate2() {
		return pCate2;
	}

	public void setpCate2(StringProperty pCate2) {
		this.pCate2 = pCate2;
	}

	public StringProperty getpName2() {
		return pName2;
	}

	public void setpName2(StringProperty pName2) {
		this.pName2 = pName2;
	}

	public StringProperty getpImage2() {
		return pImage2;
	}

	public void setpImage2(StringProperty pImage2) {
		this.pImage2 = pImage2;
	}

	public StringProperty getpDate2() {
		return pDate2;
	}

	public void setpDate2(StringProperty pDate2) {
		this.pDate2 = pDate2;
	}

	public StringProperty getpPrice2() {
		return pPrice2;
	}

	public void setpPrice2(StringProperty pPrice2) {
		this.pPrice2 = pPrice2;
	}

	public StringProperty getpContent2() {
		return pContent2;
	}

	public void setpContent2(StringProperty pContent2) {
		this.pContent2 = pContent2;
	}

	public StringProperty getpAmount2() {
		return pAmount2;
	}

	public void setpAmount2(StringProperty pAmount2) {
		this.pAmount2 = pAmount2;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getPayCate() {
		return payCate;
	}

	public void setPayCate(String payCate) {
		this.payCate = payCate;
	}

	public String getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(String payTotal) {
		this.payTotal = payTotal;
	}

	public StringProperty getPayNum2() {
		return payNum2;
	}

	public void setPayNum2(StringProperty payNum2) {
		this.payNum2 = payNum2;
	}

	public StringProperty getPayDate2() {
		return payDate2;
	}

	public void setPayDate2(StringProperty payDate2) {
		this.payDate2 = payDate2;
	}

	public StringProperty getPayCate2() {
		return payCate2;
	}

	public void setPayCate2(StringProperty payCate2) {
		this.payCate2 = payCate2;
	}

	public StringProperty getPayTotal2() {
		return payTotal2;
	}

	public void setPayTotal2(StringProperty payTotal2) {
		this.payTotal2 = payTotal2;
	}

	
	
}
