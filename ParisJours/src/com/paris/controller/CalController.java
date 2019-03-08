package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Notice;
import com.paris.model.vo.Product;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CalController implements Initializable {

	private Stage primaryStage;

	// 메뉴버튼 고정
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button main;

	// 오늘 매출
	@FXML private Label today;

	// 일별매출
	@FXML private DatePicker datePick;
	@FXML private Button dateBtn;

	// 정산관리 차트
	@FXML private PieChart category;
	@FXML private BarChart<String, Integer> month;
	@FXML private BarChart<String, Integer> year;
	@FXML private LineChart<String, Number> monthChart;

	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();

	@FXML private Stage stage;

	@FXML private Button datepick;
	@FXML private Button monthpick;

	@FXML private Tab daily_tab;
	@FXML private Tab month_tab;
	@FXML private Label dateContent;

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
	static ArrayList<Notice> notice = new ArrayList<Notice>();

	// ArrayList와 ObservableList
	static ArrayList<Product> list = new ArrayList<Product>();
	ObservableList<String> myList;

	final Label caption = new Label("");


	// 컨트롤러 기본 생성자
	public CalController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 메뉴 이벤트 (모든페이지 고정)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));
		dateBtn.setOnAction(e->dateBtn(e));

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

		// 좌측 최근 공지사항 불러오기
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


		// 정산관리 메뉴 접근 시 오늘 매출현황 출력
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		String now = (new SimpleDateFormat("yyyy-MM-dd").format(date));

		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_"+now+".txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		int sum =0;
		for(int i=0;i<list.size();i++) {
			sum += Integer.parseInt(list.get(i).getpPrice());
		}

		today.setText(String.valueOf(sum));

		// 파이차트 출력
		// 간식용 빵 판매 개수를 담을 곳
		int  sb = 0;
		// 식사용 빵/식빵 판매 개수를 담을 곳
		int mb = 0;
		// 도넛 판매 개수를 담을 곳
		int donut = 0;
		// 페스츄리/파이 판매 개수를 담을 곳
		int pie = 0;
		// 조리빵 판매 개수를 담을 곳
		int cb = 0;
		// 케이크 판매 개수를 담을 곳
		int cake = 0;
		// 샌드위치 판매 개수를 담을 곳
		int sandwich = 0;
		// 디저트 판매 개수를 담을 곳
		int dessert = 0;
		// 음료 판매 개수를 담을 곳
		int drink = 0;
		// 브런치 판매 개수를 담을 곳
		int brunch = 0;
		// 기타 판매 개수를 담을 곳
		int etc = 0;


		// 월별 매출 현황 ( 월 버튼 클릭 ) --------------------------------------------------------------------------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-01.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		int month1 = 0;
		for(int i=0;i<list.size();i++) {
			month1 += Integer.parseInt(list.get(i).getpPrice());
		}

		// 2월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-02.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		int month2 = 0;
		for(int i=0;i<list.size();i++) {
			month2 += Integer.parseInt(list.get(i).getpPrice());
		}
		// 3월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-03.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		int month3 = 0;
		for(int i=0;i<list.size();i++) {
			month3 += Integer.parseInt(list.get(i).getpPrice());
		}

		// ---------------------------------------------------------------------------------- 1분기 매출현황 담기
		int sum1 = month1+month2+month3;


		// 4월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-04.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		int month4 = 0;
		for(int i=0;i<list.size();i++) {
			month4 += Integer.parseInt(list.get(i).getpPrice());
		}


		// 5월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-05.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month5 = 0;
		for(int i=0;i<list.size();i++) {
			month5 += Integer.parseInt(list.get(i).getpPrice());
		}


		// 6월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-06.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month6 = 0;
		for(int i=0;i<list.size();i++) {
			month6 += Integer.parseInt(list.get(i).getpPrice());
		}

		// ---------------------------------------------------------------------------------- 2분기 매출현황 담기
		int sum2 = month4+month5+month6;

		// 7월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-07.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month7 = 0;
		for(int i=0;i<list.size();i++) {
			month7 += Integer.parseInt(list.get(i).getpPrice());
		}


		// 8월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-08.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month8 = 0;
		for(int i=0;i<list.size();i++) {
			month8 += Integer.parseInt(list.get(i).getpPrice());
		}



		// 9월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-09.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month9 = 0;
		for(int i=0;i<list.size();i++) {
			month9 += Integer.parseInt(list.get(i).getpPrice());
		}


		// ---------------------------------------------------------------------------------- 3분기 매출현황 담기
		int sum3 = month7+month8+month9;

		// 10월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-10.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month10 = 0;
		for(int i=0;i<list.size();i++) {
			month10 += Integer.parseInt(list.get(i).getpPrice());
		}



		// 11월
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_2018-11.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		int month11 = 0;
		for(int i=0;i<list.size();i++) {
			month11 += Integer.parseInt(list.get(i).getpPrice());
		}

		// ---------------------------------------------------------------------------------- 4분기 매출현황 담기
		int sum4 = month10+month11;

		// -------------------------------------------------------------------------------------------------  분기별 매출 차트 : 시작
		// 바차트 출력 (분기별매출)
		XYChart.Series cut4 = new XYChart.Series();
		cut4.setName("분기별 매출현황");

		cut4.getData().add(new XYChart.Data("1/4 분기", sum1));
		cut4.getData().add(new XYChart.Data("2/4 분기", sum2));
		cut4.getData().add(new XYChart.Data("3/4 분기", sum3));
		cut4.getData().add(new XYChart.Data("4/4 분기", sum4));

		month.getData().add(cut4);

		// -------------------------------------------------------------------------------------------------  분기별 매출 차트 : 종료

		// 월별 매출 라인차트로 뿌려주기
		XYChart.Series series = new XYChart.Series();
		series.setName("월별 매출");
		//populating the series with data
		series.getData().add(new XYChart.Data("1월", month1));
		series.getData().add(new XYChart.Data("2월", month2));
		series.getData().add(new XYChart.Data("3월", month3));
		series.getData().add(new XYChart.Data("4월", month4));
		series.getData().add(new XYChart.Data("5월", month5));
		series.getData().add(new XYChart.Data("6월", month6));
		series.getData().add(new XYChart.Data("7월", month7));
		series.getData().add(new XYChart.Data("8월", month8));
		series.getData().add(new XYChart.Data("9월", month9));
		series.getData().add(new XYChart.Data("10월", month10));
		series.getData().add(new XYChart.Data("11월", month11));
		monthChart.getData().add(series);

		// ------------------------------------------------------------------------------------ 연간매출현황 차트
		// 바차트 출력 (년간)
		int yearSum = sum1+sum2+sum3+sum4;
		XYChart.Series yearChart = new XYChart.Series();
		yearChart.setName("최근 5년 매출현황");

		yearChart.getData().add(new XYChart.Data("2014"  , 210000000));
		yearChart.getData().add(new XYChart.Data("2015"  , 280000000));
		yearChart.getData().add(new XYChart.Data("2016"  , 260000000));
		yearChart.getData().add(new XYChart.Data("2017"  , 326349200));
		yearChart.getData().add(new XYChart.Data("2018(올해)", yearSum));
		year.getData().add(yearChart);
		// ------------------------------------------------------------------------------------ 연간매출현황 차트 종료


		// ---------------------------------------------------------------------- 상품분류별 판매량

		StringTokenizer stcate = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_cate.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				stcate = new StringTokenizer(str,",");
				p.setpCate(stcate.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}


		for(int i=0;i<list.size();i++) {
			if(list.get(i).getpCate().equals("간식용 빵")) {
				sb += 1;
			} else if (list.get(i).getpCate().equals("식사용 빵/식빵")) {
				mb +=  1;
			} else if (list.get(i).getpCate().equals("도넛")) {
				donut +=  1;
			} else if (list.get(i).getpCate().equals("페스츄리/파이")) {
				pie +=  1;
			} else if (list.get(i).getpCate().equals("조리빵")) {
				cb +=  1;
			} else if (list.get(i).getpCate().equals("케이크")) {
				cake +=  1;
			} else if (list.get(i).getpCate().equals("샌드위치")) {
				sandwich +=  1;
			} else if (list.get(i).getpCate().equals("디저트")) {
				dessert +=  1;
			} else if (list.get(i).getpCate().equals("음료")) {
				drink +=  1;
			} else if (list.get(i).getpCate().equals("브런치")) {
				brunch +=  1;
			} else if (list.get(i).getpCate().equals("기타")) {
				etc +=  1;
			}
		}

		PieChart.Data slice1 = new PieChart.Data("간식용 빵", sb);
		PieChart.Data slice2 = new PieChart.Data("식사용 빵/식빵"  , mb);
		PieChart.Data slice3 = new PieChart.Data("도넛" , donut);
		PieChart.Data slice4 = new PieChart.Data("페스츄리/파이" , pie);
		PieChart.Data slice5 = new PieChart.Data("조리빵"  , cb);
		PieChart.Data slice6 = new PieChart.Data("케이크" , cake);
		PieChart.Data slice7 = new PieChart.Data("샌드위치" , sandwich);
		PieChart.Data slice8 = new PieChart.Data("디저트"  , dessert);
		PieChart.Data slice9 = new PieChart.Data("음료" , drink);
		PieChart.Data slice10 = new PieChart.Data("브런치" , brunch);
		PieChart.Data slice11 = new PieChart.Data("기타" , etc);
		category.getData().add(slice1);
		category.getData().add(slice2);
		category.getData().add(slice3);
		category.getData().add(slice4);
		category.getData().add(slice5);
		category.getData().add(slice6);
		category.getData().add(slice7);
		category.getData().add(slice8);
		category.getData().add(slice9);
		category.getData().add(slice10);
		category.getData().add(slice11);

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		category.getData().forEach((data) ->
		{
			data.getNode().
			addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
			{
				caption.setText(String.valueOf(data.getPieValue()));
			});
		});

	}


	// ---------------------------------------------------	-------------------------------------------------------------------------------------

	// 데이트픽커를 활용한 일자별 매출 확인
	// 날짜를 선택하면 해당 날짜의 매출 출력
	private void dateBtn(ActionEvent event) {
		String pattern = "yyyy-MM-dd";
		datePick.setPromptText(pattern.toLowerCase());
		datePick.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override 
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override 
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});

		// 해당날짜 선택
		System.out.println("일자별 매출 확인하기 -> " + datePick.getValue().toString());

		// 선택한 날짜 매출액 파일 찾기
		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Payment/pay_"+datePick.getValue().toString()+".txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Product p = new Product();
				st = new StringTokenizer(str,",");
				p.setpCate(st.nextToken());
				p.setpName(st.nextToken());
				p.setpAmount(st.nextToken());
				p.setpPrice(st.nextToken());
				list.add(p);
			}
		}catch(IOException e) {
		}

		int sum = 0;
		for(int i=0;i<list.size();i++) {
			sum += Integer.parseInt(list.get(i).getpPrice());
		}

		dateContent.setText(String.valueOf(sum));
	}

	// 공지사항 메뉴버튼 클릭 시 -> 공지사항 메뉴로 이동
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

	// 회원관리 메뉴로 이동
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

	// 재고관리 메뉴로 이동
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

	// 정산관리 메뉴로 이동
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
