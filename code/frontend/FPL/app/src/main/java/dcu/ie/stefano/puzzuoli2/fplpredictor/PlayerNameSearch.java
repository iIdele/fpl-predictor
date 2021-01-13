package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;


/**
 * PlayerNameSearch is the entity we'll be using to add search widget to App.
 */
public class PlayerNameSearch {

    // Handle search queries and return filtered list based on search query
    public void search(SearchView searchView, final LAdapter searchAdapter, final TextView textView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Collapse header when search bar expands
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextViewCompat.setTextAppearance(textView, R.style.disappear);
            }
        });

        // Restore header when search bar closes
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                TextViewCompat.setTextAppearance(textView, R.style.appear);
                return false;
            }
        });
    }
}
