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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class MainSearchController implements Initializable {

	private Stage primaryStage;

	static MainController mainController = new MainController();
	static int indexNum;
	private Stage dialogStage;

	// �޴���ư ���� (��������, ȸ������, ������, �������, ����)
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

	//�������� ����Ʈ ��������
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	// ���� ��ǰ �̹����� ��ǰ ����
	@FXML private ImageView itemImage;
	@FXML private TextArea itemContent;

	// ��������Ʈ �ʱ�ȭ ��ư
	@FXML private Button payInitBtn;

	// ��������Ʈ ���̺�� �� �÷�
	@FXML private TableView<Product> paylist;
	@FXML private TableColumn<Product, String> payName;
	@FXML private TableColumn<Product, String> payAmount;
	@FXML private TableColumn<Product, String> payPrice;

	// ��������Ʈ�� ��� �հ� �ݾ�
	@FXML private Label paySum;

	// ��������Ʈ �����ϱ� ��ư
	@FXML private Button paymentBtn;

	//��������Ʈ ������ ��� ���� ����
	static ArrayList<Product> result = new ArrayList<Product>();

	// ��ǰ����Ʈ �˻�
	@FXML private TextField searchMain;
	@FXML private Button searchMainBtn;
	@FXML private Button pTabReturnBtn;

	// ��ǰ �˻� ���̺�� �� �÷�
	@FXML private TableView<Product> tableViewSearch;
	@FXML private TableColumn<Product, String> cateSearch;
	@FXML private TableColumn<Product, String> nameSearch;
	@FXML private TableColumn<Product, String> priceSearch;
	@FXML private TableColumn<Product, String> dateSearch;
	@FXML private TableColumn<Product, String> amountSearch;

	// ��ǰ �˻� ���̺�� �ڷ� ���� ����
	static ArrayList<Product> list_search = new ArrayList<Product>();
	static ObservableList<Product> myList_search;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �޴� �̺�Ʈ (��������� ����)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));
		pTabReturnBtn.setOnAction(e->pTabReturnBtn(e));
		payInitBtn.setOnAction(e->payInitBtn(e));

		// �����ϱ� ��ư
		paymentBtn.setOnAction(e->paymentBtn(e));


		//�������� ��ư
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

		// ��ǰ �˻� ��� ��ü ���
		tableViewSearch.getItems().clear();
		list_search.clear();
		myList_search = FXCollections.observableArrayList();
		for(int i=0;i<mainController.list.size();i++) {
			if(mainController.list.get(i).getpName().contains(searchMain.getText())) {
				list_search.add(new Product((mainController.list.get(i).getpNum()),
						(mainController.list.get(i).getpCate()),
						(mainController.list.get(i).getpName()),
						(mainController.list.get(i).getpImage()),
						(mainController.list.get(i).getpDate()),
						(mainController.list.get(i).getpPrice()),
						(mainController.list.get(i).getpContent()),
						(mainController.list.get(i).getpAmount())));
			}
		}
		for(int i=0;i<list_search.size();i++) {
			myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
					(new SimpleStringProperty(list_search.get(i).getpCate())), 
					(new SimpleStringProperty(list_search.get(i).getpName())), 
					(new SimpleStringProperty(list_search.get(i).getpImage())), 
					(new SimpleStringProperty(list_search.get(i).getpDate())),
					(new SimpleStringProperty(list_search.get(i).getpPrice())),
					(new SimpleStringProperty(list_search.get(i).getpContent())),
					(new SimpleStringProperty(list_search.get(i).getpAmount()))));
		}
		cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
		nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
		amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		tableViewSearch.setItems(myList_search);

		// ����ȭ�� ��������Ʈ�� �ִ� �� ����.
		payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		paylist.setItems(mainController.payObser);

		paySum.setText(mainController.paySum_backup);

		// �� �̹��� �� ���� ������ ������ֱ�
		String aPath = path.class.getResource("").getPath();
		String bPath = TextPath.class.getResource("").getPath();

		// ��ǰ����Ʈ �ǽð� �˻� ���
		searchMain.setOnKeyReleased(ke -> {
			tableViewSearch.getItems().clear();
			list_search.clear();
			myList_search = FXCollections.observableArrayList();
			for(int i=0;i<mainController.list.size();i++) {
				if(mainController.list.get(i).getpName().contains(searchMain.getText())) {
					list_search.add(new Product((mainController.list.get(i).getpNum()),
							(mainController.list.get(i).getpCate()),
							(mainController.list.get(i).getpName()),
							(mainController.list.get(i).getpImage()),
							(mainController.list.get(i).getpDate()),
							(mainController.list.get(i).getpPrice()),
							(mainController.list.get(i).getpContent()),
							(mainController.list.get(i).getpAmount())));
				}
			}
			for(int i=0;i<list_search.size();i++) {
				myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
						(new SimpleStringProperty(list_search.get(i).getpCate())), 
						(new SimpleStringProperty(list_search.get(i).getpName())), 
						(new SimpleStringProperty(list_search.get(i).getpImage())), 
						(new SimpleStringProperty(list_search.get(i).getpDate())),
						(new SimpleStringProperty(list_search.get(i).getpPrice())),
						(new SimpleStringProperty(list_search.get(i).getpContent())),
						(new SimpleStringProperty(list_search.get(i).getpAmount()))));
			}
			cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
			nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
			priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
			dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
			amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
			tableViewSearch.setItems(myList_search);

			KeyCode keyCode = ke.getCode();
			if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
				tableViewSearch.getItems().clear();
				list_search.clear();
				myList_search = FXCollections.observableArrayList();
				for(int i=0;i<mainController.list.size();i++) {
					if(mainController.list.get(i).getpName().contains(searchMain.getText())) {
						list_search.add(new Product((mainController.list.get(i).getpNum()),
								(mainController.list.get(i).getpCate()),
								(mainController.list.get(i).getpName()),
								(mainController.list.get(i).getpImage()),
								(mainController.list.get(i).getpDate()),
								(mainController.list.get(i).getpPrice()),
								(mainController.list.get(i).getpContent()),
								(mainController.list.get(i).getpAmount())));
					}
				}
				for(int i=0;i<list_search.size();i++) {
					myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
							(new SimpleStringProperty(list_search.get(i).getpCate())), 
							(new SimpleStringProperty(list_search.get(i).getpName())), 
							(new SimpleStringProperty(list_search.get(i).getpImage())), 
							(new SimpleStringProperty(list_search.get(i).getpDate())),
							(new SimpleStringProperty(list_search.get(i).getpPrice())),
							(new SimpleStringProperty(list_search.get(i).getpContent())),
							(new SimpleStringProperty(list_search.get(i).getpAmount()))));
				}
				cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
				nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
				priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
				dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
				amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
				tableViewSearch.setItems(myList_search);

			}});

		// �˻���� ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!
		tableViewSearch.setOnMouseClicked(event -> {
			if(tableViewSearch.getSelectionModel().getSelectedItem() != null) {
				// ��ǰ����Ʈ ���̺�信 ���õ� �ε������� itemNum�� �־���
				indexNum= tableViewSearch.getSelectionModel().getFocusedIndex();
				// ������ �ش� ��ǰ �̹��� ���
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_search.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ������ �ش� ��ǰ ���� ���
				itemContent.clear();
				StringTokenizer st2 = null;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" + list_search.get(indexNum).getpContent())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						itemContent.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				if(Integer.parseInt(list_search.get(indexNum).getpAmount())>0) {
					//�� ��ǰ ��Ϻ� ��ǰ�� Ŭ���Ҷ� ���� ���� ���� ���ְ�, �� ���� list ������ ������Ʈ.
					list_search.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_search.get(indexNum).getpAmount())-1));
					for(int i=0;i<mainController.list.size();i++) {
						if(list_search.get(indexNum).getpNum().equals(mainController.list.get(i).getpNum())) {
							mainController.list.get(i).setpAmount(list_search.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list ��ǰ����Ʈ �������� -1�ؼ� �ٽ� ��ǰ���Ϸ� �����ؼ�
					// ��������Ʈ�� ��涧���� ��ǰ����Ʈ�� ������ -1 �� ���ҵǾ� �������Ͽ� ����� ��
					try (FileWriter fw = new FileWriter("Product/product.txt")){
						for(int i=0; i<mainController.list.size(); i++){
							fw.write(mainController.list.get(i).getpNum());fw.write(",");
							fw.write(mainController.list.get(i).getpCate());fw.write(",");
							fw.write(mainController.list.get(i).getpName());fw.write(",");
							fw.write(mainController.list.get(i).getpImage());fw.write(",");
							fw.write(mainController.list.get(i).getpDate().toString());fw.write(",");
							fw.write(mainController.list.get(i).getpPrice());fw.write(",");
							fw.write(mainController.list.get(i).getpContent());fw.write(",");
							fw.write(mainController.list.get(i).getpAmount());fw.write("\r\n");
						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					//��������Ʈ �ߺ��� ����
					int count=1;
					for(int i=0;i<mainController.paymentlist.size();i++) {
						if(mainController.paymentlist.get(i).getpName().equals(list_search.get(indexNum).getpName())) {
							count++;
						}
					}

					//���� ����Ʈ ��� ���
					for(int i=0;i<mainController.payObser.size();i++) {
						if(mainController.payObser.get(i).getpName2().toString().equals((new SimpleStringProperty(list_search.get(indexNum).getpName())).toString())) {
							mainController.payObser.remove(i);
						}
					}

					mainController.payObser.add(new Product(new SimpleStringProperty(list_search.get(indexNum).getpCate()),
							(new SimpleStringProperty(list_search.get(indexNum).getpName())),
							(new SimpleStringProperty(String.valueOf(count))),
							(new SimpleStringProperty(list_search.get(indexNum).getpPrice()))));

					payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					paylist.setItems(mainController.payObser);

					//��������Ʈ�� ���
					String no = count+"";
					mainController.paymentlist.add(new Product(list_search.get(indexNum).getpCate(),list_search.get(indexNum).getpName(), no, list_search.get(indexNum).getpPrice()));

					int sum = 0;
					// ��������Ʈ �հ� (��Ʈ�� -> ��Ʈ�� ��ȯ���� ���)
					for(int i=0;i<mainController.paymentlist.size();i++) {
						sum += Integer.parseInt(mainController.paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");

					//��ǰ ��� �����.
					tableViewSearch.getItems().clear();
					list_search.clear();
					myList_search = FXCollections.observableArrayList();
					for(int i=0;i<mainController.list.size();i++) {
						if(mainController.list.get(i).getpName().contains(searchMain.getText())) {
							list_search.add(new Product((mainController.list.get(i).getpNum()),
									(mainController.list.get(i).getpCate()),
									(mainController.list.get(i).getpName()),
									(mainController.list.get(i).getpImage()),
									(mainController.list.get(i).getpDate()),
									(mainController.list.get(i).getpPrice()),
									(mainController.list.get(i).getpContent()),
									(mainController.list.get(i).getpAmount())));
						}
					}
					for(int i=0;i<list_search.size();i++) {
						myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
								(new SimpleStringProperty(list_search.get(i).getpCate())), 
								(new SimpleStringProperty(list_search.get(i).getpName())), 
								(new SimpleStringProperty(list_search.get(i).getpImage())), 
								(new SimpleStringProperty(list_search.get(i).getpDate())),
								(new SimpleStringProperty(list_search.get(i).getpPrice())),
								(new SimpleStringProperty(list_search.get(i).getpContent())),
								(new SimpleStringProperty(list_search.get(i).getpAmount()))));
					}
					cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
					nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
					priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
					dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
					amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
					tableViewSearch.setItems(myList_search);


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

				int productDis=0;
				for(int i=0;i<list_search.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(list_search.get(i).getpName()).toString())) {
						list_search.get(i).setpAmount(String.valueOf(Integer.parseInt(list_search.get(i).getpAmount())+1));
						productDis++;
						for(int j=0;j<mainController.list.size();j++) {
							if(list_search.get(i).getpName().equals(mainController.list.get(j).getpName())) {
								mainController.list.get(j).setpAmount(list_search.get(i).getpAmount());
							}
						}
					}
				}
				System.out.println("��������Ʈ ��ǰ ���� Ƚ��" + productDis);

				// ��������Ʈ �ߺ��� �߰��� �Ѱ� ����
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						mainController.paymentlist.remove(i);
						break;
					}
				}
				// ���� �ߺ��� ����
				int count=0;
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						count++;
					}
				}
				// ���� �ߺ��� ����
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						mainController.payObser.get(itemNum).setpAmount2(new SimpleStringProperty(String.valueOf(count)));
						break;
					}
				}
				// ������� ��ǰ0�� ����
				if(count==0) {
					paylist.getItems().remove(itemNum);
				}
				// ��������Ʈ ���ΰ�ħ.
				mainController.payObser_sub.clear();
				for(int i=0;i<mainController.payObser.size();i++) {
					mainController.payObser_sub.add(new Product((mainController.payObser.get(i).getpNum2()),
							(mainController.payObser.get(i).getpCate2()),
							(mainController.payObser.get(i).getpName2()),
							(mainController.payObser.get(i).getpImage2()),
							(mainController.payObser.get(i).getpDate2()),
							(mainController.payObser.get(i).getpPrice2()),
							(mainController.payObser.get(i).getpContent2()),
							(mainController.payObser.get(i).getpAmount2())));
				}
				mainController.payObser.clear();
				for(int i=0;i<mainController.payObser_sub.size();i++) {
					mainController.payObser.add(new Product((mainController.payObser_sub.get(i).getpNum2()),
							(mainController.payObser_sub.get(i).getpCate2()),
							(mainController.payObser_sub.get(i).getpName2()),
							(mainController.payObser_sub.get(i).getpImage2()),
							(mainController.payObser_sub.get(i).getpDate2()),
							(mainController.payObser_sub.get(i).getpPrice2()),
							(mainController.payObser_sub.get(i).getpContent2()),
							(mainController.payObser_sub.get(i).getpAmount2())));
				}

				payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
				payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
				payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
				paylist.setItems(mainController.payObser);

				//���� �ݾ� �հ�
				int sum = 0;
				for(int i=0;i<mainController.paymentlist.size();i++) {
					sum += Integer.parseInt(mainController.paymentlist.get(i).getpPrice());
				}
				paySum.setText(sum + "");
				try (FileWriter fw = new FileWriter("Product/product.txt")) {
					for(int i=0; i<mainController.list.size(); i++) {
						fw.write(mainController.list.get(i).getpNum());
						fw.write(",");
						fw.write(mainController.list.get(i).getpCate());
						fw.write(",");
						fw.write(mainController.list.get(i).getpName());
						fw.write(",");
						fw.write(mainController.list.get(i).getpImage());
						fw.write(",");
						fw.write(mainController.list.get(i).getpDate().toString());
						fw.write(",");
						fw.write(mainController.list.get(i).getpPrice());
						fw.write(",");
						fw.write(mainController.list.get(i).getpContent());
						fw.write(",");
						fw.write(mainController.list.get(i).getpAmount());
						fw.write("\r\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				//��ǰ ��� �����.
				tableViewSearch.getItems().clear();
				list_search.clear();
				myList_search = FXCollections.observableArrayList();
				for(int i=0;i<mainController.list.size();i++) {
					if(mainController.list.get(i).getpName().contains(searchMain.getText())) {
						list_search.add(new Product((mainController.list.get(i).getpNum()),
								(mainController.list.get(i).getpCate()),
								(mainController.list.get(i).getpName()),
								(mainController.list.get(i).getpImage()),
								(mainController.list.get(i).getpDate()),
								(mainController.list.get(i).getpPrice()),
								(mainController.list.get(i).getpContent()),
								(mainController.list.get(i).getpAmount())));
					}
				}
				for(int i=0;i<list_search.size();i++) {
					myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
							(new SimpleStringProperty(list_search.get(i).getpCate())), 
							(new SimpleStringProperty(list_search.get(i).getpName())), 
							(new SimpleStringProperty(list_search.get(i).getpImage())), 
							(new SimpleStringProperty(list_search.get(i).getpDate())),
							(new SimpleStringProperty(list_search.get(i).getpPrice())),
							(new SimpleStringProperty(list_search.get(i).getpContent())),
							(new SimpleStringProperty(list_search.get(i).getpAmount()))));
				}
				cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
				nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
				priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
				dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
				amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
				tableViewSearch.setItems(myList_search);

			}
		});



	}
	private void paymentBtn(ActionEvent event) {
		// ���� ��� (Data����)
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date));

		// result ������ ���� ���� ����
		ArrayList<Product> paymentlist_sub = new ArrayList<Product>();
		int amountCount;
		// paymentlist ���� �ڷḦ �ӽ� ������ �����Ѵ�. -> �ڷ� ������ �ϱ� ���ؼ�
		paymentlist_sub.clear();
		for(int i=0;i<mainController.paymentlist.size();i++) {
			paymentlist_sub.add(new Product(mainController.paymentlist.get(i).getpCate(),
					mainController.paymentlist.get(i).getpName(),
					mainController.paymentlist.get(i).getpAmount(),
					mainController.paymentlist.get(i).getpPrice()));
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
			for(int j=0;j<mainController.paymentlist.size();j++) {
				if(paymentlist_sub.get(i).getpName().equals(mainController.paymentlist.get(j).getpName())) {
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

		mainController.payObser.clear();
		mainController.paymentlist.clear();

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

	private void pTabReturnBtn(ActionEvent event) {
		System.out.println("����ȭ�� �Ǹ������ �̵�");
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)pTabReturnBtn.getScene().getWindow(); // ���� ������ ��������
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payInitBtn(ActionEvent event) {

		//��������Ʈ�� �ִ� ��ǰ ��� ���� ����.
		for(int i=0;i<mainController.paymentlist.size();i++) {
			for(int j=0;j<mainController.list.size();j++) {
				if(mainController.paymentlist.get(i).getpName().equals(mainController.list.get(j).getpName())) {
					mainController.list.get(j).setpAmount(String.valueOf(Integer.parseInt(mainController.list.get(j).getpAmount())+1));
				}
			}
		}
		try (FileWriter fw = new FileWriter("Product/product.txt")) {
			for(int i=0; i<mainController.list.size(); i++) {
				fw.write(mainController.list.get(i).getpNum());
				fw.write(",");
				fw.write(mainController.list.get(i).getpCate());
				fw.write(",");
				fw.write(mainController.list.get(i).getpName());
				fw.write(",");
				fw.write(mainController.list.get(i).getpImage());
				fw.write(",");
				fw.write(mainController.list.get(i).getpDate().toString());
				fw.write(",");
				fw.write(mainController.list.get(i).getpPrice());
				fw.write(",");
				fw.write(mainController.list.get(i).getpContent());
				fw.write(",");
				fw.write(mainController.list.get(i).getpAmount());
				fw.write("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//��ǰ ��� �����.
		tableViewSearch.getItems().clear();
		list_search.clear();
		myList_search = FXCollections.observableArrayList();
		for(int i=0;i<mainController.list.size();i++) {
			list_search.add(new Product((mainController.list.get(i).getpNum()),
					(mainController.list.get(i).getpCate()),
					(mainController.list.get(i).getpName()),
					(mainController.list.get(i).getpImage()),
					(mainController.list.get(i).getpDate()),
					(mainController.list.get(i).getpPrice()),
					(mainController.list.get(i).getpContent()),
					(mainController.list.get(i).getpAmount())));
		}
		for(int i=0;i<list_search.size();i++) {
			if(list_search.get(i).getpName().contains(searchMain.getText())) {
				myList_search.add(new Product(new SimpleStringProperty(list_search.get(i).getpNum()),
						(new SimpleStringProperty(list_search.get(i).getpCate())), 
						(new SimpleStringProperty(list_search.get(i).getpName())), 
						(new SimpleStringProperty(list_search.get(i).getpImage())), 
						(new SimpleStringProperty(list_search.get(i).getpDate())),
						(new SimpleStringProperty(list_search.get(i).getpPrice())),
						(new SimpleStringProperty(list_search.get(i).getpContent())),
						(new SimpleStringProperty(list_search.get(i).getpAmount()))));
			}
			cateSearch.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
			nameSearch.setCellValueFactory(cellData -> cellData.getValue().getpName2());
			priceSearch.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
			dateSearch.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
			amountSearch.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
			tableViewSearch.setItems(myList_search);
		}

		paySum.setText("");
		mainController.payObser.clear();
		mainController.paymentlist.clear();
	}

}
