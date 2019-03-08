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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NoticeModifyController implements Initializable{

	@FXML Label prom;
	@FXML TextField no;
	@FXML TextField title;
	@FXML ChoiceBox<String> cate;
	@FXML TextArea content;
	@FXML TextField password;
	@FXML Button ok;
	@FXML Button cancle;

	private Stage dialogStage;
	private Notice Notice;
	private int indexNum;
	static ArrayList<Notice> list = new ArrayList<Notice>();
	ObservableList<String> options = FXCollections.observableArrayList("����","��������","�̺�Ʈ"); 
	
	// �������� ArrayList �� ObservableList
	static ArrayList<Notice> noticeDesc = new ArrayList<Notice>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cate.setValue("����");
		cate.setItems(options);
		ok.setOnAction(e->ok(e));
		cancle.setOnAction(e->cancle(e));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private void cancle(ActionEvent event) {
		dialogStage.close();
	}

	public void setNotice(Notice selectNotice, int select) {
		no.setText(selectNotice.getNo());
		cate.setValue(selectNotice.getCate());
		title.setText(selectNotice.getTitle());
		content.setText(selectNotice.getContent());
		password.setText("");
		// �Ѿ�� �ε��� �ѹ��� Ŭ�������� indexNum �� ����ش�
		// ���� : �ٸ� �޼ҵ忡�� ����ϱ� ����
		indexNum = select;
	}

	private void ok(ActionEvent event) {

		if (cate.getValue().equals("����")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("�� �з� �̼���");
			alert.setHeaderText("");
			alert.setContentText("�� �з��� �������ּ���");
			alert.showAndWait();
		} else if (title.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("���� ���ۼ�");
			alert.setHeaderText("");
			alert.setContentText("������ �ۼ����ּ���");
			alert.showAndWait();
		} else if (content.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("���� ���ۼ�");
			alert.setHeaderText("");
			alert.setContentText("������ �ۼ����ּ���");
			alert.showAndWait();
		} else if (password.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("��й�ȣ ���Է�");
			alert.setHeaderText("");
			alert.setContentText("��й�ȣ ���Է�");
			alert.showAndWait();
		} else {

			// �������� ������ �ҷ��� ArrayList�� ��´�
			StringTokenizer st6 = null;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))){
				list.clear();
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
					list.add(n);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}

			// �������� [�������� ����]
			noticeDesc.clear();
			for(int i=list.size()-1;i>=0;i--) {
				noticeDesc.add(new Notice((list.get(i).getNo()),
						(list.get(i).getCate()),
						(list.get(i).getTitle()),
						(list.get(i).getContent()),
						(list.get(i).getDate()),
						(list.get(i).getPassword())));
			}
			
			if(noticeDesc.get(indexNum).getPassword().equals(password.getText())) {
				
				noticeDesc.get(indexNum).setNo(no.getText());
				noticeDesc.get(indexNum).setCate(cate.getValue());
				noticeDesc.get(indexNum).setTitle(title.getText());
				noticeDesc.get(indexNum).setContent(content.getText());

				System.out.println("��й�ȣ ��ġ!! �ش�� �����Ϸ�");
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("�����Ϸ�");
				alert.setHeaderText("");
				alert.setContentText("�ش���� �����Ǿ����ϴ�.");
				alert.showAndWait();
				
				try (FileWriter fw = new FileWriter("Notice/notice.txt")){
					for(int i=noticeDesc.size()-1; i>=0; i--){
						fw.write(noticeDesc.get(i).getNo());
						fw.write(",");
						fw.write(noticeDesc.get(i).getCate());
						fw.write(",");
						fw.write(noticeDesc.get(i).getTitle());
						fw.write(",");
						fw.write(noticeDesc.get(i).getContent());
						fw.write(",");
						fw.write(noticeDesc.get(i).getDate());
						fw.write(",");
						fw.write(noticeDesc.get(i).getPassword());
						fw.write("\r\n");
					}

					// ������ư Ŭ���� ���� �۾��� �� ������ ���� 
					// ���̾�α� â�� �ݴ´�
					dialogStage.close();

				}catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				prom.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�");
			}
		}
	}

}
