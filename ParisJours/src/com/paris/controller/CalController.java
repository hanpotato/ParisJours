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

	// �޴���ư ����
	@FXML private Button noticeBtn;
	@FXML private Button memberBtn;
	@FXML private Button itemBtn;
	@FXML private Button payBtn;

	@FXML private Button main;

	// ���� ����
	@FXML private Label today;

	// �Ϻ�����
	@FXML private DatePicker datePick;
	@FXML private Button dateBtn;

	// ������� ��Ʈ
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

	// ArrayList�� ObservableList
	static ArrayList<Product> list = new ArrayList<Product>();
	ObservableList<String> myList;

	final Label caption = new Label("");


	// ��Ʈ�ѷ� �⺻ ������
	public CalController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// �޴� �̺�Ʈ (��������� ����)
		noticeBtn.setOnAction(e->noticeBtn(e));
		memberBtn.setOnAction(e->memberBtn(e));
		itemBtn.setOnAction(e->itemBtn(e));
		payBtn.setOnAction(e->payBtn(e));
		dateBtn.setOnAction(e->dateBtn(e));

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


		// ������� �޴� ���� �� ���� ������Ȳ ���
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

		// ������Ʈ ���
		// ���Ŀ� �� �Ǹ� ������ ���� ��
		int  sb = 0;
		// �Ļ�� ��/�Ļ� �Ǹ� ������ ���� ��
		int mb = 0;
		// ���� �Ǹ� ������ ���� ��
		int donut = 0;
		// �佺��/���� �Ǹ� ������ ���� ��
		int pie = 0;
		// ������ �Ǹ� ������ ���� ��
		int cb = 0;
		// ����ũ �Ǹ� ������ ���� ��
		int cake = 0;
		// ������ġ �Ǹ� ������ ���� ��
		int sandwich = 0;
		// ����Ʈ �Ǹ� ������ ���� ��
		int dessert = 0;
		// ���� �Ǹ� ������ ���� ��
		int drink = 0;
		// �귱ġ �Ǹ� ������ ���� ��
		int brunch = 0;
		// ��Ÿ �Ǹ� ������ ���� ��
		int etc = 0;


		// ���� ���� ��Ȳ ( �� ��ư Ŭ�� ) --------------------------------------------------------------------------------------------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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

		// 2��
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
		// 3��
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

		// ---------------------------------------------------------------------------------- 1�б� ������Ȳ ���
		int sum1 = month1+month2+month3;


		// 4��
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


		// 5��
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


		// 6��
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

		// ---------------------------------------------------------------------------------- 2�б� ������Ȳ ���
		int sum2 = month4+month5+month6;

		// 7��
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


		// 8��
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



		// 9��
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


		// ---------------------------------------------------------------------------------- 3�б� ������Ȳ ���
		int sum3 = month7+month8+month9;

		// 10��
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



		// 11��
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

		// ---------------------------------------------------------------------------------- 4�б� ������Ȳ ���
		int sum4 = month10+month11;

		// -------------------------------------------------------------------------------------------------  �б⺰ ���� ��Ʈ : ����
		// ����Ʈ ��� (�б⺰����)
		XYChart.Series cut4 = new XYChart.Series();
		cut4.setName("�б⺰ ������Ȳ");

		cut4.getData().add(new XYChart.Data("1/4 �б�", sum1));
		cut4.getData().add(new XYChart.Data("2/4 �б�", sum2));
		cut4.getData().add(new XYChart.Data("3/4 �б�", sum3));
		cut4.getData().add(new XYChart.Data("4/4 �б�", sum4));

		month.getData().add(cut4);

		// -------------------------------------------------------------------------------------------------  �б⺰ ���� ��Ʈ : ����

		// ���� ���� ������Ʈ�� �ѷ��ֱ�
		XYChart.Series series = new XYChart.Series();
		series.setName("���� ����");
		//populating the series with data
		series.getData().add(new XYChart.Data("1��", month1));
		series.getData().add(new XYChart.Data("2��", month2));
		series.getData().add(new XYChart.Data("3��", month3));
		series.getData().add(new XYChart.Data("4��", month4));
		series.getData().add(new XYChart.Data("5��", month5));
		series.getData().add(new XYChart.Data("6��", month6));
		series.getData().add(new XYChart.Data("7��", month7));
		series.getData().add(new XYChart.Data("8��", month8));
		series.getData().add(new XYChart.Data("9��", month9));
		series.getData().add(new XYChart.Data("10��", month10));
		series.getData().add(new XYChart.Data("11��", month11));
		monthChart.getData().add(series);

		// ------------------------------------------------------------------------------------ ����������Ȳ ��Ʈ
		// ����Ʈ ��� (�Ⱓ)
		int yearSum = sum1+sum2+sum3+sum4;
		XYChart.Series yearChart = new XYChart.Series();
		yearChart.setName("�ֱ� 5�� ������Ȳ");

		yearChart.getData().add(new XYChart.Data("2014"  , 210000000));
		yearChart.getData().add(new XYChart.Data("2015"  , 280000000));
		yearChart.getData().add(new XYChart.Data("2016"  , 260000000));
		yearChart.getData().add(new XYChart.Data("2017"  , 326349200));
		yearChart.getData().add(new XYChart.Data("2018(����)", yearSum));
		year.getData().add(yearChart);
		// ------------------------------------------------------------------------------------ ����������Ȳ ��Ʈ ����


		// ---------------------------------------------------------------------- ��ǰ�з��� �Ǹŷ�

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
			if(list.get(i).getpCate().equals("���Ŀ� ��")) {
				sb += 1;
			} else if (list.get(i).getpCate().equals("�Ļ�� ��/�Ļ�")) {
				mb +=  1;
			} else if (list.get(i).getpCate().equals("����")) {
				donut +=  1;
			} else if (list.get(i).getpCate().equals("�佺��/����")) {
				pie +=  1;
			} else if (list.get(i).getpCate().equals("������")) {
				cb +=  1;
			} else if (list.get(i).getpCate().equals("����ũ")) {
				cake +=  1;
			} else if (list.get(i).getpCate().equals("������ġ")) {
				sandwich +=  1;
			} else if (list.get(i).getpCate().equals("����Ʈ")) {
				dessert +=  1;
			} else if (list.get(i).getpCate().equals("����")) {
				drink +=  1;
			} else if (list.get(i).getpCate().equals("�귱ġ")) {
				brunch +=  1;
			} else if (list.get(i).getpCate().equals("��Ÿ")) {
				etc +=  1;
			}
		}

		PieChart.Data slice1 = new PieChart.Data("���Ŀ� ��", sb);
		PieChart.Data slice2 = new PieChart.Data("�Ļ�� ��/�Ļ�"  , mb);
		PieChart.Data slice3 = new PieChart.Data("����" , donut);
		PieChart.Data slice4 = new PieChart.Data("�佺��/����" , pie);
		PieChart.Data slice5 = new PieChart.Data("������"  , cb);
		PieChart.Data slice6 = new PieChart.Data("����ũ" , cake);
		PieChart.Data slice7 = new PieChart.Data("������ġ" , sandwich);
		PieChart.Data slice8 = new PieChart.Data("����Ʈ"  , dessert);
		PieChart.Data slice9 = new PieChart.Data("����" , drink);
		PieChart.Data slice10 = new PieChart.Data("�귱ġ" , brunch);
		PieChart.Data slice11 = new PieChart.Data("��Ÿ" , etc);
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

	// ����Ʈ��Ŀ�� Ȱ���� ���ں� ���� Ȯ��
	// ��¥�� �����ϸ� �ش� ��¥�� ���� ���
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

		// �ش糯¥ ����
		System.out.println("���ں� ���� Ȯ���ϱ� -> " + datePick.getValue().toString());

		// ������ ��¥ ����� ���� ã��
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

	// �������� �޴���ư Ŭ�� �� -> �������� �޴��� �̵�
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

	// ȸ������ �޴��� �̵�
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

	// ������ �޴��� �̵�
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

	// ������� �޴��� �̵�
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
