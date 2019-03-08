package com.paris.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.paris.model.vo.Member;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MemberSearchController implements Initializable{

   @FXML private TextField number;
   @FXML private TextField name;
   @FXML private TextField phone;
   @FXML private TextField email;
   @FXML private TextField age;
   @FXML private TextField address;
   @FXML private TextField memberDate;
   @FXML private TextField point;
   @FXML private Button ok;

   private Stage dialogStage;

   private Member member;

   private int indexNum;

   static ArrayList<Member> list = new ArrayList<Member>();

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      
      //�ݱ��ư
      ok.setOnAction(e->ok(e));
   }
   
   public void setDialogStage(Stage dialogStage) {
      this.dialogStage = dialogStage;
   }
   
   //�˻��� ȸ�������� �ѷ��ش�...
   public void searchMember(Member phonesearch, int phoneIndex) {

      number.setText(phonesearch.getmNum());
      name.setText(phonesearch.getmName());
      age.setText(phonesearch.getmAge());
      phone.setText(phonesearch.getmPhone());
      email.setText(phonesearch.getmEmail());
      address.setText(phonesearch.getmAddress());
      memberDate.setText(phonesearch.getmDate());
      point.setText(phonesearch.getmPoint());

      // �Ѿ�� �ε��� �ѹ��� Ŭ�������� indexNum �� ����ش�
      // ���� : �ٸ� �޼ҵ忡�� ����ϱ� ����
      indexNum = phoneIndex;
   }
   
   private void ok(ActionEvent event) {
      dialogStage.close();
   }
}
