package com.jaebe.syntezatorkrawczyka;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class EdycjaInstrumentu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edycja_instrumentu);
        instr=(sound)savedInstanceState.get("s");
        TableLayout lista= ((TableLayout) findViewById(R.id.edycjaInstrLista));
        for(moduł x : instr.values())
        {
            TableRow tr=new TableRow(getBaseContext());
            TextView txt=new TextView(getBaseContext());
            txt.setText(x.toString());
            tr.addView(txt);
            lista.addView(tr);
        }
    }
sound instr;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edycja_instrumentu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
