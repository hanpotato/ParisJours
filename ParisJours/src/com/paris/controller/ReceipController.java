package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Pay;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ReceipController implements Initializable{

   @FXML private Button cancel;
   @FXML private Button ok;
   
   @FXML private TextField paySum;
   @FXML private TextField phoneNum;
   @FXML private TextField cardNum;
   @FXML private Labeled mistakePhoneLabel;
   
   @FXML private RadioButton indiRadio;
   @FXML private RadioButton licenseRadio;
   @FXML private RadioButton phoneRadio;
   @FXML private RadioButton cardRadio;
   
   @FXML private ToggleGroup myGroup;
   @FXML private ToggleGroup myGroup2;
   
   static ArrayList<Pay> list = new ArrayList<Pay>();
   static ArrayList<Pay> paylist = new ArrayList<Pay>();
   static ArrayList<Pay> paymentlist = new ArrayList<Pay>();
   
   // ��â�� ��� ���̾�α� ���� ����
   private Stage dialogStage;
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      cancel.setOnAction(e->cancel(e));
      indiRadio.setOnAction(e->indiRadio(e));
      ok.setOnAction(e->ok(e));
   
      
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay.txt")))){
         paymentlist.clear();
         String str = null;
         while((str = br.readLine())!= null){
            Pay p = new Pay();
            st = new StringTokenizer(str,",");
            p.setItemList(st.nextToken());
            p.setPayTotal(st.nextToken());
            paymentlist.add(p);
         }
         
      }catch(IOException e) {
         e.printStackTrace();
      }

      // �ؽ�Ʈ�ʵ尡 �ѱݾ�(paySum), �����ݾ�(takeMoney), �Ž�����(change)
      paySum.setText(paymentlist.get(0).getPayTotal());
   }
   
   public void indiRadio(ActionEvent event)
   {
      ToggleGroup Mygroup = new ToggleGroup();

   }
   
   // ���̾�α� ���� �޼ҵ�
   public void setDialogStage(Stage dialogStage) {
         this.dialogStage = dialogStage;
   }
   
   private void ok(ActionEvent event)
   {
      if(!indiRadio.isSelected() && !licenseRadio.isSelected())
      {
         mistakePhoneLabel.setText("����/����� �����ϼ���.");
      }
      
      else if(!phoneRadio.isSelected() && !cardRadio.isSelected())
      {
         mistakePhoneLabel.setText("�ڵ���/ī�� �����ϼ���.");
      }
      
      else if(phoneNum.getText().equals("") && cardNum.getText().equals(""))
      {
         mistakePhoneLabel.setText("��ȣ�� �Է��ϼ���.");
      }

      else if(indiRadio.getToggleGroup()!= null || licenseRadio.getToggleGroup()!=null)
      {
         dialogStage.close();
      }
      
      else if(phoneNum.getText()!=null && cardNum.getText()!=null)
      {
         dialogStage.close();
      }
      
      else if(!phoneNum.getText().equals("") && !cardNum.getText().equals(""))
      {
         dialogStage.close();
      } else {
    	  Alert alert = new Alert(AlertType.INFORMATION);
  		alert.initOwner(dialogStage);
  		alert.setTitle("���ݿ����� ���� �Ϸ�");
  		alert.setHeaderText("");
  		alert.setContentText("���ݿ����� ������ �Ϸ� �Ǿ����ϴ�!!");
  		alert.showAndWait();
  		
  		dialogStage.close();
      }
     
   }
   
   
   private void cancel(ActionEvent event)
   {
      dialogStage.close();
   }
   
}
