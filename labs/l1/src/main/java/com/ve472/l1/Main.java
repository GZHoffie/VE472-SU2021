package com.ve472.l1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.*;

class Customer {
    String name;
    String movieName;
    int numSeats;
    Seat assignedSeat;
    String assignedHallName;
    Customer(String name, String movieName, int numSeats) {
        this.name = name;
        this.movieName = movieName;
        this.numSeats = numSeats;
        this.assignedSeat = null;
        this.assignedHallName = null;
    }
}

public class Main {
    public static void main(String[] args) {
        // Parse Options
        Options options = new Options();
        Option help = new Option("h", "help", false, "print this message");
        options.addOption(help);

        Option hall = new Option(null, "hall", true, "path of the hall config directory");
        hall.setRequired(true);
        options.addOption(hall);

        Option query = new Option(null, "query", true, "query of customer orders");
        query.setRequired(true);
        options.addOption(query);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                formatter.printHelp("cinema", options);
                System.exit(0);
                return;
            }
        } catch (ParseException e) {
            // System.out.println(e.getMessage());
            formatter.printHelp("cinema", options);
            System.exit(1);
            return;
        }

        // Main Block
        Cinema cinema = new Cinema();
        cinema.readDir(cmd.getOptionValue("hall"));

        File customerInfoFile = new File(cmd.getOptionValue("query"));
        List<Customer> customers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(customerInfoFile);
            while (scanner.hasNextLine()) {
                String info = scanner.nextLine();
                String[] infoSplit = info.split(",");
                Customer c = new Customer(infoSplit[0], infoSplit[1], Integer.parseInt(infoSplit[2]));
                customers.add(c);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        for (Customer c : customers) {
            Seat bestSeat = null;
            for (Hall h : cinema.movieMap.get(c.movieName)) {
                bestSeat = h.assignSeat(c.numSeats);
                if (bestSeat != null) {
                    c.assignedHallName = h.getHallName();
                    break;
                }
            }
            c.assignedSeat = bestSeat;
            System.out.print(c.name + "," + c.movieName);
            if (c.assignedSeat != null) {
                System.out.print("," + c.assignedHallName + "," + (c.assignedSeat.row + 1));
                for (int i = 0; i < c.numSeats; i++) {
                    System.out.print("," + (c.assignedSeat.col + 1 + i));
                }
            }
            System.out.print("\n");
        }
    }
}