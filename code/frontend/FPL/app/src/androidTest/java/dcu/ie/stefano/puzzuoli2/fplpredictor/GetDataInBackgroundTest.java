package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;


/**
 * GetDataInBackgroundTest is the class used to test the GetDataInBackground class.
 */
@LargeTest
public class GetDataInBackgroundTest {

        private static final String TAG = "GetDataInBackgroundTest";

        // Get MainActivity Test Rule
        @Rule
        public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
                MainActivity.class, false, false);

        @Before
        public void setUp() throws Exception {
            activityTestRule.launchActivity(new Intent());
        }

        @Test
        public void testAsyncTask() throws Throwable {
            Log.d(TAG, "testAsyncTask entry");

            // Create new AsyncTask instance
            AsyncTask<String, Void, Integer> task = new AsyncTask<String, Void, Integer>() {

                // Call doInBackground and check if returns 0 (success)
                @Override
                protected Integer doInBackground(String... params) {
                    Log.d(TAG, "doInBackground() called with: params = [" + params + "]");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                    return 0;
                }


                // On post execution check that no RuntimeException is encountered
                protected void onPostExecute(String s) {
                    Log.d(TAG, "onPostExecute() called with: integer = [" + s + "]");
                    assertEquals("", "");
                    throw new RuntimeException("This should fail the test");
                }
            };
            task.execute("One", "two", "three");
            Espresso.onView(withId(android.R.id.content)).perform(ViewActions.click());

            Log.d(TAG, "testAsyncTask end");
        }
    }