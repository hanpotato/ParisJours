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

	// �α��� ��ư
	@FXML private Button loginBtn;

	// ���̵� �� ��й�ȣ �Է� �ʵ�
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
	    				System.out.println("������ �α��� ����");
	    				try {
	    					Parent login = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
	    					Scene scene = new Scene(login);
	    					Stage primaryStage = (Stage)loginBtn.getScene().getWindow(); // ���� ������ ��������
	    					primaryStage.setScene(scene);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}
	    			} else {
	    				System.out.println("�α��� ����");
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
				System.out.println("������ �α��� ����");
				try {
					Parent login = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(login);
					Stage primaryStage = (Stage)loginBtn.getScene().getWindow(); // ���� ������ ��������
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("�α��� ����");
				alert.setHeaderText("");
				alert.setContentText("�α��� ����");
				alert.showAndWait();
				System.out.println("�α��� ����");
			}
		}
	}


}
