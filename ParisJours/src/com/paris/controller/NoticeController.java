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

import com.paris.model.vo.Notice;

import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NoticeController implements Initializable {

	private Stage primaryStage;
	private Stage dialogStage;

	// 메뉴버튼 고정 (공지사항, 회원관리, 재고관리, 정산관리, 메인)
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;
	@FXML private Button main;

	// 공지사항 탭
	@FXML private TabPane tab = new TabPane();
	@FXML private Tab tab_all;
	@FXML private Tab tab_notice;
	@FXML private Tab tab_event;

	// 공지사항 <전체보기> 테이블뷰 및 컬럼
	@FXML private TableView<Notice> tableview;
	@FXML private TableColumn<Notice, String> noCol;
	@FXML private TableColumn<Notice, String> cateCol;
	@FXML private TableColumn<Notice, String> titleCol;
	@FXML private TableColumn<Notice, String> DateCol;
	// 공지사항 <공지사항> 테이블뷰 및 컬럼
	@FXML private TableView<Notice> noticeTable;
	@FXML private TableColumn<Notice, String> noCol1;
	@FXML private TableColumn<Notice, String> cateCol1;
	@FXML private TableColumn<Notice, String> titleCol1;
	@FXML private TableColumn<Notice, String> DateCol1;
	// 공지사항 <이벤트> 테이블뷰 및 컬럼
	@FXML private TableView<Notice> eventTable;
	@FXML private TableColumn<Notice, String> noCol2;
	@FXML private TableColumn<Notice, String> cateCol2;
	@FXML private TableColumn<Notice, String> titleCol2;
	@FXML private TableColumn<Notice, String> DateCol2;

	// 공지사항 내용
	@FXML private Label titleLabel;
	@FXML private Label dateLabel;
	@FXML private TextArea contentArea;

	// 공지사항 글쓰기 & 수정 & 삭제 버튼
	@FXML private Button writeBtn;
	@FXML private Button deleteBtn;
	@FXML private Button modifyBtn;

	@FXML private TextField password;

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

	// 공지사항 제목 검색
	@FXML private TextField search;
	@FXML private Button searchBtn;
	@FXML private ChoiceBox<String> cate;
	ObservableList<String> options = FXCollections.observableArrayList("전체검색","공지사항","이벤트"); 

	static ArrayList<Notice> notice = new ArrayList<Notice>();

	// 공지사항 <전체보기> ArrayList
	static ArrayList<Notice> list_all = new ArrayList<Notice>();
	static ObservableList<Notice> myList_all; 
	// 공지사항 <공지사항> ArrayList
	static ArrayList<Notice> list_notice = new ArrayList<Notice>();
	static ObservableList<Notice> myList_notice; 
	// 공지사항 <이벤트> ArrayList
	static ArrayList<Notice> list_event = new ArrayList<Notice>();
	static ObservableList<Notice> myList_event; 

	// 공지사항 ArrayList 및 ObservableList
	static ArrayList<Notice> noticeDesc = new ArrayList<Notice>();

	// 공지사항 검색 Obserable List
	static ArrayList<Notice> searchNotice = new ArrayList<Notice>();
	static ObservableList<Notice> searchList;

	// 공지사항 기본생성자
	public NoticeController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 메뉴 이벤트 (모든페이지 고정)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));

		// 검색 초이스 박스
		cate.setValue("전체검색");
		cate.setItems(options);

		// 공지사항 목록 업데이트 -------------------------------------------------------------------- 시작
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

		// 공지사항 [내림차순 정렬]
		noticeDesc.clear();
		for(int i=notice.size()-1;i>=0;i--) {
			noticeDesc.add(new Notice((notice.get(i).getNo()),
					(notice.get(i).getCate()),
					(notice.get(i).getTitle()),
					(notice.get(i).getContent()),
					(notice.get(i).getDate()),
					(notice.get(i).getPassword())));
		}

		// 공지사항 <전체보기> 
		list_all.clear();
		for(int i=0;i<noticeDesc.size();i++) {
			list_all.add(new Notice((noticeDesc.get(i).getNo()),
					(noticeDesc.get(i).getCate()),
					(noticeDesc.get(i).getTitle()),
					(noticeDesc.get(i).getContent()),
					(noticeDesc.get(i).getDate()),
					(noticeDesc.get(i).getPassword())));
		}
		myList_all = FXCollections.observableArrayList();
		for(int i=0;i<list_all.size();i++) {
			myList_all.add(new Notice(new SimpleStringProperty(list_all.get(i).getNo()), 
					(new SimpleStringProperty(list_all.get(i).getCate())), 
					(new SimpleStringProperty(list_all.get(i).getTitle())), 
					(new SimpleStringProperty(list_all.get(i).getContent())), 
					(new SimpleStringProperty(list_all.get(i).getDate())),
					(new SimpleStringProperty(list_all.get(i).getPassword()))));
		}
		cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
		titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
		DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
		tableview.setItems(myList_all);

		// 공지사항 <공지사항> 
		list_notice.clear();
		for(int i=0;i<noticeDesc.size();i++) {
			if((noticeDesc.get(i).getCate()).equals("공지사항")) {	
				list_notice.add(new Notice((noticeDesc.get(i).getNo()),
						(noticeDesc.get(i).getCate()),
						(noticeDesc.get(i).getTitle()),
						(noticeDesc.get(i).getContent()),
						(noticeDesc.get(i).getDate()),
						(noticeDesc.get(i).getPassword())));
			}
		}
		myList_notice = FXCollections.observableArrayList();
		for(int i=0;i<list_notice.size();i++) {
			myList_notice.add(new Notice(new SimpleStringProperty(list_notice.get(i).getNo()), 
					(new SimpleStringProperty(list_notice.get(i).getCate())), 
					(new SimpleStringProperty(list_notice.get(i).getTitle())), 
					(new SimpleStringProperty(list_notice.get(i).getContent())), 
					(new SimpleStringProperty(list_notice.get(i).getDate())),
					(new SimpleStringProperty(list_notice.get(i).getPassword()))));
		}
		cateCol1.setCellValueFactory(cellData -> cellData.getValue().getCate2());
		titleCol1.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
		DateCol1.setCellValueFactory(cellData -> cellData.getValue().getDate2());
		noticeTable.setItems(myList_notice);

		// 공지사항 <이벤트> 
		list_event.clear();
		for(int i=0;i<noticeDesc.size();i++) {
			if((noticeDesc.get(i).getCate()).equals("이벤트")) {	
				list_event.add(new Notice((noticeDesc.get(i).getNo()),
						(noticeDesc.get(i).getCate()),
						(noticeDesc.get(i).getTitle()),
						(noticeDesc.get(i).getContent()),
						(noticeDesc.get(i).getDate()),
						(noticeDesc.get(i).getPassword())));
			}
		}
		myList_event = FXCollections.observableArrayList();
		for(int i=0;i<list_event.size();i++) {
			myList_event.add(new Notice(new SimpleStringProperty(list_event.get(i).getNo()), 
					(new SimpleStringProperty(list_event.get(i).getCate())), 
					(new SimpleStringProperty(list_event.get(i).getTitle())), 
					(new SimpleStringProperty(list_event.get(i).getContent())), 
					(new SimpleStringProperty(list_event.get(i).getDate())),
					(new SimpleStringProperty(list_event.get(i).getPassword()))));
		}
		cateCol2.setCellValueFactory(cellData -> cellData.getValue().getCate2());
		titleCol2.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
		DateCol2.setCellValueFactory(cellData -> cellData.getValue().getDate2());
		eventTable.setItems(myList_event);

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

		// 공지사항 글 삭제버튼
		deleteBtn.setOnAction(e->deleteBtn(e));
		// 공지사항 글 수정버튼
		modifyBtn.setOnAction(e->modifyBtn(e));
		// 공지사항 글 작성
		writeBtn.setOnAction(e->writeBtn(e));
		// 공지사항 검색창 반응형으로 적용
		search.setOnKeyReleased(ke -> {
			KeyCode keyCode = ke.getCode();
			tableview.getItems().clear();
			searchNotice.clear();
			searchList = FXCollections.observableArrayList();

			// 카테고리 : 전체검색
			for(int i=0;i<list_all.size();i++) {
				if(cate.getValue().equals("전체검색") && list_all.get(i).getTitle().contains(search.getText())) {
					searchNotice.add(new Notice((list_all.get(i).getNo()),
							(list_all.get(i).getCate()),
							(list_all.get(i).getTitle()),
							(list_all.get(i).getContent()),
							(list_all.get(i).getDate()),
							(list_all.get(i).getPassword())));
				}
			}


			for(int i=0;i<searchNotice.size();i++) {
				searchList.add(new Notice(new SimpleStringProperty(searchNotice.get(i).getNo()),
						(new SimpleStringProperty(searchNotice.get(i).getCate())), 
						(new SimpleStringProperty(searchNotice.get(i).getTitle())), 
						(new SimpleStringProperty(searchNotice.get(i).getContent())), 
						(new SimpleStringProperty(searchNotice.get(i).getDate())),
						(new SimpleStringProperty(searchNotice.get(i).getPassword()))));
			}

			cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
			titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
			DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
			tableview.setItems(searchList);

			if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
				tableview.getItems().clear();
				searchNotice.clear();
				searchList = FXCollections.observableArrayList();

				for(int i=0;i<list_all.size();i++) {
					if(cate.getValue().equals("전체검색") && list_all.get(i).getTitle().contains(search.getText())) {
						searchList.add(new Notice(new SimpleStringProperty(list_all.get(i).getNo()), 
								(new SimpleStringProperty(list_all.get(i).getCate())), 
								(new SimpleStringProperty(list_all.get(i).getTitle())), 
								(new SimpleStringProperty(list_all.get(i).getContent())), 
								(new SimpleStringProperty(list_all.get(i).getDate())),
								(new SimpleStringProperty(list_all.get(i).getPassword()))));
					}
					cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
					titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
					DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
					tableview.setItems(searchList);
				}}

			// 카테고리 : 공지사항 검색
			for(int i=0;i<list_notice.size();i++) {
				if(cate.getValue().equals("공지사항") && list_notice.get(i).getTitle().contains(search.getText()) && list_notice.get(i).getCate().equals("공지사항")) {
					searchNotice.add(new Notice((list_notice.get(i).getNo()),
							(list_notice.get(i).getCate()),
							(list_notice.get(i).getTitle()),
							(list_notice.get(i).getContent()),
							(list_notice.get(i).getDate()),
							(list_notice.get(i).getPassword())));
				}
			}

			for(int i=0;i<searchNotice.size();i++) {
				if(cate.getValue().equals("공지사항") && searchNotice.get(i).getTitle().contains(search.getText()) && searchNotice.get(i).getCate().equals("공지사항")) {
					searchList.add(new Notice(new SimpleStringProperty(searchNotice.get(i).getNo()), 
							(new SimpleStringProperty(searchNotice.get(i).getCate())), 
							(new SimpleStringProperty(searchNotice.get(i).getTitle())), 
							(new SimpleStringProperty(searchNotice.get(i).getContent())), 
							(new SimpleStringProperty(searchNotice.get(i).getDate())),
							(new SimpleStringProperty(searchNotice.get(i).getPassword()))));
				}
				cateCol1.setCellValueFactory(cellData -> cellData.getValue().getCate2());
				titleCol1.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
				DateCol1.setCellValueFactory(cellData -> cellData.getValue().getDate2());
				noticeTable.setItems(searchList);
			}
			if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
				noticeTable.getItems().clear();
				searchList = FXCollections.observableArrayList();

				for(int i=0;i<list_notice.size();i++) {
					if(list_notice.get(i).getTitle().contains(search.getText()) && list_notice.get(i).getCate().equals("공지사항")) {
						searchList.add(new Notice(new SimpleStringProperty(list_notice.get(i).getNo()), 
								(new SimpleStringProperty(list_notice.get(i).getCate())), 
								(new SimpleStringProperty(list_notice.get(i).getTitle())), 
								(new SimpleStringProperty(list_notice.get(i).getContent())), 
								(new SimpleStringProperty(list_notice.get(i).getDate())),
								(new SimpleStringProperty(list_notice.get(i).getPassword()))));
					}
					cateCol1.setCellValueFactory(cellData -> cellData.getValue().getCate2());
					titleCol1.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
					DateCol1.setCellValueFactory(cellData -> cellData.getValue().getDate2());
					noticeTable.setItems(searchList);
				}}

			// 카테고리 : 이벤트 검색
			for(int i=0;i<list_event.size();i++) {
				if(cate.getValue().equals("이벤트") && list_event.get(i).getTitle().contains(search.getText()) && list_event.get(i).getCate().equals("이벤트")) {
					searchNotice.add(new Notice((list_event.get(i).getNo()),
							(list_event.get(i).getCate()),
							(list_event.get(i).getTitle()),
							(list_event.get(i).getContent()),
							(list_event.get(i).getDate()),
							(list_event.get(i).getPassword())));
				}
			}

			for(int i=0;i<searchNotice.size();i++) {
				if(cate.getValue().equals("이벤트") && searchNotice.get(i).getTitle().contains(search.getText()) && searchNotice.get(i).getCate().equals("이벤트")) {
					searchList.add(new Notice(new SimpleStringProperty(searchNotice.get(i).getNo()), 
							(new SimpleStringProperty(searchNotice.get(i).getCate())), 
							(new SimpleStringProperty(searchNotice.get(i).getTitle())), 
							(new SimpleStringProperty(searchNotice.get(i).getContent())), 
							(new SimpleStringProperty(searchNotice.get(i).getDate())),
							(new SimpleStringProperty(searchNotice.get(i).getPassword()))));
				}
				cateCol2.setCellValueFactory(cellData -> cellData.getValue().getCate2());
				titleCol2.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
				DateCol2.setCellValueFactory(cellData -> cellData.getValue().getDate2());
				eventTable.setItems(searchList);
			}
			if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
				eventTable.getItems().clear();
				searchList = FXCollections.observableArrayList();

				for(int i=0;i<list_event.size();i++) {
					if(list_event.get(i).getTitle().contains(search.getText()) && list_event.get(i).getCate().equals("이벤트")) {
						searchList.add(new Notice(new SimpleStringProperty(list_event.get(i).getNo()), 
								(new SimpleStringProperty(list_event.get(i).getCate())), 
								(new SimpleStringProperty(list_event.get(i).getTitle())), 
								(new SimpleStringProperty(list_event.get(i).getContent())), 
								(new SimpleStringProperty(list_event.get(i).getDate())),
								(new SimpleStringProperty(list_event.get(i).getPassword()))));
					}
					cateCol2.setCellValueFactory(cellData -> cellData.getValue().getCate2());
					titleCol2.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
					DateCol2.setCellValueFactory(cellData -> cellData.getValue().getDate2());
					eventTable.setItems(searchList);
				}}

		});


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


		// 게시물 클릭 시, 우측에 게시물 정보 출력
		tableview.setOnMouseClicked(event -> {
			if(tableview.getSelectionModel().getSelectedItem() != null) {
				int select = tableview.getSelectionModel().getSelectedIndex();

				// 카테고리 테이블뷰에 선택된 인덱스의 제목과 전체보기 테이블뷰의 
				// [제목]과 [시간]을 전체리스트(내림차순 적용이 안된 ArrayList : notice)에서 반복문을 실행해
				// 선택된 [제목] [ 시간]과 같은 값을 같는 게시물을 전체리스트에서 찾아 파라미터값 지정

				if(search.getText().equals("")){
					String contents = list_all.get(select).getContent();
					titleLabel.setText(list_all.get(select).getTitle());
					dateLabel.setText(list_all.get(select).getDate());
					contentArea.setText(contents);
				} else {
					int selectNum = 0;

					for(int i=0;i<list_all.size();i++) {
						if(searchNotice.get(select).getTitle().equals(list_all.get(i).getTitle()) &&
								searchNotice.get(select).getDate().equals(list_all.get(i).getDate())) {
							selectNum = i;
						}
					}
					String contents = list_all.get(selectNum).getContent();
					titleLabel.setText(list_all.get(selectNum).getTitle());
					dateLabel.setText(list_all.get(selectNum).getDate());
					contentArea.setText(contents);
				}

			}
		});

		// ----------------------------------------------------------------------------------------------- >>>
		noticeTable.setOnMouseClicked(event -> {
			if(noticeTable.getSelectionModel().getSelectedItem() != null) {
				int noticenum1 = noticeTable.getSelectionModel().getFocusedIndex();	

				if(search.getText().equals("")){
					String contents = list_notice.get(noticenum1).getContent();
					titleLabel.setText(list_notice.get(noticenum1).getTitle());
					dateLabel.setText(list_notice.get(noticenum1).getDate());
					contentArea.setText(contents);

				} else {
					int selectNum = 0;

					for(int i=0;i<list_notice.size();i++) {
						if(searchNotice.get(noticenum1).getTitle().equals(list_notice.get(i).getTitle()) &&
								searchNotice.get(noticenum1).getDate().equals(list_notice.get(i).getDate())) {
							selectNum = i;
						}
					}
					String contents = list_notice.get(selectNum).getContent();
					titleLabel.setText(list_notice.get(selectNum).getTitle());
					dateLabel.setText(list_notice.get(selectNum).getDate());
					contentArea.setText(contents);
				}
			}
		});

		// ----------------------------------------------------------------------------------------------- >>>
		eventTable.setOnMouseClicked(event -> {
			if(eventTable.getSelectionModel().getSelectedItem() != null) {
				int noticenum2 = eventTable.getSelectionModel().getFocusedIndex();	

				if(search.getText().equals("")){
					String contents = list_event.get(noticenum2).getContent();
					titleLabel.setText(list_event.get(noticenum2).getTitle());
					dateLabel.setText(list_event.get(noticenum2).getDate());
					contentArea.setText(contents);
				} else {
					int selectNum = 0;

					for(int i=0;i<list_event.size();i++) {
						if(searchNotice.get(noticenum2).getTitle().equals(list_event.get(i).getTitle()) &&
								searchNotice.get(noticenum2).getDate().equals(list_event.get(i).getDate())) {
							selectNum = i;
						}
					}
					String contents = list_event.get(selectNum).getContent();
					titleLabel.setText(list_event.get(selectNum).getTitle());
					dateLabel.setText(list_event.get(selectNum).getDate());
					contentArea.setText(contents);
				}
			}
		});

	}

	/*// ■ 공지사항 검색 기능 추가 (18.11.13)
	private void searchBtn(ActionEvent event) {

		tableview.getItems().clear();
		searchList = FXCollections.observableArrayList();

		// 카테고리 : 전체검색
		for(int i=0;i<noticeDesc.size();i++) {
			if(cate.getValue().equals("전체검색") && noticeDesc.get(i).getTitle().contains(search.getText())) {
				searchList.add(new Notice(new SimpleStringProperty(noticeDesc.get(i).getNo()), 
						(new SimpleStringProperty(noticeDesc.get(i).getCate())), 
						(new SimpleStringProperty(noticeDesc.get(i).getTitle())), 
						(new SimpleStringProperty(noticeDesc.get(i).getContent())), 
						(new SimpleStringProperty(noticeDesc.get(i).getDate())),
						(new SimpleStringProperty(noticeDesc.get(i).getPassword()))));
			}
			cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
			titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
			DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
			tableview.setItems(searchList);
		}

		// 카테고리 : 공지사항 검색
		for(int i=0;i<noticeDesc.size();i++) {
			if(cate.getValue().equals("공지사항") && noticeDesc.get(i).getTitle().contains(search.getText()) && noticeDesc.get(i).getCate().equals("공지사항")) {
				searchList.add(new Notice(new SimpleStringProperty(noticeDesc.get(i).getNo()), 
						(new SimpleStringProperty(noticeDesc.get(i).getCate())), 
						(new SimpleStringProperty(noticeDesc.get(i).getTitle())), 
						(new SimpleStringProperty(noticeDesc.get(i).getContent())), 
						(new SimpleStringProperty(noticeDesc.get(i).getDate())),
						(new SimpleStringProperty(noticeDesc.get(i).getPassword()))));
			}
			cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
			titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
			DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
			tableview.setItems(searchList);
		}

		// 카테고리 : 이벤트 검색
		for(int i=0;i<noticeDesc.size();i++) {
			if(cate.getValue().equals("이벤트") && noticeDesc.get(i).getTitle().contains(search.getText()) && noticeDesc.get(i).getCate().equals("이벤트")) {
				searchList.add(new Notice(new SimpleStringProperty(noticeDesc.get(i).getNo()), 
						(new SimpleStringProperty(noticeDesc.get(i).getCate())), 
						(new SimpleStringProperty(noticeDesc.get(i).getTitle())), 
						(new SimpleStringProperty(noticeDesc.get(i).getContent())), 
						(new SimpleStringProperty(noticeDesc.get(i).getDate())),
						(new SimpleStringProperty(noticeDesc.get(i).getPassword()))));
			}
			cateCol.setCellValueFactory(cellData -> cellData.getValue().getCate2());
			titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle2());
			DateCol.setCellValueFactory(cellData -> cellData.getValue().getDate2());
			tableview.setItems(searchList);
		}

	}*/

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

	public void editNotice(Notice selectNotice, int select) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/noticeModify.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("공지사항 글 수정");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NoticeModifyController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setNotice(selectNotice, select);
			dialogStage.showAndWait();

			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene2= new Scene(product);
				Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// --------------------------------------------------------------------------------------------------------------------------------------------- >> 수정버튼
	private void modifyBtn(ActionEvent event) {

		if(tab_all.isSelected()) {

			if(search.getText().equals("")) {
				int select = tableview.getSelectionModel().getSelectedIndex();
				Notice selectNotice = list_all.get(select);
				editNotice(selectNotice, select);
			} else {
				int select = tableview.getSelectionModel().getSelectedIndex();
				Notice selectNotice = searchNotice.get(select);
				editNotice(selectNotice, select);
			}

		} else if (tab_notice.isSelected()) {

			if(search.getText().equals("")) {
				int select = noticeTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_notice.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_notice.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = notice.get(i);
						selectNum = i;

						editNotice(selectNotice, selectNum);
					}
				}
			} else {
				int select = noticeTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_notice.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_notice.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = searchNotice.get(select);
						selectNum = i;

						editNotice(selectNotice, selectNum);
					}
				}
			}


		} else if (tab_event.isSelected()) {

			if(search.getText().equals("")) {
				int select = eventTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_event.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_event.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = notice.get(i);
						selectNum = i;

						editNotice(selectNotice, selectNum);
					}
				}
			} else {
				int select = eventTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_event.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_event.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = searchNotice.get(select);
						selectNum = i;

						editNotice(selectNotice, selectNum);
					}
				}
			}
		}

		// 좌측 최근 공지사항 불러오기
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
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		} catch (Exception e) {
		}
	}

	public void deleteNotice(Notice selectNotice, int select) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/noticeDelete.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("공지사항 글 삭제");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NoticeDeleteController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.deleteNotice(selectNotice, select);
			dialogStage.showAndWait();

			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
				Scene scene2= new Scene(product);
				Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
				primaryStage.setScene(scene2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 공지사항 글 삭제
	private void deleteBtn(ActionEvent event) {

		if(tab_all.isSelected()) {

			if(search.getText().equals("")) {
				int select = tableview.getSelectionModel().getSelectedIndex();
				Notice selectNotice = noticeDesc.get(select);
				deleteNotice(selectNotice, select);
			} else {
				int select = tableview.getSelectionModel().getSelectedIndex();
				Notice selectNotice = searchNotice.get(select);
				deleteNotice(selectNotice, select);
			}

		} else if (tab_notice.isSelected()) {

			if(search.getText().equals("")) {
				int select = noticeTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_notice.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_notice.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = notice.get(i);
						selectNum = i;

						deleteNotice(selectNotice, selectNum);
					}
				}
			} else {
				int select = noticeTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_notice.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_notice.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = searchNotice.get(select);
						selectNum = i;

						deleteNotice(selectNotice, selectNum);
					}
				}
			}


		} else if (tab_event.isSelected()) {

			if(search.getText().equals("")) {
				int select = eventTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_event.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_event.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = notice.get(i);
						selectNum = i;

						deleteNotice(selectNotice, selectNum);
					}
				}
			} else {
				int select = eventTable.getSelectionModel().getSelectedIndex();
				Notice selectNotice;
				int selectNum = 0;
				for(int i=0;i<notice.size();i++) {
					if(list_event.get(select).getTitle().equals(notice.get(i).getTitle()) &&
							list_event.get(select).getDate().equals(notice.get(i).getDate())) {
						selectNotice = searchNotice.get(select);
						selectNum = i;

						deleteNotice(selectNotice, selectNum);
					}
				}
			}
		}

		// 좌측 최근 공지사항 불러오기
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


	private void writeBtn(ActionEvent event) {
		System.out.println("글쓰기 메뉴로 이동");
		try {
			Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/noticeWrite.fxml"));
			Scene scene = new Scene(notice);
			Stage primaryStage = (Stage)writeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
