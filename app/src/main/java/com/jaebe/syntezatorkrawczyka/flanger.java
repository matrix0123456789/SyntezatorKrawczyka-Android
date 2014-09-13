package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 2014-09-13.
 */
public class flanger implements moduł {
    public ArrayList<Typ> wejście = new ArrayList<Typ>();
    float przesunięciea = 0, czestotliwosc = 0;


    public ArrayList<Typ> getWejście() {
        return wejście;
    }

    public Typ[] getWyjście() {
        return wyjście;
    }

    Typ[] wyjście = new Typ[2];

    public Hashtable<String, String> getUstawienia() {
        return ustawienia;
    }

    Hashtable<String, String> ustawienia = new Hashtable<String, String>();

    Node XML;

    public void setXML(Node x) {
        XML = x;
    }

    public Node getXML() {
        return XML;
    }


    public flanger() {
        wyjście[0] = new Typ();
        ustawienia = new Hashtable<String, String>();
    }

    public void akt() {

        czestotliwosc = Float.parseFloat(ustawienia.get("czestotliwosc"));
        przesunięciea = Float.parseFloat(ustawienia.get("przesuniecie"));
    }

    public long symuluj(long p) {
        return wyjście[0].DrógiModół.symuluj(p);
    }

    public float[] działaj(nuta input, float[] dane) {

        if (przesunięciea == 0 || czestotliwosc == 0) {
            for (int i = 0; i < dane.length; i++)
                dane[i] += input.dane[i];
            return dane;
        } else {
            float przesunięcie = przesunięciea * plik.kHz;
            double ileNaCykl = 1 / czestotliwosc * plik.Hz / Math.PI / 2;
            int losIGenerujOd = input.los + input.generujOd;
            double z;
            for (int i = 0; i < dane.length; i++) {

                z = przesunięcie * Math.sin((i + losIGenerujOd) / ileNaCykl);
                int x = i + (int) Math.floor(z);


                double proporcje = z - Math.floor(z);
                if (input.dane.length > x + 1 && x >= 0)
                    dane[i] = ((float) (input.dane[x] * (1 - proporcje) + input.dane[x + 1] * proporcje) / 2) + dane[i];
                    /* else { } if (i > 2000)
                         if (dane[i] == 0 && dane[i - 1] == 0)
                         { }*/


            }
            return dane;
        }
    }

    public void działaj(nuta input) {

        if (przesunięciea == 0 || czestotliwosc == 0) {
            if (wyjście[0].DrógiModół != null) {
                wyjście[0].DrógiModół.działaj(input);
            }
        } else {
            float przesunięcie = przesunięciea * plik.kHz;
            float ileNaCykl = 1 / czestotliwosc * plik.Hz;
            float[] noweDane = new float[input.dane.length];
            int losIGenerujOd = input.los + input.generujOd;
            double z;
            for (int i = 0; i < input.dane.length; i++) {
                float zx = (i + losIGenerujOd) / ileNaCykl;

                z = przesunięcie * Math.sin((i + losIGenerujOd) / ileNaCykl);
                int x = i + (int) Math.floor(z);


                double proporcje = z - Math.floor(z);
                if (input.dane.length > x + 1 && x >= 0)
                    noweDane[i] = ((float) (input.dane[x] * (1 - proporcje) + input.dane[x + 1] * proporcje) / 2);
                //noweDane[i] = z / 500;
                    /*else { }
                    if(i>0)
                        if (noweDane[i] == 0 && noweDane[i - 1] == 0)
                        { }*/
            }
            input.dane = noweDane;
            if (wyjście[0].DrógiModół != null) {
                wyjście[0].DrógiModół.działaj(input);
            }
        }
    }
}
