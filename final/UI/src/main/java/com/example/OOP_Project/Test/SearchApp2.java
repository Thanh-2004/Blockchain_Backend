package com.example.OOP_Project.Test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;
import java.net.*;

import java.util.List;
import java.util.ArrayList;

public class SearchApp2 extends Application {

    public Socket socket;
    private String ip;
    private int port;
    private DataInputStream din;
    private DataOutputStream dout;
    private String nickname;

//    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    @Override
    public void start(Stage primaryStage) {
        // Tạo TextField cho từ khóa tìm kiếm
        TextField keywordTextField = new TextField();
        keywordTextField.setPromptText("Nhập từ khóa tìm kiếm");

        // Tạo Button để gửi yêu cầu tìm kiếm
        Button searchButton = new Button("Tìm kiếm");

        searchButton.setOnAction(event -> {
            String keyword = keywordTextField.getText().trim();
            if (!keyword.isEmpty()) {
                // Gửi yêu cầu tìm kiếm đến server Python
                sendSearchRequest(keyword);
            }
        });

        // Sắp xếp các thành phần trong một VBox
        VBox root = new VBox(10, keywordTextField, searchButton);
        root.setPadding(new Insets(20));

        // Tạo scene và hiển thị stage
        Scene scene = new Scene(root, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ứng dụng tìm kiếm");
        primaryStage.show();
    }
    private void sendSearchRequest(String keyword) {
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
//            String response;
//            while ((response = in.readLine()) != null) {
//                System.out.println("Dữ liệu nhận được từ máy chủ: " + response);
//            }

            // Đóng kết nối
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void initializeConnection() {
//        try {
//            // Kết nối tới máy chủ
//            socket = new Socket("localhost", 1234);
//
//            // Tạo luồng đầu vào để nhận dữ liệu từ máy chủ
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            // Tạo luồng đầu ra để gửi dữ liệu tới máy chủ
//            out = new PrintWriter(socket.getOutputStream(), true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void sendSearchRequest(String keyword) {
//        try {
//            System.out.println(keyword);
//
//            // Gửi dữ liệu cho máy chủ
//            out.println(keyword);
//
//            System.out.println("đang chờ");
//
//            // Nhận phản hồi từ máy chủ
//            String response = in.readLine();
//            System.out.println("Phản hồi từ máy chủ: " + response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void closeConnection() {
//        try {
//            // Đóng luồng và kết nối
//            in.close();
//            out.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
//        SearchApp2 search = new SearchApp2();
//        search.initializeConnection();
        launch(args);
//        search.closeConnection();

    }
}

