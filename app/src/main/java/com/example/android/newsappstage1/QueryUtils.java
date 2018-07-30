package com.example.android.newsappstage1;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving News data from Guardian api.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor {@link QueryUtils}.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsfeedData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(R.string.read_timeout);
            urlConnection.setConnectTimeout(R.string.connect_timeout);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
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

    /**
     * Convert the {@link InputStream} into a String which contains the
     *  JSON response from the server.
     */
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

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the JSON response.
     */
    private static List<News> extractFeatureFromJson(String NewsfeedJSON) {
        if (TextUtils.isEmpty(NewsfeedJSON)) {
            return null;
        }

        // Create an empty ArrayList
        List<News> news = new ArrayList<>();

        try {

            // Create a new JSON object
            JSONObject baseJsonResponse = new JSONObject(NewsfeedJSON);

            JSONObject response = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or news).
            JSONArray NewsArray = response.getJSONArray("results");

            // For each News in the NewsArray, create an {@link News} object
            for (int i = 0; i < NewsArray.length(); i++) {

                // Get one of the News at position i within the list of news
                JSONObject currentNews = NewsArray.getJSONObject(i);

                // Extract the value for the key called "section"
                String section = currentNews.getString("sectionName");

                // Extract the value for the key called "title"
                String title = currentNews.getString("webTitle");

                JSONArray tags = currentNews.getJSONArray("tags");

                String authorName = null;
                String authorSurname = null;
                for (int j = 0; j < tags.length(); j++) {
                    JSONObject currentAuthor = tags.getJSONObject(j);
                    authorName = currentAuthor.getString("firstName");
                    authorSurname = currentAuthor.getString("lastName");
                }

                // Extract the value for the key called "time"
                String time = currentNews.getString("webPublicationDate");

                // Extract the value for the key called "url"
                String url = currentNews.getString("webUrl");


                // Create a new {@link News} object with the title, authorName, authorSurname, time,
                // and url from the JSON response.
                News News = new News(section, title, authorName, authorSurname, time, url);

                // Add the new {@link News} to the list of news.
                news.add(News);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);

        }

        // Return the list of news
        return news;
    }
}