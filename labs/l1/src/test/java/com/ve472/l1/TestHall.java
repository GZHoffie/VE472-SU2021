package com.ve472.l1;

import java.io.File;
import java.util.List;

public class TestHall {
    public static void main(String[] args) {
        Hall hall = new Hall();
        File configFile = new File("sample.config");
        hall.readConfig(configFile);
        System.out.println(hall.getHallName());
        for (List<Boolean> l : hall.seats) {
            for (Boolean b : l) {
                System.out.print(b);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        System.out.println(hall.numRows);
        System.out.println(hall.numCols);
    }
}
