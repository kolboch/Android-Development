package com.bochynski.example.lab2;

public class Movie {
    private String title, genre, year;
    private boolean seenByUser;

    public Movie() {
        this("", "", "", false);
    }

    public Movie(String title, String genre, String year) {
        this(title, genre, year, false);
    }

    public Movie(String title, String genre, String year, boolean seen) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.seenByUser = seen;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        String description = "Beautiful movie " + title +
                "\ncreated by unknown director.\n" +
                "One of a kind genre: " + genre +
                "\n Definitely a must watch of " + year;
        return description;
    }

    public void setSeenByUser(boolean wasSeen){
        this.seenByUser = wasSeen;
    }

    public boolean getSeenByUser() {
        return this.seenByUser;
    }
}
