package com.ve472.l1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Seat {
    // An int pair
    final int row;
    final int col;

    Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Hall {
    // Attributes
    private String hallName;
    private String movieName;
    public List<List<Boolean>> seats = new ArrayList<>();

    public int numRows = 0;
    public int numCols = 0;
    private double backCenterX;
    private double backCenterY;

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
            if (seats.size() > 0) {
                numCols = seats.get(0).size();
            }
            backCenterX = numRows;
            backCenterY = (numCols + 1) / 2.0;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private Boolean hasConsecutiveSeats(Seat seat, int numSeats) {
        /*
        Judge whether there are `numSeats` consecutive seats starting
        from `seat`. The `seat` is assumed to be within the hall.
         */
        assert (seat.row >= 0 && seat.row < numRows
                && seat.col >= 0 && seat.col < numCols);
        if (seat.col + numSeats > numCols) {
            return false;
        }
        for (int i = 0; i < numSeats; i++) {
            if (!seats.get(seat.row).get(seat.col + i)) {
                return false;
            }
        }
        return true;
    }

    private double distanceToCentroid(Seat seat, int numSeats) {
        double seatsCentroidX = seat.row + 1;
        double seatsCentroidY = (seat.col + seat.col + numSeats + 1) / 2.0;
        return Math.pow(seatsCentroidX - backCenterX, 2) + Math.pow(seatsCentroidY - backCenterY, 2);
    }


    private Seat findSeats(int numSeats) {
        /*
        Find if there are consecutive seats. If yes, return the left-most seat that is
        the best according to the lab manual. Return null otherwise.
         */
        if (numSeats > numCols) {
            return null;
        } else {
            double minDistance = -1;
            Seat bestSeat = null;
            for (int row = numRows - 1; row >= 0; row--) {
                for (int col = 0; col <= numCols - numSeats; col++) {
                    Seat seat = new Seat(row, col);
                    if (hasConsecutiveSeats(seat, numSeats)) {
                        double distance = distanceToCentroid(seat, numSeats);
                        if (minDistance < 0 || distance < minDistance) {
                            minDistance = distance;
                            bestSeat = seat;
                        }
                    }
                }
            }
            return bestSeat;
        }
    }

    public Seat assignSeat(int numSeats) {
        Seat bestSeat = findSeats(numSeats);
        if (bestSeat != null) {
            for (int i = 0; i < numSeats; i++) {
                seats.get(bestSeat.row).set(bestSeat.col + i, false);
            }
        }
        return bestSeat;
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
        // For debugging
        System.out.println("Hall Name: " + hallName + "; Movie Name: " + movieName);
    }
}
