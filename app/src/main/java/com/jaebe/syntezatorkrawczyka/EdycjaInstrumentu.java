package com.jaebe.syntezatorkrawczyka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Enumeration;


public class EdycjaInstrumentu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edycja_instrumentu);

            instr=Statyczne.otwartyplik.moduły.get(getIntent().getStringExtra("s"));

        TableLayout lista= ((TableLayout) findViewById(R.id.edycjaInstrLista));
        for(moduł x : instr.values())
        {
            if (x.toString().length() == 0)
                continue;
            TableRow tr=new TableRow(getBaseContext());
            TextView txt=new TextView(getBaseContext());
            txt.setText(x.toString());
            txt.setMinHeight(35);
            tr.addView(txt);
            tr.setTag(x);
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class c = ((moduł) v.getTag()).UI();
                    Intent startAnotherActivity = new Intent(getApplicationContext(), c);
                    startAnotherActivity.putExtra("s", getIntent().getStringExtra("s"));
                    Enumeration<String> klucze = instr.keys();
                    while (true) {
                        String k = klucze.nextElement();
                        if (k == null)
                            break;
                        if (instr.get(k) == v.getTag()) {

                            startAnotherActivity.putExtra("m", k);
                            break;
                        }
                    }
                    startActivity(startAnotherActivity);
                }
            });
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
