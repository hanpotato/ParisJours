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

	// 메뉴버튼
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button mainBtn;

	// @혜진 버튼추가
	@FXML private Button cardPay;
	@FXML private Button cashPay;
	@FXML private Button cashReceipBtn;
	@FXML private Button receipBtn;

	// 결제리스트 테이블뷰 및 컬럼
	@FXML private TableView<Product> paylist;
	@FXML private TableColumn<Product, String> payName;
	@FXML private TableColumn<Product, String> payAmount;
	@FXML private TableColumn<Product, String> payPrice;

	static ArrayList<Product> list = new ArrayList<Product>();
	static ArrayList<Product> paymentlist = new ArrayList<Product>();
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	static ObservableList<Product> myList;
	static ObservableList<Product> payObser = FXCollections.observableArrayList();

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 메뉴 이벤트 (모든페이지 고정)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));

		// @ 혜진 추가한 버튼
		cardPay.setOnAction(e->cardPay(e));
		cashPay.setOnAction(e->cashPay(e));
		receipBtn.setOnAction(e->receipBtn(e));

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

	// @혜진이 버튼 컨트롤 추가 //카드결제로 이동
	private void cardPay(ActionEvent event)
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/card.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// 다이얼로그 (새창) 스테이지를 만든다.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("카드로 결제하기");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// 수정 Controller를 설정한다
			CardController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
			dialogStage.showAndWait();

			// 카드결제 완료 후 메인으로 돌려보내기
			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
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

	//현금결제로 이동
	private void cashPay(ActionEvent event)
	{
		try {
			// fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/paris/view/cash.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// 다이얼로그 (새창) 스테이지를 만든다.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("현금으로 결제하기");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);


			// 수정 Controller를 설정한다
			CashController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
			dialogStage.showAndWait();

			// 수정버튼이 눌리면 다시 재고관리를 새로고침 한다
			try {
				Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
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

	//현금영수증으로 이동
	

	//영수증 관리로 이동
	private void receipBtn(ActionEvent event)
	{
		try { //화면 이동 구현 소스
			Parent receipPrint = FXMLLoader.load(getClass().getResource("/com/paris/view/receipPrint.fxml"));
			Scene scene = new Scene(receipPrint);
			Stage primaryStage = (Stage)receipBtn.getScene().getWindow(); // 현재 윈도우 가져오기
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
