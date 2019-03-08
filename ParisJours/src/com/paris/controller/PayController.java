package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Notice;
import com.paris.model.vo.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PayController implements Initializable {

	private Stage primaryStage;

	// �޴���ư
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button mainBtn;

	// @���� ��ư�߰�
	@FXML private Button cardPay;
	@FXML private Button cashPay;
	@FXML private Button cashReceipBtn;
	@FXML private Button receipBtn;

	// ��������Ʈ ���̺�� �� �÷�
	@FXML private TableView<Product> paylist;
	@FXML private TableColumn<Product, String> payName;
	@FXML private TableColumn<Product, String> payAmount;
	@FXML private TableColumn<Product, String> payPrice;

	static ArrayList<Product> list = new ArrayList<Product>();
	static ArrayList<Product> paymentlist = new ArrayList<Product>();
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	static ObservableList<Product> myList;
	static ObservableList<Product> payObser = FXCollections.observableArrayList();

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// �޴� �̺�Ʈ (��������� ����)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));

		// @ ���� �߰��� ��ư
		cardPay.setOnAction(e->cardPay(e));
		cashPay.setOnAction(e->cashPay(e));
		receipBtn.setOnAction(e->receipBtn(e));

		// �ֱ� �������� ��� ������Ʈ --------------------------------------------------------------------����
		StringTokenizer st6 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))){
			notice.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Notice n = new Notice();
				st6 = new StringTokenizer(str,",");
				n.setNo(st6.nextToken());
				n.setCate(st6.nextToken());
				n.setTitle(st6.nextToken());
				n.setContent(st6.nextToken());
				n.setDate(st6.nextToken());
				n.setPassword(st6.nextToken());
				notice.add(n);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		// �ֱ� �������� ������ �󺧿� ���
		try {
			notice_1.setText(notice.get(notice.size()-1).getTitle());
			date_1.setText(notice.get(notice.size()-1).getDate().substring(3, 9));

			notice_2.setText(notice.get(notice.size()-2).getTitle());
			date_2.setText(notice.get(notice.size()-2).getDate().substring(3, 9));

			notice_3.setText(notice.get(notice.size()-3).getTitle());
			date_3.setText(notice.get(notice.size()-3).getDate().substring(3, 9));

			notice_4.setText(notice.get(notice.size()-4).getTitle());
			date_4.setText(notice.get(notice.size()-4).getDate().substring(3, 9));

			notice_5.setText(notice.get(notice.size()-5).getTitle());
			date_5.setText(notice.get(notice.size()-5).getDate().substring(3, 9));
		}
		catch (Exception e) {
		}

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

		// �ֱ� �������� ��� ������Ʈ -------------------------------------------------------------------- ����

		//�������� ��ư
		mainBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("������������ �̵�");
				try {
					Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)mainBtn.getScene().getWindow(); // ���� ������ ��������
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// @������ ��ư ��Ʈ�� �߰� //ī������� �̵�
	private void cardPay(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/card.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// ���̾�α� (��â) ���������� �����.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("ī��� �����ϱ�");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// ���� Controller�� �����Ѵ�
			CardController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
			dialogStage.showAndWait();

			// ī����� �Ϸ� �� �������� ����������
			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
				Scene scene2= new Scene(product);
				Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//���ݰ����� �̵�
	private void cashPay(ActionEvent event)
	{
		try {
			// fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/cash.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// ���̾�α� (��â) ���������� �����.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("�������� �����ϱ�");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);


			// ���� Controller�� �����Ѵ�
			CashController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
			dialogStage.showAndWait();

			// ������ư�� ������ �ٽ� �������� ���ΰ�ħ �Ѵ�
			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
				Scene scene2= new Scene(product);
				Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // ���� ������ ��������
				primaryStage.setScene(scene2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//���ݿ��������� �̵�
	

	//������ ������ �̵�
	private void receipBtn(ActionEvent event)
	{
		try { //ȭ�� �̵� ���� �ҽ�
			Parent receipPrint = FXMLLoader.load(getClass().getResource("/com/paris/view/receipPrint.fxml"));
			Scene scene = new Scene(receipPrint);
			Stage primaryStage = (Stage)receipBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
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
			Stage primaryStage = (Stage)payBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
