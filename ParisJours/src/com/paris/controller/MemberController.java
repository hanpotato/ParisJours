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

   // �޴���ư ���� (�̹��������� ���� Pane�� ��ư���� ���)
   @FXML private Button noticeBtn;
   @FXML private Button memberBtn;
   @FXML private Button itemBtn;
   @FXML private Button payBtn;

   @FXML private Button main;

   // ȸ������ �޴����� FXML
   @FXML private Button deleteBtn;
   @FXML private Button modifyBtn;
   @FXML private Button fileLoadBtn;
   @FXML private Button fileSaveBtn;
   @FXML private Button searchBtn;
   @FXML private Button writeBtn;
   @FXML private TableView<Member> tableview;

   // ȸ������ �÷�
   @FXML private TableColumn<Member, String> mNum;
   @FXML private TableColumn<Member, String> mName;
   @FXML private TableColumn<Member, String> mAge;
   @FXML private TableColumn<Member, String> mPhone;
   @FXML private TableColumn<Member, String> mEmail;
   @FXML private TableColumn<Member, String> mAddress;
   @FXML private TableColumn<Member, String> mDate;
   @FXML private TableColumn<Member, String> mPoint;

   // ȸ���ٷ� ���
   @FXML private TextField id;
   @FXML private TextField age;
   @FXML private TextField phone;
   @FXML private TextField email;
   @FXML private TextField address;

   //ȸ���˻�
   @FXML private TextField searchPhone;
   
   // �������� �˻� Obserable List
   static ArrayList<Member> sList = new ArrayList<Member>();
   static ObservableList<Member> searchList;

   // ���ϼ���
   private Stage stage;
   private Desktop desktop = Desktop.getDesktop();
   FileChooser fileChooser = new FileChooser();
   File file;

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
   static ArrayList<Member> list = new ArrayList<Member>();


   private Member select; 
   private int phoneIndex;
   private Stage dialogStage;

   public MemberController() {

   }

   ObservableList<Member> myList; 

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // �޴� �̺�Ʈ (��������� ����)
      noticeBtn.setOnAction(e->noticeBtn(e));
      memberBtn.setOnAction(e->memberBtn(e));
      itemBtn.setOnAction(e->itemBtn(e));
      payBtn.setOnAction(e->payBtn(e));

      // ȸ�� ���ü��� �� ����
      modifyBtn.setOnAction(e->modifyBtn(e));
      deleteBtn.setOnAction(e->deleteBtn(e));

      // ȸ������Ʈ ���� ���� �� �ҷ�����
      fileLoadBtn.setOnAction(e->fileLoadBtn(e));
      fileSaveBtn.setOnAction(e->fileSaveBtn(e));

      // ȸ���˻�
      searchBtn.setOnAction(e->searchBtn(e));

      // �� ȸ�� �˻�â ���������� ����
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

      // ȸ�� �ٷε��
      writeBtn.setOnAction(e->writeBtn(e));

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

      // ȸ����� �ҷ�����
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
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("�ؽ�Ʈ����", "*.txt"));
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
         // fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/memberSearch.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // ���̾�α� (��â) ���������� �����.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("ȸ�� �˻� ���");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // ���� Controller�� �����Ѵ�
         MemberSearchController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // �Ű������� ���� �������ڷ�� �ε��� ��ȣ�� controller�� setMember �޼ҵ�� �Ѱ��ش�
         controller.searchMember(phonesearch, phoneIndex);
         // ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
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
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("�ؽ�Ʈ����", "*.txt"));
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
         // fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/memberModify.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // ���̾�α� (��â) ���������� �����.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("��ǰ���� ����");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // ���� Controller�� �����Ѵ�
         MemberModifyController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // �Ű������� ���� �������ڷ�� �ε��� ��ȣ�� controller�� setProduct �޼ҵ�� �Ѱ��ش�
         controller.setMember(selectMember, select);

         // ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
         dialogStage.showAndWait();

         // ������ư�� ������ �ٽ� �������� ���ΰ�ħ �Ѵ�
         try {
            Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
            Scene scene2= new Scene(member);
            Stage primaryStage = (Stage)itemBtn.getScene().getWindow(); // ���� ������ ��������
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
      //���̺��� �����ϴ� �����ʰ� �ʿ�.
      int selectIndex = tableview.getSelectionModel().getSelectedIndex();
      
      Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("ȸ�� ����");
		alert.setHeaderText("");
		alert.setContentText("�����Ǿ����ϴ�");
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

   // �޴�����ȣ ���ڸ� �Է¹ް�
   public boolean NumCheck(String str) {
      char c;
      for (int i=0; i<str.length(); i++) {
         c = str.charAt(i);
         if (c<48 || c>57) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("�޴��� ��ȣ ����");
            alert.setHeaderText("");
            alert.setContentText("���ڸ� �Է����ּ���");
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
         alert.setTitle("ȸ���̸� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("ȸ���̸��� �Է��ϼ���");
         alert.showAndWait();
      } else if (age.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("���� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("���̸� �Է��ϼ���");
         alert.showAndWait();
      } else if (phone.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("�޴��� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("�޴�����ȣ�� �Է��ϼ���");
         alert.showAndWait();
      } else if (email.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("�̸��� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("�̸��Ϲ�ȣ �Է��ϼ���");
         alert.showAndWait();
      } else if (address.getText().equals("")){
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("�ּ� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("�ּҸ� �Է��ϼ���");
         alert.showAndWait();
      } else {

         // ȸ����ȣ �ڵ� ����
         String num ="";
         if(list.size() != 0){
            num = ((Integer.parseInt(list.get(list.size()-1).getmNum())+1)+"");
         }else{
            num = 1+"";
         }

         // ���Գ�¥ ����
         Calendar calendar = Calendar.getInstance();
         java.util.Date date = calendar.getTime();
         String now = (new SimpleDateFormat("yy-MM-dd").format(date));

         // ����Ʈ ����
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

         // ��ǰ ��� �Ϸ� �޽���
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.initOwner(dialogStage);
         alert.setTitle("ȸ����� �Ϸ�");
         alert.setHeaderText("");
         alert.setContentText(id.getText() + "�� ȸ����� �Ϸ�");
         alert.showAndWait();


         System.out.println(id.getText() + " �� ȸ����� �Ϸ�!");
         try {

            Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
            Scene scene = new Scene(member);
            Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);


         } catch (Exception e) {
            e.printStackTrace();
         }

      }

   }

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

   private void payBtn(ActionEvent event) {
      System.out.println("������� �޴��� �̵�");

      try {

         Parent pay = FXMLLoader.load(getClass().getResource("/com/paris/view/cal.fxml"));
         Scene scene = new Scene(pay);
         Stage primaryStage = (Stage)memberBtn.getScene().getWindow(); // ���� ������ ��������
         primaryStage.setScene(scene);


      } catch (Exception e) {
         e.printStackTrace();
      }
   }


}