package com.example.OOP_Project.Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NumberIncrementer extends Application {

    private int number = 0; // Số ban đầu

    @Override
    public void start(Stage primaryStage) {
        // Tạo một nút để click
        Button button = new Button("Click để tăng số");

        // Xử lý sự kiện khi nút được click
        button.setOnAction(event -> {
            number++; // Tăng giá trị số lên 1 đơn vị
            button.setText("Số hiện tại: " + number); // Cập nhật nội dung của nút
        });

        // Tạo layout và thêm nút vào layout
        StackPane root = new StackPane();
        root.getChildren().add(button);

        // Tạo scene với layout và hiển thị nó trên primaryStage
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Number Incrementer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
