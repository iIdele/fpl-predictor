package dcu.ie.stefano.puzzuoli2.fplpredictor;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * SortSpinnerTest is the class used to test the SortSpinner class.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SortSpinnerTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    public SortSpinnerTest() {
    }

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    // Check that sort by textview is clickable
    @Test
    public void buttonIsClickable() {
        onView(withId(R.id.tvSortBy)).check(matches(isClickable()));
    }

    // Check that sort by textview is enabled
    @Test
    public void buttonIsEnabled() {
        onView(withId(R.id. tvSortBy)).check(matches(isEnabled()));
    }

    // Check that sort by textview is displayed
    @Test
    public void buttonIsDisplayed() {
        onView(withId(R.id. tvSortBy)).check(matches(isDisplayed()));
    }

    // Check that sort by textview is not cut-off
    @Test
    public void buttonIsCompletelyDisplayed() {
        onView(withId(R.id. tvSortBy)).check(matches(isCompletelyDisplayed()));
    }

    // Check that sort by textview has correct text
    @Test
    public void buttonWithText() {
        onView(withId(R.id.tvSortBy)).check(matches(withText("Sort By")));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}

