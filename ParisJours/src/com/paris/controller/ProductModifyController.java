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

	// ��â�� ��� ���̾�α� ���� ����
	private Stage dialogStage;

	// �Ѱܹ޴� Product ��ü�� ���� ���� ����
	private Product product;

	// �Ѱܹ޴� ���õ� �ε��� ��ȣ�� ���� ����
	private int indexNum;

	// ���ϼ���
	private Stage stage;
	private Desktop desktop = Desktop.getDesktop();
	FileChooser fileChooser = new FileChooser();
	File file;

	// ��ǰ�̹��� ��θ� ���� ��
	String fileImage = "";

	// ��ǰ���� (txt����) ��θ� ���� ��
	String fileContent = "";

	// ���Ϸκ��� ������ ����Ʈ�� �ҷ��� ���� ArrayList ���� (Static Ŭ��������)
	ProductController productcontroller = new ProductController();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// �����Ϸ� ��ư ��ũ
		ok.setOnAction(e->ok(e));
		imageBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
				// ���ϼ��� �̹������ϸ� �����ϰ�
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("��ǰ�̹��� (JPG, PNG, GIF)", "*.jpg", "*.png", "*.gif")
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
				// ���ϼ��� �ؽ�Ʈ ���ϸ� �����ϰ�
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("��ǰ���� (�ؽ�Ʈ����)", "*.txt")
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

	// ���̾�α� ���� �޼ҵ�
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	private void cancle(ActionEvent event) {
		dialogStage.close();
	}

	// Product Controller ���� �Ѿ���� ���õ� �����۰�, ���õ� �ε��� ��ȣ�� �޾�
	// TextField (code, cate, name, ...) �� ���õ� ������ �������� setText(����)�� �ѷ��ش�
	public void setProduct(Product selectedProduct, int select) {
		code.setText(selectedProduct.getpNum());
		cate.setText(selectedProduct.getpCate());
		name.setText(selectedProduct.getpName());
		imagePath.setText(selectedProduct.getpImage());
		date.setText(selectedProduct.getpDate());
		price.setText(selectedProduct.getpPrice());
		itemPath.setText(selectedProduct.getpContent());
		amount.setText(selectedProduct.getpAmount());

		// �Ѿ�� �ε��� �ѹ��� Ŭ�������� indexNum �� ����ش�
		// ���� : �ٸ� �޼ҵ忡�� ����ϱ� ����
		indexNum = select;
	}


	// ������ư�� �������� �̺�Ʈ �޼ҵ�
	private void ok(ActionEvent event) {

		// ArrayList���� indexNum�� ��� �ε��� ��ȣ�� ����Ͽ�
		// TextField�� �Էµ� ������ �����۵��� �����Ѵ� (.setpNum, .setpCate.... ��)
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
	      
		// ���� ����� ArrayList�� �ٽ� ������ ����Ʈ ���Ϸ� �����Ѵ�
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

		// ������ư Ŭ���� ���� �۾��� �� ������ ���� 
		// ���̾�α� â�� �ݴ´�
		dialogStage.close();
	}
}
