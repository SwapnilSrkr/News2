package com.example.android.news2;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {}

    private static List<News> extractNewsFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newses = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray newsesArray = response.getJSONArray("results");
            for (int i = 0; i < newsesArray.length(); i++) {
                JSONObject currentNews = newsesArray.getJSONObject(i);
                String authorArticle;
                if (currentNews.has("fields")) {
                    JSONObject fieldsObject = currentNews.getJSONObject("fields");

                    if (fieldsObject.has("byline")) {
                        authorArticle = fieldsObject.getString("byline");
                    }else {
                        authorArticle = "unknown author";
                    }
                }else {
                    authorArticle = "missing author info";
                }
                String titleArticle = currentNews.getString("webTitle");
                String sectionArticle = "#" + currentNews.getString("sectionName");

                String dateArticle = currentNews.getString("webPublicationDate");

                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(dateArticle);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                dateArticle = formattedDate;

                String urlArticle = currentNews.getString("webUrl");

                News newsItem = new News(titleArticle, sectionArticle, authorArticle, dateArticle, urlArticle);

                newses.add(newsItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newses;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        final int READ_TIMEOUT = 10000;
        final int CONNECT_TIMEOUT = 15000;
        final int CORRECT_RESPONSE_CODE = 200;

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == CORRECT_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    static List<News> fetchNewsData(String requestUrl) {

        final int SLEEP_TIME_MILLIS = 2000;

        try {
            Thread.sleep(SLEEP_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        List<News> listNewses = extractNewsFromJson(jsonResponse);
        return listNewses;
    }
}
