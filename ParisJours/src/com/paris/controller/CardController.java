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

	// ���� ����� ��Ʈ��
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

	// ������ ArrayList
	static ArrayList<Pay> printList = new ArrayList<Pay>();

	static ArrayList<Pay> list = new ArrayList<Pay>();
	static ArrayList<Pay> payList = new ArrayList<Pay>();

	// ����Ʈ������ ���� Member ArrayList
	static ArrayList<Member> memberList = new ArrayList<Member>();

	ObservableList<String> option = FXCollections.observableArrayList("����", "�Ͻú�", "3����", "6����");

	// ��â�� ��� ���̾�α� ���� ����
	private Stage dialogStage;

	String payitemList = "";

	// ����Ʈ ���� �� ����� ���� 1ȸ�� �����ϵ���
	int pointCount = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ȸ���˻��� ���� Member ���� �ҷ���
		// ����� ����� ���� ��������͸� �ҷ��� ArrayList (memberList) �� ��Ƶ�
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

		cate.setValue("����");
		cate.setItems(option);

		ok.setOnAction(e->ok(e));
		cancel.setOnAction(e->cancel(e));

		// ��������Ʈ�κ��� �Ѿ�� ��ǰ�� �� ���հ�ݾ�
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

		// list (Product��) �κ��� ���� �����Ѿ�
		int sum = Integer.parseInt(list.get(0).getPayTotal());
		paySum.setText(String.valueOf(sum));

		// ������ ����Ʈ (�����ݾ׿� 0.03%)
		int totalpay = Integer.parseInt(list.get(0).getPayTotal());
		savePoint.setText(String.valueOf((int)(totalpay*0.03)));

	}

	// ����� -> ȸ���˻� ��ư
	private void searchBtn(ActionEvent event) {
		for(int i=0;i<memberList.size();i++) {
			if(memberList.get(i).getmPhone().equals(phone.getText())) {
				name.setText(memberList.get(i).getmName());
				point.setText(memberList.get(i).getmPoint());
			}
		}
	}

	// ����Ʈ ����ư
	// �� �հ� �ݾ� - ����Ʈ ���ݾ� ���!!
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
						System.out.println(memberList.get(i).getmName()+"�� ����Ʈ [ " + usePoint.getText() + " ] �� ���!");
					} else {
						usePoint.setText("����Ʈ�� �����մϴ�");
						Alert alert = new Alert(AlertType.ERROR);
						alert.initOwner(dialogStage);
						alert.setTitle("����Ʈ ��� �Ұ�");
						alert.setHeaderText("");
						alert.setContentText("����Ʈ�� 1000�� �̻��� ��, ��� �����մϴ�");
						alert.showAndWait();
					}
				}
			}
			// �ٽ� ȸ������Ʈ ���Ϸ� ����
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
			alert.setTitle("����Ʈ ��� �Ϸ�");
			alert.setHeaderText("");
			alert.setContentText("�̹� ����Ʈ ������ ���� �Ǿ����ϴ�.");
			alert.showAndWait();
		}


	}

	// ����Ʈ ���� (������ �����ݾ׿� 0.03%)
	private void pointBtn(ActionEvent event) {
		if(pointCount<1) {
			int totalpay = Integer.parseInt(list.get(0).getPayTotal());
			for(int i=0;i<memberList.size();i++) {
				if(phone.getText().equals(memberList.get(i).getmPhone())) {
					memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint())) + ((int)(totalpay*0.03))));
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(dialogStage);
					alert.setTitle("����Ʈ ����");
					alert.setHeaderText("");
					alert.setContentText("����Ʈ�� �����Ǿ����ϴ�.");
					alert.showAndWait();
				}
			}
			usePoint.setText("�������� �Ұ�");


			// �ٽ� ȸ������Ʈ ���Ϸ� ����
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
			alert.setTitle("����Ʈ ���� �Ұ�");
			alert.setHeaderText("");
			alert.setContentText("�̹� ����Ʈ ���� ����Ǿ����ϴ�");
			alert.showAndWait();
		}
	}

	// ������ư �׼�
	private void ok(ActionEvent event) {
		String isOk ="";
		if(cardNum.getText().equals(isOk)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("ī���ȣ ���Է�");
			alert.setHeaderText("");
			alert.setContentText("ī���ȣ�� �Է����ּ���");
			alert.showAndWait();
		}
		else if(month.getText().equals(isOk)) { 
		
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("ī�� �� ���Է�");
			alert.setHeaderText("");
			alert.setContentText("���� �Է����ּ���");
			alert.showAndWait();
		} else if (year.getText().equals(isOk)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("ī�� �� ���Է�");
			alert.setHeaderText("");
			alert.setContentText("���� �Է����ּ���");
			alert.showAndWait();
		} else if (cate.getValue().equals("����")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("�Һ� ����");
			alert.setHeaderText("");
			alert.setContentText("�Һθ� �������ּ���");
			alert.showAndWait();
		} else {
			System.out.println("ī����� �Ϸ�!");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("���� �Ϸ�");
			alert.setHeaderText("");
			alert.setContentText("ī������� �Ϸ�Ǿ����ϴ�!!");
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
			// ��������Ʈ�κ��� �Ѿ�� ��ǰ�� �� ���հ�ݾ�
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

			// PayList�� Pay.java ī����� �����ڸ� �̿��ؼ� ���� ����ֱ�
			// �����ð� ����
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String now = (new SimpleDateFormat("yy-MM-dd HH:mm").format(date));

			payList.add(new Pay("card", cardNum.getText(), month.getText(), year.getText(), cate.getValue(), list.get(0).getItemList(), list.get(0).getPayTotal(),  now));

			// ���������� �ٽ� ���Ϸ� ����
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

			// ������ ����Ʈ ���� �ҷ��ͼ� ����Ʈ�� ���
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

			// ������ ��ȣ �ڵ�����
			String printNo ="";
			if(printList.size() != 0){
				printNo = ((Integer.parseInt(printList.get(printList.size()-1).getNum())+1)+"");
			}else{
				printNo = 1+"";
			}
			// ���������� Ȱ���� �ڷ� ������ ArrayList printList�� �߰����ֱ�
			printList.add(new Pay(printNo, "ī��", now, list.get(0).getPayTotal(), list.get(0).getItemList()));

			// ������ ����Ʈ�� �ٽ� ���Ϸ� ����
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

			// �۾��� ������ â �ݾ��ֱ�
			dialogStage.close();
		}
	}




	// ���̾�α� ���� �޼ҵ�
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// ���� ���, â�ݱ�
	private void cancel(ActionEvent event) {
		dialogStage.close();
	}
}
