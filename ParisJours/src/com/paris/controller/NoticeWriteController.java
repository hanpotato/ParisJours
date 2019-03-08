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

import com.paris.model.vo.Notice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NoticeWriteController implements Initializable {

	private Stage primaryStage;

	// �޴���ư ����
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button main;

	// �ֱٰԽù� ( �������� )
	@FXML private Label notice_1;
	@FXML private Label notice_2;
	@FXML private Label notice_3;
	@FXML private Label notice_4;
	@FXML private Label notice_5;
	@FXML private Label date_1;
	@FXML private Label date_2;
	@FXML private Label date_3;
	@FXML private Label date_4;
	@FXML private Label date_5;
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	// �������� �޴� ���� FXML
	@FXML private Button writeBtn;
	@FXML private TextField title;
	@FXML private TextArea content;
	@FXML private ChoiceBox<String> cate;
	@FXML private PasswordField password;

	private Stage dialogStage; 
	
	static ArrayList<Notice> list = new ArrayList<Notice>();
	ObservableList<String> options = FXCollections.observableArrayList("����","��������","�̺�Ʈ"); 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));

		// �� �ۼ� ��, �ٽ� ������������ ���ư���
		writeBtn.setOnAction(e->writeBtn(e));

		main.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("������������ �̵�");
				try {
					Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// ī�װ� ���� ���̽� �ڽ�
		cate.setValue("����");
		cate.setItems(options);

		// ���� �ֱ� �������� �ҷ�����
		StringTokenizer st5 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))){
			notice.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Notice n = new Notice();
				st5 = new StringTokenizer(str,",");
				n.setNo(st5.nextToken());
				n.setCate(st5.nextToken());
				n.setTitle(st5.nextToken());
				n.setContent(st5.nextToken());
				n.setDate(st5.nextToken());
				n.setPassword(st5.nextToken());
				notice.add(n);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		// �ֱ� �������� ������ �󺧿� ���
		notice_1.setText(notice.get(notice.size()-1).getTitle());
		notice_2.setText(notice.get(notice.size()-2).getTitle());
		notice_3.setText(notice.get(notice.size()-3).getTitle());
		notice_4.setText(notice.get(notice.size()-4).getTitle());
		notice_5.setText(notice.get(notice.size()-5).getTitle());
		
		notice_1.setOnMouseClicked(event-> {
			System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_2.setOnMouseClicked(event-> {
			System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_3.setOnMouseClicked(event-> {
			System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_4.setOnMouseClicked(event-> {
			System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_5.setOnMouseClicked(event-> {
			System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		date_1.setText(notice.get(notice.size()-1).getDate().substring(3, 9));
		date_2.setText(notice.get(notice.size()-2).getDate().substring(3, 9));
		date_3.setText(notice.get(notice.size()-3).getDate().substring(3, 9));
		date_4.setText(notice.get(notice.size()-4).getDate().substring(3, 9));
		date_5.setText(notice.get(notice.size()-5).getDate().substring(3, 9));

	}

	// �������� ������ ArrayList�� �߰� ����, �߰��� �����͸� ���Ϸ� ����
	private void writeBtn(ActionEvent event) {
		// �Է��� null �� üũ �� �� �ۼ�
		if(cate.getValue().equals("����")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("ī�װ� �̼���");
			alert.setHeaderText("");
			alert.setContentText("ī�װ��� �����ϼ���");
			alert.showAndWait();
		} else 	if(title.getText().equals("")){
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("���� ���Է�");
				alert.setHeaderText("");
				alert.setContentText("������ �Է��ϼ���");
				alert.showAndWait();
			} else 
		if(password.getText().equals("")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("��й�ȣ�� ���Է�");
			alert.setHeaderText("");
			alert.setContentText("��й�ȣ�� �Է��ϼ���");
			alert.showAndWait();
		} else if(content.getText().equals("")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("���� ���Է�");
			alert.setHeaderText("");
			alert.setContentText("������ �Է��ϼ���");
			alert.showAndWait();
		} else {
		
		// �������� �����͸� ���� �ҷ��ͼ� list�� ����ֱ�
		StringTokenizer st5 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Notice n = new Notice();
				st5 = new StringTokenizer(str,",");
				n.setNo(st5.nextToken());
				n.setCate(st5.nextToken());
				n.setTitle(st5.nextToken());
				n.setContent(st5.nextToken());
				n.setDate(st5.nextToken());
				n.setPassword(st5.nextToken());
				list.add(n);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// ���Ϸκ��� �߰��� list �� �ۼ��� �� ���
		String num="";
		if(list.size() != 0){
			num = ((Integer.parseInt(list.get(list.size()-1).getNo())+1)+"");
		}else{
			num = 1+"";
		}

		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yy-MM-dd HH:mm").format(date));
		
		list.add(new Notice(num, cate.getValue(), title.getText(), content.getText(), now, password.getText()));
		
		// ���������� �ٽ� ���Ϸ� ����
		try (FileWriter fw = new FileWriter("Notice/notice.txt")){
			for(int i=0; i<list.size(); i++){
				fw.write(list.get(i).getNo());
				fw.write(",");
				fw.write(list.get(i).getCate());
				fw.write(",");
				fw.write(list.get(i).getTitle());
				fw.write(",");
				fw.write(list.get(i).getContent());
				fw.write(",");
				fw.write(list.get(i).getDate());
				fw.write(",");
				fw.write(list.get(i).getPassword());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("�������� �۾��� �Ϸ�");
		try {
			Parent write = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
			Scene scene = new Scene(write);
			Stage primaryStage = (Stage)writeBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	private void noticeBtn(ActionEvent event) {
		System.out.println("�������� �޴��� �̵�");
		try {
			Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
			Scene scene = new Scene(notice);
			Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void memberBtn(ActionEvent event) {
		System.out.println("ȸ������ �޴��� �̵�");

		try {
			Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
			Scene scene = new Scene(member);
			Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void itemBtn(ActionEvent event) {
		System.out.println("������ �޴��� �̵�");

		try {

			Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
			Scene scene = new Scene(product);
			Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payBtn(ActionEvent event) {
		System.out.println("������� �޴��� �̵�");
		try {
			Parent pay = FXMLLoader.load(getClass().getResource("/com/paris/view/cal.fxml"));
			Scene scene = new Scene(pay);
			Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
