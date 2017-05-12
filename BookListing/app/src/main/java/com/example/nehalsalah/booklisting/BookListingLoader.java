package com.example.nehalsalah.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of book listings by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BookListingLoader extends AsyncTaskLoader<List<BookListing>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookListingLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookListingLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookListingLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<BookListing> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of bookListings.
        List<BookListing> bookListings = QueryUtils.fetchBookListingData(mUrl);
        return bookListings;
    }
}

