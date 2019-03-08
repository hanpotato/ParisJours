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

	// ��â�� ��� ���̾�α� ���� ����
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
		takeMoneyText.setText(takeMoneyText.getText());

		// ������ ����Ʈ (�����ݾ׿� 0.03%)
		int paySum = Integer.parseInt(paymentlist.get(0).getPayTotal());
		savePoint.setText(String.valueOf((int)(paySum*0.03)));

		// ������ ���⼭ �Ž������� ������ִ� ��ư�ϳ��� ����
		// �� �޼ҵ忡�� �Ž����� change.setText()�� ������ҰͰ���
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

	// ����Ʈ ����ư Ŭ����, �� �հ� �ݾ� - ����Ʈ ���ݾ� ���!!
	private void pointBtn(ActionEvent event) {
		int totalpay = Integer.parseInt(paymentlist.get(0).getPayTotal());
		for(int i=0;i<memberList.size();i++) {
			if(phone.getText().equals(memberList.get(i).getmPhone())) {
				memberList.get(i).setmPoint(String.valueOf((Integer.parseInt(memberList.get(i).getmPoint())) + ((int)(totalpay*0.03))));
				System.out.println(memberList.get(i).getmName() + "�� ����Ʈ [ " + (String.valueOf(totalpay*0.03)) + " ]�� ���� �Ϸ�!");
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
	}

	// ����Ʈ ���� (������ �����ݾ׿� 0.03%)
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
					System.out.println(memberList.get(i).getmName()+"�� ����Ʈ [ " + usePoint.getText() + " ] �� ���!");
				} else {
					usePoint.setText("����Ʈ�� �����մϴ�");
					paySum.setText(String.valueOf(totalpay));
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
	}

	// ���ݿ����� ���� ��ư
	private void receipBtn(ActionEvent event) 
	{
		try {
			// fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/receip.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// ���̾�α� (��â) ���������� �����.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("���ݿ�����");
			dialogStage.setResizable(false); 
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// ���� Controller�� �����Ѵ�
			ReceipController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			// ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
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
			mistakeLabel.setText("�ݾ��� Ȯ�����ּ���.");
		}
	}

	public void ok(ActionEvent event) {

		// ��������Ʈ�κ��� �Ѿ�� ��ǰ�� �� ���հ�ݾ�
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

		// PayList�� Pay.java ī����� �����ڸ� �̿��ؼ� ���� ����ֱ�
		// �����ð� ����
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yy-MM-dd HH:mm").format(date));

		// ���ݰ��� ��Ϲ�ȣ �ڵ�����
		String cashCountNo ="";
		if(paylist.size() != 0){
			cashCountNo = ((Integer.parseInt(paylist.get(paylist.size()-1).getCashCount())+1)+"");
		}else{
			cashCountNo = 1+"";
		}

		paylist.add(new Pay("����", cashCountNo, takeMoneyText.getText(), paymentlist.get(0).getPayTotal(), paymentlist.get(0).getItemList(), now));

		// ���������� �ٽ� ���Ϸ� ����
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
		printList.add(new Pay(printNo, "����", now, paymentlist.get(0).getPayTotal(), paymentlist.get(0).getItemList()));

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("���� �Ϸ�");
		alert.setHeaderText("");
		alert.setContentText("���ݰ����� �Ϸ� �Ǿ����ϴ�!!");
		alert.showAndWait();
		
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

	// ���̾�α� ���� �޼ҵ�
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private void cancel(ActionEvent event) {
		dialogStage.close();
	}

}