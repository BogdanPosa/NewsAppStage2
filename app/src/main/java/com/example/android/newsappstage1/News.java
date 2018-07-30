package com.example.android.newsappstage1;

/**
 * An {@link News} object contains information related to a single News.
 */
public class News {

    /** section of the News */
    private String mSection;

    /** title of the News */
    private String mTitle;

    /** author name of the News */
    private String mAuthorName;

    /** author surname of the News */
    private String mAuthorSurname;

    /** Date and time of the News */
    private String mDateTime;

    /** Website URL of the News */
    private String mUrl;

    /**
     * Constructs a new {@link News} object.
     *
     * @param section is the section (theme) of the News
     * @param title is the title of the News
     * @param authorName is the name of the Author of the story in the section
     * @param authorSurname is the surname of the Author of the story in the section
     * @param datetime is the date and time in dateTtimeZ format
     * @param url is the website URL to find more details about the News
     */
    public News(String section, String title, String authorName, String authorSurname, String datetime, String url) {
        mSection = section;
        mTitle = title;
        mAuthorName = authorName;
        mAuthorSurname = authorSurname;
        mDateTime = datetime;
        mUrl = url;
    }

    /**
     * Returns the section of the News.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns the title of the News.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the author name of the News.
     */
    public String getAuthorName() {
        return mAuthorName;
    }
    /**
     * Returns the author surname of the News.
     */
    public String getAuthorSurname() {
        return mAuthorSurname;
    }

    /**
     * Returns the datetime of the News.
     */
    public String getDateTime() {
        return mDateTime;
    }

    /**
     * Returns the website URL to find more information about the News.
     */
    public String getUrl() {
        return mUrl;
    }
}