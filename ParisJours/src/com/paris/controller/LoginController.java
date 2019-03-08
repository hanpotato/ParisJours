package com.paris.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.paris.model.vo.Member;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	private Stage primaryStage;

	// 로그인 버튼
	@FXML private Button loginBtn;

	// 아이디 및 비밀번호 입력 필드
	@FXML private TextField mId;
	@FXML private PasswordField mPw;

	private Stage dialogStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		
		loginBtn.setOnAction(e->loginBtn(e));
		mPw.setOnKeyPressed(ke -> {
	        KeyCode keyCode = ke.getCode();
	        if (keyCode.equals(KeyCode.ENTER)) {
	        	list.clear();
	    		list.add(new Member("admin", "admin"));
	    		
	        	for(int i=0;i<list.size();i++) {
	    			if(mId.getText().equals(list.get(i).getId()) && mPw.getText().equals(list.get(i).getPass()) ) {
	    				System.out.println("관리자 로그인 성공");
	    				try {
	    					Parent login = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
	    					Scene scene = new Scene(login);
	    					Stage primaryStage = (Stage)loginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
	    					primaryStage.setScene(scene);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}
	    			} else {
	    				System.out.println("로그인 실패");
	    			}
	    		}
	        }});
	}

	static ArrayList<Member> list = new ArrayList<Member>();
	private void loginBtn(ActionEvent event) {
		list.clear();
		list.add(new Member("admin", "admin"));
		
		for(int i=0;i<list.size();i++) {
			if(mId.getText().equals(list.get(i).getId()) && mPw.getText().equals(list.get(i).getPass()) ) {
				System.out.println("관리자 로그인 성공");
				try {
					Parent login = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(login);
					Stage primaryStage = (Stage)loginBtn.getScene().getWindow(); // 현재 윈도우 가져오기
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("");
				alert.setContentText("로그인 실패");
				alert.showAndWait();
				System.out.println("로그인 실패");
			}
		}
	}


}
