package com.jaebe.syntezatorkrawczyka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends Activity {
    short ilePrzyciskówKolumn = 4;
    short ilePrzyciskówWierszy = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);

        } catch (Throwable e) {
        }
        setContentView(R.layout.activity_main);

        try {
            rysujPrzyciski();
        } catch (Throwable e) {
        }
        ((Button) findViewById(R.id.edycjaDzwiekuOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TableLayout) findViewById(R.id.edycjaDzwieku)).setVisibility(View.INVISIBLE);
            }
        });

    }
int edytowanyDzwiek=0;
    void rysujPrzyciski() {
        TableLayout przyciskiGrid = ((TableLayout) findViewById(R.id.PrzyciskiGrid));
        przyciskiGrid.removeAllViews();
        int szerokość = przyciskiGrid.getWidth() / ilePrzyciskówKolumn;
        int wysokość = przyciskiGrid.getHeight() / ilePrzyciskówWierszy;
        for (short i = 0; i < ilePrzyciskówWierszy; i++) {
            TableRow wiersz = new TableRow(this);
            for (short j = 0; j < ilePrzyciskówKolumn; j++) {
                Button przyciskRaz = new Button(this);
                przyciskRaz.setWidth(szerokość);
                przyciskRaz.setHeight(wysokość);
                przyciskRaz.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (edytujDźwięk && event.getAction() == MotionEvent.ACTION_DOWN) {
                            ((TableLayout) findViewById(R.id.edycjaDzwieku)).setVisibility(View.VISIBLE);
                            edytowanyDzwiek=(Integer)v.getTag();
                            ListView lista= ((ListView) findViewById(R.id.edycjaDzwiekuLista));
                            for(sound s : Statyczne.otwartyplik.moduły.values())
                            {
                                TextView txt=new TextView(getBaseContext());
                                txt.setText(s.nazwa);
                                lista.addView(txt);
                               /* ListMenuItemView ch
                                lista.addView(ch);*/
                            }

                        } else if (event.getAction() == MotionEvent.ACTION_DOWN && Statyczne.otwartyplik.DrumLista.size() > ((Integer) v.getTag())) {
                            DrumJeden jeden = Statyczne.otwartyplik.DrumLista.get((Integer) v.getTag());
                            if (jeden.sekw != null) {

                                if (jeden.sekw.czyWłączone()) {
                                    short oktawa = 0;

                                    if (jeden.nuta == null) {
                                        jeden.nuta = new nuta();
                                        synchronized ( klawiaturaKomputera.wszytskieNuty){
                                        klawiaturaKomputera.wszytskieNuty.add(jeden.nuta);}
                                    }
                                    jeden.nuta.ilepróbek = jeden.nuta.ilepróbekNaStarcie = plik.Hz / funkcje.częstotliwość((short) 0, jeden.wysokość / 2f);
                                    jeden.nuta.długość = Integer.MAX_VALUE / 16;
                                    jeden.nuta.sekw = jeden.sekw;
                                }
                            }
                        } else if (event.getAction() == MotionEvent.ACTION_UP && Statyczne.otwartyplik.DrumLista.size() > ((Integer) v.getTag())) {
                            DrumJeden jeden = Statyczne.otwartyplik.DrumLista.get((Integer) v.getTag());
                            if (jeden.nuta != null) {
                                jeden.nuta.długość = (int) ((System.currentTimeMillis() - jeden.nuta.start.getTime()) * plik.kHz);
                                jeden.nuta = null;
                            }
                        }
                        return false;
                    }
                });
                przyciskRaz.setTag((Integer) (i * ilePrzyciskówKolumn + j));
                wiersz.addView(przyciskRaz);
            }
            przyciskiGrid.addView(wiersz);
        }
    }

    public void ustawWymiary(View ac) {

        ilePrzyciskówKolumn = (short) ((int) Integer.parseInt(((EditText) findViewById(R.id.wymiaryKolumny)).getText().toString()));
        ilePrzyciskówWierszy = (short) ((int) Integer.parseInt(((EditText) findViewById(R.id.wymiaryWiersze)).getText().toString()));

        ((TableLayout) findViewById(R.id.wymiary)).setVisibility(View.INVISIBLE);
        rysujPrzyciski();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    boolean edytujDźwięk = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_wymiary: {
                ((TableLayout) findViewById(R.id.wymiary)).setVisibility(View.VISIBLE);
                ((EditText) findViewById(R.id.wymiaryKolumny)).setText(((Integer) (int) ilePrzyciskówKolumn).toString());
                ((EditText) findViewById(R.id.wymiaryWiersze)).setText(((Integer) (int) ilePrzyciskówWierszy).toString());

            }
            return true;
            case R.id.action_edytujDzwieki: {
                edytujDźwięk = !edytujDźwięk;
                return true;
            }

            case R.id.action_OtworzPlik: {

                return true;
            }
            case R.id.action_Logowanie: {
                Intent startAnotherActivity = new Intent(getApplicationContext(), Logowanie.class);
                startActivity(startAnotherActivity);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
