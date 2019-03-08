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
      // 수정완료 버튼 링크
      ok.setOnAction(e->ok(e));
      cancle.setOnAction(e->cancle(e));
   }

   public void setDialogStage(Stage dialogStage) {
      this.dialogStage = dialogStage;
   }

   // Member Controller 에서 넘어오는 선택된 회원과 선택된 인덱스 번호를 받아
   // TextField에 선택된 회원 정보들을 setText(지정)로 뿌려준다
   public void setMember(Member selectedMember, int select) {
      
      number.setText(selectedMember.getmNum());
      name.setText(selectedMember.getmName());
      age.setText(selectedMember.getmAge());
      phone.setText(selectedMember.getmPhone());
      email.setText(selectedMember.getmEmail());
      address.setText(selectedMember.getmAddress());
      memberDate.setText(selectedMember.getmDate());
      point.setText(selectedMember.getmPoint());
      
      

      // 넘어온 인덱스 넘버를 클래스변수 indexNum 에 담아준다
      // 이유 : 다른 메소드에서 사용하기 위해
      indexNum = select;
   }

   private void ok(ActionEvent event) {

      // 1. 현재 회원정보 파일을 불러와 ArrayList에 담는다
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

      // ArrayList에서 indexNum에 담긴 인덱스 번호를 사용하여
      // TextField에 입력된 값으로 아이템들을 수정한다 (.setpNum, .setpCate.... 등)
      
      list.get(indexNum).setmNum(number.getText());
      list.get(indexNum).setmName(name.getText());
      list.get(indexNum).setmPhone(phone.getText());
      list.get(indexNum).setmEmail(email.getText());
      list.get(indexNum).setmAge(age.getText());
      list.get(indexNum).setmAddress(address.getText());
      list.get(indexNum).setmDate(memberDate.getText());
      list.get(indexNum).setmPoint(point.getText());

      // 위에 변경된 ArrayList를 다시 재고관리 리스트 파일로 저장한다
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

      // 수정버튼 클릭시 위에 작업이 다 끝나고 나면 
      // 다이얼로그 창을 닫는다
      dialogStage.close();
   }
   private void cancle(ActionEvent event) {
      dialogStage.close();
   }
}

