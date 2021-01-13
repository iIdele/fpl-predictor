package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MainActivityTest is the class used to test the MainActivity class.
 */
public class MainActivityTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        // Check Sort by spinner
        Spinner mySpinner = mActivity.findViewById(R.id.sortSpinner);
        assertNotNull(mySpinner);
        // Check FPLPP Heading
        TextView FPLPPHeading = mActivity.findViewById(R.id.FPLPPHeading);
        assertNotNull(FPLPPHeading);
        // Check Search View
        SearchView searchView = mActivity.findViewById(R.id.searchView);
        assertNotNull(searchView);


    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
