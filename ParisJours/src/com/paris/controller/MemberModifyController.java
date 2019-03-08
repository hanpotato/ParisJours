package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Member;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MemberModifyController implements Initializable{

   @FXML private Label number;
   @FXML private TextField name;
   @FXML private TextField phone;
   @FXML private TextField email;
   @FXML private TextField age;
   @FXML private TextField address;
   @FXML private TextField memberDate;
   @FXML private TextField point;
   @FXML private Button cancle;
   @FXML private Button ok;

   private Stage dialogStage;

   private Member member;

   private int indexNum;

   static ArrayList<Member> list = new ArrayList<Member>();

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // �����Ϸ� ��ư ��ũ
      ok.setOnAction(e->ok(e));
      cancle.setOnAction(e->cancle(e));
   }

   public void setDialogStage(Stage dialogStage) {
      this.dialogStage = dialogStage;
   }

   // Member Controller ���� �Ѿ���� ���õ� ȸ���� ���õ� �ε��� ��ȣ�� �޾�
   // TextField�� ���õ� ȸ�� �������� setText(����)�� �ѷ��ش�
   public void setMember(Member selectedMember, int select) {
      
      number.setText(selectedMember.getmNum());
      name.setText(selectedMember.getmName());
      age.setText(selectedMember.getmAge());
      phone.setText(selectedMember.getmPhone());
      email.setText(selectedMember.getmEmail());
      address.setText(selectedMember.getmAddress());
      memberDate.setText(selectedMember.getmDate());
      point.setText(selectedMember.getmPoint());
      
      

      // �Ѿ�� �ε��� �ѹ��� Ŭ�������� indexNum �� ����ش�
      // ���� : �ٸ� �޼ҵ忡�� ����ϱ� ����
      indexNum = select;
   }

   private void ok(ActionEvent event) {

      // 1. ���� ȸ������ ������ �ҷ��� ArrayList�� ��´�
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Member/member.txt")))){
         list.clear();
         String str = null;
         while((str = br.readLine())!= null){
            Member m = new Member();
            st = new StringTokenizer(str,",");
            m.setmNum(st.nextToken());
            m.setmName(st.nextToken());
            m.setmAge(st.nextToken());
            m.setmPhone(st.nextToken());
            m.setmEmail(st.nextToken());
            m.setmAddress(st.nextToken());
            m.setmDate(st.nextToken());
            m.setmPoint(st.nextToken());
            
            list.add(m);
         }
      }catch(IOException e) {
         e.printStackTrace();
      }

      // ArrayList���� indexNum�� ��� �ε��� ��ȣ�� ����Ͽ�
      // TextField�� �Էµ� ������ �����۵��� �����Ѵ� (.setpNum, .setpCate.... ��)
      
      list.get(indexNum).setmNum(number.getText());
      list.get(indexNum).setmName(name.getText());
      list.get(indexNum).setmPhone(phone.getText());
      list.get(indexNum).setmEmail(email.getText());
      list.get(indexNum).setmAge(age.getText());
      list.get(indexNum).setmAddress(address.getText());
      list.get(indexNum).setmDate(memberDate.getText());
      list.get(indexNum).setmPoint(point.getText());

      // ���� ����� ArrayList�� �ٽ� ������ ����Ʈ ���Ϸ� �����Ѵ�
      try (FileWriter fw = new FileWriter("Member/member.txt")){
         for(int i=0; i<list.size(); i++){
            fw.write(list.get(i).getmNum());
            fw.write(",");
            fw.write(list.get(i).getmName());
            fw.write(",");
            fw.write(list.get(i).getmAge());
            fw.write(",");
            fw.write(list.get(i).getmPhone());
            fw.write(",");
            fw.write(list.get(i).getmEmail());
            fw.write(",");
            fw.write(list.get(i).getmAddress());
            fw.write(",");
            fw.write(list.get(i).getmDate());
            fw.write(",");
            fw.write(list.get(i).getmPoint());
            fw.write("\r\n");
         }
      }catch (Exception e) {
         e.printStackTrace();
      }

      // ������ư Ŭ���� ���� �۾��� �� ������ ���� 
      // ���̾�α� â�� �ݴ´�
      dialogStage.close();
   }
   private void cancle(ActionEvent event) {
      dialogStage.close();
   }
}

