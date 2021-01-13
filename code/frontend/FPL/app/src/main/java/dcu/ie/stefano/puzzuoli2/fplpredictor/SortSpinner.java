package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.common.base.CharMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * SortSpinner is the entity we'll be using to make drop down spinner and sort lisview in various ways.
 */
class SortSpinner {
    // Create spinner and assign values
    protected Spinner makeSpinner(Context c, final Spinner sortSpinner, TextView sortBy) {
        // Assign values to spinner from strings.xml
        String [] items = new String[] {"Predictions (Descending)", "Predictions (Ascending)", "Player Name (A - Z)", "Player Name (Z - A)"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(c,R.layout.textview_spinner,items);
        sortSpinner.setAdapter(adapter);
//        // Assign default spinner for lesser android versions
//        if (Build.VERSION.SDK_INT > 21)
//            sortSpinner.setBackground(getDrawable(c, R.drawable.ic_action_spinner));

        // Make sort by textview clickable and to open spinner on click
        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortSpinner.performClick();
            }
        });

        return sortSpinner;
    }


    // Sort by Predictions in descending order
    protected LAdapter sortPredictionsDescending(Context c, ArrayList<Prediction> predictions){
        Collections.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return (o1.getPoints_prediction().compareTo(o2.getPoints_prediction()));
            }
        });
        Collections.reverse(predictions);
        return new LAdapter(c, predictions);
    }

    // Sort by Predictions in Ascending order
    protected LAdapter sortPredictionsAscending(Context c, ArrayList<Prediction> predictions){
        Collections.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return (o1.getPoints_prediction().compareTo(o2.getPoints_prediction()));
            }
        });
        return new LAdapter(c, predictions);
    }

    // Sort by Player Name in Ascending order
    protected LAdapter sortNamesAscending(Context c, ArrayList<Prediction> predictions){
        Collections.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return CharMatcher.inRange('a','z').retainFrom(o1.getPlayer_name().toLowerCase()).compareTo(CharMatcher.inRange('a','z').retainFrom(o2.getPlayer_name().toLowerCase()));

            }
        });
        return new LAdapter(c, predictions);
    }

    // Sort by Player Name in descending order
    protected LAdapter sortNamesDescending(Context c, ArrayList<Prediction> predictions){
        Collections.sort(predictions, new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return CharMatcher.inRange('a','z').retainFrom(o1.getPlayer_name().toLowerCase()).compareTo(CharMatcher.inRange('a','z').retainFrom(o2.getPlayer_name().toLowerCase()));

            }
        });
        Collections.reverse(predictions);
        return new LAdapter(c, predictions);
    }
}
