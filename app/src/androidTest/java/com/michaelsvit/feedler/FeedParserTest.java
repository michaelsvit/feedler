package com.michaelsvit.feedler;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Michael on 4/20/2017.
 */
@RunWith(AndroidJUnit4.class)
public class FeedParserTest {
    @Test
    public void parse() throws Exception {
        final String RSS_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"\n" +
                "\txmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n" +
                "\txmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n" +
                "\txmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
                "\txmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
                "\txmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\"\n" +
                "\txmlns:slash=\"http://purl.org/rss/1.0/modules/slash/\"\n" +
                "\t>\n" +
                "\n" +
                "<channel>\n" +
                "\t<title>Android Police &#8211; Android News, Apps, Games, Phones, Tablets</title>\n" +
                "\t<atom:link href=\"http://www.androidpolice.com/feed/\" rel=\"self\" type=\"application/rss+xml\" />\n" +
                "\t<link>http://www.androidpolice.com</link>\n" +
                "\t<description>Looking after everything Android</description>\n" +
                "\t<lastBuildDate>Thu, 20 Apr 2017 18:45:24 +0000</lastBuildDate>\n" +
                "\t<language>en-US</language>\n" +
                "\t<sy:updatePeriod>hourly</sy:updatePeriod>\n" +
                "\t<sy:updateFrequency>1</sy:updateFrequency>\n" +
                "\t<generator>https://wordpress.org/?v=4.7.3</generator>\n" +
                "<atom:link rel=\"hub\" href=\"https://pubsubhubbub.appspot.com\"/><atom:link rel=\"hub\" href=\"https://pubsubhubbub.superfeedr.com\"/><atom:link rel=\"hub\" href=\"https://androidpolice.superfeedr.com\"/>\t<item>\n" +
                "\t\t<title>Sprint offering two-for-one deal on Galaxy S8, but beware the fine print</title>\n" +
                "\t\t<link>http://www.androidpolice.com/2017/04/20/sprint-offering-two-one-deal-galaxy-s8-beware-fine-print/</link>\n" +
                "\t\t<comments>http://www.androidpolice.com/2017/04/20/sprint-offering-two-one-deal-galaxy-s8-beware-fine-print/#respond</comments>\n" +
                "\t\t<pubDate>Thu, 20 Apr 2017 18:44:41 +0000</pubDate>\n" +
                "\t\t<dc:creator><![CDATA[Ryan Whitwam]]></dc:creator>\n" +
                "\t\t\t\t<category><![CDATA[Deals]]></category>\n" +
                "\t\t<category><![CDATA[Galaxy S8]]></category>\n" +
                "\t\t<category><![CDATA[News]]></category>\n" +
                "\t\t<category><![CDATA[Samsung]]></category>\n" +
                "\t\t<category><![CDATA[Sprint]]></category>\n" +
                "\t\t<category><![CDATA[deals]]></category>\n" +
                "\t\t<category><![CDATA[galaxy s8]]></category>\n" +
                "\t\t<category><![CDATA[lease]]></category>\n" +
                "\n" +
                "\t\t<guid isPermaLink=\"false\">http://www.androidpolice.com/?p=419837</guid>\n" +
                "\t\t<description>description</description>\n" +
                "\t\t<wfw:commentRss>http://www.androidpolice.com/2017/04/20/sprint-offering-two-one-deal-galaxy-s8-beware-fine-print/feed/</wfw:commentRss>\n" +
                "\t\t<slash:comments>0</slash:comments>\n" +
                "\t\t</item>" +
                "</channel>\n" +
                "</rss>";

        final String expectedFeedTitle = "Android Police – Android News, Apps, Games, Phones, Tablets";
        final String expectedFeedDescription = "Looking after everything Android";

        final String expectedArticleTitle = "Sprint offering two-for-one deal on Galaxy S8, but beware the fine print";
        final String expectedArticleUrl = "http://www.androidpolice.com/2017/04/20/sprint-offering-two-one-deal-galaxy-s8-beware-fine-print/";
        final String expectedArticleDescription = "description";
        final String expectedArticleGuid = "http://www.androidpolice.com/?p=419837";
        final String expectedArticlePubDate = "Thu, 20 Apr 2017 18:44:41 +0000";
        final List<String> expectedArticleCategories = new ArrayList<>();
        expectedArticleCategories.add("Deals");
        expectedArticleCategories.add("Galaxy S8");
        expectedArticleCategories.add("News");
        expectedArticleCategories.add("Samsung");
        expectedArticleCategories.add("Sprint");
        expectedArticleCategories.add("deals");
        expectedArticleCategories.add("galaxy s8");
        expectedArticleCategories.add("lease");

        Feed feed = FeedParser.parse((new ByteArrayInputStream(RSS_STRING.getBytes(Charset.forName("UTF-8")))));

        assertNotNull(feed);
        assertEquals(expectedFeedTitle, feed.getTitle());
        assertEquals(expectedFeedDescription, feed.getDescription());

        List<Article> articles = feed.getArticles();
        assertNotNull(articles);
        assertTrue(articles.size() == 1);

        Article article = articles.get(0);
        assertNotNull(article);
        assertEquals(expectedArticleTitle, article.getTitle());
        assertEquals(expectedArticleUrl, article.getUrl());
        assertEquals(expectedArticleDescription, article.getDescription());
        List<String> articleCategories = article.getCategories();
        assertNotNull(articleCategories);
        for (int i = 0; i < expectedArticleCategories.size(); i++) {
            assertEquals(expectedArticleCategories.get(i), articleCategories.get(i));
        }
        assertEquals(expectedArticleGuid, article.getGuid());
        assertEquals(expectedArticlePubDate, article.getPubDate());
    }

}