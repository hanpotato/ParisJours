package com.paris.controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.paris.model.vo.Member;
import com.paris.model.vo.Notice;
import com.paris.model.vo.Product;

import Product.TextPath;
import Product.images.path;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProductController implements Initializable {

   private Stage primaryStage;

   // �޴���ư ����
   @FXML
   private Button noticeBtn;
   @FXML
   private Button memberBtn;
   @FXML
   private Button itemBtn;
   @FXML
   private Button payBtn;

   @FXML
   private Button main;
   
   // ��ǰ���� ���� (2018.11.15)
   @FXML Button contentModify;

   // ��ǰ��� ��ư
   @FXML
   private Button writeBtn;

   // ������ ���̺��
   @FXML
   private TableView<Product> tableview;

   // ������ ���̺�� �÷�
   @FXML
   private TableColumn<Product, String> pNum;
   @FXML
   private TableColumn<Product, String> pCate;
   @FXML
   private TableColumn<Product, String> pName;
   @FXML
   private TableColumn<Product, String> pPrice;
   @FXML
   private TableColumn<Product, String> pDate;
   @FXML
   private TableColumn<Product, String> pAmount;

   // ��ǰ���
   @FXML
   private ChoiceBox<String> cate;
   @FXML
   private TextField item;
   @FXML
   private TextField price;
   @FXML
   private Button imageBtn;
   @FXML
   private Button contentBtn;
   @FXML
   private DatePicker date;
   @FXML
   private TextField amount;
   @FXML
   private Label imagePath;
   @FXML
   private Label contentPath;

   // ��ǰ�� �˻� TextField & �˻� ��ư
   @FXML
   private TextField search;
   @FXML
   private Button searchBtn;
   @FXML
   private TextField searchName;

   // ���� ��ǰ �̹��� �� ��ǰ����
   @FXML
   private ImageView itemImage;
   @FXML
   private TextArea itemContent;

   // ��ǰ����Ʈ ���� & ���� ��ư
   @FXML
   private Button deleteBtn;
   @FXML
   private Button modifyBtn;

   // ��ǰ����Ʈ ���Ϸ� ���� & �ҷ����� ��ư
   @FXML
   private Button fileLoadBtn;
   @FXML
   private Button fileSaveBtn;

   // �ֱٰԽù� ( �������� )
   @FXML
   private Label notice_1;
   @FXML
   private Label notice_2;
   @FXML
   private Label notice_3;
   @FXML
   private Label notice_4;
   @FXML
   private Label notice_5;
   @FXML
   private Label date_1;
   @FXML
   private Label date_2;
   @FXML
   private Label date_3;
   @FXML
   private Label date_4;
   @FXML
   private Label date_5;
   static ArrayList<Notice> notice = new ArrayList<Notice>();

   // ��ǰ�� ���� ����Ʈ ArrayList�� ObservableList
   static ArrayList<Product> list = new ArrayList<Product>();
   ObservableList<Product> myList;

   // ��ǰ��� �˻� Observable List(18.11.13)
   static ArrayList<Product> sList = new ArrayList<Product>();
   static ObservableList<Product> searchList;

   // ��ǰ��� ī�װ� ChoiceBox ����
   ObservableList<String> options = FXCollections.observableArrayList("����", "���Ŀ� ��", "�Ļ�� ��/�Ļ�", "����", "�佺��/����",
         "������", "����ũ", "������ġ", "����Ʈ", "����", "�귱ġ", "��Ÿ");

   // ���ϼ���
   private Stage stage;
   private Desktop desktop = Desktop.getDesktop();
   FileChooser fileChooser = new FileChooser();
   File file;
   private Stage dialogStage;

   // ��ǰ�̹��� ��θ� ���� ��
   String fileImage = "";

   // ��ǰ���� (txt����) ��θ� ���� ��
   String fileContent = "";

   public String sProductName;
   public int nameIndex;

   // ��Ʈ�ѷ� �⺻ ������
   public ProductController() {
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // �޴� �̺�Ʈ (��������� ����)
      noticeBtn.setOnAction(e -> noticeBtn(e));
      memberBtn.setOnAction(e -> memberBtn(e));
      itemBtn.setOnAction(e -> itemBtn(e));
      payBtn.setOnAction(e -> payBtn(e));
      
      // ��ǰ ���� ����
      contentModify.setOnAction(e->contentModify(e));

      // ��ǰ ������ư
      modifyBtn.setOnAction(e -> modifyBtn(e));
      // ��ǰ ������ư
      deleteBtn.setOnAction(e -> deleteBtn(e));
      // ��ǰ �˻���ư
      searchBtn.setOnAction(e -> searchBtn(e));

      // ���� �ֱ� �������� �ҷ�����
      StringTokenizer st5 = null;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Notice/notice.txt")))) {
         notice.clear();
         String str = null;
         while ((str = br.readLine()) != null) {
            Notice n = new Notice();
            st5 = new StringTokenizer(str, ",");
            n.setNo(st5.nextToken());
            n.setCate(st5.nextToken());
            n.setTitle(st5.nextToken());
            n.setContent(st5.nextToken());
            n.setDate(st5.nextToken());
            notice.add(n);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      // �ֱ� �������� ������ �󺧿� ���
      try {
         notice_1.setText(notice.get(notice.size() - 1).getTitle());
         date_1.setText(notice.get(notice.size() - 1).getDate().substring(3, 9));

         notice_2.setText(notice.get(notice.size() - 2).getTitle());
         date_2.setText(notice.get(notice.size() - 2).getDate().substring(3, 9));

         notice_3.setText(notice.get(notice.size() - 3).getTitle());
         date_3.setText(notice.get(notice.size() - 3).getDate().substring(3, 9));

         notice_4.setText(notice.get(notice.size() - 4).getTitle());
         date_4.setText(notice.get(notice.size() - 4).getDate().substring(3, 9));

         notice_5.setText(notice.get(notice.size() - 5).getTitle());
         date_5.setText(notice.get(notice.size() - 5).getDate().substring(3, 9));
      } catch (Exception e) {
      }

      notice_1.setOnMouseClicked(event -> {
         System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_2.setOnMouseClicked(event -> {
         System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_3.setOnMouseClicked(event -> {
         System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_4.setOnMouseClicked(event -> {
         System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_5.setOnMouseClicked(event -> {
         System.out.println("�ֱٰԽù� Ŭ�� -> �������� �޴��� �̵�");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });

      // �������� ��ư
      main.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            System.out.println("������������ �̵�");
            try {
               Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
               Scene scene = new Scene(main);
               Stage primaryStage = (Stage) memberBtn.getScene().getWindow(); // ���� ������ ��������
               primaryStage.setScene(scene);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });

      // ��ǰ�̹��� �� ��ǰ���� ���� ����
      imageBtn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(final ActionEvent e) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            // ���ϼ��� �̹������ϸ� �����ϰ�
            fileChooser.getExtensionFilters()
                  .addAll(new FileChooser.ExtensionFilter("�� �̹��� ����", "*.jpg", "*.png", "*.gif"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
               fileImage = file.getName();
               imagePath.setMaxSize(100, 26);
               imagePath.setText(file.getName());
            }
         }
      });

      contentBtn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(final ActionEvent e) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            // ���ϼ��� �ؽ�Ʈ ���ϸ� �����ϰ�
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("�� ���� ����", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
               fileContent = file.getName();
               contentPath.setMaxSize(100, 26);
               contentPath.setText(file.getName());
            }
         }
      });

      // ��ǰ��� ��ư �̺�Ʈ
      writeBtn.setOnAction(e -> writeBtn(e));

      // ��ǰ����Ʈ ���� ���� �� �ҷ�����
      fileLoadBtn.setOnAction(e -> fileLoadBtn(e));
      fileSaveBtn.setOnAction(e -> fileSaveBtn(e));

      // ��ǰī�װ� ChoiceBox
      cate.setValue("����");
      cate.setItems(options);

      // ��ǰ ���� �޴� ���� �� �ڵ����� ����� ���Ͽ��� ������ �ҷ�����
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream("Product/product.txt")))) {
         list.clear();
         String str = null;
         while ((str = br.readLine()) != null) {
            Product p = new Product();
            st = new StringTokenizer(str, ",");
            p.setpNum(st.nextToken());
            p.setpCate(st.nextToken());
            p.setpName(st.nextToken());
            p.setpImage(st.nextToken());
            p.setpDate(st.nextToken());
            p.setpPrice(st.nextToken());
            p.setpContent(st.nextToken());
            p.setpAmount(st.nextToken());
            list.add(p);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      // Observable ArrayList �� ��ǰ���� Product������(Property ���� �޴�)
      // �̿��� ArrayList ����Ʈ��ŭ ����Ʈ�� ���
      tableview.getItems().clear();
      sList.clear();
      searchList = FXCollections.observableArrayList();

      for(int i=0;i<list.size();i++) {
          if (list.get(i).getpName().contains(searchName.getText())) {
         	 sList.add(new Product(list.get(i).getpNum(),
                  (list.get(i).getpCate()),
                  (list.get(i).getpName()),
                  (list.get(i).getpImage()),
                  (list.get(i).getpDate()),
                  (list.get(i).getpPrice()),
                  (list.get(i).getpContent()),
                  (list.get(i).getpAmount())));
          }

      }
      for (int i = 0; i < sList.size(); i++) {
            searchList.add(new Product(new SimpleStringProperty(sList.get(i).getpNum()),
                  (new SimpleStringProperty(sList.get(i).getpCate())),
                  (new SimpleStringProperty(sList.get(i).getpName())),
                  (new SimpleStringProperty(sList.get(i).getpImage())),
                  (new SimpleStringProperty(sList.get(i).getpDate())),
                  (new SimpleStringProperty(sList.get(i).getpPrice())),
                  (new SimpleStringProperty(sList.get(i).getpContent())),
                  (new SimpleStringProperty(sList.get(i).getpAmount()))));

         pNum.setCellValueFactory(cellData -> cellData.getValue().getpNum2());
         pCate.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
         pName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
         pDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
         pPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
         pAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
         tableview.setItems(searchList);
      }
      /*
       * // ������ �ش� ��ǰ ���� ��� itemContent.clear(); StringTokenizer st2 = null; try
       * (BufferedReader br = new BufferedReader( new InputStreamReader(new
       * FileInputStream(bPath + "/" + list.get(itemNum).getpContent())))) { String
       * textFile = null; while ((textFile = br.readLine()) != null) { st2 = new
       * StringTokenizer(textFile, "\n"); itemContent.appendText(st2.nextToken() +
       * "\n"); } } catch (IOException e) { e.printStackTrace(); }
       */

      // ���̺�信 ���콺 Ŭ�� �̺�Ʈ������ ����!!!
      tableview.setOnMouseClicked(event -> {
         if (tableview.getSelectionModel().getSelectedItem() != null) {
            int itemNum = tableview.getSelectionModel().getFocusedIndex();

            String aPath = path.class.getResource("").getPath();
            String bPath = TextPath.class.getResource("").getPath();

            try (FileInputStream input = new FileInputStream(aPath + "/" + sList.get(itemNum).getpImage())) {
               Image image = new Image(input);
               itemImage.setImage(image);
            } catch (Exception e) {
               e.printStackTrace();
            }
/*
            for (int i = 0; i < list.size(); i++) {
               if (searchName.getText().equals(list.get(i).getpName())) {
                  try (FileInputStream input = new FileInputStream(aPath + "/" + sList.get(i).getpImage())) {
                     Image image = new Image(input);
                     itemImage.setImage(image);
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
                  
               }
            }
*/
            // ������ �ش� ��ǰ ���� ���
            itemContent.clear();
            StringTokenizer st2 = null;

            try (BufferedReader br = new BufferedReader(
                  new InputStreamReader(new FileInputStream(bPath + "/" + sList.get(itemNum).getpContent())))) {
               String textFile = null;
               while ((textFile = br.readLine()) != null) {
                  st2 = new StringTokenizer(textFile, "\n");
                  itemContent.appendText(st2.nextToken() + "\n");
               }
            } catch (IOException e) {
               e.printStackTrace();
            }
/*      
            for (int i = 0; i < list.size(); i++) {

               if (searchName.getText().equals(list.get(i).getpName())) {
                  itemContent.clear();
                  try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(bPath + "/" + list.get(i).getpContent())))) {
                     String textFile = null;
                     while ((textFile = br.readLine()) != null) {
                        st2 = new StringTokenizer(textFile, "\n");
                        itemContent.appendText(st2.nextToken() + "\n");
                     }
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               }
            }
            */
         }
      });

      // �˻�â ���������� ����(18.11.13)
      searchName.setOnKeyReleased(ke -> {
         searchBtn.setOnAction(e -> searchBtn(e));
         tableview.getItems().clear();
         sList.clear();
         searchList = FXCollections.observableArrayList();

         for(int i=0;i<list.size();i++) {
             if (list.get(i).getpName().contains(searchName.getText())) {
            	 sList.add(new Product(list.get(i).getpNum(),
                     (list.get(i).getpCate()),
                     (list.get(i).getpName()),
                     (list.get(i).getpImage()),
                     (list.get(i).getpDate()),
                     (list.get(i).getpPrice()),
                     (list.get(i).getpContent()),
                     (list.get(i).getpAmount())));
             }

         }
         for (int i = 0; i < sList.size(); i++) {
               searchList.add(new Product(new SimpleStringProperty(sList.get(i).getpNum()),
                     (new SimpleStringProperty(sList.get(i).getpCate())),
                     (new SimpleStringProperty(sList.get(i).getpName())),
                     (new SimpleStringProperty(sList.get(i).getpImage())),
                     (new SimpleStringProperty(sList.get(i).getpDate())),
                     (new SimpleStringProperty(sList.get(i).getpPrice())),
                     (new SimpleStringProperty(sList.get(i).getpContent())),
                     (new SimpleStringProperty(sList.get(i).getpAmount()))));

            pNum.setCellValueFactory(cellData -> cellData.getValue().getpNum2());
            pCate.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
            pName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
            pDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
            pPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
            pAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
            tableview.setItems(searchList);
         }
         KeyCode keyCode = ke.getCode();
         if (keyCode.equals(KeyCode.ENTER) && keyCode.equals(KeyCode.BACK_SPACE)) {
             tableview.getItems().clear();
             sList.clear();
             searchList = FXCollections.observableArrayList();

             for(int i=0;i<list.size();i++) {
                 if (list.get(i).getpName().contains(searchName.getText())) {
                	 sList.add(new Product(list.get(i).getpNum(),
                         (list.get(i).getpCate()),
                         (list.get(i).getpName()),
                         (list.get(i).getpImage()),
                         (list.get(i).getpDate()),
                         (list.get(i).getpPrice()),
                         (list.get(i).getpContent()),
                         (list.get(i).getpAmount())));
                 }

             }
             for (int i = 0; i < sList.size(); i++) {
                   searchList.add(new Product(new SimpleStringProperty(sList.get(i).getpNum()),
                         (new SimpleStringProperty(sList.get(i).getpCate())),
                         (new SimpleStringProperty(sList.get(i).getpName())),
                         (new SimpleStringProperty(sList.get(i).getpImage())),
                         (new SimpleStringProperty(sList.get(i).getpDate())),
                         (new SimpleStringProperty(sList.get(i).getpPrice())),
                         (new SimpleStringProperty(sList.get(i).getpContent())),
                         (new SimpleStringProperty(sList.get(i).getpAmount()))));

                pNum.setCellValueFactory(cellData -> cellData.getValue().getpNum2());
                pCate.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
                pName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
                pDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
                pPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
                pAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
                tableview.setItems(searchList);
             }
         }
      });

   }

   // ��ǰ���� ���� (2018.11.15)
   private void contentModify(ActionEvent event) {
	   String bPath = TextPath.class.getResource("").getPath();
	   int select = tableview.getSelectionModel().getSelectedIndex();
			   try (FileWriter fw = new FileWriter(bPath + "/" +sList.get(select).getpContent())) {
			            fw.write(itemContent.getText());
			         }catch (IOException e) {
			             e.printStackTrace();
			         }
			   Alert alert = new Alert(AlertType.INFORMATION);
		         alert.initOwner(dialogStage);
		         alert.setTitle("��ǰ���� �����Ϸ�");
		         alert.setHeaderText("");
		         alert.setContentText("�ش� ��ǰ�� ������ �����Ǿ����ϴ�!");
		         alert.showAndWait();
			   
   }
   
   // �˻� ��ư �׼�
   private void searchBtn(ActionEvent event) {
	   int select = tableview.getSelectionModel().getSelectedIndex();
            Product namesearch = sList.get(select);
            searchProductPop(namesearch, select);
   }

   // search �˻����
   public void searchProductPop(Product namesearch, int nameIndex) {
      try {
         // fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/productSearch.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // ���̾�α� (��â) ���������� �����.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("��ǰ �˻� ���");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // �˻� Controller�� �����Ѵ�
         ProductSearchController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         // �Ű������� ���� �������ڷ�� �ε��� ��ȣ�� controller�� setProduct �޼ҵ�� �Ѱ��ش�
         controller.searchProduct(namesearch, nameIndex);

         // ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
         dialogStage.showAndWait();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // ���̺�� ����Ʈ ������ ����
   private void deleteBtn(ActionEvent event) {
      // ���̺��� �����ϴ� �����ʰ� �ʿ�
      int selectIndex = tableview.getSelectionModel().getSelectedIndex();

      for(int i=0;i<list.size();i++) {
    	  if(list.get(i).getpName().equals(sList.get(selectIndex).getpName())) {
    		  list.remove(i);
    	  }
      }
      tableview.getItems().remove(selectIndex);
      sList.remove(selectIndex);
      try (FileWriter fw = new FileWriter("Product/product.txt")) {
         for (int i = 0; i < list.size(); i++) {
            fw.write(list.get(i).getpNum());
            fw.write(",");
            fw.write(list.get(i).getpCate());
            fw.write(",");
            fw.write(list.get(i).getpName());
            fw.write(",");
            fw.write(list.get(i).getpImage());
            fw.write(",");
            fw.write(list.get(i).getpDate().toString());
            fw.write(",");
            fw.write(list.get(i).getpPrice());
            fw.write(",");
            fw.write(list.get(i).getpContent());
            fw.write(",");
            fw.write(list.get(i).getpAmount());
            fw.write("\r\n");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // ���� ���� ��ư Ŭ�� �� -> ���� ����
   private void fileSaveBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("�ؽ�Ʈ����", "*.txt"));
      File file = fileChooser.showSaveDialog(stage);
      try (FileWriter fw = new FileWriter(file.getAbsolutePath())) {
         for (int i = 0; i < list.size(); i++) {
            fw.write(list.get(i).getpNum());
            fw.write(",");
            fw.write(list.get(i).getpCate());
            fw.write(",");
            fw.write(list.get(i).getpName());
            fw.write(",");
            fw.write(list.get(i).getpImage());
            fw.write(",");
            fw.write(list.get(i).getpDate().toString());
            fw.write(",");
            fw.write(list.get(i).getpPrice());
            fw.write(",");
            fw.write(list.get(i).getpContent());
            fw.write(",");
            fw.write(list.get(i).getpAmount());
            fw.write("\r\n");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // ���� �ҷ����� ��ư Ŭ�� �� -> ���� ���� -> Observable List �� ���
   private void fileLoadBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("�ؽ�Ʈ����", "*.txt"));
      File file = fileChooser.showOpenDialog(stage);
      StringTokenizer st = null;
      try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream(file.getAbsolutePath())))) {
         list.clear();
         String str = null;
         while ((str = br.readLine()) != null) {
            Product p = new Product();
            st = new StringTokenizer(str, ",");
            p.setpNum(st.nextToken());
            p.setpCate(st.nextToken());
            p.setpName(st.nextToken());
            p.setpImage(st.nextToken());
            p.setpDate(st.nextToken());
            p.setpPrice(st.nextToken());
            p.setpContent(st.nextToken());
            p.setpAmount(st.nextToken());
            list.add(p);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      tableview.getItems().clear();
      sList.clear();
      searchList = FXCollections.observableArrayList();

      for(int i=0;i<list.size();i++) {
          if (list.get(i).getpName().contains(searchName.getText())) {
         	 sList.add(new Product(list.get(i).getpNum(),
                  (list.get(i).getpCate()),
                  (list.get(i).getpName()),
                  (list.get(i).getpImage()),
                  (list.get(i).getpDate()),
                  (list.get(i).getpPrice()),
                  (list.get(i).getpContent()),
                  (list.get(i).getpAmount())));
          }

      }
      for (int i = 0; i < sList.size(); i++) {
            searchList.add(new Product(new SimpleStringProperty(sList.get(i).getpNum()),
                  (new SimpleStringProperty(sList.get(i).getpCate())),
                  (new SimpleStringProperty(sList.get(i).getpName())),
                  (new SimpleStringProperty(sList.get(i).getpImage())),
                  (new SimpleStringProperty(sList.get(i).getpDate())),
                  (new SimpleStringProperty(sList.get(i).getpPrice())),
                  (new SimpleStringProperty(sList.get(i).getpContent())),
                  (new SimpleStringProperty(sList.get(i).getpAmount()))));

         pNum.setCellValueFactory(cellData -> cellData.getValue().getpNum2());
         pCate.setCellValueFactory(cellData -> cellData.getValue().getpCate2());
         pName.setCellValueFactory(cellData -> cellData.getValue().getpName2());
         pDate.setCellValueFactory(cellData -> cellData.getValue().getpDate2());
         pPrice.setCellValueFactory(cellData -> cellData.getValue().getpPrice2());
         pAmount.setCellValueFactory(cellData -> cellData.getValue().getpAmount2());
         tableview.setItems(searchList);
      }
   }

   // �������� �޴���ư Ŭ�� �� -> �������� �޴��� �̵�
   private void noticeBtn(ActionEvent event) {
      System.out.println("�������� �޴��� �̵�");
      try {
         Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
         Scene scene = new Scene(notice);
         Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // ���� ������ ��������
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // ������ư�� ������, ���õ� �������ڷ��, �ε�����ȣ�� �Ű������� �޴´�
   public void editProduct(Product selectProduct, int select) {
      try {
         // fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/ProductModify.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // ���̾�α� (��â) ���������� �����.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("��ǰ���� ����");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // ���� Controller�� �����Ѵ�
         ProductModifyController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // �Ű������� ���� �������ڷ�� �ε��� ��ȣ�� controller�� setProduct �޼ҵ�� �Ѱ��ش�
         controller.setProduct(selectProduct, select);

         // ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
         dialogStage.showAndWait();

         // ������ư�� ������ �ٽ� �������� ���ΰ�ħ �Ѵ�
         try {
            Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
            Scene scene2 = new Scene(product);
            Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene2);
         } catch (Exception e) {
            e.printStackTrace();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // ��ǰ���� ��ư �׼�
   private void modifyBtn(ActionEvent event) {
      int select = tableview.getSelectionModel().getSelectedIndex();
      Product selectProduct = sList.get(select);
      editProduct(selectProduct, select);
   }

   // ��ǰ��� ��ư �׼�
   private void writeBtn(ActionEvent event) {

      // list.add(new Product(num, cate.getValue(), item.getText(), fileImage,
      // date.getValue().toString(),price.getText(), fileContent, amount.getText()));

      if (cate.getValue().equals("����")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ�з� �̼���");
         alert.setHeaderText("");
         alert.setContentText("��ǰ�з��� �����ϼ���");
         alert.showAndWait();
      } else if (item.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ�� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("��ǰ���� �Է��ϼ���");
         alert.showAndWait();
      } else if (fileImage == null) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ�̹��� �̵��");
         alert.setHeaderText("");
         alert.setContentText("��ǰ�̹����� ���ε� ���ּ���");
         alert.showAndWait();
      } else if (date.getValue() == null) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("���곯¥ �̼���");
         alert.setHeaderText("");
         alert.setContentText("��ϻ�ǰ�� ���곯¥�� �������ּ���");
         alert.showAndWait();
      } else if (price.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ���� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("����� ��ǰ�� ������ �Է����ּ���");
         alert.showAndWait();
      } else if (fileContent.toString().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ���� �̵��");
         alert.setHeaderText("");
         alert.setContentText("����� ��ǰ�� ���������� ���ε� ���ּ���");
         alert.showAndWait();
      } else if (amount.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ���� ���Է�");
         alert.setHeaderText("");
         alert.setContentText("����� ��ǰ�� ������ �Է����ּ���");
         alert.showAndWait();
      } else {
         // ��ǰ��ȣ �ڵ� ����
         String num = "";
         if (list.size() != 0) {
            num = ((Integer.parseInt(list.get(list.size() - 1).getpNum()) + 1) + "");
         } else {
            num = 1001 + "";
         }

         // ������ (Date ����)
         String pattern = "yy-MM-dd";
         date.setPromptText(pattern.toLowerCase());
         date.setConverter(new StringConverter<LocalDate>() {
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

         // ArrayList�� ���� �� -> FileWriter�� ���Ϸ� ����
         list.add(new Product(num, cate.getValue(), item.getText(), fileImage, date.getValue().toString(),
               price.getText(), fileContent, amount.getText()));

         try (FileWriter fw = new FileWriter("Product/product.txt")) {
            for (int i = 0; i < list.size(); i++) {
               fw.write(list.get(i).getpNum());
               fw.write(",");
               fw.write(list.get(i).getpCate());
               fw.write(",");
               fw.write(list.get(i).getpName());
               fw.write(",");
               fw.write(list.get(i).getpImage());
               fw.write(",");
               fw.write(list.get(i).getpDate().toString());
               fw.write(",");
               fw.write(list.get(i).getpPrice());
               fw.write(",");
               fw.write(list.get(i).getpContent());
               fw.write(",");
               fw.write(list.get(i).getpAmount());
               fw.write("\r\n");
            }

         } catch (Exception e) {
            e.printStackTrace();
         }

         // ��ǰ ��� �Ϸ� �޽���
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.initOwner(dialogStage);
         alert.setTitle("��ǰ��� �Ϸ�");
         alert.setHeaderText("");
         alert.setContentText(item.getText() + "��ǰ�� ��� �Ǿ����ϴ�.");
         alert.showAndWait();

         System.out.println("[ " + item.getText() + " ] ��ǰ��� �Ϸ�");

         // ��� �� ������ ȭ���� �ٽ� ���ε� ���ֱ� (���ΰ�ħ)
         try {
            Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
            Scene scene = new Scene(product);
            Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // ���� ������ ��������
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   // ȸ������ �޴��� �̵�
   private void memberBtn(ActionEvent event) {
      System.out.println("ȸ������ �޴��� �̵�");
      try {
         Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
         Scene scene = new Scene(member);
         Stage primaryStage = (Stage) memberBtn.getScene().getWindow(); // ���� ������ ��������
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
         Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // ���� ������ ��������
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
         Stage primaryStage = (Stage) payBtn.getScene().getWindow(); // ���� ������ ��������
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}