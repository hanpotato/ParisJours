package com.paris.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Product;

import Product.TextPath;
import Product.images.path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProductSearchController implements Initializable {

	@FXML private TextField code;
	@FXML private TextField cate;
	@FXML private TextField name;
	@FXML private ImageView imagePath;
	@FXML private TextField date;
	@FXML private TextField price;

	@FXML private TextField amount;   
	@FXML private Button cancle;
	@FXML private Button ok;
	@FXML private TextArea itemPath;


	@FXML private TextField content;
	@FXML private TextField num;

	private Stage dialogStage;

	private Product product;

	private int indexNum;


	static ArrayList<Product> List=new ArrayList<>();
	ProductController productcontroller = new ProductController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 수정완료 버튼 링크
		ok.setOnAction(e->ok(e));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void searchProduct(Product namesearch,int nameIndex ) {
/*
		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Product/product.txt")))){
			List.clear();
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
				List.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
*/
		code.setText(namesearch.getpNum());
		cate.setText(namesearch.getpCate());
		name.setText(namesearch.getpName());
		date.setText(namesearch.getpDate());
		price.setText(namesearch.getpPrice());

		amount.setText(namesearch.getpAmount());

		String aPath = path.class.getResource("").getPath();
		String bPath = TextPath.class.getResource("").getPath();

		try (FileInputStream input = new FileInputStream(aPath + "/" + productcontroller.sList.get(nameIndex).getpImage())) {
			Image image = new Image(input);
			imagePath.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringTokenizer st2 = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bPath + "/" +productcontroller.sList.get(nameIndex).getpContent())))){
			String textFile = null;
			while((textFile = br.readLine())!= null){
				st2 = new StringTokenizer(textFile,"\n");
				itemPath.appendText(st2.nextToken() + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		indexNum=nameIndex;

	}

	private void ok(ActionEvent event) {
		dialogStage.close();
	}


}
