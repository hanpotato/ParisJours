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
import com.paris.model.vo.Product;

import Product.TextPath;
import Product.images.path;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private Stage primaryStage;

	// �޴���ư
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button main;

	// ��������Ʈ ���̺�� �� �÷�
	@FXML private TableView<Product> paylist;
	@FXML private TableColumn<Product, String> payName;
	@FXML private TableColumn<Product, String> payAmount;
	@FXML private TableColumn<Product, String> payPrice;

	// ��������Ʈ�� ��� �հ� �ݾ�
	@FXML private Label paySum;

	// ��������Ʈ �����ϱ� ��ư
	@FXML private Button paymentBtn;

	//���� �հ�ݾ� ��� ����
	String paySum_backup;

	// ��������Ʈ �ʱ�ȭ ��ư
	@FXML private Button payInitBtn;

	// �� ��� ����
	@FXML private TabPane tabPane = new TabPane();

	// ���� ��ǰ
	@FXML private Tab tab_sb;
	@FXML private Tab tab_mb;
	@FXML private Tab tab_donut;
	@FXML private Tab tab_pie;
	@FXML private Tab tab_cb;
	@FXML private Tab tab_cake;
	@FXML private Tab tab_sandwich;
	@FXML private Tab tab_dessert;
	@FXML private Tab tab_drink;
	@FXML private Tab tab_brunch;
	@FXML private Tab tab_etc;

	private Stage dialogStage;

	//"����","���Ŀ� ��","�Ļ�� ��/�Ļ�","����","�佺��/����","������","����ũ","������ġ","����Ʈ","����","�귱ġ","��Ÿ"
	// ���� ��ǰ ���̺�� �� �÷� -��ü
	@FXML private TableView<Product> tableView;
	@FXML private TableColumn<Product, String> name;
	@FXML private TableColumn<Product, String> price;
	@FXML private TableColumn<Product, String> date;
	@FXML private TableColumn<Product, String> amount;
	// ���� ��ǰ ���̺�� �� �÷� -���Ŀ� ��(snack bread)
	@FXML private TableView<Product> sbTableView;
	@FXML private TableColumn<Product, String> sbName;
	@FXML private TableColumn<Product, String> sbPrice;
	@FXML private TableColumn<Product, String> sbDate;
	@FXML private TableColumn<Product, String> sbAmount;
	// ���� ��ǰ ���̺�� �� �÷� -�Ļ�� ��(meal bread)
	@FXML private TableView<Product> mbTableView;
	@FXML private TableColumn<Product, String> mbName;
	@FXML private TableColumn<Product, String> mbPrice;
	@FXML private TableColumn<Product, String> mbDate;
	@FXML private TableColumn<Product, String> mbAmount;
	// ���� ��ǰ ���̺�� �� �÷� -����(donut)
	@FXML private TableView<Product> donutTableView;
	@FXML private TableColumn<Product, String> donutName;
	@FXML private TableColumn<Product, String> donutPrice;
	@FXML private TableColumn<Product, String> donutDate;
	@FXML private TableColumn<Product, String> donutAmount;
	// ���� ��ǰ ���̺�� �� �÷� -����(pie)
	@FXML private TableView<Product> pieTableView;
	@FXML private TableColumn<Product, String> pieName;
	@FXML private TableColumn<Product, String> piePrice;
	@FXML private TableColumn<Product, String> pieDate;
	@FXML private TableColumn<Product, String> pieAmount;
	// ���� ��ǰ ���̺�� �� �÷� -������(cook bread)
	@FXML private TableView<Product> cbTableView;
	@FXML private TableColumn<Product, String> cbName;
	@FXML private TableColumn<Product, String> cbPrice;
	@FXML private TableColumn<Product, String> cbDate;
	@FXML private TableColumn<Product, String> cbAmount;
	// ���� ��ǰ ���̺�� �� �÷� -����ũ(cake)
	@FXML private TableView<Product> cakeTableView;
	@FXML private TableColumn<Product, String> cakeName;
	@FXML private TableColumn<Product, String> cakePrice;
	@FXML private TableColumn<Product, String> cakeDate;
	@FXML private TableColumn<Product, String> cakeAmount;
	// ���� ��ǰ ���̺�� �� �÷� -������ġ(sandwich)
	@FXML private TableView<Product> sandwichTableView;
	@FXML private TableColumn<Product, String> sandwichName;
	@FXML private TableColumn<Product, String> sandwichPrice;
	@FXML private TableColumn<Product, String> sandwichDate;
	@FXML private TableColumn<Product, String> sandwichAmount;
	// ���� ��ǰ ���̺�� �� �÷� -����Ʈ(dessert)
	@FXML private TableView<Product> dessertTableView;
	@FXML private TableColumn<Product, String> dessertName;
	@FXML private TableColumn<Product, String> dessertPrice;
	@FXML private TableColumn<Product, String> dessertDate;
	@FXML private TableColumn<Product, String> dessertAmount;
	// ���� ��ǰ ���̺�� �� �÷� -����(drink)
	@FXML private TableView<Product> drinkTableView;
	@FXML private TableColumn<Product, String> drinkName;
	@FXML private TableColumn<Product, String> drinkPrice;
	@FXML private TableColumn<Product, String> drinkDate;
	@FXML private TableColumn<Product, String> drinkAmount;
	// ���� ��ǰ ���̺�� �� �÷� -�귱ġ(brunch)
	@FXML private TableView<Product> brunchTableView;
	@FXML private TableColumn<Product, String> brunchName;
	@FXML private TableColumn<Product, String> brunchPrice;
	@FXML private TableColumn<Product, String> brunchDate;
	@FXML private TableColumn<Product, String> brunchAmount;
	// ���� ��ǰ ���̺�� �� �÷� -��Ÿ(etc)
	@FXML private TableView<Product> etcTableView;
	@FXML private TableColumn<Product, String> etcName;
	@FXML private TableColumn<Product, String> etcPrice;
	@FXML private TableColumn<Product, String> etcDate;
	@FXML private TableColumn<Product, String> etcAmount;
	//��������Ʈ ������ ����
	@FXML private TableView<Product> subtTableView;
	@FXML private TableColumn<Product, String> subName;
	@FXML private TableColumn<Product, String> subPrice;
	@FXML private TableColumn<Product, String> subDate;
	@FXML private TableColumn<Product, String> subAmount;

	// ���� ��ǰ �̹����� ��ǰ ����
	@FXML private ImageView itemImage;
	@FXML private TextArea itemContent;

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

	static ArrayList<Product> list = new ArrayList<Product>();
	static ArrayList<Product> list_sb = new ArrayList<Product>();
	static ArrayList<Product> list_mb = new ArrayList<Product>();
	static ArrayList<Product> list_donut = new ArrayList<Product>();
	static ArrayList<Product> list_pie = new ArrayList<Product>();
	static ArrayList<Product> list_cb = new ArrayList<Product>();
	static ArrayList<Product> list_cake = new ArrayList<Product>();
	static ArrayList<Product> list_sandwich = new ArrayList<Product>();
	static ArrayList<Product> list_dessert = new ArrayList<Product>();
	static ArrayList<Product> list_drink = new ArrayList<Product>();
	static ArrayList<Product> list_brunch = new ArrayList<Product>();
	static ArrayList<Product> list_etc = new ArrayList<Product>();

	static int indexNum;

	static ObservableList<Product> myList;
	//"����","���Ŀ� ��","�Ļ�� ��/�Ļ�","����",
	// "�佺��/����","������","����ũ","������ġ",
	// "����Ʈ","����","�귱ġ","��Ÿ"
	static ObservableList<Product> myList_sb;
	static ObservableList<Product> myList_mb;
	static ObservableList<Product> myList_donut;
	static ObservableList<Product> myList_pie;
	static ObservableList<Product> myList_cb;
	static ObservableList<Product> myList_cake;
	static ObservableList<Product> myList_sandwich;
	static ObservableList<Product> myList_dessert;
	static ObservableList<Product> myList_drink;
	static ObservableList<Product> myList_brunch;
	static ObservableList<Product> myList_etc;
	static ObservableList<Product> payObser_sub = FXCollections.observableArrayList();

	// ��ǰ����Ʈ �˻�
	@FXML private TextField searchMain;
	@FXML private Button searchMainBtn;

	// �ߺ����� �׽�Ʈ Array
	static ArrayList<Product> result = new ArrayList<Product>();

	static ArrayList<Product> paymentlist = new ArrayList<Product>();
	static ObservableList<Product> payObser = FXCollections.observableArrayList();
	static ObservableList<Product> payObser_sb = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));
		searchMainBtn.setOnAction(e->searchMainBtn(e));
		payInitBtn.setOnAction(e->payInitBtn(e));

		// �����ϱ� ��ư
		paymentBtn.setOnAction(e->paymentBtn(e));

		// ���������� ��ư
		main.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("������������ �̵�");
				try {
					Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/login.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

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


		// ��ǰ �� ��� ������Ʈ --------------------------------------------------------------------------- ����

		// ��ǰ����Ʈ �ҷ�����
		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Product/product.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpNum(st.nextToken());
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpImage(st.nextToken());
				p.setpDate(st.nextToken());
				p.setpPrice(st.nextToken());
				p.setpContent(st.nextToken());
				p.setpAmount(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		// ���Ŀ� ��(snack bread)��ǰ ��� �ҷ����� -------------------------------------------------------------------------- 
		list_sb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("���Ŀ� ��")) {	
				list_sb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_sb = FXCollections.observableArrayList();
		for(int i=0;i<list_sb.size();i++) {
			myList_sb.add(new Product(new SimpleStringProperty(list_sb.get(i).getpNum()),
					(new SimpleStringProperty(list_sb.get(i).getpCate())), 
					(new SimpleStringProperty(list_sb.get(i).getpName())), 
					(new SimpleStringProperty(list_sb.get(i).getpImage())), 
					(new SimpleStringProperty(list_sb.get(i).getpDate())),
					(new SimpleStringProperty(list_sb.get(i).getpPrice())),
					(new SimpleStringProperty(list_sb.get(i).getpContent())),
					(new SimpleStringProperty(list_sb.get(i).getpAmount()))));
		}
		sbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		sbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		sbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		sbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		sbTableView.setItems(myList_sb);

		// �Ļ�� ��(meal bread) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_mb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�Ļ�� ��/�Ļ�")) {	
				list_mb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_mb = FXCollections.observableArrayList();
		for(int i=0;i<list_mb.size();i++) {
			myList_mb.add(new Product(new SimpleStringProperty(list_mb.get(i).getpNum()),
					(new SimpleStringProperty(list_mb.get(i).getpCate())), 
					(new SimpleStringProperty(list_mb.get(i).getpName())), 
					(new SimpleStringProperty(list_mb.get(i).getpImage())), 
					(new SimpleStringProperty(list_mb.get(i).getpDate())),
					(new SimpleStringProperty(list_mb.get(i).getpPrice())),
					(new SimpleStringProperty(list_mb.get(i).getpContent())),
					(new SimpleStringProperty(list_mb.get(i).getpAmount()))));
		}
		mbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		mbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		mbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		mbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		mbTableView.setItems(myList_mb);

		// ����(donut) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_donut.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����")) {	
				list_donut.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_donut = FXCollections.observableArrayList();
		for(int i=0;i<list_donut.size();i++) {
			myList_donut.add(new Product(new SimpleStringProperty(list_donut.get(i).getpNum()),
					(new SimpleStringProperty(list_donut.get(i).getpCate())), 
					(new SimpleStringProperty(list_donut.get(i).getpName())), 
					(new SimpleStringProperty(list_donut.get(i).getpImage())), 
					(new SimpleStringProperty(list_donut.get(i).getpDate())),
					(new SimpleStringProperty(list_donut.get(i).getpPrice())),
					(new SimpleStringProperty(list_donut.get(i).getpContent())),
					(new SimpleStringProperty(list_donut.get(i).getpAmount()))));
		}
		donutName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		donutPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		donutDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		donutAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		donutTableView.setItems(myList_donut);

		// ����(pie) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_pie.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�佺��/����")) {	
				list_pie.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_pie = FXCollections.observableArrayList();
		for(int i=0;i<list_pie.size();i++) {
			myList_pie.add(new Product(new SimpleStringProperty(list_pie.get(i).getpNum()),
					(new SimpleStringProperty(list_pie.get(i).getpCate())), 
					(new SimpleStringProperty(list_pie.get(i).getpName())), 
					(new SimpleStringProperty(list_pie.get(i).getpImage())), 
					(new SimpleStringProperty(list_pie.get(i).getpDate())),
					(new SimpleStringProperty(list_pie.get(i).getpPrice())),
					(new SimpleStringProperty(list_pie.get(i).getpContent())),
					(new SimpleStringProperty(list_pie.get(i).getpAmount()))));
		}
		pieName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		piePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		pieDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		pieAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		pieTableView.setItems(myList_pie);

		// ������(cook bread) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_cb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("������")) {	
				list_cb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_cb = FXCollections.observableArrayList();
		for(int i=0;i<list_cb.size();i++) {
			myList_cb.add(new Product(new SimpleStringProperty(list_cb.get(i).getpNum()),
					(new SimpleStringProperty(list_cb.get(i).getpCate())), 
					(new SimpleStringProperty(list_cb.get(i).getpName())), 
					(new SimpleStringProperty(list_cb.get(i).getpImage())), 
					(new SimpleStringProperty(list_cb.get(i).getpDate())),
					(new SimpleStringProperty(list_cb.get(i).getpPrice())),
					(new SimpleStringProperty(list_cb.get(i).getpContent())),
					(new SimpleStringProperty(list_cb.get(i).getpAmount()))));
		}
		cbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		cbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		cbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		cbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		cbTableView.setItems(myList_cb);

		// ����ũ(cake) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_cake.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����ũ")) {	
				list_cake.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_cake = FXCollections.observableArrayList();
		for(int i=0;i<list_cake.size();i++) {
			myList_cake.add(new Product(new SimpleStringProperty(list_cake.get(i).getpNum()),
					(new SimpleStringProperty(list_cake.get(i).getpCate())), 
					(new SimpleStringProperty(list_cake.get(i).getpName())), 
					(new SimpleStringProperty(list_cake.get(i).getpImage())), 
					(new SimpleStringProperty(list_cake.get(i).getpDate())),
					(new SimpleStringProperty(list_cake.get(i).getpPrice())),
					(new SimpleStringProperty(list_cake.get(i).getpContent())),
					(new SimpleStringProperty(list_cake.get(i).getpAmount()))));
		}
		cakeName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		cakePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		cakeDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		cakeAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		cakeTableView.setItems(myList_cake);

		// ������ġ(sandwich) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_sandwich.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("������ġ")) {	
				list_sandwich.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_sandwich = FXCollections.observableArrayList();
		for(int i=0;i<list_sandwich.size();i++) {
			myList_sandwich.add(new Product(new SimpleStringProperty(list_sandwich.get(i).getpNum()),
					(new SimpleStringProperty(list_sandwich.get(i).getpCate())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpName())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpImage())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpDate())),
					(new SimpleStringProperty(list_sandwich.get(i).getpPrice())),
					(new SimpleStringProperty(list_sandwich.get(i).getpContent())),
					(new SimpleStringProperty(list_sandwich.get(i).getpAmount()))));
		}
		sandwichName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		sandwichPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		sandwichDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		sandwichAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		sandwichTableView.setItems(myList_sandwich);

		// ����Ʈ(dessert) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_dessert.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����Ʈ")) {	
				list_dessert.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_dessert = FXCollections.observableArrayList();
		for(int i=0;i<list_dessert.size();i++) {
			myList_dessert.add(new Product(new SimpleStringProperty(list_dessert.get(i).getpNum()),
					(new SimpleStringProperty(list_dessert.get(i).getpCate())), 
					(new SimpleStringProperty(list_dessert.get(i).getpName())), 
					(new SimpleStringProperty(list_dessert.get(i).getpImage())), 
					(new SimpleStringProperty(list_dessert.get(i).getpDate())),
					(new SimpleStringProperty(list_dessert.get(i).getpPrice())),
					(new SimpleStringProperty(list_dessert.get(i).getpContent())),
					(new SimpleStringProperty(list_dessert.get(i).getpAmount()))));
		}
		dessertName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		dessertPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		dessertDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		dessertAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		dessertTableView.setItems(myList_dessert);

		// ����(drink) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_drink.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����")) {	
				list_drink.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_drink = FXCollections.observableArrayList();
		for(int i=0;i<list_drink.size();i++) {
			myList_drink.add(new Product(new SimpleStringProperty(list_drink.get(i).getpNum()),
					(new SimpleStringProperty(list_drink.get(i).getpCate())), 
					(new SimpleStringProperty(list_drink.get(i).getpName())), 
					(new SimpleStringProperty(list_drink.get(i).getpImage())), 
					(new SimpleStringProperty(list_drink.get(i).getpDate())),
					(new SimpleStringProperty(list_drink.get(i).getpPrice())),
					(new SimpleStringProperty(list_drink.get(i).getpContent())),
					(new SimpleStringProperty(list_drink.get(i).getpAmount()))));
		}
		drinkName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		drinkPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		drinkDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		drinkAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		drinkTableView.setItems(myList_drink);

		// �귱ġ(brunch) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_brunch.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�귱ġ")) {	
				list_brunch.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_brunch = FXCollections.observableArrayList();
		for(int i=0;i<list_brunch.size();i++) {
			myList_brunch.add(new Product(new SimpleStringProperty(list_brunch.get(i).getpNum()),
					(new SimpleStringProperty(list_brunch.get(i).getpCate())), 
					(new SimpleStringProperty(list_brunch.get(i).getpName())), 
					(new SimpleStringProperty(list_brunch.get(i).getpImage())), 
					(new SimpleStringProperty(list_brunch.get(i).getpDate())),
					(new SimpleStringProperty(list_brunch.get(i).getpPrice())),
					(new SimpleStringProperty(list_brunch.get(i).getpContent())),
					(new SimpleStringProperty(list_brunch.get(i).getpAmount()))));
		}
		brunchName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		brunchPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		brunchDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		brunchAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		brunchTableView.setItems(myList_brunch);

		// ��Ÿ(etc) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_etc.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("��Ÿ")) {	
				list_etc.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_etc = FXCollections.observableArrayList();
		for(int i=0;i<list_etc.size();i++) {
			myList_etc.add(new Product(new SimpleStringProperty(list_etc.get(i).getpNum()),
					(new SimpleStringProperty(list_etc.get(i).getpCate())), 
					(new SimpleStringProperty(list_etc.get(i).getpName())), 
					(new SimpleStringProperty(list_etc.get(i).getpImage())), 
					(new SimpleStringProperty(list_etc.get(i).getpDate())),
					(new SimpleStringProperty(list_etc.get(i).getpPrice())),
					(new SimpleStringProperty(list_etc.get(i).getpContent())),
					(new SimpleStringProperty(list_etc.get(i).getpAmount()))));
		}
		etcName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		etcPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		etcDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		etcAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		etcTableView.setItems(myList_etc);

		// ��ǰ �� ��� ������Ʈ -------------------------------------------------------------------- ����

		// ���� ����Ʈ �ҽ� -------------------------------------------------------------------------- ����

		payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		paylist.setItems(payObser);

		paySum.setText(paySum_backup);

		// �� �̹��� �� ���� ������ ������ֱ�
		String aPath = path.class.getResource("").getPath();
		String bPath = TextPath.class.getResource("").getPath();

		// ���Ŀ� ��(snack bread) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		sbTableView.setOnMouseClicked(event -> {
			if(sbTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= sbTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_sb.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_sb.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_sb.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_sb.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_sb.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_sb.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_sb.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_sb.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_sb.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_sb.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_sb.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_sb.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_sb.get(indexNum).getpCate(),list_sb.get(indexNum).getpName(), no, list_sb.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_sb.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("���Ŀ� ��")) {	
							list_sb.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_sb = FXCollections.observableArrayList();
					for(int i=0;i<list_sb.size();i++) {
						myList_sb.add(new Product(new SimpleStringProperty(list_sb.get(i).getpNum()),
								(new SimpleStringProperty(list_sb.get(i).getpCate())), 
								(new SimpleStringProperty(list_sb.get(i).getpName())), 
								(new SimpleStringProperty(list_sb.get(i).getpImage())), 
								(new SimpleStringProperty(list_sb.get(i).getpDate())),
								(new SimpleStringProperty(list_sb.get(i).getpPrice())),
								(new SimpleStringProperty(list_sb.get(i).getpContent())),
								(new SimpleStringProperty(list_sb.get(i).getpAmount()))));
					}
					sbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					sbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					sbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					sbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					sbTableView.setItems(myList_sb);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// �Ļ�� ��(meal bread) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		mbTableView.setOnMouseClicked(event -> {
			if(mbTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= mbTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_mb.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_mb.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_mb.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_mb.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_mb.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_mb.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_mb.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_mb.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_mb.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_mb.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_mb.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_mb.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_mb.get(indexNum).getpCate(),list_mb.get(indexNum).getpName(), no, list_mb.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_mb.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("�Ļ�� ��/�Ļ�")) {	
							list_mb.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_mb = FXCollections.observableArrayList();
					for(int i=0;i<list_mb.size();i++) {
						myList_mb.add(new Product(new SimpleStringProperty(list_mb.get(i).getpNum()),
								(new SimpleStringProperty(list_mb.get(i).getpCate())), 
								(new SimpleStringProperty(list_mb.get(i).getpName())), 
								(new SimpleStringProperty(list_mb.get(i).getpImage())), 
								(new SimpleStringProperty(list_mb.get(i).getpDate())),
								(new SimpleStringProperty(list_mb.get(i).getpPrice())),
								(new SimpleStringProperty(list_mb.get(i).getpContent())),
								(new SimpleStringProperty(list_mb.get(i).getpAmount()))));
					}
					mbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					mbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					mbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					mbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					mbTableView.setItems(myList_mb);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ����(donut) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		donutTableView.setOnMouseClicked(event -> {
			if(donutTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= donutTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_donut.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_donut.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_donut.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_donut.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_donut.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_donut.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_donut.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_donut.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_donut.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_donut.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_donut.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_donut.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_donut.get(indexNum).getpCate(),list_donut.get(indexNum).getpName(), no, list_donut.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_donut.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("����")) {	
							list_donut.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_donut = FXCollections.observableArrayList();
					for(int i=0;i<list_donut.size();i++) {
						myList_donut.add(new Product(new SimpleStringProperty(list_donut.get(i).getpNum()),
								(new SimpleStringProperty(list_donut.get(i).getpCate())), 
								(new SimpleStringProperty(list_donut.get(i).getpName())), 
								(new SimpleStringProperty(list_donut.get(i).getpImage())), 
								(new SimpleStringProperty(list_donut.get(i).getpDate())),
								(new SimpleStringProperty(list_donut.get(i).getpPrice())),
								(new SimpleStringProperty(list_donut.get(i).getpContent())),
								(new SimpleStringProperty(list_donut.get(i).getpAmount()))));
					}
					donutName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					donutPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					donutDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					donutAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					donutTableView.setItems(myList_donut);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ����(pie) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		pieTableView.setOnMouseClicked(event -> {
			if(pieTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= pieTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_pie.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_pie.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_pie.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_pie.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_pie.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_pie.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_pie.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_pie.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_pie.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_pie.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_pie.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_pie.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_pie.get(indexNum).getpCate(),list_pie.get(indexNum).getpName(), no, list_pie.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_pie.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("�佺��/����")) {	
							list_pie.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_pie = FXCollections.observableArrayList();
					for(int i=0;i<list_pie.size();i++) {
						myList_pie.add(new Product(new SimpleStringProperty(list_pie.get(i).getpNum()),
								(new SimpleStringProperty(list_pie.get(i).getpCate())), 
								(new SimpleStringProperty(list_pie.get(i).getpName())), 
								(new SimpleStringProperty(list_pie.get(i).getpImage())), 
								(new SimpleStringProperty(list_pie.get(i).getpDate())),
								(new SimpleStringProperty(list_pie.get(i).getpPrice())),
								(new SimpleStringProperty(list_pie.get(i).getpContent())),
								(new SimpleStringProperty(list_pie.get(i).getpAmount()))));
					}
					pieName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					piePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					pieDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					pieAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					pieTableView.setItems(myList_pie);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ������(cook bread) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		cbTableView.setOnMouseClicked(event -> {
			if(cbTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= cbTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_cb.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_cb.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_cb.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_cb.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_cb.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_cb.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_cb.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_cb.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_cb.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_cb.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_cb.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_cb.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_cb.get(indexNum).getpCate(),list_cb.get(indexNum).getpName(), no, list_cb.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_cb.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("������")) {	
							list_cb.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_cb = FXCollections.observableArrayList();
					for(int i=0;i<list_cb.size();i++) {
						myList_cb.add(new Product(new SimpleStringProperty(list_cb.get(i).getpNum()),
								(new SimpleStringProperty(list_cb.get(i).getpCate())), 
								(new SimpleStringProperty(list_cb.get(i).getpName())), 
								(new SimpleStringProperty(list_cb.get(i).getpImage())), 
								(new SimpleStringProperty(list_cb.get(i).getpDate())),
								(new SimpleStringProperty(list_cb.get(i).getpPrice())),
								(new SimpleStringProperty(list_cb.get(i).getpContent())),
								(new SimpleStringProperty(list_cb.get(i).getpAmount()))));
					}
					cbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					cbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					cbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					cbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					cbTableView.setItems(myList_cb);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ����ũ(cake) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		cakeTableView.setOnMouseClicked(event -> {
			if(cakeTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= cakeTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_cake.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_cake.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_cake.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_cake.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_cake.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_cake.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_cake.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_cake.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_cake.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_cake.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_cake.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_cake.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_cake.get(indexNum).getpCate(),list_cake.get(indexNum).getpName(), no, list_cake.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_cake.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("����ũ")) {	
							list_cake.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_cake = FXCollections.observableArrayList();
					for(int i=0;i<list_cake.size();i++) {
						myList_cake.add(new Product(new SimpleStringProperty(list_cake.get(i).getpNum()),
								(new SimpleStringProperty(list_cake.get(i).getpCate())), 
								(new SimpleStringProperty(list_cake.get(i).getpName())), 
								(new SimpleStringProperty(list_cake.get(i).getpImage())), 
								(new SimpleStringProperty(list_cake.get(i).getpDate())),
								(new SimpleStringProperty(list_cake.get(i).getpPrice())),
								(new SimpleStringProperty(list_cake.get(i).getpContent())),
								(new SimpleStringProperty(list_cake.get(i).getpAmount()))));
					}
					cakeName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					cakePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					cakeDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					cakeAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					cakeTableView.setItems(myList_cake);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ������ġ(sandwich) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		sandwichTableView.setOnMouseClicked(event -> {
			if(sandwichTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= sandwichTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_sandwich.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_sandwich.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_sandwich.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_sandwich.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_sandwich.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_sandwich.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_sandwich.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_sandwich.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_sandwich.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_sandwich.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_sandwich.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_sandwich.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_sandwich.get(indexNum).getpCate(),list_sandwich.get(indexNum).getpName(), no, list_sandwich.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_sandwich.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("������ġ")) {	
							list_sandwich.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_sandwich = FXCollections.observableArrayList();
					for(int i=0;i<list_sandwich.size();i++) {
						myList_sandwich.add(new Product(new SimpleStringProperty(list_sandwich.get(i).getpNum()),
								(new SimpleStringProperty(list_sandwich.get(i).getpCate())), 
								(new SimpleStringProperty(list_sandwich.get(i).getpName())), 
								(new SimpleStringProperty(list_sandwich.get(i).getpImage())), 
								(new SimpleStringProperty(list_sandwich.get(i).getpDate())),
								(new SimpleStringProperty(list_sandwich.get(i).getpPrice())),
								(new SimpleStringProperty(list_sandwich.get(i).getpContent())),
								(new SimpleStringProperty(list_sandwich.get(i).getpAmount()))));
					}
					sandwichName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					sandwichPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					sandwichDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					sandwichAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					sandwichTableView.setItems(myList_sandwich);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ����Ʈ(dessert) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		dessertTableView.setOnMouseClicked(event -> {
			if(dessertTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= dessertTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_dessert.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_dessert.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_dessert.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_dessert.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_dessert.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_dessert.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_dessert.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_dessert.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_dessert.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_dessert.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_dessert.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_dessert.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_dessert.get(indexNum).getpCate(),list_dessert.get(indexNum).getpName(), no, list_dessert.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_dessert.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("����Ʈ")) {	
							list_dessert.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_dessert = FXCollections.observableArrayList();
					for(int i=0;i<list_dessert.size();i++) {
						myList_dessert.add(new Product(new SimpleStringProperty(list_dessert.get(i).getpNum()),
								(new SimpleStringProperty(list_dessert.get(i).getpCate())), 
								(new SimpleStringProperty(list_dessert.get(i).getpName())), 
								(new SimpleStringProperty(list_dessert.get(i).getpImage())), 
								(new SimpleStringProperty(list_dessert.get(i).getpDate())),
								(new SimpleStringProperty(list_dessert.get(i).getpPrice())),
								(new SimpleStringProperty(list_dessert.get(i).getpContent())),
								(new SimpleStringProperty(list_dessert.get(i).getpAmount()))));
					}
					dessertName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					dessertPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					dessertDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					dessertAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					dessertTableView.setItems(myList_dessert);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ����(drink) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		drinkTableView.setOnMouseClicked(event -> {
			if(drinkTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= drinkTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_drink.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_drink.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_drink.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_drink.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_drink.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_drink.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_drink.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_drink.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_drink.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_drink.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_drink.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_drink.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_drink.get(indexNum).getpCate(),list_drink.get(indexNum).getpName(), no, list_drink.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_drink.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("����")) {	
							list_drink.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_drink = FXCollections.observableArrayList();
					for(int i=0;i<list_drink.size();i++) {
						myList_drink.add(new Product(new SimpleStringProperty(list_drink.get(i).getpNum()),
								(new SimpleStringProperty(list_drink.get(i).getpCate())), 
								(new SimpleStringProperty(list_drink.get(i).getpName())), 
								(new SimpleStringProperty(list_drink.get(i).getpImage())), 
								(new SimpleStringProperty(list_drink.get(i).getpDate())),
								(new SimpleStringProperty(list_drink.get(i).getpPrice())),
								(new SimpleStringProperty(list_drink.get(i).getpContent())),
								(new SimpleStringProperty(list_drink.get(i).getpAmount()))));
					}
					drinkName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					drinkPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					drinkDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					drinkAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					drinkTableView.setItems(myList_drink);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// �귱ġ(brunch) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		brunchTableView.setOnMouseClicked(event -> {
			if(brunchTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= brunchTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_brunch.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_brunch.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_brunch.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_brunch.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_brunch.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_brunch.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_brunch.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_brunch.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_brunch.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_brunch.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_brunch.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_brunch.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					// ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_brunch.get(indexNum).getpCate(),list_brunch.get(indexNum).getpName(), no, list_brunch.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_brunch.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("�귱ġ")) {	
							list_brunch.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_brunch = FXCollections.observableArrayList();
					for(int i=0;i<list_brunch.size();i++) {
						myList_brunch.add(new Product(new SimpleStringProperty(list_brunch.get(i).getpNum()),
								(new SimpleStringProperty(list_brunch.get(i).getpCate())), 
								(new SimpleStringProperty(list_brunch.get(i).getpName())), 
								(new SimpleStringProperty(list_brunch.get(i).getpImage())), 
								(new SimpleStringProperty(list_brunch.get(i).getpDate())),
								(new SimpleStringProperty(list_brunch.get(i).getpPrice())),
								(new SimpleStringProperty(list_brunch.get(i).getpContent())),
								(new SimpleStringProperty(list_brunch.get(i).getpAmount()))));
					}
					brunchName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					brunchPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					brunchDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					brunchAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					brunchTableView.setItems(myList_brunch);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ��Ÿ(etc) ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!---------------------------------------------------------
		etcTableView.setOnMouseClicked(event -> {
			if(etcTableView.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= etcTableView.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_etc.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_etc.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_etc.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_etc.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_etc.get(indexNum).getpAmount())-1));
					for(int i=0;i<list.size();i++) {
						if(list_etc.get(indexNum).getpNum().equals(list.get(i).getpNum())) {
							list.get(i).setpAmount(list_etc.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<list.size(); i++){
							fw.write(list.get(i).getpNum());fw.write(",");
							fw.write(list.get(i).getpCate());fw.write(",");
							fw.write(list.get(i).getpName());fw.write(",");
							fw.write(list.get(i).getpImage());fw.write(",");
							fw.write(list.get(i).getpDate().toString());fw.write(",");
							fw.write(list.get(i).getpPrice());fw.write(",");
							fw.write(list.get(i).getpContent());fw.write(",");
							fw.write(list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<paymentlist.size();i++) {
						if(paymentlist.get(i).getpName().equals(list_etc.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<payObser.size();i++) {
						if(payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_etc.get(indexNum).getpName())).toString())) {
							payObser.remove(i);
						}
					}

					payObser.add(new Product(new SimpleStringProperty(list_etc.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_etc.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_etc.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(payObser);

					//  ��������Ʈ�� ���
					String no = 1+"";
					paymentlist.add(new Product(list_etc.get(indexNum).getpCate(),list_etc.get(indexNum).getpName(), no, list_etc.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<paymentlist.size();i++) {
						sum += Integer.parseInt(paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");
					list_etc.clear();
					for(int i=0;i<list.size();i++) {
						if((list.get(i).getpCate()).equals("��Ÿ")) {	
							list_etc.add(new Product((list.get(i).getpNum()),
									(list.get(i).getpCate()),
									(list.get(i).getpName()),
									(list.get(i).getpImage()),
									(list.get(i).getpDate()),
									(list.get(i).getpPrice()),
									(list.get(i).getpContent()),
									(list.get(i).getpAmount())));
						}
					}
					myList_etc = FXCollections.observableArrayList();
					for(int i=0;i<list_etc.size();i++) {
						myList_etc.add(new Product(new SimpleStringProperty(list_etc.get(i).getpNum()),
								(new SimpleStringProperty(list_etc.get(i).getpCate())), 
								(new SimpleStringProperty(list_etc.get(i).getpName())), 
								(new SimpleStringProperty(list_etc.get(i).getpImage())), 
								(new SimpleStringProperty(list_etc.get(i).getpDate())),
								(new SimpleStringProperty(list_etc.get(i).getpPrice())),
								(new SimpleStringProperty(list_etc.get(i).getpContent())),
								(new SimpleStringProperty(list_etc.get(i).getpAmount()))));
					}
					etcName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					etcPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					etcDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					etcAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					etcTableView.setItems(myList_etc);
				}
				else {
					System.out.println("������ �����մϴ�.");
					// ���� �޽����� �����ش�.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("����");
					alert.setHeaderText("");
					alert.setContentText("������ �����մϴ�.");
					alert.showAndWait();
				}
			}});

		// ��������Ʈ ���̺� ���콺 Ŭ�� �̺�Ʈ������ ����!!!
		paylist.setOnMouseClicked(event -> {
			if(paylist.getSelectionModel().getSelectedItem() != null) {
				int itemNum = paylist.getSelectionModel().getFocusedIndex();

				//payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(list_sub.get(i).getpName()).toString())
				//�� ��ǰ�з��� ���� ���� ����
				String str=payObser.get(itemNum).getpCate2().toString();
				System.out.println(str);
				ArrayList<Product> list_sub = new ArrayList<Product>();
				ObservableList<Product> mylist_sub;
				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("���Ŀ� ��").toString())) {
					list_sub = list_sb;
					mylist_sub = myList_sb;
					subtTableView = sbTableView;
					subName = sbName;
					subPrice = sbPrice;
					subDate = sbDate;
					subAmount = sbAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("�Ļ�� ��/�Ļ�").toString())) {
					list_sub = list_mb;
					mylist_sub = myList_mb;
					subtTableView = mbTableView;
					subName = mbName;
					subPrice = mbPrice;
					subDate = mbDate;
					subAmount = mbAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("����").toString())) {
					list_sub = list_donut;
					mylist_sub = myList_donut;
					subtTableView = donutTableView;
					subName = donutName;
					subPrice = donutPrice;
					subDate = donutDate;
					subAmount = donutAmount;
				}


				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("�佺��/����").toString())) {
					list_sub = list_pie;
					mylist_sub = myList_pie;
					subtTableView = pieTableView;
					subName = pieName;
					subPrice = piePrice;
					subDate = pieDate;
					subAmount = pieAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("������").toString())) {
					list_sub = list_cb;
					mylist_sub = myList_cb;
					subtTableView = cbTableView;
					subName = cbName;
					subPrice = cbPrice;
					subDate = cbDate;
					subAmount = cbAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("����ũ").toString())) {
					list_sub = list_cake;
					mylist_sub = myList_cake;
					subtTableView = cakeTableView;
					subName = cakeName;
					subPrice = cakePrice;
					subDate = cakeDate;
					subAmount = cakeAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("������ġ").toString())) {
					list_sub = list_sandwich;
					mylist_sub = myList_sandwich;
					subtTableView = sandwichTableView;
					subName = sandwichName;
					subPrice = sandwichPrice;
					subDate = sandwichDate;
					subAmount = sandwichAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("����Ʈ").toString())) {
					list_sub = list_dessert;
					mylist_sub = myList_dessert;
					subtTableView = dessertTableView;
					subName = dessertName;
					subPrice = dessertPrice;
					subDate = dessertDate;
					subAmount = dessertAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("����").toString())) {
					list_sub = list_drink;
					mylist_sub = myList_drink;
					subtTableView = drinkTableView;
					subName = drinkName;
					subPrice = drinkPrice;
					subDate = drinkDate;
					subAmount = drinkAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("�귱ġ").toString())) {
					list_sub = list_brunch;
					mylist_sub = myList_brunch;
					subtTableView = brunchTableView;
					subName = brunchName;
					subPrice = brunchPrice;
					subDate = brunchDate;
					subAmount = brunchAmount;
				}

				if(payObser.get(itemNum).getpCate2().toString().equals(new SimpleStringProperty("��Ÿ").toString())) {
					list_sub = list_etc;
					mylist_sub = myList_etc;
					subtTableView = etcTableView;
					subName = etcName;
					subPrice = etcPrice;
					subDate = etcDate;
					subAmount = etcAmount;
				}

				int productDis=0;
				for(int i=0;i<list_sub.size();i++) {
					if(payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(list_sub.get(i).getpName()).toString())) {
						list_sub.get(i).setpAmount(String.valueOf(Integer.parseInt(list_sub.get(i).getpAmount())+1));
						productDis++;
						for(int j=0;j<list.size();j++) {
							if(list_sub.get(i).getpName().equals(list.get(j).getpName())) {
								list.get(j).setpAmount(list_sub.get(i).getpAmount());
							}
						}
					}
				}
				System.out.println("��������Ʈ ��ǰ ���� Ƚ��" + productDis);

				// ��������Ʈ �ߺ��� �߰��� �Ѱ� ����
				for(int i=0;i<paymentlist.size();i++) {
					if(payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(paymentlist.get(i).getpName()).toString())) {
						paymentlist.remove(i);
						break;
					}
				}
				// ���� �ߺ��� ����
				int count=0;
				for(int i=0;i<paymentlist.size();i++) {
					if(payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(paymentlist.get(i).getpName()).toString())) {
						count++;
					}
				}
				// ���� �ߺ��� ����
				for(int i=0;i<paymentlist.size();i++) {
					if(payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(paymentlist.get(i).getpName()).toString())) {
						payObser.get(itemNum).setpAmount2(new SimpleStringProperty(String.valueOf(count)));
						break;
					}
				}
				// ������� ��ǰ0�� ����
				if(count==0) {
					paylist.getItems().remove(itemNum);
				}
				// ��������Ʈ ���ΰ�ħ.
				payObser_sub.clear();
				for(int i=0;i<payObser.size();i++) {
					payObser_sub.add(new Product((payObser.get(i).getpNum2()),
							(payObser.get(i).getpCate2()),
							(payObser.get(i).getpName2()),
							(payObser.get(i).getpImage2()),
							(payObser.get(i).getpDate2()),
							(payObser.get(i).getpPrice2()),
							(payObser.get(i).getpContent2()),
							(payObser.get(i).getpAmount2())));
				}
				payObser.clear();
				for(int i=0;i<payObser_sub.size();i++) {
					payObser.add(new Product((payObser_sub.get(i).getpNum2()),
							(payObser_sub.get(i).getpCate2()),
							(payObser_sub.get(i).getpName2()),
							(payObser_sub.get(i).getpImage2()),
							(payObser_sub.get(i).getpDate2()),
							(payObser_sub.get(i).getpPrice2()),
							(payObser_sub.get(i).getpContent2()),
							(payObser_sub.get(i).getpAmount2())));
				}

				payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
				payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
				payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
				paylist.setItems(payObser);

				//���� �ݾ� �հ�
				int sum = 0;
				for(int i=0;i<paymentlist.size();i++) {
					sum += Integer.parseInt(paymentlist.get(i).getpPrice());
				}
				paySum.setText(sum + "");
				try (FileWriter fw = new FileWriter("Product/product.txt")) {
					for(int i=0; i<list.size(); i++) {
						fw.write(list.get(i).getpNum());
						fw.write(",");
						fw.write(list.get(i).getpCate());
						fw.write(",");
						fw.write(list.get(i).getpName());
						fw.write(",");
						fw.write(list.get(i).getpImage());
						fw.write(",");
						fw.write(list.get(i).getpDate().toString());
						fw.write(",");
						fw.write(list.get(i).getpPrice());
						fw.write(",");
						fw.write(list.get(i).getpContent());
						fw.write(",");
						fw.write(list.get(i).getpAmount());
						fw.write("\r\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				mylist_sub = FXCollections.observableArrayList();
				for(int i=0;i<list_sub.size();i++) {
					mylist_sub.add(new Product(new SimpleStringProperty(list_sub.get(i).getpNum()),
							(new SimpleStringProperty(list_sub.get(i).getpCate())), 
							(new SimpleStringProperty(list_sub.get(i).getpName())), 
							(new SimpleStringProperty(list_sub.get(i).getpImage())), 
							(new SimpleStringProperty(list_sub.get(i).getpDate())),
							(new SimpleStringProperty(list_sub.get(i).getpPrice())),
							(new SimpleStringProperty(list_sub.get(i).getpContent())),
							(new SimpleStringProperty(list_sub.get(i).getpAmount()))));
				}
				subName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
				subPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
				subDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
				subAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
				subtTableView.setItems(mylist_sub);
			}
		});
	}
	// ���� ����Ʈ �ҽ� ----------------------------------------------------------------------- ����




	// ��ư �޼ҵ� ----------------------------------------------------------------------------- ����
	private void paymentBtn(ActionEvent event) {
		// ���� ��� (Data����)
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date));

		// result ������ ���� ���� ����
		ArrayList<Product> paymentlist_sub = new ArrayList<Product>();
		int amountCount;
		// paymentlist ���� �ڷḦ �ӽ� ������ �����Ѵ�. -> �ڷ� ������ �ϱ� ���ؼ�
		for(int i=0;i<paymentlist.size();i++) {
			paymentlist_sub.add(new Product(paymentlist.get(i).getpCate(),
					paymentlist.get(i).getpName(),
					paymentlist.get(i).getpAmount(),
					paymentlist.get(i).getpPrice()));
		}
		// paymentlist_sub �ߺ����� �̸��� "0"���� �ٲ۴�.
		for(int i=0;i<paymentlist_sub.size();i++) {
			for(int j=i+1;j<paymentlist_sub.size();j++) {
				if(paymentlist_sub.get(i).getpName().equals(paymentlist_sub.get(j).getpName())) {
					paymentlist_sub.get(i).setpName("0");
				}
			}
		}
		// paymentlist_sub �̸� ���� �������� �������� ������ ���Ѵ�.
		for(int i=0;i<paymentlist_sub.size();i++) {
			amountCount=1;
			for(int j=0;j<paymentlist.size();j++) {
				if(paymentlist_sub.get(i).getpName().equals(paymentlist.get(j).getpName())) {
					amountCount++;
				}
				paymentlist_sub.get(i).setpAmount(String.valueOf(amountCount-1));
			}
		}

		// paymentlist_sub ���� �հ� ����
		for(int i=0;i<paymentlist_sub.size();i++) {
			paymentlist_sub.get(i).setpPrice(String.valueOf((Integer.parseInt(paymentlist_sub.get(i).getpPrice())*(Integer.parseInt(paymentlist_sub.get(i).getpAmount())))));
		}

		// result ������ paymentlist_sub �̸� ���� "0"�� �ƴ� �������� �ִ´�.
		result.clear();
		for(int i=0;i<paymentlist_sub.size();i++) {
			if(!(paymentlist_sub.get(i).getpName().equals("0"))) {
				result.add(new Product(paymentlist_sub.get(i).getpCate(), paymentlist_sub.get(i).getpName(), paymentlist_sub.get(i).getpAmount(), paymentlist_sub.get(i).getpPrice()));
			}
		}

		// ��ǰ����� ��� ����
		try (FileWriter fw = new FileWriter("Payment/pay_item_"+now +".txt")) {
			for(int i=0; i<result.size(); i++){
				fw.write(result.get(i).getpCate());
				fw.write(",");
				fw.write(result.get(i).getpName());
				fw.write(",");
				fw.write(result.get(i).getpAmount());
				fw.write(",");
				fw.write(result.get(i).getpPrice());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		// ��ǰ��ο� �հ谡 ��� �ڷḦ ����
		try (FileWriter fw = new FileWriter("Payment/pay.txt")) {
			fw.write("Payment/pay_item_"+now+".txt");
			fw.write(",");
			fw.write(paySum.getText());
			fw.write("\r\n");

		}catch (Exception e) {
			e.printStackTrace();
		}


		// ����������� ����, ���� ����
		String now2 = (new SimpleDateFormat("yyyy-MM-dd").format(date));
		try (FileWriter fw = new FileWriter("Payment/pay_"+now2 +".txt", true)) {
			for(int i=0; i<result.size(); i++){
				fw.write(result.get(i).getpCate());
				fw.write(",");
				fw.write(result.get(i).getpName());
				fw.write(",");
				fw.write(result.get(i).getpAmount());
				fw.write(",");
				fw.write(result.get(i).getpPrice());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		// ����������� ����, ��� ����
		String now3 = (new SimpleDateFormat("yyyy-MM").format(date));
		try (FileWriter fw = new FileWriter("Payment/pay_"+now3 +".txt", true)) {
			for(int i=0; i<result.size(); i++){
				fw.write(result.get(i).getpCate());
				fw.write(",");
				fw.write(result.get(i).getpName());
				fw.write(",");
				fw.write(result.get(i).getpAmount());
				fw.write(",");
				fw.write(result.get(i).getpPrice());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		// ����������� ����, ��ǰ�� �Ǹŷ�
		try (FileWriter fw = new FileWriter("Payment/pay_cate.txt", true)) {
			for(int i=0; i<result.size(); i++){
				fw.write(result.get(i).getpCate());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("���� �޴��� �̵�");

		int sum = 0; // ���ݾ� �ʱ�ȭ
		paySum.setText(sum + "");

		try {
			Parent payment = FXMLLoader.load(getClass().getResource("/com/paris/view/pay.fxml"));
			Scene scene = new Scene(payment);
			Stage primaryStage = (Stage)paymentBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}

		payObser.clear();
		paymentlist.clear();

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

	private void searchMainBtn(ActionEvent event) {
		System.out.println("����ȭ�� ��ǰ�˻�â���� �̵�");
		try {
			Parent mainSearch = FXMLLoader.load(getClass().getResource("/com/paris/view/mainSearch.fxml"));
			Scene scene = new Scene(mainSearch);
			Stage primaryStage = (Stage)searchMainBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payInitBtn(ActionEvent event) {

		//��������Ʈ�� �ִ� ��ǰ ��� ���� ����.
		for(int i=0;i<paymentlist.size();i++) {
			for(int j=0;j<list.size();j++) {
				if(paymentlist.get(i).getpName().equals(list.get(j).getpName())) {
					list.get(j).setpAmount(String.valueOf(Integer.parseInt(list.get(j).getpAmount())+1));
				}
			}
		}
		try (FileWriter fw = new FileWriter("Product/product.txt")) {
			for(int i=0; i<list.size(); i++) {
				fw.write(list.get(i).getpNum());
				fw.write(",");
				fw.write(list.get(i).getpCate());
				fw.write(",");
				fw.write(list.get(i).getpName());
				fw.write(",");
				fw.write(list.get(i).getpImage());
				fw.write(",");
				fw.write(list.get(i).getpDate().toString());
				fw.write(",");
				fw.write(list.get(i).getpPrice());
				fw.write(",");
				fw.write(list.get(i).getpContent());
				fw.write(",");
				fw.write(list.get(i).getpAmount());
				fw.write("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		// ���Ŀ� ��(snack bread)��ǰ ��� �ҷ����� -------------------------------------------------------------------------- 
		list_sb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("���Ŀ� ��")) {	
				list_sb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_sb = FXCollections.observableArrayList();
		for(int i=0;i<list_sb.size();i++) {
			myList_sb.add(new Product(new SimpleStringProperty(list_sb.get(i).getpNum()),
					(new SimpleStringProperty(list_sb.get(i).getpCate())), 
					(new SimpleStringProperty(list_sb.get(i).getpName())), 
					(new SimpleStringProperty(list_sb.get(i).getpImage())), 
					(new SimpleStringProperty(list_sb.get(i).getpDate())),
					(new SimpleStringProperty(list_sb.get(i).getpPrice())),
					(new SimpleStringProperty(list_sb.get(i).getpContent())),
					(new SimpleStringProperty(list_sb.get(i).getpAmount()))));
		}
		sbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		sbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		sbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		sbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		sbTableView.setItems(myList_sb);

		// �Ļ�� ��(meal bread) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_mb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�Ļ�� ��/�Ļ�")) {	
				list_mb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_mb = FXCollections.observableArrayList();
		for(int i=0;i<list_mb.size();i++) {
			myList_mb.add(new Product(new SimpleStringProperty(list_mb.get(i).getpNum()),
					(new SimpleStringProperty(list_mb.get(i).getpCate())), 
					(new SimpleStringProperty(list_mb.get(i).getpName())), 
					(new SimpleStringProperty(list_mb.get(i).getpImage())), 
					(new SimpleStringProperty(list_mb.get(i).getpDate())),
					(new SimpleStringProperty(list_mb.get(i).getpPrice())),
					(new SimpleStringProperty(list_mb.get(i).getpContent())),
					(new SimpleStringProperty(list_mb.get(i).getpAmount()))));
		}
		mbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		mbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		mbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		mbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		mbTableView.setItems(myList_mb);

		// ����(donut) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_donut.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����")) {	
				list_donut.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_donut = FXCollections.observableArrayList();
		for(int i=0;i<list_donut.size();i++) {
			myList_donut.add(new Product(new SimpleStringProperty(list_donut.get(i).getpNum()),
					(new SimpleStringProperty(list_donut.get(i).getpCate())), 
					(new SimpleStringProperty(list_donut.get(i).getpName())), 
					(new SimpleStringProperty(list_donut.get(i).getpImage())), 
					(new SimpleStringProperty(list_donut.get(i).getpDate())),
					(new SimpleStringProperty(list_donut.get(i).getpPrice())),
					(new SimpleStringProperty(list_donut.get(i).getpContent())),
					(new SimpleStringProperty(list_donut.get(i).getpAmount()))));
		}
		donutName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		donutPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		donutDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		donutAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		donutTableView.setItems(myList_donut);

		// ����(pie) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_pie.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�佺��/����")) {	
				list_pie.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_pie = FXCollections.observableArrayList();
		for(int i=0;i<list_pie.size();i++) {
			myList_pie.add(new Product(new SimpleStringProperty(list_pie.get(i).getpNum()),
					(new SimpleStringProperty(list_pie.get(i).getpCate())), 
					(new SimpleStringProperty(list_pie.get(i).getpName())), 
					(new SimpleStringProperty(list_pie.get(i).getpImage())), 
					(new SimpleStringProperty(list_pie.get(i).getpDate())),
					(new SimpleStringProperty(list_pie.get(i).getpPrice())),
					(new SimpleStringProperty(list_pie.get(i).getpContent())),
					(new SimpleStringProperty(list_pie.get(i).getpAmount()))));
		}
		pieName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		piePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		pieDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		pieAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		pieTableView.setItems(myList_pie);

		// ������(cook bread) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_cb.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("������")) {	
				list_cb.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_cb = FXCollections.observableArrayList();
		for(int i=0;i<list_cb.size();i++) {
			myList_cb.add(new Product(new SimpleStringProperty(list_cb.get(i).getpNum()),
					(new SimpleStringProperty(list_cb.get(i).getpCate())), 
					(new SimpleStringProperty(list_cb.get(i).getpName())), 
					(new SimpleStringProperty(list_cb.get(i).getpImage())), 
					(new SimpleStringProperty(list_cb.get(i).getpDate())),
					(new SimpleStringProperty(list_cb.get(i).getpPrice())),
					(new SimpleStringProperty(list_cb.get(i).getpContent())),
					(new SimpleStringProperty(list_cb.get(i).getpAmount()))));
		}
		cbName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		cbPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		cbDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		cbAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		cbTableView.setItems(myList_cb);

		// ����ũ(cake) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_cake.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����ũ")) {	
				list_cake.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_cake = FXCollections.observableArrayList();
		for(int i=0;i<list_cake.size();i++) {
			myList_cake.add(new Product(new SimpleStringProperty(list_cake.get(i).getpNum()),
					(new SimpleStringProperty(list_cake.get(i).getpCate())), 
					(new SimpleStringProperty(list_cake.get(i).getpName())), 
					(new SimpleStringProperty(list_cake.get(i).getpImage())), 
					(new SimpleStringProperty(list_cake.get(i).getpDate())),
					(new SimpleStringProperty(list_cake.get(i).getpPrice())),
					(new SimpleStringProperty(list_cake.get(i).getpContent())),
					(new SimpleStringProperty(list_cake.get(i).getpAmount()))));
		}
		cakeName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		cakePrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		cakeDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		cakeAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		cakeTableView.setItems(myList_cake);

		// ������ġ(sandwich) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_sandwich.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("������ġ")) {	
				list_sandwich.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_sandwich = FXCollections.observableArrayList();
		for(int i=0;i<list_sandwich.size();i++) {
			myList_sandwich.add(new Product(new SimpleStringProperty(list_sandwich.get(i).getpNum()),
					(new SimpleStringProperty(list_sandwich.get(i).getpCate())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpName())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpImage())), 
					(new SimpleStringProperty(list_sandwich.get(i).getpDate())),
					(new SimpleStringProperty(list_sandwich.get(i).getpPrice())),
					(new SimpleStringProperty(list_sandwich.get(i).getpContent())),
					(new SimpleStringProperty(list_sandwich.get(i).getpAmount()))));
		}
		sandwichName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		sandwichPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		sandwichDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		sandwichAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		sandwichTableView.setItems(myList_sandwich);

		// ����Ʈ(dessert) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_dessert.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����Ʈ")) {	
				list_dessert.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_dessert = FXCollections.observableArrayList();
		for(int i=0;i<list_dessert.size();i++) {
			myList_dessert.add(new Product(new SimpleStringProperty(list_dessert.get(i).getpNum()),
					(new SimpleStringProperty(list_dessert.get(i).getpCate())), 
					(new SimpleStringProperty(list_dessert.get(i).getpName())), 
					(new SimpleStringProperty(list_dessert.get(i).getpImage())), 
					(new SimpleStringProperty(list_dessert.get(i).getpDate())),
					(new SimpleStringProperty(list_dessert.get(i).getpPrice())),
					(new SimpleStringProperty(list_dessert.get(i).getpContent())),
					(new SimpleStringProperty(list_dessert.get(i).getpAmount()))));
		}
		dessertName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		dessertPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		dessertDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		dessertAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		dessertTableView.setItems(myList_dessert);

		// ����(drink) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_drink.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("����")) {	
				list_drink.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_drink = FXCollections.observableArrayList();
		for(int i=0;i<list_drink.size();i++) {
			myList_drink.add(new Product(new SimpleStringProperty(list_drink.get(i).getpNum()),
					(new SimpleStringProperty(list_drink.get(i).getpCate())), 
					(new SimpleStringProperty(list_drink.get(i).getpName())), 
					(new SimpleStringProperty(list_drink.get(i).getpImage())), 
					(new SimpleStringProperty(list_drink.get(i).getpDate())),
					(new SimpleStringProperty(list_drink.get(i).getpPrice())),
					(new SimpleStringProperty(list_drink.get(i).getpContent())),
					(new SimpleStringProperty(list_drink.get(i).getpAmount()))));
		}
		drinkName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		drinkPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		drinkDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		drinkAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		drinkTableView.setItems(myList_drink);

		// �귱ġ(brunch) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_brunch.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("�귱ġ")) {	
				list_brunch.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_brunch = FXCollections.observableArrayList();
		for(int i=0;i<list_brunch.size();i++) {
			myList_brunch.add(new Product(new SimpleStringProperty(list_brunch.get(i).getpNum()),
					(new SimpleStringProperty(list_brunch.get(i).getpCate())), 
					(new SimpleStringProperty(list_brunch.get(i).getpName())), 
					(new SimpleStringProperty(list_brunch.get(i).getpImage())), 
					(new SimpleStringProperty(list_brunch.get(i).getpDate())),
					(new SimpleStringProperty(list_brunch.get(i).getpPrice())),
					(new SimpleStringProperty(list_brunch.get(i).getpContent())),
					(new SimpleStringProperty(list_brunch.get(i).getpAmount()))));
		}
		brunchName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		brunchPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		brunchDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		brunchAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		brunchTableView.setItems(myList_brunch);

		// ��Ÿ(etc) ��ǰ ��� �ҷ����� --------------------------------------------------------------------------
		list_etc.clear();
		for(int i=0;i<list.size();i++) {
			if((list.get(i).getpCate()).equals("��Ÿ")) {	
				list_etc.add(new Product((list.get(i).getpNum()),
						(list.get(i).getpCate()),
						(list.get(i).getpName()),
						(list.get(i).getpImage()),
						(list.get(i).getpDate()),
						(list.get(i).getpPrice()),
						(list.get(i).getpContent()),
						(list.get(i).getpAmount())));
			}
		}
		myList_etc = FXCollections.observableArrayList();
		for(int i=0;i<list_etc.size();i++) {
			myList_etc.add(new Product(new SimpleStringProperty(list_etc.get(i).getpNum()),
					(new SimpleStringProperty(list_etc.get(i).getpCate())), 
					(new SimpleStringProperty(list_etc.get(i).getpName())), 
					(new SimpleStringProperty(list_etc.get(i).getpImage())), 
					(new SimpleStringProperty(list_etc.get(i).getpDate())),
					(new SimpleStringProperty(list_etc.get(i).getpPrice())),
					(new SimpleStringProperty(list_etc.get(i).getpContent())),
					(new SimpleStringProperty(list_etc.get(i).getpAmount()))));
		}
		etcName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		etcPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		etcDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		etcAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		etcTableView.setItems(myList_etc);

		paySum.setText("");
		payObser.clear();
		paymentlist.clear();
	}

}
