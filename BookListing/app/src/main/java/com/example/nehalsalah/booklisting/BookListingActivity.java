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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button to search
        Button searching = (Button) findViewById(R.id.search);
        // Set a click listener on that View
        searching.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the search button is clicked on.
            @Override
            public void onClick(View view) {
                EditText searchEditText = (EditText) findViewById(R.id.SearchEditText);
                Editable searchEditable = searchEditText.getText();
                String userSearch = searchEditable.toString();
                String USGS_REQUEST_URL =
                        "https://www.googleapis.com/books/v1/volumes?q="+ userSearch;
                // Create a new intent to open the search result Activity}
                Intent SearchingIntent = new Intent(BookListingActivity.this, SearchingResult.class);
                SearchingIntent.putExtra("my_variable",USGS_REQUEST_URL);
                // Start the new activity
                startActivity(SearchingIntent);
            }
        });
    }
}
