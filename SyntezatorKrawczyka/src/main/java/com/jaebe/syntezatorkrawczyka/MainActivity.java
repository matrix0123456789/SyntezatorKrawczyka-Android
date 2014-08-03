package com.jaebe.syntezatorkrawczyka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;


public class MainActivity extends ActionBarActivity {
    short ilePrzyciskówKolumn=4;
    short ilePrzyciskówWierszy=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{

            super.onCreate(savedInstanceState);
        }catch(Throwable e){}
        setContentView(R.layout.activity_main);

        try{
            rysujPrzyciski();
        }
        catch(Throwable e){}

    }

    void rysujPrzyciski(){
        TableLayout przyciskiGrid=((TableLayout)  findViewById(R.id.PrzyciskiGrid));
        przyciskiGrid.removeAllViews();
        int szerokość=przyciskiGrid.getWidth()/ilePrzyciskówKolumn;
        int wysokość=przyciskiGrid.getHeight()/ilePrzyciskówWierszy;
        for(short i=0;i<ilePrzyciskówWierszy;i++)
        {
            TableRow wiersz=new TableRow(this);
            for(short j=0;j<ilePrzyciskówKolumn;j++){
                Button przyciskRaz=new Button(this);
                przyciskRaz.setWidth(szerokość);
                przyciskRaz.setHeight(wysokość);
                przyciskRaz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edytujDźwięk)
                        {

                        }
                    }
                });
                wiersz.setTag((Integer)(i*ilePrzyciskówKolumn+j));
                wiersz.addView(przyciskRaz);
            }
            przyciskiGrid.addView(wiersz);
        }
    }
    public void ustawWymiary(Activity ac){

        ilePrzyciskówKolumn=(short)((int)Integer.parseInt(((EditText)  findViewById(R.id.wymiaryKolumny)).getText().toString()));
        ilePrzyciskówWierszy=(short)((int)Integer.parseInt(((EditText)  findViewById(R.id.wymiaryWiersze)).getText().toString()));

        ((TableLayout)  findViewById(R.id.wymiary)).setVisibility(View.INVISIBLE);
        rysujPrzyciski();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    boolean edytujDźwięk=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_wymiary:
            {
                ((TableLayout)  findViewById(R.id.wymiary)).setVisibility(View.VISIBLE);
                ((EditText)  findViewById(R.id.wymiaryKolumny)).setText(((Integer)(int)ilePrzyciskówKolumn).toString());
                ((EditText) findViewById(R.id.wymiaryWiersze)).setText(((Integer)(int)ilePrzyciskówWierszy).toString());

            }
            return true;
            case R.id.action_edytujDzwieki:{
                edytujDźwięk=!edytujDźwięk;
                return true;
            }

            case R.id.action_OtworzPlik:{

                return true;
            }
            case R.id.action_Logowanie:{
                Intent startAnotherActivity = new Intent(getApplicationContext(), Logowanie.class);
                startActivity(startAnotherActivity);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
