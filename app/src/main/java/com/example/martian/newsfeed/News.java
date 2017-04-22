package com.example.martian.newsfeed;

/**
 * Created by martian on 22/1/17.
 */

public class News {
    private String title, date, author, link, description;

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDate() {
        return date;
    }

    public News setDate(String date) {
        this.date = date;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public News setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getLink() {
        return link;
    }

    public News setLink(String link) {
        this.link = link;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public News setDescription(String description) {
        this.description = description;
        return this;
    }
}


