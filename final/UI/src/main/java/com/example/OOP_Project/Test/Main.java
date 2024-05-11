package com.example.OOP_Project.Test;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo một VBox
        VBox vBox = new VBox();

        // Thêm một số phần tử vào VBox
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        vBox.getChildren().addAll(button1, button2);

        // Tạo một nút để xóa các phần tử trong VBox
        Button clearButton = new Button("Clear VBox");
        clearButton.setOnAction(event -> {
            // Xóa tất cả các phần tử trong VBox
            vBox.getChildren().clear();
        });

        // Thêm nút xóa vào VBox
        vBox.getChildren().add(clearButton);

        // Tạo một Scene với VBox làm nội dung
        Scene scene = new Scene(vBox, 300, 200);

        // Đặt scene cho Stage và hiển thị nó
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clear VBox Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
