package com.example.OOP_Project.Test;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;

public class pythonTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Send Request to Python");
        btn.setOnAction(event -> {
            try {
                // Kích hoạt tiến trình Python
                Process process = Runtime.getRuntime().exec("python3 /Users/nguyentrithanh/Documents/20232/Object-OrientedProgramming/Blockchain_Backend/test3/SearchEngine_2.py");

                // Gửi dữ liệu đến tiến trình Python
                OutputStream outputStream = process.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write("New York");
                writer.newLine();
                writer.flush();

                // Đọc kết quả từ tiến trình Python
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String result = reader.readLine();
                System.out.println("Result from Python: " + result);

                // Hiển thị kết quả trong JavaFX
                // Ví dụ: thay đổi thành tác vụ hiển thị kết quả trên giao diện người dùng
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("JavaFX Python Communication");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
