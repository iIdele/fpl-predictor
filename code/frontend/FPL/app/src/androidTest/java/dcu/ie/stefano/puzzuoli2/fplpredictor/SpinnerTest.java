package dcu.ie.stefano.puzzuoli2.fplpredictor;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * SpinnerTest is the class used to test the SortSpinner class.
 */
public class SpinnerTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    // Different sorting options
    private static final String sort1 = "Predictions (Descending)";
    private static final String sort2 = "Predictions (Ascending)";
    private static final String sort3 = "Player Name (Ascending)";
    private static final String sort4 = "Player Name (Descending)";

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    public SpinnerTest(){

    }

    // In a randomly generated order test all sorting options one at a time
    @Test
    public void testSpinnerItemsExist() {
        onView(withId(R.id.sortSpinner)).perform(click());
        int i = getRandomNumberInRange(1,4);
        if (i == 1)
            onData(allOf(is(instanceOf(String.class)),is(sort1))).perform(click());
        else if (i == 2)
            onData(allOf(is(instanceOf(String.class)),is(sort2))).perform(click());
        else if (i == 3)
            onData(allOf(is(instanceOf(String.class)),is(sort3))).perform(click());
        else if (i == 4)
            onData(allOf(is(instanceOf(String.class)),is(sort4))).perform(click());
    }

    // Generate random integer used to randomly pick sorting option
    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}
