package com.example.nehalsalah.booklisting;

        import android.app.LoaderManager;
        import android.app.LoaderManager.LoaderCallbacks;
        import android.content.Context;
        import android.content.Intent;
        import android.content.Loader;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

public class SearchingResult extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<BookListing>> {

    private static final String LOG_TAG = BookListingActivity.class.getName();

    /**
     * URL for book listings  data from the USGS dataset
     */
   // private static final String USGS_REQUEST_URL =            "https://www.googleapis.com/books/v1/volumes?q=car\\";

    /**
     * Constant value for the book listing  loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int bookListing_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     */
    private BookListingAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklisting_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("my_variable");
            Log.d("search", "Value: " + value);
        }
        // Find a reference to the {@link ListView} in the layout
        ListView BookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        BookListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of book listings  as input
        mAdapter = new BookListingAdapter(this, new ArrayList<BookListing>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        BookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book .
        BookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book  that was clicked on
                BookListing currentBookListing = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri BookUri = Uri.parse(currentBookListing.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, BookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(bookListing_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public Loader<List<BookListing>> onCreateLoader(int i, Bundle bundle) {
       Bundle extras = getIntent().getExtras();
        String value="https://www.googleapis.com/books/v1/volumes?q=uh";
        if (extras != null) {
            value = extras.getString("my_variable");
            Log.d("search", "Value: " + value);
        }

        return new BookListingLoader(this, value);
    }

    @Override
    public void onLoadFinished(Loader<List<BookListing>> loader, List<BookListing> bookListings) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No bookListings found."
        mEmptyStateTextView.setText(R.string.no_data);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link BookListing}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookListings != null && !bookListings.isEmpty()) {
            mAdapter.addAll(bookListings);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<BookListing>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}
