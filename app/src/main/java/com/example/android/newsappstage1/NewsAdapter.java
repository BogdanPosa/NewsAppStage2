package com.example.android.newsappstage1;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each News
 * in the data source (a list of {@link News} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * The part of the webPublicationDate string from the guardian api service that we use to determine
     * where the date and the time are ("dateTtimeZ").
     */
    private static final String DATETIME_SEPARATOR = "T";
    private static final String DATETIME_END = "Z";

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param news is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the News at the given position
     * in the list of Newsfeeds.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the News at the given position in the list of NewsFeeds
        News currentNews = getItem(position);

        // Find the TextView with view ID section
        TextView sectionView = listItemView.findViewById(R.id.section);
        // Display the section of the current News in that TextView
        sectionView.setText(currentNews.getSection());

        // Set the proper background color on the section rectangle.
        GradientDrawable sectionRectangle = (GradientDrawable) sectionView.getBackground();
        // Get the appropriate background color based on the current News section
        int sectionColor = getSectionColor(currentNews.getSection());
        // Set the color on the section rectangle
        sectionRectangle.setColor(sectionColor);

        // Set the background color of the list item
        LinearLayout sectionLayout = listItemView.findViewById(R.id.list_item_layout);
        int sectionBackground = getSectionBackground(currentNews.getSection());
        //Set color of the background
        sectionLayout.setBackgroundColor(sectionBackground);

        // Find the TextView with view ID title
        TextView titleView =  listItemView.findViewById(R.id.title);
        // Display the location of the current News in that TextView
        titleView.setText(currentNews.getTitle());

        // Get both name and surname and then concatenate them in a author name
        String name = currentNews.getAuthorName();
        String surname = currentNews.getAuthorSurname();
        String author =name+" "+surname;

        // Find the TextView with view ID title
        TextView authorView = listItemView.findViewById(R.id.author);
        // Display the location of the current News in that TextView
        authorView.setText(author);

        // Create a new DateTime object from the DateTime of the News
        String originalDateTime = currentNews.getDateTime();

        // If the original DateTime (i.e. "2018-03-13T19:53:59Z") contains
        // a T and a Z
        // then the first part before T is the date and the second after T is the time,
        // so they can be displayed in 2 TextViews.
        String theDate;
        String theTime;

        // Check whether the originalLocation string contains the " of " text
        if (originalDateTime.contains(DATETIME_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
            String[] parts = originalDateTime.split(DATETIME_SEPARATOR);
            // Date before T "2018-03-13"
            theDate = parts[0];
            // time after T "19:53:59Z"
            theTime = parts[1];
        } else {
            // Otherwise, there today's news
            theDate = getContext().getString(R.string.today);
            // the time will always be displayed
            theTime = originalDateTime;
        }
        // Check whether the Z exists
        if (theTime.contains(DATETIME_END)) {
            String[] parts = theTime.split(DATETIME_END);
            // time without Z
            theTime = parts[0];
        } else {
            // Otherwise, there is Z and remains the same
            theTime = theTime;
        }

        // Find the TextView with view ID date
        TextView dateView =  listItemView.findViewById(R.id.date);
        // Display the date of the current News in that TextView
        dateView.setText(theDate);
        // Find the TextView with view ID time
        TextView timeView =  listItemView.findViewById(R.id.time);
        // Display the time of the current News in that TextView
        timeView.setText(theTime);

        return listItemView;
    }

    /**
     * Return the color for the section rectangle based on the type of the News.
     *
     * @param section of the News
     */
    private int getSectionColor(String section) {
        int sectionColorResourceId;
        switch (section) {
            case "":
            case "Politics":
                sectionColorResourceId = R.color.section1;
                break;
            case "Business":
                sectionColorResourceId = R.color.section2;
                break;
            case "Opinion":
                sectionColorResourceId = R.color.section3;
                break;
            case "World news":
                sectionColorResourceId = R.color.section4;
                break;
            case "UK news":
                sectionColorResourceId = R.color.section4;
                break;
            case "Science":
                sectionColorResourceId = R.color.section6;
                break;
            case "Society":
                sectionColorResourceId = R.color.section7;
                break;
            case "Music":
                sectionColorResourceId = R.color.section8;
                break;
            case "Technology":
                sectionColorResourceId = R.color.section9;
                break;
            case "US news":
                sectionColorResourceId = R.color.section10;
                break;
            case "Australia news":
                sectionColorResourceId = R.color.section10;
                break;
            default:
                sectionColorResourceId = R.color.section1;
                break;
        }

        return ContextCompat.getColor(getContext(), sectionColorResourceId);
    }

    /**
     * Return the background color for the section rectangle based on the intensity of the News.
     * For some sections a different background is used to make them more visible
     * @param section of the News
     */
    private int getSectionBackground(String section) {
        int sectionColorBackground;
        switch (section) {
            case "":
            case "Politics":
                sectionColorBackground = R.color.section5;
                break;
            case "Business":
                sectionColorBackground = R.color.section5;
                break;
            case "Opinion":
                sectionColorBackground = R.color.section11;
                break;
            case "World news":
                sectionColorBackground = R.color.section5;
                break;
            case "UK news":
                sectionColorBackground = R.color.section5;
                break;
            case "US news":
                sectionColorBackground = R.color.section5;
                break;
            case "Science":
                sectionColorBackground = R.color.section6;
                break;
            case "Technology":
                sectionColorBackground = R.color.section4;
                break;
            default:
                sectionColorBackground = R.color.section11;
                break;
        }

        return ContextCompat.getColor(getContext(), sectionColorBackground);
    }
}