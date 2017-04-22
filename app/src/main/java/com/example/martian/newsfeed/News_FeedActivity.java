package com.example.martian.newsfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class News_FeedActivity extends AppCompatActivity {
    public static final String LOG_TAG = News_FeedActivity.class.getSimpleName();

    private static final String rss_url = "http://www.pcworld.com/index.rss";
    private List<News> newses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);

        Log.i("Message", "Not able to call");
        NewsFeedAsyncTask newsSync = new NewsFeedAsyncTask();
        newsSync.execute(rss_url);

        try {
            newses = newsSync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //newses.add(new News("Aakash", "pubDate", "author", "link"));
        NewsAdapter adapter = new NewsAdapter(this, newses);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }


    private class NewsFeedAsyncTask extends AsyncTask<String, Void, List<News>> {

        private List<News> newses = new ArrayList<>();

        protected List<News> doInBackground(String... params) {

            String strUrl = params[0];
            InputStream stream;
            //ArrayList<News> Newsdata = new ArrayList<>();
            try {
                URL url = new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10 * 1000);
                connection.setConnectTimeout(10 * 1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int response = connection.getResponseCode();
                Log.d("debug", "The Response is " + response);
                stream = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stream, null);
                //int eventType = xpp.getEventType();

                while (xpp.next() != XmlPullParser.END_DOCUMENT) {

                    News news = new News();

                    if (xpp.getEventType() != XmlPullParser.START_TAG) {
                        Log.i("First Time it was here", "");
                        //xpp.next();
                        continue;
                        //xpp.next();
                    }
                    String title = xpp.getName(), pubDate = xpp.getName(), link = xpp.getName(), author = xpp.getName();

                    if (title.equalsIgnoreCase("title")) {
                        if (xpp.next() == XmlPullParser.TEXT) {
                            title = xpp.getText();
                            Log.i("Title is", "" + title);
                            news.setTitle(title);
                            xpp.nextTag();

                        }
                    }
                    if (pubDate.equalsIgnoreCase("pubDate")) {
                        if (xpp.next() == XmlPullParser.TEXT) {
                            pubDate = xpp.getText();
                            Log.i("PubDate is", "" + pubDate);
                            news.setDate(pubDate);
                            xpp.nextTag();

                        }
                    }
                    if (author.equalsIgnoreCase("author")) {
                        if (xpp.next() == XmlPullParser.TEXT) {
                            author = xpp.getText();
                            Log.i("Author is", "" + author);
                            news.setAuthor(author);
                            xpp.nextTag();


                        }
                    }

                    if (link.equalsIgnoreCase("link")) {
                        if (xpp.next() == XmlPullParser.TEXT) {
                            link = xpp.getText();
                            Log.i("Link is", "" + link);
                            news.setLink(link);
                            xpp.nextTag();

                        }
                    }

                    this.newses.add(news);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            this.newses.remove(0);
            this.newses.remove(0);
            this.newses.remove(0);
            return this.newses;
        }
    }
}

