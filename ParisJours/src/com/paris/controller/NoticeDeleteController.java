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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NoticeDeleteController implements Initializable{

	@FXML Label prom;
	@FXML TextField passCheck;
	@FXML Button ok;
	@FXML Button cancle;

	private Stage dialogStage;
	private Notice Notice;
	private int indexNum;
	static ArrayList<Notice> list = new ArrayList<Notice>();
	static ArrayList<Notice> noticeDesc = new ArrayList<Notice>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ok.setOnAction(e->ok(e));
		cancle.setOnAction(e->cancle(e));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	private void cancle(ActionEvent event) {
		dialogStage.close();
	}

	public void deleteNotice(Notice selectNotice, int select) {
		indexNum = select;
	}

	private void ok(ActionEvent event) {
		StringTokenizer st = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))){
			list.clear();
			String str = null;
			while((str = br.readLine())!= null){
				Notice n = new Notice();
				st = new StringTokenizer(str,",");
				n.setNo(st.nextToken());
				n.setCate(st.nextToken());
				n.setTitle(st.nextToken());
				n.setContent(st.nextToken());
				n.setDate(st.nextToken());
				n.setPassword((st.nextToken()));
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

		if(noticeDesc.get(indexNum).getPassword().equals(passCheck.getText())) {
			noticeDesc.remove(indexNum);
			
			System.out.println("비밀번호 일치!! 해당글 삭제완료");
			
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

				dialogStage.close();

			}catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			prom.setText("비밀번호가 일치하지 않습니다!");
		}


	}

}
