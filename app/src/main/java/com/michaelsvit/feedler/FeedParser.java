package com.michaelsvit.feedler;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 4/20/2017.
 */

public class FeedParser {

    private static final String ns = null;

    public static Feed parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            Feed feed = null;
            final boolean isRssDoc = parser.getName().equals("rss");
            final boolean isVersion2 = parser.getAttributeValue(null, "version").equals("2.0");
            if (isRssDoc && isVersion2) {
                parser.nextTag();
                feed = readRss2Feed(parser);
            }
            return feed;
        } finally {
            in.close();
        }
    }

    private static Feed readRss2Feed(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        String feedTitle = null;
        String feedDescription = null;
        List<Article> feedArticles = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();
            switch (tagName) {
                case "item":
                    feedArticles.add(getFeedArticle(parser));
                    break;
                case "title":
                    feedTitle = readText(parser);
                    break;
                case "description":
                    feedDescription = readText(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        return new Feed(feedTitle, feedDescription, feedArticles);
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = null;
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }

    private static void skip(XmlPullParser parser) throws IOException, XmlPullParserException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static Article getFeedArticle(XmlPullParser parser) throws IOException, XmlPullParserException {
        String title = null;
        String url = null;
        String description = null;
        List<String> categories = new ArrayList<>();
        String guid = null;
        String pubDate = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();
            switch (tagName) {
                case "title":
                    title = readText(parser);
                    break;
                case "link":
                    url = readText(parser);
                    break;
                case "description":
                    description = readText(parser);
                    break;
                case "category":
                    categories.add(readText(parser));
                    break;
                case "guid":
                    guid = readText(parser);
                    break;
                case "pubdate":
                case "pubDate":
                    //TODO: convert to unix timestamp
                    pubDate = readText(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new Article(title, url, description, categories, guid, pubDate);
    }

}
