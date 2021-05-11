package com.ve472.l1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hall {
    // Attributes
    private String hallName;
    private String movieName;
    public List<List<Boolean>> seats = new ArrayList<>();

    public int numRows = 0;
    public int numCols = 0;

    public void readConfig(File configFile) {
        /*
        Read the config file with directory `filePath` and store the information
        into the class attributes.
         */
        try {
            Scanner scanner = new Scanner(configFile);
            hallName = scanner.nextLine();
            movieName = scanner.nextLine();
            while (scanner.hasNextLine()) {
                List<Boolean> row = new ArrayList<>();
                String seatsStr = scanner.nextLine();
                String[] seatsStrSplit = seatsStr.split("\\s+");
                for (String s : seatsStrSplit) {
                    row.add("1".equals(s));
                }
                seats.add(row);
            }
            numRows = seats.size();
            numCols = seats.get(0).size();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public String getHallName() { return hallName; }

    public String getMovieName() { return movieName; }

    public void printSeats() {
        // For debugging
        for (List<Boolean> l : seats) {
            for (Boolean b : l) {
                System.out.print(b + " ");
            }
            System.out.print("\n");
        }
        System.out.println(numRows);
        System.out.println(numCols);
    }

    public void printInfo() {
        System.out.println("Hall Name: " + hallName + "; Movie Name: " + movieName);
    }
}
