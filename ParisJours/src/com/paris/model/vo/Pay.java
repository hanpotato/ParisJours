package com.paris.model.vo;

import javafx.beans.property.StringProperty;

public class Pay {
	
	// 카드결제
	private String cardNum;  // 카드번호
	private String cardMonth; // 유효기간 월
	private String cardYear; // 유효기간 년
	private String cardCate; // 할부 여부
	
	// 현금결제
	private String cashCount; // 인덱스번호
	private String cashTake; // 현금결제 시, 받은 금액
	
	// 카드, 현금 공통으로 들어갈 변수
	private String cardorcash; // 카드, 현금 구분
	private String PayTotal; // 계산 총금액
	private String itemList; // 상품내역 파일 경로
	private String payDate; // 결제한 시간
	
	// 영수증에 추가된 생성자
	private String Num;
	
	// 영수증관리 ObservableList 를 사용하기 위한 StringProperty변수들
	private StringProperty Num2;
	private StringProperty cardorcash2;
	private StringProperty payDate2;
	private StringProperty payTotal2;
	private StringProperty itemList2;
	
	public Pay() {
	}
	
	// 영수증 관리 Tableview 에 출력을 위한 생성자
	public Pay(StringProperty num2, StringProperty cardorcash2, StringProperty payDate2, StringProperty payTotal2,
			StringProperty itemList2) {
		super();
		Num2 = num2;
		this.cardorcash2 = cardorcash2;
		this.payDate2 = payDate2;
		this.payTotal2 = payTotal2;
		this.itemList2 = itemList2;
	}



	// 메인으로부터 상품내역과 총합을 받을 생성자
	public Pay(String itemList, String payTotal) {
		super();
		this.itemList = itemList;
		this. PayTotal = payTotal;
	}
	
	// 카드결제 생성자
	public Pay(String cardorcash, String cardNum, String cardMonth, String cardYear, String cardCate, String itemList, String payTotal,
			String payDate) {
		super();
		this.cardNum = cardNum;
		this.cardMonth = cardMonth;
		this.cardYear = cardYear;
		this.cardCate = cardCate;
		this.cardorcash = cardorcash;
		this.PayTotal = payTotal;
		this.itemList = itemList;
		this.payDate = payDate;
	}


	// 현금결제 생성자
	public Pay(String cardorcash, String cashCount, String cashTake, String payTotal, String itemList, String payDate) {
		super();
		this.cashCount = cashCount;
		this.cashTake = cashTake;
		this.cardorcash = cardorcash;
		this.PayTotal = payTotal;
		this.itemList = itemList;
		this.payDate = payDate;
	}
	
	// 영수증 출력 및 관리 생성자
	public Pay(String Num, String cardorcash, String payDate, String payTotal, String itemList ) {
		super();
		this.Num = Num;
		this.cardorcash = cardorcash;
		PayTotal = payTotal;
		this.itemList = itemList;
		this.payDate = payDate;
	}
	
	// -----------------------------------------------------------------------

	public String getCardNum() {
		return cardNum;
	}

	

	public String getNum() {
		return Num;
	}

	public void setNum(String num) {
		Num = num;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public String getCardCate() {
		return cardCate;
	}

	public void setCardCate(String cardCate) {
		this.cardCate = cardCate;
	}

	public String getCashCount() {
		return cashCount;
	}

	public void setCashCount(String cashCount) {
		this.cashCount = cashCount;
	}

	public String getCashTake() {
		return cashTake;
	}

	public void setCashTake(String cashTake) {
		this.cashTake = cashTake;
	}

	public String getCardorcash() {
		return cardorcash;
	}

	public void setCardorcash(String cardorcash) {
		this.cardorcash = cardorcash;
	}

	public String getPayTotal() {
		return PayTotal;
	}

	public void setPayTotal(String payTotal) {
		PayTotal = payTotal;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getItemList() {
		return itemList;
	}

	public void setItemList(String itemList) {
		this.itemList = itemList;
	}

	public StringProperty getNum2() {
		return Num2;
	}

	public void setNum2(StringProperty num2) {
		Num2 = num2;
	}

	public StringProperty getCardorcash2() {
		return cardorcash2;
	}

	public void setCardorcash2(StringProperty cardorcash2) {
		this.cardorcash2 = cardorcash2;
	}

	public StringProperty getPayDate2() {
		return payDate2;
	}

	public void setPayDate2(StringProperty payDate2) {
		this.payDate2 = payDate2;
	}

	public StringProperty getPayTotal2() {
		return payTotal2;
	}

	public void setPayTotal2(StringProperty payTotal2) {
		this.payTotal2 = payTotal2;
	}

	public StringProperty getItemList2() {
		return itemList2;
	}

	public void setItemList2(StringProperty itemList2) {
		this.itemList2 = itemList2;
	}
	
	
	
	
	
}
