package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 16.08.14.
 */
public class sekwencer implements moduł, soundStart {
    public void akt() {
    }

    public ArrayList<Typ> wejście = new ArrayList<Typ>();

    public ArrayList<Typ> getWejście() {
        return wejście;
    }

    public Typ[] getWyjście() {
        return wyjście;
    }

    Typ[] wyjście = new Typ[1];

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

    public sekwencer() {
        wyjście[0] = new Typ();
        ustawienia.put("oktawy", "0");
    }
        /*[DllImport("HelloWorldLib", CallingConvention = CallingConvention.StdCall)]
        extern unsafe static void generuj_nutę(float[][] wej, int pozycja, float[] fala);
        [DllImport("HelloWorldLib", CallingConvention = CallingConvention.StdCall)]
        extern unsafe static float suma(float* a, float* b);
        [DllImport("HelloWorldLib", CallingConvention = CallingConvention.StdCall)]
        extern unsafe static float sumab(float a, float b);*/

    public /*unsafe*/ void działaj(nuta o) {
        if (wyjście[0].DrógiModół != null) {

            //else
            {
                float oktawy = Float.parseFloat(ustawienia.get("oktawy"));
                o.ilepróbek = o.ilepróbek / Math.pow(2, oktawy);
                o.głośność = o.balans0 = o.balans1 = 1;

                wyjście[0].DrógiModół.działaj(o);
            }
        }
    }

    public long symuluj(long p) {
        return wyjście[0].DrógiModół.symuluj(p);
    }

    public boolean czyWłączone()

    {
        if (wyjście[0].DrógiModół == null)
            return false;
        else
            return true;
    }

    @Override
    public String toString() {
        return "Sekwencer";
    }

    @Override
    public Class UI() {
        return oscylatorUI.class;
    }
}
