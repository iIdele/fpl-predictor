package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CustomToastUtil is the main entity we'll be using to create and display custom Toasts (with dark custom_toast_background).
 */
public class CustomToastUtil {

    private Context context;            // To store Context from activity to display on
    private Activity activity;          // To store  Activity to display on

    CustomToastUtil(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    // Display custom Toast (dark custom_toast_background)
    public void showToast(String message)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) this.activity.findViewById(R.id.custom_toast_container));

        Toast toast = new Toast(this.activity.getApplicationContext());

        // Get TextView associated with custom Toast
        TextView text = layout.findViewById(R.id.customToastText);
        // Set text and toast attributes
        text.setText(message);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 95);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
