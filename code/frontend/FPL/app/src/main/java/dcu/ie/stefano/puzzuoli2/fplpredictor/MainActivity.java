package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    int nOfPredictions = 0;                    // Total number of Predictions to retrieve
    String errors;                             // To store error messages
    CountDownLatch latch1, latch2, latch3;     // For synchronization
    ArrayList<Prediction> predictions;         // To store Predictions retrieved
    DataQueryBuilder queryBuilder;             // To query Backendless for data we want to retrieve
    SearchView searchView;                     // To create dynamic search
    ListView listView;                         // To display predictions in a list
    LAdapter searchAdapter;                    // To format display of predictions
    Spinner sortSpinner;                       // To sort display of predictions
    SortSpinner spinner = new SortSpinner();   // Custom spinner class add functions to spinner
    PlayerNameSearch playerNameSearch= new PlayerNameSearch(); // To search for predictions by player name

    // Custom toast (with dark background)
    CustomToastUtil customToastUtil = new CustomToastUtil(MainActivity.this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ID for sort by textview
        TextView sortBy = findViewById(R.id.tvSortBy);
        // ID for sortSpinner
        sortSpinner = findViewById(R.id.sortSpinner);
        // Assign values to spinner
        sortSpinner = spinner.makeSpinner(getBaseContext(),sortSpinner, sortBy);


        // Retrieve and display Players/Predictions from Backendless
        new GetDataInBackground().execute();
    }

    // Retrieve Predictions from Backendless

    @SuppressLint("StaticFieldLeak")
    protected class GetDataInBackground extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Inform user that predictions are being loaded
            customToastUtil.showToast("Loading predictions... please wait");

        }

        @Override
        protected String doInBackground(Void... params) {

            // Save all error messages
            errors = "";

            // To assure total number of objects is obtained before setting number of pages
            latch1 = new CountDownLatch(1);

            // Get total number of predictions in Backendless table
            Backendless.Data.of(Prediction.class).getObjectCount(new AsyncCallback<Integer>() {
                @Override
                public void handleResponse(Integer response) {

                    nOfPredictions = response;
                    latch1.countDown();

                }

                // To store error (if occurs)
                @Override
                public void handleFault(BackendlessFault fault) {

                    errors += fault.getMessage() + "\n";

                }
            });

            try
            {
                // Wait until all objects are obtained before setting number of pages
                latch1.await();
            }
            catch (InterruptedException e)
            {
                errors += e.getMessage() + "\n";
            }

            // Get number of pages needed (100 objects max per page)
            int nofPages = (int) Math.ceil(nOfPredictions / 100.0);

            // To assure all objects have been retrieved before returning
            latch2 = new CountDownLatch(nofPages);

            // ArrayList of Predictions retrieved from Backendless
            predictions = new ArrayList<>();

            // To query Backendless for data we want to retrieve
            queryBuilder = DataQueryBuilder.create();

            int offset = 100;

            for (int i = 0; i < nofPages; i++)
            {
                // Get all non-null Players/Predictions, 100 at a time and sort by predictions
                queryBuilder.setWhereClause("player_name is not null");
                queryBuilder.setPageSize(100).setOffset(offset * i);
                queryBuilder.setSortBy("points_prediction");

                latch3 = new CountDownLatch(1);

                // Add Predictions to ArrayList one at a time from page retrieved from Backendless
                Backendless.Persistence.of(Prediction.class).find(queryBuilder, new AsyncCallback<List<Prediction>>() {
                    @Override
                    public void handleResponse(List<Prediction> response) {

                        predictions.addAll(response);

                        queryBuilder.prepareNextPage();
                        latch2.countDown();
                        latch3.countDown();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        errors += fault.getMessage() + "\n";
                    }
                });

                try
                {
                    // Wait for all objects to be added to predictions ArrayList
                    latch3.await();
                }
                catch (InterruptedException e)
                {
                    errors += e.getMessage() + "\n";
                }
            }

            try
            {
                // Wait for all objects to be retrieved before returning
                latch2.await();
            }
            catch(InterruptedException e)
            {
                errors += e.getMessage() + "\n";
            }

            // Return any error messages encountered
            return errors;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Inform user on outcome of predictions retrieval
            if (! s.equals(""))
            {
                customToastUtil.showToast("The following errors occured: " + "\n" + s);
            }
            else
            {
                customToastUtil.showToast("Predictions loaded successfully.");
            }

            // Verify if players have been successfully retrieved from Backendless
            Log.i("MyTag", "Players/predictions read in ArrayList");

            // ID for listView
            listView = findViewById(R.id.listView);
            // ID for searchView
            searchView = findViewById(R.id.searchView);

            // Make use of LAdapter to dynamically output Players and Predictions in ListView
            searchAdapter = new LAdapter(MainActivity.this, predictions);
            // Sort list by Predictions in descending order by default
            searchAdapter = spinner.sortPredictionsDescending(MainActivity.this,predictions);

            // Use searchview and custom class to add dynamic search function
            final TextView headerText = findViewById(R.id.FPLPPHeading);
            playerNameSearch.search(searchView,searchAdapter, headerText);

            // Create listview to display predictions
            listView.setAdapter(searchAdapter);

            sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // String to match sort selected in spinner
                    String text = parent.getItemAtPosition(position).toString();
                    // Sort listView based on selected sort
                    switch (text) {
                        // Sort by Predictions in descending order
                        case "Predictions (Descending)":
                            searchAdapter = spinner.sortPredictionsDescending(MainActivity.this, predictions);
                            // Update search with new filtered adapter
                            playerNameSearch.search(searchView, searchAdapter, headerText);
                            listView.setAdapter(searchAdapter);
                            customToastUtil.showToast("Sorted By: Predictions (Descending)");
                            break;
                        // Sort by Predictions in ascending order
                        case "Predictions (Ascending)":
                            searchAdapter = spinner.sortPredictionsAscending(MainActivity.this, predictions);
                            // Update search with new filtered adapter
                            playerNameSearch.search(searchView, searchAdapter, headerText);
                            listView.setAdapter(searchAdapter);
                            customToastUtil.showToast("Sorted By: Predictions (Ascending)");
                            break;
                        // Sort by Player Name in ascending order
                        case "Player Name (A - Z)":
                            searchAdapter = spinner.sortNamesAscending(MainActivity.this, predictions);
                            // Update search with new filtered adapter
                            playerNameSearch.search(searchView, searchAdapter, headerText);
                            listView.setAdapter(searchAdapter);
                            customToastUtil.showToast("Sorted By: Player Name (A - Z)");
                            break;
                        // Sort by PLayer Name in descending order
                        case "Player Name (Z - A)":
                            searchAdapter = spinner.sortNamesDescending(MainActivity.this, predictions);
                            // Update search with new filtered adapter
                            playerNameSearch.search(searchView, searchAdapter, headerText);
                            listView.setAdapter(searchAdapter);
                            customToastUtil.showToast("Sorted By: Player Name (Z - A)");
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // Toast to show player name and prediction when clicked in listview
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    customToastUtil.showToast(String.format(Locale.ENGLISH
                            ,"%s: %d",searchAdapter.getItem(i).getPlayer_name(),searchAdapter.getItem(i).getPoints_prediction()));
                }
            });
        }
    }
}
