package com.michaelsvit.feedler;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Michael on 4/21/2017.
 */

public class FeedLoader extends AsyncTaskLoader<Feed> {

    private static final String LOG_TAG = FeedLoader.class.getSimpleName();

    private String url;

    public FeedLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public Feed loadInBackground() {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error retrieving RSS feed for url: " + url);
        }
        if (response == null) {
            return null;
        }

        try {
            return FeedParser.parse(response.body().byteStream());
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error while parsing RSS feed response");
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
