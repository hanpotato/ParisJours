package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Member;
import com.paris.model.vo.Pay;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CardController implements Initializable{

	@FXML private Button main;
	@FXML private Button ok;
	@FXML private Button cancel;

	@FXML private TextField cardNum;
	@FXML private TextField month;
	@FXML private TextField year;
	@FXML private TextField totalPay;
	@FXML private TextField paySum;

	// 우측 멤버쉽 컨트롤
	@FXML private TextField phone;
	@FXML private Button searchBtn;
	@FXML private Label point;
	@FXML private Label name;
	@FXML private Label savePoint;
	@FXML private TextField usePoint;
	@FXML private Button usePointBtn;
	@FXML private Button pointBtn;
	// ---------------------------------->>

	@FXML private ChoiceBox<String> cate;

	// 영수증 ArrayList
	static ArrayList<Pay> printList = new ArrayList<Pay>();

	static ArrayList<Pay> list = new ArrayList<Pay>();
	static ArrayList<Pay> payList = new ArrayList<Pay>();

	// 포인트적립을 위한 Member ArrayList
	static ArrayList<Member> memberList = new ArrayList<Member>();

	ObservableList<String> option = FXCollections.observableArrayList("선택", "일시불", "3개월", "6개월");

	// 새창을 띄울 다이얼로그 변수 선언
	private Stage dialogStage;

	String payitemList = "";

	// 포인트 적립 및 사용을 최초 1회만 가능하도록
	int pointCount = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 회원검색을 위해 Member 파일 불러옴
		// 멤버쉽 기능을 위해 멤버데이터를 불러와 ArrayList (memberList) 에 담아둠
		StringTokenizer stm = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Member/member.txt")))){
			memberList.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Member m = new Member();
				stm = new StringTokenizer(str,",");
				m.setmNum(stm.nextToken());
				m.setmName(stm.nextToken());
				m.setmAge(stm.nextToken());
				m.setmPhone(stm.nextToken());
				m.setmEmail(stm.nextToken());
				m.setmAddress(stm.nextToken());
				m.setmDate(stm.nextToken());
				m.setmPoint(stm.nextToken());
				memberList.add(m);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		searchBtn.setOnAction(e->searchBtn(e));
		pointBtn.setOnAction(e->pointBtn(e));

		usePointBtn.setOnAction(e->usePointBtn(e));

		cate.setValue("선택");
		cate.setItems(option);

		ok.setOnAction(e->ok(e));
		cancel.setOnAction(e->cancel(e));

		// 결제리스트로부터 넘어온 상품들 및 총합계금액
		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay.txt")))) {
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Pay p = new Pay();
				st = new StringTokenizer(str,",");
				p.setItemList(st.nextToken());
				p.setPayTotal(st.nextToken());
				list.add(p);
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

		// list (Product형) 로부터 받은 결제총액
		int sum = Integer.parseInt(list.get(0).getPayTotal());
		paySum.setText(String.valueOf(sum));

		// 적립될 포인트 (결제금액에 0.03%)
		int totalpay = Integer.parseInt(list.get(0).getPayTotal());
		savePoint.setText(String.valueOf((int)(totalpay*0.03)));

	}

	// 멤버쉽 -> 회원검색 버튼
	private void searchBtn(ActionEvent event) {
		for(int i=0;i<memberList.size();i++) {
			if(memberList.get(i).getmPhone().equals(phone.getText())) {
				name.setText(memberList.get(i).getmName());
				point.setText(memberList.get(i).getmPoint());
			}
		}
	}

	// 포인트 사용버튼
	// 총 합계 금액 - 포인트 사용금액 계산!!
	private void usePointBtn(ActionEvent event) {
		if(pointCount<1) {
			int totalpay = Integer.parseInt(list.get(0).getPayTotal());
			int usepoint = Integer.parseInt(usePoint.getText());
			paySum.setText(String.valueOf((totalpay-usepoint)));
			list.get(0).setPayTotal(String.valueOf((totalpay-usepoint)));

			for(int i=0;i<memberList.size();i++) {
				if(memberList.get(i).getmPhone().equals(phone.getText())) {
					if(Integer.parseInt(memberList.get(i).getmPoint())>1000) {
						memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint()))-
								(Integer.parseInt(usePoint.getText()))));
						System.out.println(memberList.get(i).getmName()+"님 포인트 [ " + usePoint.getText() + " ] 점 사용!");
					} else {
						usePoint.setText("포인트가 부족합니다");
						Alert alert = new Alert(AlertType.ERROR);
						alert.initOwner(dialogStage);
						alert.setTitle("포인트 사용 불가");
						alert.setHeaderText("");
						alert.setContentText("포인트는 1000점 이상보유 시, 사용 가능합니다");
						alert.showAndWait();
					}
				}
			}
			// 다시 회원리스트 파일로 저장
			try (FileWriter fw = new FileWriter("Member/member.txt")){
				for(int i=0; i<memberList.size();i++){
					fw.write(memberList.get(i).getmNum());
					fw.write(",");
					fw.write(memberList.get(i).getmName());
					fw.write(",");
					fw.write(memberList.get(i).getmAge());
					fw.write(",");
					fw.write(memberList.get(i).getmPhone());
					fw.write(",");
					fw.write(memberList.get(i).getmEmail());
					fw.write(",");
					fw.write(memberList.get(i).getmAddress());
					fw.write(",");
					fw.write(memberList.get(i).getmDate());
					fw.write(",");
					fw.write(memberList.get(i).getmPoint());
					fw.write("\r\n");
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			pointCount++;
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("포인트 사용 완료");
			alert.setHeaderText("");
			alert.setContentText("이미 포인트 할인이 적용 되었습니다.");
			alert.showAndWait();
		}


	}

	// 포인트 적립 (적립율 결제금액에 0.03%)
	private void pointBtn(ActionEvent event) {
		if(pointCount<1) {
			int totalpay = Integer.parseInt(list.get(0).getPayTotal());
			for(int i=0;i<memberList.size();i++) {
				if(phone.getText().equals(memberList.get(i).getmPhone())) {
					memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint())) + ((int)(totalpay*0.03))));
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(dialogStage);
					alert.setTitle("포인트 적립");
					alert.setHeaderText("");
					alert.setContentText("포인트가 적립되었습니다.");
					alert.showAndWait();
				}
			}
			usePoint.setText("할인적용 불가");


			// 다시 회원리스트 파일로 저장
			try (FileWriter fw = new FileWriter("Member/member.txt")){
				for(int i=0; i<memberList.size();i++){
					fw.write(memberList.get(i).getmNum());
					fw.write(",");
					fw.write(memberList.get(i).getmName());
					fw.write(",");
					fw.write(memberList.get(i).getmAge());
					fw.write(",");
					fw.write(memberList.get(i).getmPhone());
					fw.write(",");
					fw.write(memberList.get(i).getmEmail());
					fw.write(",");
					fw.write(memberList.get(i).getmAddress());
					fw.write(",");
					fw.write(memberList.get(i).getmDate());
					fw.write(",");
					fw.write(memberList.get(i).getmPoint());
					fw.write("\r\n");
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			pointCount++;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("포인트 적립 불가");
			alert.setHeaderText("");
			alert.setContentText("이미 포인트 적립 적용되었습니다");
			alert.showAndWait();
		}
	}

	// 결제버튼 액션
	private void ok(ActionEvent event) {
		String isOk ="";
		if(cardNum.getText().equals(isOk)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("카드번호 미입력");
			alert.setHeaderText("");
			alert.setContentText("카드번호를 입력해주세요");
			alert.showAndWait();
		}
		else if(month.getText().equals(isOk)) { 
		
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("카드 월 미입력");
			alert.setHeaderText("");
			alert.setContentText("월을 입력해주세요");
			alert.showAndWait();
		} else if (year.getText().equals(isOk)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("카드 년 미입력");
			alert.setHeaderText("");
			alert.setContentText("년을 입력해주세요");
			alert.showAndWait();
		} else if (cate.getValue().equals("선택")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("할부 선택");
			alert.setHeaderText("");
			alert.setContentText("할부를 선택해주세요");
			alert.showAndWait();
		} else {
			System.out.println("카드결제 완료!");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("결제 완료");
			alert.setHeaderText("");
			alert.setContentText("카드결제가 완료되었습니다!!");
			alert.showAndWait();
			try (FileWriter fw = new FileWriter("Member/member.txt")){
				for(int i=0; i<memberList.size();i++){
					fw.write(memberList.get(i).getmNum());
					fw.write(",");
					fw.write(memberList.get(i).getmName());
					fw.write(",");
					fw.write(memberList.get(i).getmAge());
					fw.write(",");
					fw.write(memberList.get(i).getmPhone());
					fw.write(",");
					fw.write(memberList.get(i).getmEmail());
					fw.write(",");
					fw.write(memberList.get(i).getmAddress());
					fw.write(",");
					fw.write(memberList.get(i).getmDate());
					fw.write(",");
					fw.write(memberList.get(i).getmPoint());
					fw.write("\r\n");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			// 결제리스트로부터 넘어온 상품들 및 총합계금액
			StringTokenizer st5 = null;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay.txt")))) {
				list.clear();
				String str = null;
				while((str = br.readLine())!= null){
					Pay p = new Pay();
					st5 = new StringTokenizer(str,",");
					p.setItemList(st5.nextToken());
					p.setPayTotal(st5.nextToken());
					list.add(p);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}

			// PayList에 Pay.java 카드결제 생성자를 이용해서 내용 담아주기
			// 결제시간 생성
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String now = (new SimpleDateFormat("yy-MM-dd HH:mm").format(date));

			payList.add(new Pay("card", cardNum.getText(), month.getText(), year.getText(), cate.getValue(), list.get(0).getItemList(), list.get(0).getPayTotal(),  now));

			// 최종적으로 다시 파일로 저장
			try (FileWriter fw = new FileWriter("Payment/Card.txt", true)){
				for(int i=0; i<payList.size(); i++){
					fw.write(payList.get(i).getCardorcash());
					fw.write(",");
					fw.write(payList.get(i).getCardNum());
					fw.write(",");
					fw.write(payList.get(i).getCardMonth());
					fw.write(",");
					fw.write(payList.get(i).getCardYear());
					fw.write(",");
					fw.write(payList.get(i).getCardCate());
					fw.write(",");
					fw.write(payList.get(i).getItemList());
					fw.write(",");
					fw.write(payList.get(i).getPayDate());
					fw.write("\r\n");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

			// 영수증 리스트 파일 불러와서 리스트에 담기
			StringTokenizer st6 = null;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/Print.txt")))) {
				printList.clear();
				String str = null;
				while((str = br.readLine())!= null){
					Pay p = new Pay();
					st6 = new StringTokenizer(str,",");
					p.setNum(st6.nextToken());
					p.setCardorcash(st6.nextToken());
					p.setPayDate(st6.nextToken());
					p.setPayTotal(st6.nextToken());
					p.setItemList(st6.nextToken());
					printList.add(p);
				}

			}catch(IOException e) {
				e.printStackTrace();
			}

			// 영수증 번호 자동생성
			String printNo ="";
			if(printList.size() != 0){
				printNo = ((Integer.parseInt(printList.get(printList.size()-1).getNum())+1)+"");
			}else{
				printNo = 1+"";
			}
			// 영수증에서 활용할 자료 영수증 ArrayList printList에 추가해주기
			printList.add(new Pay(printNo, "카드", now, list.get(0).getPayTotal(), list.get(0).getItemList()));

			// 영수증 리스트를 다시 파일로 저장
			try (FileWriter fw = new FileWriter("Payment/Print.txt")){
				for(int i=0; i<printList.size(); i++){
					fw.write(printList.get(i).getNum());
					fw.write(",");
					fw.write(printList.get(i).getCardorcash());
					fw.write(",");
					fw.write(printList.get(i).getPayDate());
					fw.write(",");
					fw.write(printList.get(i).getPayTotal());
					fw.write(",");
					fw.write(printList.get(i).getItemList());
					fw.write("\r\n");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

			// 작업이 끝나면 창 닫아주기
			dialogStage.close();
		}
	}




	// 다이얼로그 생성 메소드
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// 결제 취소, 창닫기
	private void cancel(ActionEvent event) {
		dialogStage.close();
	}
}
