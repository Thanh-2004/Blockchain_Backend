package com.example.OOP_Project.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {





    public static void main (String[] args) {
        String csvFile = "/Users/nguyentrithanh/Documents/20232/Object-OrientedProgramming/BlockChain_UI_Son/src/main/java/com/example/OOP_Project/Test/ngu.csv";
        File file = new File(csvFile);
        String line = "";

        ArrayList<Integer> indexList = new ArrayList<>(Arrays.asList(3, 2, 5));

//        HashMap<Integer, String[]> newsRow = new HashMap<>();

        String[][] newsRow = new String[3][12];

        int i  = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while((line = br.readLine()) != null && !line.isEmpty()) {
                String[] fields = line.split(",");
//                System.out.println("code: " + fields[0] + " name: " + fields[1] + " age: " + fields[2]);
                if (indexList.contains(Integer.parseInt(fields[0]))) {
//                    System.out.println("code: " + fields[0] + " name: " + fields[1] + " age: " + fields[2]);
                    int newsIndex = indexList.indexOf(Integer.parseInt(fields[0]));
                    newsRow[newsIndex] = fields;
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    System.out.println(newsRow[1][1]);
    }
}
