package com.ve472.l1;

import java.io.File;
import java.util.*;

public class Cinema {
    public HashMap<String, Hall> hallNameMap = new HashMap<>();
    public HashMap<String, List<Hall>> movieMap = new HashMap<>();

    public void readDir(String dirName) {
        /*
        Read all files under directory dirName and store the information
        inside the hash map `cinema`. The directory is assumed to be containing
        only files (no sub-directory)
         */
        File dir = new File(dirName);
        for (final File f : Objects.requireNonNull(dir.listFiles())){
            if (f.isDirectory()){
                continue;
            }
            Hall hall = new Hall();
            hall.readConfig(f);
            hallNameMap.put(hall.getHallName(), hall);
            String movieName = hall.getMovieName();
            if (movieMap.containsKey(movieName)) {
                List<Hall> hallList = movieMap.get(movieName);
                hallList.add(hall);
                movieMap.replace(movieName, hallList);
            } else {
                // No hall found for this movie yet.
                List<Hall> newList = new ArrayList<>();
                newList.add(hall);
                movieMap.put(movieName, newList);
            }

        }
    }

    public void printMovieList() {
        for (String s : movieMap.keySet()) {
            System.out.print(s + ": ");
            List<Hall> l = movieMap.get(s);
            for (Hall h : l) {
                System.out.print(h.getHallName() + " ");
            }
            System.out.print("\n");
        }
        /*
        for (Map.Entry<String, List<Hall>> me : movieMap.entrySet()) {
            System.out.print(me.getKey() + ": ");
            List<Hall> hallList = me.getValue();
            for (Hall h : hallList) {
                System.out.print(h.getHallName() + " ");
            }
            System.out.print("\n");
        }

         */
    }

    public void printHallList() {
        for (Map.Entry<String, Hall> me : hallNameMap.entrySet()) {
            Hall h = me.getValue();
            System.out.println(me.getKey() + ": " + h.getMovieName());
        }
    }
}
