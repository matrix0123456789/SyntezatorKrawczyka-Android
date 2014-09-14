package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 2014-09-13.
 */
public class zmianaWysokości implements moduł {

    public ArrayList<Typ> wejście = new ArrayList<Typ>();

    private float tony;
    private float oktawy;

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


    public zmianaWysokości() {
        wyjście[0] = new Typ();
        wyjście[1] = new Typ();
        ustawienia = new Hashtable<String, String>();
        ustawienia.put("oktawy", "0");
        ustawienia.put("tony", "0");
        ustawienia.put("czestotliwosc", "0");
        akt();
    }
    public void działaj(nuta o)
    {
        o.ilepróbek = o.ilepróbek / Math.pow(2, oktawy + (tony / 6));

        wyjście[0].DrógiModół.działaj(o);
    }
    public void akt() {

        oktawy = Float.parseFloat(ustawienia.get("oktawy"));
        tony = Float.parseFloat(ustawienia.get("tony"));
    }

    @Override
    public Class UI() {
        return null;
    }

    public long symuluj(long p)
    {
        return wyjście[0].DrógiModół.symuluj(p);
    }

    @Override
    public String toString() {
        return "Zmiana Wysokości";
    }
}
