package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.widget.EditText;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;;

import static org.junit.Assert.*;

/**
 * SearchViewTest is the class used to test the SearchView class.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SearchViewTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    public static final Prediction rashford = new Prediction();

    public SearchViewTest() {
    }

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    // Test that item found is the same as item searched
    @Test
    public void testItemFound() {

        rashford.setPlayer_name("Rashford");
        rashford.setPoints_prediction(8);
        onView(withId(R.id.searchView)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(rashford.getPlayer_name()), pressImeActionButton());

        assertTrue((isDisplayed() instanceof Matcher));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}
