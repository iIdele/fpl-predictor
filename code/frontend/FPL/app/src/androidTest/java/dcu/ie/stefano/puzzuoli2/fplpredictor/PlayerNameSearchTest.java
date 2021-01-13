package dcu.ie.stefano.puzzuoli2.fplpredictor;

import androidx.test.espresso.assertion.LayoutAssertions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * PlayerNameSearchTest is the class used to test the PlayerNameSearch class.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class PlayerNameSearchTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    public PlayerNameSearchTest() {
    }

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    // Test that item found is the same as item searched
    @Test
    public void isCorrect() {
        // Check if string in textview remains correct after search is clicked
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.FPLPPHeading)).check(matches(withText("FPL Point Predictor")));
    }

    @Test
    public void notEllipsized() {

        // Check that textview is not cut-off by search expanding
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.FPLPPHeading)).check(LayoutAssertions.noEllipsizedText());
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}

