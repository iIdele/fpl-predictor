package dcu.ie.stefano.puzzuoli2.fplpredictor;

import android.app.Application;

import com.backendless.Backendless;

/**
 * BackendApplication is the main entity we'll be using to authenticate application from Backendless.
 */
public class BackendApplication extends Application
{
    public static final String APPLICATION_ID = "4F0F517F-05A7-2291-FF5D-56F1DBCAB300";
    public static final String API_KEY = "F6B8F48E-B772-4C0B-8D64-755062A052C8";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();

        // Set Server URL
        Backendless.setUrl(SERVER_URL);
        // Set App ID and API Key
        Backendless.initApp(getApplicationContext(), APPLICATION_ID, API_KEY);
    }

}
