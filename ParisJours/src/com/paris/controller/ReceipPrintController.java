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
import com.paris.model.vo.Pay;
import com.paris.model.vo.Product;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReceipPrintController implements Initializable{

	// 메뉴버튼
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button mainBtn;

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

	// 현금영수증 테이블뷰와 테이블컬렁 초기화
	@FXML private TableView<Pay> receiTable;
	@FXML private TableColumn<Pay, String> rNo;
	@FXML private TableColumn<Pay, String> rDate;
	@FXML private TableColumn<Pay, String> rCate;
	@FXML private TableColumn<Pay, String> rTotal;

	@FXML private TextArea printArea;
	@FXML private Button cancelPrintBtn;
	@FXML private Button print;

	// 영수증 테이블뷰
	static ObservableList<Pay> rePrint;
	static ArrayList<Pay> printList = new ArrayList<Pay>();

	static ArrayList<Product> paymentlist = new ArrayList<Product>();
	static ArrayList<Notice> notice = new ArrayList<Notice>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));

		cancelPrintBtn.setOnAction(e->cancelPrintBtn(e));
		print.setOnAction(e->print(e));

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

		// 최근 공지사항 목록 업데이트 -------------------------------------------------------------------- 종료


		// 영수증 리스트 파일 불러와서 리스트에 담기
		StringTokenizer st7 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/Print.txt")))) {
			printList.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Pay p = new Pay();
				st7 = new StringTokenizer(str,",");
				p.setNum(st7.nextToken());
				p.setCardorcash(st7.nextToken());
				p.setPayDate(st7.nextToken());
				p.setPayTotal(st7.nextToken());
				p.setItemList(st7.nextToken());
				printList.add(p);
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

		ArrayList<Pay> printList_sub = new ArrayList<Pay>();
		for(int i=printList.size()-1;i>=0;i--) {
			printList_sub.add(new Pay(printList.get(i).getNum(),
					(printList.get(i).getCardorcash()), 
					(printList.get(i).getPayDate()), 
					(printList.get(i).getPayTotal()), 
					(printList.get(i).getItemList())));
		}

		rePrint = FXCollections.observableArrayList();
		for(int i=0;i<printList_sub.size();i++) {
			rePrint.add(new Pay(new SimpleStringProperty(printList_sub.get(i).getNum()),
					(new SimpleStringProperty(printList_sub.get(i).getCardorcash())), 
					(new SimpleStringProperty(printList_sub.get(i).getPayDate())), 
					(new SimpleStringProperty(printList_sub.get(i).getPayTotal())), 
					(new SimpleStringProperty(printList_sub.get(i).getItemList()))));
		}
		
		rNo.setCellValueFactory(cellData -> cellData.getValue().getNum2());
		rCate.setCellValueFactory(cellData -> cellData.getValue().getCardorcash2());
		rDate.setCellValueFactory(cellData -> cellData.getValue().getPayDate2());
		rTotal.setCellValueFactory(cellData -> cellData.getValue().getPayTotal2());
		receiTable.setItems(rePrint);

		// 테이블뷰에 마우스 클릭 이벤트리스너 장착!!!
		receiTable.setOnMouseClicked(event -> {
			if(receiTable.getSelectionModel().getSelectedItem() != null) {
				int itemNum = receiTable.getSelectionModel().getFocusedIndex();

				// 우측 해당 상품 정보 출력
				printArea.clear();
				StringTokenizer st2 = null;
				// 영수증 출력부분
				printArea.setText("역삼점 PARISJOURS" + "\n" +
						"02-12134-4567" + "\n" +
						"서울시 강남구 역삼동 KH 정보교육원" + "\n" +
						"--------------------------------" + "\n" +
						"역삼점을 이용해주셔서 감사합니다." + "\n" +
						"결제시간 : "+ printList_sub.get(itemNum).getPayDate() + "\n" +
						"총 결제 금액 : " + printList_sub.get(itemNum).getPayTotal() + "\n" +
						"--------------------------------" + "\n");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(printList_sub.get(itemNum).getItemList())))){
					String textFile = null;
					while((textFile = br.readLine())!= null){
						st2 = new StringTokenizer(textFile,"\n");
						printArea.appendText(st2.nextToken() + "\n");
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				printArea.appendText("--------------------------------" + "\n"  + "또 이용해주세요 감사합니다!");

			}});

		//메인으로 버튼
		mainBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("메인페이지로 이동");
				try {
					Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
					Scene scene = new Scene(main);
					Stage primaryStage = (Stage)mainBtn.getScene().getWindow(); // 현재 윈도우 가져오기
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	// ------------------------------------------------------------------------------------------------------------ 닫기버튼
	private void cancelPrintBtn(ActionEvent event)
	{
		try { //화면 이동 구현 소스
			Parent next = FXMLLoader.load(getClass().getResource("/com/paris/view/pay.fxml"));
			Scene scene = new Scene(next);
			Stage primaryStage = (Stage)cancelPrintBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------------------------------ 인쇄 버튼
		private void print(ActionEvent event)
		{
			System.out.println("찌직~찌지직!!!!! 찌직~");
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
			Stage primaryStage = (Stage)payBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
