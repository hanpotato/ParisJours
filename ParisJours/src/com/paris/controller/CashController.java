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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CashController implements Initializable{

	@FXML private Button ok;
	@FXML private Button cancel;
	@FXML private Button changeCalBtn;
	@FXML private Button receipBtn;

	@FXML private TextField paySum;
	@FXML private TextField takeMoneyText;
	@FXML private TextField totalPay;
	@FXML private TextField changeText;
	@FXML private Labeled mistakeLabel;

	@FXML private TextField phone;
	@FXML private Button searchBtn;
	@FXML private Label point;
	@FXML private Label name;
	@FXML private Label savePoint;
	@FXML private TextField usePoint;
	@FXML private Button usePointBtn;
	@FXML private Button pointBtn;

	static ArrayList<Pay> printList = new ArrayList<Pay>();
	static ArrayList<Pay> paylist = new ArrayList<Pay>();
	static ArrayList<Pay> paymentlist = new ArrayList<Pay>();
	static ArrayList<Member> memberList = new ArrayList<Member>();

	// 새창을 띄울 다이얼로그 변수 선언
	private Stage dialogStage;
	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancel.setOnAction(e->cancel(e));
		changeCalBtn.setOnAction(e->changeCalBtn(e));
		ok.setOnAction(e->ok(e));
		usePointBtn.setOnAction(e->usePointBtn(e));
		searchBtn.setOnAction(e->searchBtn(e));
		pointBtn.setOnAction(e->pointBtn(e));
		receipBtn.setOnAction(e->receipBtn(e));

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

		// 텍스트필드가 총금액(paySum), 받은금액(takeMoney), 거스름돈(change)
		paySum.setText(paymentlist.get(0).getPayTotal());
		takeMoneyText.setText(takeMoneyText.getText());

		// 적립될 포인트 (결제금액에 0.03%)
		int paySum = Integer.parseInt(paymentlist.get(0).getPayTotal());
		savePoint.setText(String.valueOf((int)(paySum*0.03)));

		// 혜진아 여기서 거스름돈을 계산해주는 버튼하나를 만들어서
		// 그 메소드에서 거스름돈 change.setText()를 해줘야할것같아
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

	// 포인트 사용버튼 클릭시, 총 합계 금액 - 포인트 사용금액 계산!!
	private void pointBtn(ActionEvent event) {
		int totalpay = Integer.parseInt(paymentlist.get(0).getPayTotal());
		for(int i=0;i<memberList.size();i++) {
			if(phone.getText().equals(memberList.get(i).getmPhone())) {
				memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint())) + ((int)(totalpay*0.03))));
				System.out.println(memberList.get(i).getmName() + "님 포인트 [ " + (String.valueOf(totalpay*0.03)) + " ]점 적립 완료!");
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
	}

	// 포인트 적립 (적립율 결제금액에 0.03%)
	private void usePointBtn(ActionEvent event) {
		int totalpay = Integer.parseInt(paymentlist.get(0).getPayTotal());
		int usepoint = Integer.parseInt(usePoint.getText());
		paySum.setText(String.valueOf((totalpay-usepoint)));
		paymentlist.get(0).setPayTotal(String.valueOf((totalpay-usepoint)));

		for(int i=0;i<memberList.size();i++) {
			if(memberList.get(i).getmPhone().equals(phone.getText())) {
				if(Integer.parseInt(memberList.get(i).getmPoint())>1000) {
					memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint()))-
							(Integer.parseInt(usePoint.getText()))));
					System.out.println(memberList.get(i).getmName()+"님 포인트 [ " + usePoint.getText() + " ] 점 사용!");
				} else {
					usePoint.setText("포인트가 부족합니다");
					paySum.setText(String.valueOf(totalpay));
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
	}

	// 현금영수증 발행 버튼
	private void receipBtn(ActionEvent event) 
	{
		try {
			// fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/receip.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// 다이얼로그 (새창) 스테이지를 만든다.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("현금영수증");
			dialogStage.setResizable(false); 
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// 수정 Controller를 설정한다
			ReceipController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			// 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeCalBtn(ActionEvent event) {

		int sum = Integer.parseInt(paymentlist.get(0).getPayTotal());
		int take =Integer.parseInt(takeMoneyText.getText());
		//changeText.setText((take-sum)+"");
		if(take>sum)
		{
			changeText.setText((take-sum)+"");
			mistakeLabel.setText("");
		}
		else if(take<sum)
		{
			mistakeLabel.setText("금액을 확인해주세요.");
		}
	}

	public void ok(ActionEvent event) {

		// 결제리스트로부터 넘어온 상품들 및 총합계금액
		StringTokenizer st5 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay.txt")))) {
			paymentlist.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Pay p = new Pay();
				st5 = new StringTokenizer(str,",");
				p.setItemList(st5.nextToken());
				p.setPayTotal(st5.nextToken());
				paymentlist.add(p);
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

		// PayList에 Pay.java 카드결제 생성자를 이용해서 내용 담아주기
		// 결제시간 생성
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yy-MM-dd HH:mm").format(date));

		// 현금결제 기록번호 자동증가
		String cashCountNo ="";
		if(paylist.size() != 0){
			cashCountNo = ((Integer.parseInt(paylist.get(paylist.size()-1).getCashCount())+1)+"");
		}else{
			cashCountNo = 1+"";
		}

		paylist.add(new Pay("현금", cashCountNo, takeMoneyText.getText(), paymentlist.get(0).getPayTotal(), paymentlist.get(0).getItemList(), now));

		// 최종적으로 다시 파일로 저장
		try (FileWriter fw = new FileWriter("Payment/Cash.txt", true)){
			for(int i=0; i<paylist.size(); i++){
				fw.write(paylist.get(i).getCardorcash());
				fw.write(",");
				fw.write(paylist.get(i).getCashCount());
				fw.write(",");
				fw.write(paylist.get(i).getCashTake());
				fw.write(",");
				fw.write(paylist.get(i).getPayTotal());
				fw.write(",");
				fw.write(paylist.get(i).getItemList());
				fw.write(",");
				fw.write(paylist.get(i).getPayDate());
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
		printList.add(new Pay(printNo, "현금", now, paymentlist.get(0).getPayTotal(), paymentlist.get(0).getItemList()));

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("결제 완료");
		alert.setHeaderText("");
		alert.setContentText("현금결제가 완료 되었습니다!!");
		alert.showAndWait();
		
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

	// 다이얼로그 생성 메소드
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private void cancel(ActionEvent event) {
		dialogStage.close();
	}

}