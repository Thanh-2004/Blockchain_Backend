package com.example.OOP_Project.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class articleRun extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Article article = new Article();

        // Thay đổi thông số cho ví dụ
        String title = "Example Title";
        String type = "Example Type";
        String news = "Example News";
        String summary = "Example Summary";
        String link = "path/to/image.jpg";

        AnchorPane anchorPane = article.getAnchorPane(title, type, news, summary, link);

        Scene scene = new Scene(anchorPane, 675, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

