package com.jaebe.syntezatorkrawczyka;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class Logowanie extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.id.login_form);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logowanie, menu);
        return true;
    }

}
