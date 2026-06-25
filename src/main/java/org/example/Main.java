package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws InterruptedException {
        int n = 2;
        int t = 5;
        int h = 3;
        int d = 14;
        int t1 = 5;
        int t2 = 10;
        new QueueMaster(n, t, h, d, t1, t2);
    }

    public static int[] getConfig(){
        File myObj = new File("src/main/resources/config.txt");
        int[] config = new int[2];

        try (Scanner myReader = new Scanner(myObj)) {
            for(int i = 0; i < 2; i++){
                config[i] = Integer.parseInt(myReader.nextLine());
                System.out.println((i == 0 ? "X" : "Y") + " = " + config[i]);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return config;
    }
}
