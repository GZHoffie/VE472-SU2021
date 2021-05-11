package com.ve472.l1;

public class TestCinema {
    public static void main(String[] args) {
        Cinema c = new Cinema();
        c.readDir("l1/config");
        c.printHallList();
        c.printMovieList();
    }
}
