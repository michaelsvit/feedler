package com.michaelsvit.feedler;

import java.util.List;

/**
 * Created by Michael on 4/20/2017.
 */

class Article {
    private String title;
    private String url;
    private String description;
    private List<String> categories;
    private String guid;
    private String pubDate;

    public Article(String title, String url, String description, List<String> categories, String guid, String pubDate) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.categories = categories;
        this.guid = guid;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }
}
