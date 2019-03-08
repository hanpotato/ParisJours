package com.paris.model.vo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Notice {
	
	private String no;
	private String cate;
	private String title;
	private String content;
	private String date;
	private String password;
	
	private StringProperty no2;
    private StringProperty cate2;
    private StringProperty title2;
    private StringProperty content2;
    private StringProperty date2;
    private StringProperty password2;
    private StringProperty nCount2;
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}

	public Notice(String no, String cate, String title, String content, String date, String password) {
		super();
		this.no = no;
		this.cate = cate;
		this.title = title;
		this.content = content;
		this.date = date;
		this.password = password;
	}

	public Notice(StringProperty no2, StringProperty cate2, StringProperty title2, StringProperty content2,
			StringProperty date2, StringProperty password2) {
		super();
		this.no2 = no2;
		this.cate2 = cate2;
		this.title2 = title2;
		this.content2 = content2;
		this.date2 = date2;
		this.password2 = password2;
	}

	public Notice(StringProperty content2) {
		super();
		this.content2 = content2;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public StringProperty getNo2() {
		return no2;
	}

	public void setNo2(StringProperty no2) {
		this.no2 = no2;
	}

	public StringProperty getCate2() {
		return cate2;
	}

	public void setCate2(StringProperty cate2) {
		this.cate2 = cate2;
	}

	public StringProperty getTitle2() {
		return title2;
	}

	public void setTitle2(StringProperty title2) {
		this.title2 = title2;
	}

	public StringProperty getContent2() {
		return content2;
	}

	public void setContent2(StringProperty content2) {
		this.content2 = content2;
	}

	public StringProperty getDate2() {
		return date2;
	}

	public void setDate2(StringProperty date2) {
		this.date2 = date2;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public StringProperty getPassword2() {
		return password2;
	}



	public void setPassword2(StringProperty password2) {
		this.password2 = password2;
	}
	
	
}
