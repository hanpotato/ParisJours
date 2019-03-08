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
	ObservableList<String> options = FXCollections.observableArrayList("선택","공지사항","이벤트"); 
	
	// 공지사항 ArrayList 및 ObservableList
	static ArrayList<Notice> noticeDesc = new ArrayList<Notice>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cate.setValue("선택");
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
		// 넘어온 인덱스 넘버를 클래스변수 indexNum 에 담아준다
		// 이유 : 다른 메소드에서 사용하기 위해
		indexNum = select;
	}

	private void ok(ActionEvent event) {

		if (cate.getValue().equals("선택")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("글 분류 미선택");
			alert.setHeaderText("");
			alert.setContentText("글 분류를 선택해주세요");
			alert.showAndWait();
		} else if (title.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("제목 미작성");
			alert.setHeaderText("");
			alert.setContentText("제목을 작성해주세요");
			alert.showAndWait();
		} else if (content.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("내용 미작성");
			alert.setHeaderText("");
			alert.setContentText("내용을 작성해주세요");
			alert.showAndWait();
		} else if (password.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("비밀번호 미입력");
			alert.setHeaderText("");
			alert.setContentText("비밀번호 미입력");
			alert.showAndWait();
		} else {

			// 공지사항 파일을 불러와 ArrayList에 담는다
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

			// 공지사항 [내림차순 정렬]
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

				System.out.println("비밀번호 일치!! 해당글 수정완료");
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("수정완료");
				alert.setHeaderText("");
				alert.setContentText("해당글이 수정되었습니다.");
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

					// 수정버튼 클릭시 위에 작업이 다 끝나고 나면 
					// 다이얼로그 창을 닫는다
					dialogStage.close();

				}catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				prom.setText("비밀번호가 일치하지 않습니다");
			}
		}
	}

}
