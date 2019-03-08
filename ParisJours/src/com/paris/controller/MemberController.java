package com.paris.controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Member;
import com.paris.model.vo.Notice;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberController implements Initializable {

   private Stage primaryStage;

   // 메뉴버튼 고정 (이미지삽입을 위해 Pane을 버튼으로 사용)
   @FXML private Button noticeBtn;
   @FXML private Button memberBtn;
   @FXML private Button itemBtn;
   @FXML private Button payBtn;

   @FXML private Button main;

   // 회원관리 메뉴관련 FXML
   @FXML private Button deleteBtn;
   @FXML private Button modifyBtn;
   @FXML private Button fileLoadBtn;
   @FXML private Button fileSaveBtn;
   @FXML private Button searchBtn;
   @FXML private Button writeBtn;
   @FXML private TableView<Member> tableview;

   // 회원관리 컬럼
   @FXML private TableColumn<Member, String> mNum;
   @FXML private TableColumn<Member, String> mName;
   @FXML private TableColumn<Member, String> mAge;
   @FXML private TableColumn<Member, String> mPhone;
   @FXML private TableColumn<Member, String> mEmail;
   @FXML private TableColumn<Member, String> mAddress;
   @FXML private TableColumn<Member, String> mDate;
   @FXML private TableColumn<Member, String> mPoint;

   // 회원바로 등록
   @FXML private TextField id;
   @FXML private TextField age;
   @FXML private TextField phone;
   @FXML private TextField email;
   @FXML private TextField address;

   //회원검색
   @FXML private TextField searchPhone;
   
   // 공지사항 검색 Obserable List
   static ArrayList<Member> sList = new ArrayList<Member>();
   static ObservableList<Member> searchList;

   // 파일선택
   private Stage stage;
   private Desktop desktop = Desktop.getDesktop();
   FileChooser fileChooser = new FileChooser();
   File file;

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
   static ArrayList<Member> list = new ArrayList<Member>();


   private Member select; 
   private int phoneIndex;
   private Stage dialogStage;

   public MemberController() {

   }

   ObservableList<Member> myList; 

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // 메뉴 이벤트 (모든페이지 고정)
      noticeBtn.setOnAction(e->noticeBtn(e));
      memberBtn.setOnAction(e->memberBtn(e));
      itemBtn.setOnAction(e->itemBtn(e));
      payBtn.setOnAction(e->payBtn(e));

      // 회원 선택수정 및 삭제
      modifyBtn.setOnAction(e->modifyBtn(e));
      deleteBtn.setOnAction(e->deleteBtn(e));

      // 회원리스트 파일 저장 및 불러오기
      fileLoadBtn.setOnAction(e->fileLoadBtn(e));
      fileSaveBtn.setOnAction(e->fileSaveBtn(e));

      // 회원검색
      searchBtn.setOnAction(e->searchBtn(e));

      // ■ 회원 검색창 반응형으로 적용
      searchPhone.setOnKeyReleased(ke -> {

    	  KeyCode keyCode = ke.getCode();
    	  searchBtn.setOnAction(e->searchBtn(e));
    	  tableview.getItems().clear();
    	  sList.clear();
    	  searchList = FXCollections.observableArrayList();

    	  for(int i=0;i<list.size();i++) {
    		  if(list.get(i).getmPhone().contains(searchPhone.getText())) {
    			  sList.add(new Member((list.get(i).getmNum()),
    					  (list.get(i).getmName()),
    					  (list.get(i).getmAge()),
    					  (list.get(i).getmPhone()),
    					  (list.get(i).getmEmail()),
    					  (list.get(i).getmAddress()),
    					  (list.get(i).getmDate()),
    					  (list.get(i).getmPoint())));
    		  }
    	  }
    	  for(int i=0;i<sList.size();i++) {
    		  searchList.add(new Member(new SimpleStringProperty(sList.get(i).getmNum()), 
    				  (new SimpleStringProperty(sList.get(i).getmName())), 
    				  (new SimpleStringProperty(sList.get(i).getmAge())), 
    				  (new SimpleStringProperty(sList.get(i).getmPhone())), 
    				  (new SimpleStringProperty(sList.get(i).getmEmail())), 
    				  (new SimpleStringProperty(sList.get(i).getmAddress())), 
    				  (new SimpleStringProperty(sList.get(i).getmDate())), 
    				  (new SimpleStringProperty(sList.get(i).getmPoint()))));

    		  mNum.setCellValueFactory(cellData -> cellData.getValue().getmNum2());
    		  mName.setCellValueFactory(cellData -> cellData.getValue().getmName2());
    		  mAge.setCellValueFactory(cellData -> cellData.getValue().getmAge2());
    		  mPhone.setCellValueFactory(cellData -> cellData.getValue().getmPhone2());
    		  mEmail.setCellValueFactory(cellData -> cellData.getValue().getmEmail2());
    		  mAddress.setCellValueFactory(cellData -> cellData.getValue().getmAddress2());
    		  mDate.setCellValueFactory(cellData -> cellData.getValue().getmDate2());
    		  mPoint.setCellValueFactory(cellData -> cellData.getValue().getmPoint2());
    		  tableview.setItems(searchList);
    	  }
    	  if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
    		  tableview.getItems().clear();
    		  sList.clear();
    		  searchList = FXCollections.observableArrayList();

    		  for(int i=0;i<list.size();i++) {
    			  if(list.get(i).getmPhone().contains(searchPhone.getText())) {
    				  sList.add(new Member((list.get(i).getmNum()),
    						  (list.get(i).getmName()),
    						  (list.get(i).getmAge()),
    						  (list.get(i).getmPhone()),
    						  (list.get(i).getmEmail()),
    						  (list.get(i).getmAddress()),
    						  (list.get(i).getmDate()),
    						  (list.get(i).getmPoint())));
    			  }
    		  }
    		  for(int i=0;i<sList.size();i++) {
    			  searchList.add(new Member(new SimpleStringProperty(sList.get(i).getmNum()), 
    					  (new SimpleStringProperty(sList.get(i).getmName())), 
    					  (new SimpleStringProperty(sList.get(i).getmAge())), 
    					  (new SimpleStringProperty(sList.get(i).getmPhone())), 
    					  (new SimpleStringProperty(sList.get(i).getmEmail())), 
    					  (new SimpleStringProperty(sList.get(i).getmAddress())), 
    					  (new SimpleStringProperty(sList.get(i).getmDate())), 
    					  (new SimpleStringProperty(sList.get(i).getmPoint()))));

    			  mNum.setCellValueFactory(cellData -> cellData.getValue().getmNum2());
    			  mName.setCellValueFactory(cellData -> cellData.getValue().getmName2());
    			  mAge.setCellValueFactory(cellData -> cellData.getValue().getmAge2());
    			  mPhone.setCellValueFactory(cellData -> cellData.getValue().getmPhone2());
    			  mEmail.setCellValueFactory(cellData -> cellData.getValue().getmEmail2());
    			  mAddress.setCellValueFactory(cellData -> cellData.getValue().getmAddress2());
    			  mDate.setCellValueFactory(cellData -> cellData.getValue().getmDate2());
    			  mPoint.setCellValueFactory(cellData -> cellData.getValue().getmPoint2());
    			  tableview.setItems(searchList);
    		  }
    	  }

      });

      // 회원 바로등록
      writeBtn.setOnAction(e->writeBtn(e));

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

      // 회원목록 불러오기
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Member/member.txt")))){
         list.clear();
         String str = null;
         while((str = br.readLine())!= null){
            Member m = new Member();
            st = new StringTokenizer(str,",");
            m.setmNum(st.nextToken());
            m.setmName(st.nextToken());
            m.setmAge(st.nextToken());
            m.setmPhone(st.nextToken());
            m.setmEmail(st.nextToken());
            m.setmAddress(st.nextToken());
            m.setmDate(st.nextToken());
            m.setmPoint(st.nextToken());
            list.add(m);
         }
      }catch(IOException e) {
         e.printStackTrace();
      }
	  tableview.getItems().clear();
	  sList.clear();
	  searchList = FXCollections.observableArrayList();

	  for(int i=0;i<list.size();i++) {
		  if(list.get(i).getmPhone().contains(searchPhone.getText())) {
			  sList.add(new Member((list.get(i).getmNum()),
					  (list.get(i).getmName()),
					  (list.get(i).getmAge()),
					  (list.get(i).getmPhone()),
					  (list.get(i).getmEmail()),
					  (list.get(i).getmAddress()),
					  (list.get(i).getmDate()),
					  (list.get(i).getmPoint())));
		  }
	  }
	  for(int i=0;i<sList.size();i++) {
		  searchList.add(new Member(new SimpleStringProperty(sList.get(i).getmNum()), 
				  (new SimpleStringProperty(sList.get(i).getmName())), 
				  (new SimpleStringProperty(sList.get(i).getmAge())), 
				  (new SimpleStringProperty(sList.get(i).getmPhone())), 
				  (new SimpleStringProperty(sList.get(i).getmEmail())), 
				  (new SimpleStringProperty(sList.get(i).getmAddress())), 
				  (new SimpleStringProperty(sList.get(i).getmDate())), 
				  (new SimpleStringProperty(sList.get(i).getmPoint()))));

		  mNum.setCellValueFactory(cellData -> cellData.getValue().getmNum2());
		  mName.setCellValueFactory(cellData -> cellData.getValue().getmName2());
		  mAge.setCellValueFactory(cellData -> cellData.getValue().getmAge2());
		  mPhone.setCellValueFactory(cellData -> cellData.getValue().getmPhone2());
		  mEmail.setCellValueFactory(cellData -> cellData.getValue().getmEmail2());
		  mAddress.setCellValueFactory(cellData -> cellData.getValue().getmAddress2());
		  mDate.setCellValueFactory(cellData -> cellData.getValue().getmDate2());
		  mPoint.setCellValueFactory(cellData -> cellData.getValue().getmPoint2());
		  tableview.setItems(searchList);
	  }
   }

   private void fileLoadBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("텍스트파일", "*.txt"));
      File file = fileChooser.showOpenDialog(stage);
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())))){
         list.clear();
         String str = null;
         while((str = br.readLine())!= null){
            Member m = new Member();
            st = new StringTokenizer(str,",");
            m.setmNum(st.nextToken());
            m.setmName(st.nextToken());
            m.setmAge(st.nextToken());
            m.setmPhone(st.nextToken());
            m.setmEmail(st.nextToken());
            m.setmAddress(st.nextToken());
            m.setmDate(st.nextToken());
            m.setmPoint(st.nextToken());
            list.add(m);
         }
      }catch(IOException e) {
         e.printStackTrace();
      }
	  tableview.getItems().clear();
	  sList.clear();
	  searchList = FXCollections.observableArrayList();

	  for(int i=0;i<list.size();i++) {
		  if(list.get(i).getmPhone().contains(searchPhone.getText())) {
			  sList.add(new Member((list.get(i).getmNum()),
					  (list.get(i).getmName()),
					  (list.get(i).getmAge()),
					  (list.get(i).getmPhone()),
					  (list.get(i).getmEmail()),
					  (list.get(i).getmAddress()),
					  (list.get(i).getmDate()),
					  (list.get(i).getmPoint())));
		  }
	  }
	  for(int i=0;i<sList.size();i++) {
		  searchList.add(new Member(new SimpleStringProperty(sList.get(i).getmNum()), 
				  (new SimpleStringProperty(sList.get(i).getmName())), 
				  (new SimpleStringProperty(sList.get(i).getmAge())), 
				  (new SimpleStringProperty(sList.get(i).getmPhone())), 
				  (new SimpleStringProperty(sList.get(i).getmEmail())), 
				  (new SimpleStringProperty(sList.get(i).getmAddress())), 
				  (new SimpleStringProperty(sList.get(i).getmDate())), 
				  (new SimpleStringProperty(sList.get(i).getmPoint()))));

		  mNum.setCellValueFactory(cellData -> cellData.getValue().getmNum2());
		  mName.setCellValueFactory(cellData -> cellData.getValue().getmName2());
		  mAge.setCellValueFactory(cellData -> cellData.getValue().getmAge2());
		  mPhone.setCellValueFactory(cellData -> cellData.getValue().getmPhone2());
		  mEmail.setCellValueFactory(cellData -> cellData.getValue().getmEmail2());
		  mAddress.setCellValueFactory(cellData -> cellData.getValue().getmAddress2());
		  mDate.setCellValueFactory(cellData -> cellData.getValue().getmDate2());
		  mPoint.setCellValueFactory(cellData -> cellData.getValue().getmPoint2());
		  tableview.setItems(searchList);
	  }
   }

   public void searchMemberPop(Member phonesearch, int phoneIndex) {
      try {
         // fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/memberSearch.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // 다이얼로그 (새창) 스테이지를 만든다.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("회원 검색 결과");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // 수정 Controller를 설정한다
         MemberSearchController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // 매개값으로 받은 아이템자료와 인덱스 번호를 controller에 setMember 메소드로 넘겨준다
         controller.searchMember(phonesearch, phoneIndex);
         // 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
         dialogStage.showAndWait();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
      
   private void searchBtn (ActionEvent event) {
	   int select = tableview.getSelectionModel().getSelectedIndex();
                  Member phonesearch = sList.get(select);
                  searchMemberPop(phonesearch, select);
    }

   private void fileSaveBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("텍스트파일", "*.txt"));
      File file = fileChooser.showSaveDialog(stage);
      try (FileWriter fw = new FileWriter(file.getAbsolutePath())) {
         for(int i=0; i<list.size(); i++){
            fw.write(list.get(i).getmNum());
            fw.write(",");
            fw.write(list.get(i).getmName());
            fw.write(",");
            fw.write(list.get(i).getmAge());
            fw.write(",");
            fw.write(list.get(i).getmPhone());
            fw.write(",");
            fw.write(list.get(i).getmEmail());
            fw.write(",");
            fw.write(list.get(i).getmAddress());
            fw.write(",");
            fw.write(list.get(i).getmDate());
            fw.write(",");
            fw.write(list.get(i).getmPoint());
            fw.write("\r\n");
         }

      }catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void editMember(Member selectMember, int select) {
      try {
         // fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/memberModify.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // 다이얼로그 (새창) 스테이지를 만든다.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("상품정보 수정");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // 수정 Controller를 설정한다
         MemberModifyController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // 매개값으로 받은 아이템자료와 인덱스 번호를 controller에 setProduct 메소드로 넘겨준다
         controller.setMember(selectMember, select);

         // 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
         dialogStage.showAndWait();

         // 수정버튼이 눌리면 다시 재고관리를 새로고침 한다
         try {
            Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
            Scene scene2= new Scene(member);
            Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene2);
         } catch (Exception e) {
            e.printStackTrace();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   private void modifyBtn(ActionEvent event) {
      int select = tableview.getSelectionModel().getSelectedIndex();
      Member selectMember = sList.get(select);
      editMember(selectMember, select);
   }


   private void deleteBtn(ActionEvent event) {
      //테이블을 감시하는 리스너가 필요.
      int selectIndex = tableview.getSelectionModel().getSelectedIndex();
      
      Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("회원 삭제");
		alert.setHeaderText("");
		alert.setContentText("삭제되었습니다");
		alert.showAndWait();
		
      for(int i=0;i<list.size();i++) {
    	  if(list.get(i).getmPhone().equals(sList.get(selectIndex).getmPhone())) {
    		  list.remove(i);
    		  
    	  }
      }
      tableview.getItems().remove(selectIndex);
      sList.remove(selectIndex);
      
      try (FileWriter fw = new FileWriter("Member/member.txt")){
         for(int i=0; i<list.size(); i++){
            fw.write(list.get(i).getmNum());
            fw.write(",");
            fw.write(list.get(i).getmName());
            fw.write(",");
            fw.write(list.get(i).getmAge());
            fw.write(",");
            fw.write(list.get(i).getmPhone());
            fw.write(",");
            fw.write(list.get(i).getmEmail());
            fw.write(",");
            fw.write(list.get(i).getmAddress());
            fw.write(",");
            fw.write(list.get(i).getmDate());
            fw.write(",");
            fw.write(list.get(i).getmPoint());
            fw.write("\r\n");
         }
      }catch (Exception e) {
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

   // 휴대폰번호 숫자만 입력받게
   public boolean NumCheck(String str) {
      char c;
      for (int i=0; i<str.length(); i++) {
         c = str.charAt(i);
         if (c<48 || c>57) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("휴대폰 번호 오류");
            alert.setHeaderText("");
            alert.setContentText("숫자만 입력해주세요");
            alert.showAndWait();
            return false;
         }
      }
      return true;
   }

   private void writeBtn(ActionEvent event) {


      if(id.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("회원이름 미입력");
         alert.setHeaderText("");
         alert.setContentText("회원이름을 입력하세요");
         alert.showAndWait();
      } else if (age.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("나이 미입력");
         alert.setHeaderText("");
         alert.setContentText("나이를 입력하세요");
         alert.showAndWait();
      } else if (phone.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("휴대폰 미입력");
         alert.setHeaderText("");
         alert.setContentText("휴대폰번호를 입력하세요");
         alert.showAndWait();
      } else if (email.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("이메일 미입력");
         alert.setHeaderText("");
         alert.setContentText("이메일번호 입력하세요");
         alert.showAndWait();
      } else if (address.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("주소 미입력");
         alert.setHeaderText("");
         alert.setContentText("주소를 입력하세요");
         alert.showAndWait();
      } else {

         // 회원번호 자동 생성
         String num ="";
         if(list.size() != 0){
            num = ((Integer.parseInt(list.get(list.size()-1).getmNum())+1)+"");
         }else{
            num = 1+"";
         }

         // 가입날짜 생성
         Calendar calendar = Calendar.getInstance();
         java.util.Date date = calendar.getTime();
         String now = (new SimpleDateFormat("yy-MM-dd").format(date));

         // 포인트 생성
         String point = 0+"";

         list.add(new Member(num, id.getText(), age.getText(), 
               phone.getText(), email.getText(), address.getText(), now, point));

         try (FileWriter fw = new FileWriter("Member/member.txt")){

            for(int i=0; i<list.size(); i++){
               fw.write(list.get(i).getmNum());
               fw.write(",");
               fw.write(list.get(i).getmName());
               fw.write(",");
               fw.write(list.get(i).getmAge());
               fw.write(",");
               fw.write(list.get(i).getmPhone());
               fw.write(",");
               fw.write(list.get(i).getmEmail());
               fw.write(",");
               fw.write(list.get(i).getmAddress());
               fw.write(",");
               fw.write(list.get(i).getmDate());
               fw.write(",");
               fw.write(list.get(i).getmPoint());
               fw.write("\r\n");
            }

         }catch (Exception e) {
            e.printStackTrace();
         }

         // 상품 등록 완료 메시지
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.initOwner(dialogStage);
         alert.setTitle("회원등록 완료");
         alert.setHeaderText("");
         alert.setContentText(id.getText() + "님 회원등록 완료");
         alert.showAndWait();


         System.out.println(id.getText() + " 님 회원등록 완료!");
         try {

            Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
            Scene scene = new Scene(member);
            Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);


         } catch (Exception e) {
            e.printStackTrace();
         }

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
         Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
         primaryStage.setScene(scene);


      } catch (Exception e) {
         e.printStackTrace();
      }
   }


}