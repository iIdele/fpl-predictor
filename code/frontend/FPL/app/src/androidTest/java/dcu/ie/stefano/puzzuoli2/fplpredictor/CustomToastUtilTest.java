package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * CustomToastUtilTest is the class used to test the CustomToastUtil class.
 */
public class CustomToastUtilTest {

    // Get MainActivity Test Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    // Set up expected messages
    private static final String expectedMessage1 = "Test message 1";
    private static final String expectedMessage2 = "Test message 2";


    // Test showToast method
    @Test
    public void showToast() {
        mActivity = mActivityTestRule.getActivity();

        // Get Context of MainActivity
        Context context = (Context) mActivity;
        // Get MainActivity Activity
        Activity activity = (Activity) context;

        // Create new CustomToastUtil to test
        final CustomToastUtil customToastUtil = new CustomToastUtil(context, activity);

        // Get TextView associated with customToastText and get its text
        TextView customToastText = mActivity.findViewById(R.id.customToastText);
        final String actualMessage = customToastText.getText().toString();

        Looper.prepare();

        // Check if after method executes expected messages match actual messages
        Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                customToastUtil.showToast(expectedMessage1);
                assertEquals(expectedMessage1,actualMessage);

                customToastUtil.showToast(expectedMessage2);
                assertEquals(expectedMessage2, actualMessage);
            }
        };


    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}