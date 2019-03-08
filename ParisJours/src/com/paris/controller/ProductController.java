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

   // 메뉴버튼 고정
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
   
   // 상품설명 수정 (2018.11.15)
   @FXML Button contentModify;

   // 상품등록 버튼
   @FXML
   private Button writeBtn;

   // 재고관리 테이블뷰
   @FXML
   private TableView<Product> tableview;

   // 재고관리 테이블뷰 컬럼
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

   // 상품등록
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

   // 상품명 검색 TextField & 검색 버튼
   @FXML
   private TextField search;
   @FXML
   private Button searchBtn;
   @FXML
   private TextField searchName;

   // 좌측 상품 이미지 및 제품설명
   @FXML
   private ImageView itemImage;
   @FXML
   private TextArea itemContent;

   // 상품리스트 수정 & 삭제 버튼
   @FXML
   private Button deleteBtn;
   @FXML
   private Button modifyBtn;

   // 상품리스트 파일로 저장 & 불러오기 버튼
   @FXML
   private Button fileLoadBtn;
   @FXML
   private Button fileSaveBtn;

   // 최근게시물 ( 공지사항 )
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

   // 제품을 담을 리스트 ArrayList와 ObservableList
   static ArrayList<Product> list = new ArrayList<Product>();
   ObservableList<Product> myList;

   // 상품등록 검색 Observable List(18.11.13)
   static ArrayList<Product> sList = new ArrayList<Product>();
   static ObservableList<Product> searchList;

   // 상품등록 카테고리 ChoiceBox 내용
   ObservableList<String> options = FXCollections.observableArrayList("종류", "간식용 빵", "식사용 빵/식빵", "도넛", "페스츄리/파이",
         "조리빵", "케이크", "샌드위치", "디저트", "음료", "브런치", "기타");

   // 파일선택
   private Stage stage;
   private Desktop desktop = Desktop.getDesktop();
   FileChooser fileChooser = new FileChooser();
   File file;
   private Stage dialogStage;

   // 제품이미지 경로를 담을 곳
   String fileImage = "";

   // 제품설명 (txt파일) 경로를 담을 곳
   String fileContent = "";

   public String sProductName;
   public int nameIndex;

   // 컨트롤러 기본 생성자
   public ProductController() {
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // 메뉴 이벤트 (모든페이지 고정)
      noticeBtn.setOnAction(e -> noticeBtn(e));
      memberBtn.setOnAction(e -> memberBtn(e));
      itemBtn.setOnAction(e -> itemBtn(e));
      payBtn.setOnAction(e -> payBtn(e));
      
      // 상품 설명 수정
      contentModify.setOnAction(e->contentModify(e));

      // 제품 수정버튼
      modifyBtn.setOnAction(e -> modifyBtn(e));
      // 제품 삭제버튼
      deleteBtn.setOnAction(e -> deleteBtn(e));
      // 제품 검색버튼
      searchBtn.setOnAction(e -> searchBtn(e));

      // 좌측 최근 공지사항 불러오기
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

      // 최근 공지사항 제목을 라벨에 출력
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
         System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_2.setOnMouseClicked(event -> {
         System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_3.setOnMouseClicked(event -> {
         System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_4.setOnMouseClicked(event -> {
         System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      notice_5.setOnMouseClicked(event -> {
         System.out.println("최근게시물 클릭 -> 공지사항 메뉴로 이동");
         try {
            Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
            Scene scene = new Scene(notice);
            Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      });

      // 메인으로 버튼
      main.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            System.out.println("메인페이지로 이동");
            try {
               Parent main = FXMLLoader.load(getClass().getResource("/com/paris/view/main.fxml"));
               Scene scene = new Scene(main);
               Stage primaryStage = (Stage) memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
               primaryStage.setScene(scene);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });

      // 상품이미지 및 상품정보 파일 선택
      imageBtn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(final ActionEvent e) {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            // 파일선택 이미지파일만 가능하게
            fileChooser.getExtensionFilters()
                  .addAll(new FileChooser.ExtensionFilter("빵 이미지 파일", "*.jpg", "*.png", "*.gif"));
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
            // 파일선택 텍스트 파일만 가능하게
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("빵 설명 파일", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
               fileContent = file.getName();
               contentPath.setMaxSize(100, 26);
               contentPath.setText(file.getName());
            }
         }
      });

      // 상품등록 버튼 이벤트
      writeBtn.setOnAction(e -> writeBtn(e));

      // 상품리스트 파일 저장 및 불러오기
      fileLoadBtn.setOnAction(e -> fileLoadBtn(e));
      fileSaveBtn.setOnAction(e -> fileSaveBtn(e));

      // 상품카테고리 ChoiceBox
      cate.setValue("종류");
      cate.setItems(options);

      // 상품 관리 메뉴 접근 시 자동으로 저장된 파일에서 데이터 불러오기
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

      // Observable ArrayList 에 상품정보 Product생성자(Property 값을 받는)
      // 이용해 ArrayList 사이트만큼 리스트에 담기
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
       * // 좌측에 해당 상품 정보 출력 itemContent.clear(); StringTokenizer st2 = null; try
       * (BufferedReader br = new BufferedReader( new InputStreamReader(new
       * FileInputStream(bPath + "/" + list.get(itemNum).getpContent())))) { String
       * textFile = null; while ((textFile = br.readLine()) != null) { st2 = new
       * StringTokenizer(textFile, "\n"); itemContent.appendText(st2.nextToken() +
       * "\n"); } } catch (IOException e) { e.printStackTrace(); }
       */

      // 테이블뷰에 마우스 클릭 이벤트리스너 장착!!!
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
            // 좌측에 해당 상품 정보 출력
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

      // 검색창 반응형으로 적용(18.11.13)
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

   // 상품설명 수정 (2018.11.15)
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
		         alert.setTitle("상품설명 수정완료");
		         alert.setHeaderText("");
		         alert.setContentText("해당 상품의 내용이 수정되었습니다!");
		         alert.showAndWait();
			   
   }
   
   // 검색 버튼 액션
   private void searchBtn(ActionEvent event) {
	   int select = tableview.getSelectionModel().getSelectedIndex();
            Product namesearch = sList.get(select);
            searchProductPop(namesearch, select);
   }

   // search 검색기능
   public void searchProductPop(Product namesearch, int nameIndex) {
      try {
         // fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/productSearch.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // 다이얼로그 (새창) 스테이지를 만든다.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("상품 검색 결과");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // 검색 Controller를 설정한다
         ProductSearchController controller = loader.getController();
         controller.setDialogStage(dialogStage);
         // 매개값으로 받은 아이템자료와 인덱스 번호를 controller에 setProduct 메소드로 넘겨준다
         controller.searchProduct(namesearch, nameIndex);

         // 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
         dialogStage.showAndWait();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // 테이블뷰 리스트 아이템 삭제
   private void deleteBtn(ActionEvent event) {
      // 테이블을 감시하는 리스너가 필요
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

   // 파일 저장 버튼 클릭 시 -> 파일 저장
   private void fileSaveBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("텍스트파일", "*.txt"));
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

   // 파일 불러오기 버튼 클릭 시 -> 파일 열기 -> Observable List 에 담아
   private void fileLoadBtn(ActionEvent event) {
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("텍스트파일", "*.txt"));
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

   // 공지사항 메뉴버튼 클릭 시 -> 공지사항 메뉴로 이동
   private void noticeBtn(ActionEvent event) {
      System.out.println("공지사항 메뉴로 이동");
      try {
         Parent notice = FXMLLoader.load(getClass().getResource("/com/paris/view/notice.fxml"));
         Scene scene = new Scene(notice);
         Stage primaryStage = (Stage) noticeBtn.getScene().getWindow(); // 현재 윈도우 가져오기
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 수정버튼을 누르고, 선택된 아이템자료와, 인덱스번호를 매개값으로 받는다
   public void editProduct(Product selectProduct, int select) {
      try {
         // fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/com/paris/view/ProductModify.fxml"));
         AnchorPane page = (AnchorPane) loader.load();

         // 다이얼로그 (새창) 스테이지를 만든다.
         Stage dialogStage = new Stage();
         dialogStage.setTitle("상품정보 수정");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         dialogStage.initOwner(primaryStage);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);

         // 수정 Controller를 설정한다
         ProductModifyController controller = loader.getController();
         controller.setDialogStage(dialogStage);

         // 매개값으로 받은 아이템자료와 인덱스 번호를 controller에 setProduct 메소드로 넘겨준다
         controller.setProduct(selectProduct, select);

         // 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
         dialogStage.showAndWait();

         // 수정버튼이 눌리면 다시 재고관리를 새로고침 한다
         try {
            Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
            Scene scene2 = new Scene(product);
            Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene2);
         } catch (Exception e) {
            e.printStackTrace();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // 상품수정 버튼 액션
   private void modifyBtn(ActionEvent event) {
      int select = tableview.getSelectionModel().getSelectedIndex();
      Product selectProduct = sList.get(select);
      editProduct(selectProduct, select);
   }

   // 상품등록 버튼 액션
   private void writeBtn(ActionEvent event) {

      // list.add(new Product(num, cate.getValue(), item.getText(), fileImage,
      // date.getValue().toString(),price.getText(), fileContent, amount.getText()));

      if (cate.getValue().equals("종류")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품분류 미선택");
         alert.setHeaderText("");
         alert.setContentText("상품분류를 선택하세요");
         alert.showAndWait();
      } else if (item.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품명 미입력");
         alert.setHeaderText("");
         alert.setContentText("상품명을 입력하세요");
         alert.showAndWait();
      } else if (fileImage == null) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품이미지 미등록");
         alert.setHeaderText("");
         alert.setContentText("상품이미지를 업로드 해주세요");
         alert.showAndWait();
      } else if (date.getValue() == null) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("생산날짜 미선택");
         alert.setHeaderText("");
         alert.setContentText("등록상품의 생산날짜를 지정해주세요");
         alert.showAndWait();
      } else if (price.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품가격 미입력");
         alert.setHeaderText("");
         alert.setContentText("등록할 상품의 가격을 입력해주세요");
         alert.showAndWait();
      } else if (fileContent.toString().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품설명 미등록");
         alert.setHeaderText("");
         alert.setContentText("등록할 상품의 설명파일을 업로드 해주세요");
         alert.showAndWait();
      } else if (amount.getText().equals("")) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.initOwner(dialogStage);
         alert.setTitle("상품수량 미입력");
         alert.setHeaderText("");
         alert.setContentText("등록할 상품의 수량을 입력해주세요");
         alert.showAndWait();
      } else {
         // 상품번호 자동 생성
         String num = "";
         if (list.size() != 0) {
            num = ((Integer.parseInt(list.get(list.size() - 1).getpNum()) + 1) + "");
         } else {
            num = 1001 + "";
         }

         // 생산일 (Date 포맷)
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

         // ArrayList에 담은 후 -> FileWriter로 파일로 저장
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

         // 상품 등록 완료 메시지
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.initOwner(dialogStage);
         alert.setTitle("상품등록 완료");
         alert.setHeaderText("");
         alert.setContentText(item.getText() + "상품이 등록 되었습니다.");
         alert.showAndWait();

         System.out.println("[ " + item.getText() + " ] 상품등록 완료");

         // 등록 후 재고관리 화면을 다시 리로드 해주기 (새로고침)
         try {
            Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
            Scene scene = new Scene(product);
            Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
            primaryStage.setScene(scene);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   // 회원관리 메뉴로 이동
   private void memberBtn(ActionEvent event) {
      System.out.println("회원관리 메뉴로 이동");
      try {
         Parent member = FXMLLoader.load(getClass().getResource("/com/paris/view/member.fxml"));
         Scene scene = new Scene(member);
         Stage primaryStage = (Stage) memberBtn.getScene().getWindow(); // 현재 윈도우 가져오기
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 재고관리 메뉴로 이동
   private void itemBtn(ActionEvent event) {
      System.out.println("재고관리 메뉴로 이동");
      try {
         Parent product = FXMLLoader.load(getClass().getResource("/com/paris/view/product.fxml"));
         Scene scene = new Scene(product);
         Stage primaryStage = (Stage) itemBtn.getScene().getWindow(); // 현재 윈도우 가져오기
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 정산관리 메뉴로 이동
   private void payBtn(ActionEvent event) {
      System.out.println("정산관리 메뉴로 이동");
      try {
         Parent pay = FXMLLoader.load(getClass().getResource("/com/paris/view/cal.fxml"));
         Scene scene = new Scene(pay);
         Stage primaryStage = (Stage) payBtn.getScene().getWindow(); // 현재 윈도우 가져오기
         primaryStage.setScene(scene);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}