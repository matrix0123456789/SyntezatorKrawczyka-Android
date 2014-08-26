package com.jaebe.syntezatorkrawczyka;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class Logowanie extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.logowanie);
        ((Button)findViewById(R.id.sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Statyczne.serwer.loguj(((EditText)findViewById(R.id.email)).getText().toString(), ((EditText)findViewById(R.id.password)).getText().toString()).equals("ok"))
                {
                    ((View)findViewById(R.id.logowanie)).setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logowanie, menu);
        return true;
    }

}
