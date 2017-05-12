package com.example.nehalsalah.booklisting;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An {@link BookListingAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link BookListing} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookListingAdapter extends ArrayAdapter<BookListing> {

    /**
     * Constructs a new {@link BookListingAdapter}.
     *
     * @param context of the app
     * @param bookListings is the list of bookListings, which is the data source of the adapter
     */
    public BookListingAdapter(Context context, List<BookListing> bookListings) {
        super(context, 0, bookListings);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        BookListing currentBookListing = getItem(position);

        // Find the TextView with view result number of research
      //  TextView NumberView = (TextView) listItemView.findViewById(R.id.number);
       // NumberView.setText(currentBookListing.getNumber());

        // Find the TextView with Title of the book
        TextView TitleView = (TextView) listItemView.findViewById(R.id.title);
        TitleView.setText(currentBookListing.getTitle());

        // Find the TextView with Description of the book
        TextView DescriptionView = (TextView) listItemView.findViewById(R.id.description);
        DescriptionView.setText(currentBookListing.getDescription());



        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}
