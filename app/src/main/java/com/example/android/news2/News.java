package com.example.android.news2;

public class News {
    private String mTitle;
    private String mSection;
    private String mAuthor;
    private String mDate;
    private String mNewsUrl;

    public News(String title, String section, String author, String date, String newsUrl) {
        this.mTitle = title;
        this.mSection = section;
        this.mAuthor = author;
        this.mDate = date;
        this.mNewsUrl = newsUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }
}
