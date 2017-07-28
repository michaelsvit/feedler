package com.michaelsvit.feedler;

import java.util.List;

/**
 * Created by Michael on 4/21/2017.
 */

class Feed {
    private String title;
    private String description;
    private List<Article> articles;

    public Feed(String title, String description, List<Article> articles) {
        this.title = title;
        this.description = description;
        this.articles = articles;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
