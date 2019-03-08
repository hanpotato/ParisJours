package com.paris.controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Product;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProductModifyController implements Initializable{

	@FXML private TextField code;
	@FXML private TextField cate;
	@FXML private TextField name;
	@FXML private TextField imagePath;
	@FXML private TextField date;
	@FXML private TextField price;
	@FXML private TextField itemPath;
	@FXML private TextField amount;
	@FXML private Button imageBtn;
	@FXML private Button contentBtn;
	@FXML private Button cancle;
	@FXML private Button ok;

	// 새창을 띄울 다이얼로그 변수 선언
	private Stage dialogStage;

	// 넘겨받는 Product 객체를 받을 변수 선언
	private Product product;

	// 넘겨받는 선택된 인덱스 번호를 담을 변수
	private int indexNum;

	// 파일선택
	private Stage stage;
	private Desktop desktop = Desktop.getDesktop();
	FileChooser fileChooser = new FileChooser();
	File file;

	// 제품이미지 경로를 담을 곳
	String fileImage = "";

	// 제품설명 (txt파일) 경로를 담을 곳
	String fileContent = "";

	// 파일로부터 재고관리 리스트를 불러와 담을 ArrayList 선언 (Static 클래스변수)
	ProductController productcontroller = new ProductController();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 수정완료 버튼 링크
		ok.setOnAction(e->ok(e));
		imageBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
				// 파일선택 이미지파일만 가능하게
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("제품이미지 (JPG, PNG, GIF)", "*.jpg", "*.png", "*.gif")
						);
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					fileImage = file.getName();
					imagePath.setText(file.getName());
				}
			}
		});

		contentBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
				// 파일선택 텍스트 파일만 가능하게
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("제품설명 (텍스트파일)", "*.txt")
						);
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					fileContent = file.getName();
					itemPath.setText(file.getName());
				}
			}
		});
		cancle.setOnAction(e->cancle(e));
	}

	// 다이얼로그 생성 메소드
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	private void cancle(ActionEvent event) {
		dialogStage.close();
	}

	// Product Controller 에서 넘어오는 선택된 아이템과, 선택된 인덱스 번호를 받아
	// TextField (code, cate, name, ...) 에 선택된 아이템 정보들을 setText(지정)로 뿌려준다
	public void setProduct(Product selectedProduct, int select) {
		code.setText(selectedProduct.getpNum());
		cate.setText(selectedProduct.getpCate());
		name.setText(selectedProduct.getpName());
		imagePath.setText(selectedProduct.getpImage());
		date.setText(selectedProduct.getpDate());
		price.setText(selectedProduct.getpPrice());
		itemPath.setText(selectedProduct.getpContent());
		amount.setText(selectedProduct.getpAmount());

		// 넘어온 인덱스 넘버를 클래스변수 indexNum 에 담아준다
		// 이유 : 다른 메소드에서 사용하기 위해
		indexNum = select;
	}


	// 수정버튼을 눌렀을때 이벤트 메소드
	private void ok(ActionEvent event) {

		// ArrayList에서 indexNum에 담긴 인덱스 번호를 사용하여
		// TextField에 입력된 값으로 아이템들을 수정한다 (.setpNum, .setpCate.... 등)
		productcontroller.sList.get(indexNum).setpNum(code.getText());
		productcontroller.sList.get(indexNum).setpCate(cate.getText());
		productcontroller.sList.get(indexNum).setpName(name.getText());
		productcontroller.sList.get(indexNum).setpImage(imagePath.getText());
		productcontroller.sList.get(indexNum).setpDate(date.getText());
		productcontroller.sList.get(indexNum).setpPrice(price.getText());
		productcontroller.sList.get(indexNum).setpContent(itemPath.getText());
		productcontroller.sList.get(indexNum).setpAmount(amount.getText());

	      for(int i=0;i<productcontroller.list.size();i++) {
	    	  if(productcontroller.list.get(i).getpNum().equals(productcontroller.sList.get(indexNum).getpNum())) {
	    		  productcontroller.list.get(i).setpNum(productcontroller.sList.get(indexNum).getpNum());
	    		  productcontroller.list.get(i).setpCate(productcontroller.sList.get(indexNum).getpCate());
	    		  productcontroller.list.get(i).setpName(productcontroller.sList.get(indexNum).getpName());
	    		  productcontroller.list.get(i).setpImage(productcontroller.sList.get(indexNum).getpImage());
	    		  productcontroller.list.get(i).setpDate(productcontroller.sList.get(indexNum).getpDate());
	    		  productcontroller.list.get(i).setpPrice(productcontroller.sList.get(indexNum).getpPrice());
	    		  productcontroller.list.get(i).setpContent(productcontroller.sList.get(indexNum).getpContent());
	    		  productcontroller.list.get(i).setpAmount(productcontroller.sList.get(indexNum).getpAmount());

	    	  }
	      }
	      
		// 위에 변경된 ArrayList를 다시 재고관리 리스트 파일로 저장한다
		try (FileWriter fw = new FileWriter("Product/product.txt")){
			for(int i=0; i<productcontroller.list.size(); i++){
				fw.write(productcontroller.list.get(i).getpNum());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpCate());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpName());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpImage());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpDate().toString());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpPrice());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpContent());
				fw.write(",");
				fw.write(productcontroller.list.get(i).getpAmount());
				fw.write("\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		// 수정버튼 클릭시 위에 작업이 다 끝나고 나면 
		// 다이얼로그 창을 닫는다
		dialogStage.close();
	}
}
