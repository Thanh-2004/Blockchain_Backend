package com.example.OOP_Project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class TodayController {


    private Stage stage;
    private Scene scene;
    private Parent root;

//    String[][] inputs = {
//            {"Title 1", "Type 1", "News 1", "Summary 1", "Category 1", "Tag 1", "Time 1", "http://www.google.com", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 2", "Type 2", "News 2", "Summary 2", "Category 2", "Tag 2", "Time 2", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 3", "Type 3", "News 3", "Summary 3", "Category 3", "Tag 3", "Time 3", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 4", "Type 4", "News 4", "Summary 4", "Category 4", "Tag 4", "Time 4", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 5", "Type 5", "News 5", "Summary 5", "Category 5", "Tag 5", "Time 5", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 6", "Type 6", "News 6", "Summary 6", "Category 6", "Tag 6", "Time 6", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 7", "Type 7", "News 7", "Summary 7", "Category 7", "Tag 7", "Time 7", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 8", "Type 8", "News 8", "Summary 8", "Category 8", "Tag 8", "Time 8", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 9", "Type 9", "News 9", "Summary 9", "Category 9", "Tag 9", "Time 9", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"},
//            {"Title 10", "Type 10", "News 10", "Summary 10", "Category 10", "Tag 10", "Time 10", "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png"}
//    };

    private String[][] inputs = new String[10][12];

//    int[] index = new int[10];

    @FXML private TextField input;


    public String indexReceiver(String keyword) {
        try {
            System.out.println(keyword);
//            Socket socket = new Socket("localhost", 12345);
            Socket socket = new Socket("localhost", 12345);

            // Tạo luồng đầu vào để nhận dữ liệu từ máy chủ
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Tạo luồng đầu ra để gửi dữ liệu tới máy chủ
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Gửi dữ liệu cho máy chủ
            out.println(keyword);

            System.out.println("đang chờ");
            // Nhận phản hồi từ máy chủ
            String response = in.readLine();
            System.out.println("Phản hồi từ máy chủ: " + response);

            System.out.println("đang chờ");

            // Đóng kết nối
            socket.close();

            return (response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Integer> indexConverter(String indexString) {
        String[] indexArray = indexString.split(" ");
        ArrayList<Integer> index = new ArrayList<>();

        for (int i = 0; i < indexArray.length; i++) {
            index.add(Integer.parseInt(indexArray[i]));
        }

        return index;
    }

    public void updateInputs(ArrayList<Integer> index) {
        String csvFile = "/Users/nguyentrithanh/Documents/20232/Object-OrientedProgramming/BlockChain_UI_Son/src/main/java/com/example/OOP_Project/Test/ngu.csv";
        File file = new File(csvFile);
        String line = "";

        ArrayList<Integer> indexList = index;



//        String[][] newsRow = new String[3][12];

        int i  = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while((line = br.readLine()) != null && !line.isEmpty()) {
                String[] fields = line.split(",");
                if (indexList.contains(Integer.parseInt(fields[0]))) {
                    int newsIndex = indexList.indexOf(Integer.parseInt(fields[0]));
                    this.inputs[newsIndex] = Arrays.copyOfRange(fields, 1, fields.length);
                }
            }
            for (int j = 0; j < inputs[1].length; j ++) {
                System.out.println(inputs[1][j]);
                System.out.println(j);
        }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleSearch() {
        String searchText = input.getText();
        String indexString = indexReceiver(searchText);

        ArrayList<Integer> index = indexConverter(indexString);
        updateInputs(index);
        addArticles();
    }



    @FXML
    private DetailController detailController;

    @FXML
    private VBox articleContainer;

    @FXML
    private VBox laterContainer;



    public void initialize() {
        if (articleContainer != null) {
            addArticles();
//            debug_fetch();
        }
        if (laterContainer != null) {
            addreadlater();
        }
    }

    public static ArrayList<Article> articles = new ArrayList<>();
    public void addArticles() {
        articleContainer.getChildren().clear();
        articles.clear();
        for (int i = 0; i < inputs.length; i++) {
            Article article = new Article();
            // need to fix to be appropriated
            AnchorPane art = article.createArticle(inputs[i][3], inputs[i][2], inputs[i][5], inputs[i][4], inputs[i][0]);

            Button moreButton = new Button();
            moreButton.setLayoutX(600);
            moreButton.setLayoutY(15);
            moreButton.setPrefSize(50, 40);
            moreButton.setStyle("-fx-background-color: #020023;");

            URL imageUrl = getClass().getResource("/image/more.png");

            Image aterImage = new Image(imageUrl.toExternalForm());
            ImageView moreImageView = new ImageView(aterImage);

            moreImageView.setFitWidth(41);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(1.0);
            colorAdjust.setContrast(-0.05);
            colorAdjust.setSaturation(0.04);
            moreImageView.setEffect(colorAdjust);

            moreImageView.setFitHeight(29);
            moreButton.setGraphic(moreImageView);

            int finalI = i; // Variable 'i' needs to be effectively final or be effectively used as a final variable
            moreButton.setOnAction(event -> {

                setPane(inputs[finalI][3], inputs[finalI][2], inputs[finalI][5], inputs[finalI][4], inputs[finalI][9], inputs[finalI][7], inputs[finalI][6], inputs[finalI][0],inputs[finalI][8]);
                // need to fix to be appropriated
            });


            art.getChildren().add(moreButton);
            articleContainer.getChildren().add(art);
            articles.add(article);

        }
        System.out.println(articles.size());
    }

    public void addreadlater() {

        int i =0;
        for (Article article : articles) {
            if (article.getPane() == true) {
                // need to fix to be appropriated
                AnchorPane art = article.getAnchorPane(inputs[i][3], inputs[i][2], inputs[i][5], inputs[i][4], inputs[i][0]);

                Button moreButton = new Button();
                moreButton.setLayoutX(593);
                moreButton.setLayoutY(3);
                moreButton.setPrefSize(50, 47);
                moreButton.setStyle("-fx-background-color: #020023;");
                URL imageUrl = getClass().getResource("/image/more.png");

                Image aterImage = new Image(imageUrl.toExternalForm());
                ImageView moreImageView = new ImageView(aterImage);

                moreImageView.setFitWidth(41);
                moreImageView.setFitHeight(29);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(1.0);
                colorAdjust.setContrast(-0.05);
                colorAdjust.setSaturation(0.04);
                moreImageView.setEffect(colorAdjust);

                moreButton.setGraphic(moreImageView);

                int finalI = i; // Variable 'i' needs to be effectively final or be effectively used as a final variable
                moreButton.setOnAction(event -> {
                    // need to fix to be appropriated
                    setPane(inputs[finalI][3], inputs[finalI][2], inputs[finalI][5], inputs[finalI][4], inputs[finalI][9], inputs[finalI][7], inputs[finalI][6], inputs[finalI][0],inputs[finalI][8]);
                });
                art.getChildren().add(moreButton);
                laterContainer.getChildren().add(art);
            }
            i ++;
        }
    }




    public void setPane(String title, String type, String news, String summary, String category, String tag, String time, String facebookLink, String imageLink) {
        detailController.showDetail();
        detailController.setDetail(title, type, news, summary, category, tag, time, facebookLink, imageLink);
    }

}
