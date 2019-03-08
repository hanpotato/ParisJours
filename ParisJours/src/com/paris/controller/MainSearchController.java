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

	// 메뉴버튼 고정 (공지사항, 회원관리, 재고관리, 정산관리, 메인)
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;
	@FXML private Button main;

	// 최근게시물 ( 공지사항 )
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

	//공지사항 리스트 변수지정
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	// 메인 상품 이미지와 상품 설명
	@FXML private ImageView itemImage;
	@FXML private TextArea itemContent;

	// 결제리스트 초기화 버튼
	@FXML private Button payInitBtn;

	// 결제리스트 테이블뷰 및 컬럼
	@FXML private TableView<Product> paylist;
	@FXML private TableColumn<Product, String> payName;
	@FXML private TableColumn<Product, String> payAmount;
	@FXML private TableColumn<Product, String> payPrice;

	// 결제리스트에 담긴 합계 금액
	@FXML private Label paySum;

	// 결제리스트 결제하기 버튼
	@FXML private Button paymentBtn;

	//결제리스트 영수증 출력 관련 변수
	static ArrayList<Product> result = new ArrayList<Product>();

	// 상품리스트 검색
	@FXML private TextField searchMain;
	@FXML private Button searchMainBtn;
	@FXML private Button pTabReturnBtn;

	// 상품 검색 테이블뷰 및 컬럼
	@FXML private TableView<Product> tableViewSearch;
	@FXML private TableColumn<Product, String> cateSearch;
	@FXML private TableColumn<Product, String> nameSearch;
	@FXML private TableColumn<Product, String> priceSearch;
	@FXML private TableColumn<Product, String> dateSearch;
	@FXML private TableColumn<Product, String> amountSearch;

	// 상품 검색 테이블뷰 자료 연동 변수
	static ArrayList<Product> list_search = new ArrayList<Product>();
	static ObservableList<Product> myList_search;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 메뉴 이벤트 (모든페이지 고정)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));
		pTabReturnBtn.setOnAction(e->pTabReturnBtn(e));
		payInitBtn.setOnAction(e->payInitBtn(e));

		// 결제하기 버튼
		paymentBtn.setOnAction(e->paymentBtn(e));


		//메인으로 버튼
		main.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("메인페이지로 이동");
				try {
					Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// 최근 공지사항 목록 업데이트 --------------------------------------------------------------------시작
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

		// 최근 공지사항 제목을 라벨에 출력
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
			System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_2.setOnMouseClicked(event-> {
			System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_3.setOnMouseClicked(event-> {
			System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_4.setOnMouseClicked(event-> {
			System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		notice_5.setOnMouseClicked(event-> {
			System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
			try {
				Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene = new Scene(notice);
				Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// 상품 검색 목록 전체 출력
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

		// 메인화면 결제리스트에 있는 값 받음.
		payName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
		payAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
		payPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
		paylist.setItems(mainController.payObser);

		paySum.setText(mainController.paySum_backup);

		// ■ 이미지 및 파일 절대경로 만들어주기
		String aPath = path.class.getResource("").getPath();
		String bPath = TextPath.class.getResource("").getPath();

		// 상품리스트 실시간 검색 기능
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

		// 검색결과 테이블뷰에 마우스 클릭 이벤트리스너 장착!!!
		tableViewSearch.setOnMouseClicked(event -> {
			if(tableViewSearch.getSelectionModel().getSelectedItem() != null) {
				// 상품리스트 테이블뷰에 선택된 인덱스값을 itemNum에 넣어줌
				indexNum= tableViewSearch.getSelectionModel().getFocusedIndex();
				// 우측에 해당 상품 이미지 출력
				try (FileInputStream input = new FileInputStream(aPath + "/" + list_search.get(indexNum).getpImage())) {
					Image image = new Image(input);
					itemImage.setImage(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 우측에 해당 상품 정보 출력
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
					//각 상품 목록별 상품을 클릭할때 마다 수량 감소 해주고, 그 값을 list 변수에 업데이트.
					list_search.get(indexNum).setpAmount(String.valueOf(Integer.parseInt(list_search.get(indexNum).getpAmount())-1));
					for(int i=0;i<mainController.list.size();i++) {
						if(list_search.get(indexNum).getpNum().equals(mainController.list.get(i).getpNum())) {
							mainController.list.get(i).setpAmount(list_search.get(indexNum).getpAmount());
						}
					}
					// ArrayList = list 상품리스트 수량에서 -1해서 다시 상품파일로 저장해서
					// 결제리스트에 담길때마다 상품리스트에 수량이 -1 씩 감소되어 기존파일에 덮어쓰기 됨
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

					//결제리스트 중복값 갯수
					int count=1;
					for(int i=0;i<mainController.paymentlist.size();i++) {
						if(mainController.paymentlist.get(i).getpName().equals(list_search.get(indexNum).getpName())) {
							count++;
						}
					}

					//결제 리스트 목록 출력
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

					//결제리스트에 담기
					String no = count+"";
					mainController.paymentlist.add(new Product(list_search.get(indexNum).getpCate(),list_search.get(indexNum).getpName(), no, list_search.get(indexNum).getpPrice()));

					int sum = 0;
					// 결제리스트 합계 (스트링 -> 인트로 변환시켜 계산)
					for(int i=0;i<mainController.paymentlist.size();i++) {
						sum += Integer.parseInt(mainController.paymentlist.get(i).getpPrice());
					}
					paySum.setText(sum + "");

					//상품 목록 재출력.
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
					System.out.println("수량이 부족합니다.");
					// 오류 메시지를 보여준다.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(dialogStage);
					alert.setTitle("에러");
					alert.setHeaderText("");
					alert.setContentText("수량이 부족합니다.");
					alert.showAndWait();
				}
			}});

		// 결제리스트 테이블에 마우스 클릭 이벤트리스너 장착!!!
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
				System.out.println("결제리스트 상품 복구 횟수" + productDis);

				// 결제리스트 중복값 발견후 한개 삭제
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						mainController.paymentlist.remove(i);
						break;
					}
				}
				// 결제 중복값 갯수
				int count=0;
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						count++;
					}
				}
				// 결제 중복값 변경
				for(int i=0;i<mainController.paymentlist.size();i++) {
					if(mainController.payObser.get(itemNum).getpName2().toString().equals(new SimpleStringProperty(mainController.paymentlist.get(i).getpName()).toString())) {
						mainController.payObser.get(itemNum).setpAmount2(new SimpleStringProperty(String.valueOf(count)));
						break;
					}
				}
				// 결제목록 상품0개 제거
				if(count==0) {
					paylist.getItems().remove(itemNum);
				}
				// 결제리스트 새로고침.
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

				//결제 금액 합계
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

				//상품 목록 재출력.
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
		// 결제 기록 (Data포맷)
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date));

		// result 수정을 위한 변수 선언
		ArrayList<Product> paymentlist_sub = new ArrayList<Product>();
		int amountCount;
		// paymentlist 변수 자료를 임시 변수에 저장한다. -> 자료 취합을 하기 위해서
		paymentlist_sub.clear();
		for(int i=0;i<mainController.paymentlist.size();i++) {
			paymentlist_sub.add(new Product(mainController.paymentlist.get(i).getpCate(),
					mainController.paymentlist.get(i).getpName(),
					mainController.paymentlist.get(i).getpAmount(),
					mainController.paymentlist.get(i).getpPrice()));
		}
		// paymentlist_sub 중복값은 이름을 "0"으로 바꾼다.
		for(int i=0;i<paymentlist_sub.size();i++) {
			for(int j=i+1;j<paymentlist_sub.size();j++) {
				if(paymentlist_sub.get(i).getpName().equals(paymentlist_sub.get(j).getpName())) {
					paymentlist_sub.get(i).setpName("0");
				}
			}
		}
		// paymentlist_sub 이름 변수 기준으로 변수마다 갯수를 구한다.
		for(int i=0;i<paymentlist_sub.size();i++) {
			amountCount=1;
			for(int j=0;j<mainController.paymentlist.size();j++) {
				if(paymentlist_sub.get(i).getpName().equals(mainController.paymentlist.get(j).getpName())) {
					amountCount++;
				}
				paymentlist_sub.get(i).setpAmount(String.valueOf(amountCount-1));
			}
		}

		// paymentlist_sub 가격 합계 수정
		for(int i=0;i<paymentlist_sub.size();i++) {
			paymentlist_sub.get(i).setpPrice(String.valueOf((Integer.parseInt(paymentlist_sub.get(i).getpPrice())*(Integer.parseInt(paymentlist_sub.get(i).getpAmount())))));
		}

		// result 변수에 paymentlist_sub 이름 변수 "0"이 아닌 나머지를 넣는다.
		result.clear();
		for(int i=0;i<paymentlist_sub.size();i++) {
			if(!(paymentlist_sub.get(i).getpName().equals("0"))) {
				result.add(new Product(paymentlist_sub.get(i).getpCate(), paymentlist_sub.get(i).getpName(), paymentlist_sub.get(i).getpAmount(), paymentlist_sub.get(i).getpPrice()));
			}
		}

		// 상품목록이 담긴 파일
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

		// 상품경로와 합계가 담긴 자료를 저장
		try (FileWriter fw = new FileWriter("Payment/pay.txt")) {
			fw.write("Payment/pay_item_"+now+".txt");
			fw.write(",");
			fw.write(paySum.getText());
			fw.write("\r\n");

		}catch (Exception e) {
			e.printStackTrace();
		}

		// 정산관리에서 쓰일, 당일 매출
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

		// 정산관리에서 쓰일, 당월 매출
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

		// 정산관리에서 쓰일, 상품별 판매량
		try (FileWriter fw = new FileWriter("Payment/pay_cate.txt", true)) {
			for(int i=0; i<result.size(); i++){
				fw.write(result.get(i).getpCate());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("결제 메뉴로 이동");

		int sum = 0; // 계산금액 초기화
		paySum.setText(sum + "");

		try {
			Parent payment = FXMLLoader.load(getClass().getResource("/com/paris/view/pay.fxml"));
			Scene scene = new Scene(payment);
			Stage primaryStage = (Stage)paymentBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mainController.payObser.clear();
		mainController.paymentlist.clear();

	}

	private void noticeBtn(ActionEvent event) {
		System.out.println("공지사항 메뉴로 이동");
		try {
			Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
			Scene scene = new Scene(notice);
			Stage primaryStage = (Stage)noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void memberBtn(ActionEvent event) {
		System.out.println("회원관리 메뉴로 이동");

		try {
			Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
			Scene scene = new Scene(member);
			Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void itemBtn(ActionEvent event) {
		System.out.println("재고관리 메뉴로 이동");

		try {

			Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
			Scene scene = new Scene(product);
			Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payBtn(ActionEvent event) {
		System.out.println("정산관리 메뉴로 이동");
		try {
			Parent pay = FXMLLoader.load(getClass().getResource("/com/paris/view/cal.fxml"));
			Scene scene = new Scene(pay);
			Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void pTabReturnBtn(ActionEvent event) {
		System.out.println("메인화면 탭목록으로 이동");
		try {
			Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
			Scene scene = new Scene(main);
			Stage primaryStage = (Stage)pTabReturnBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payInitBtn(ActionEvent event) {

		//결제리스트에 있는 상품 목록 수량 복구.
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

		//상품 목록 재출력.
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
