package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 16.08.14.
 */
public class glosnosc implements filtr {

    public ArrayList<Typ> wejście = new ArrayList<Typ>();
    private float głośność;

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


    public glosnosc() {
        wyjście[0] = new Typ();
        wyjście[1] = new Typ();
        ustawienia = new Hashtable<String, String>();
        akt();
    }

    public long symuluj(long p)
    {
        return wyjście[0].DrógiModół.symuluj(p);
    }
    public void akt() {

        głośność = Float.parseFloat(ustawienia.get("głośność"));
    }

    public void działaj(nuta input)
    {

        // bool ucinanie;
       /* if (ustawienia["ucinanie"] == "true")//uważać, czy nie jest po pogłosie
        {
            var ucinanieWartość = float.Parse(ustawienia["ucinanieWartość"], CultureInfo.InvariantCulture);
            var ucinanieWartośćMinus = -ucinanieWartość;
            var ucinaniePomnożone = ucinanieWartość * głośność;
            var ucinaniePomnożoneMinus = -ucinaniePomnożone;
            for (var i = 0; i < input.dane.Length; i++)
            {
                if (ucinaniePomnożone <= input.dane[i])
                    input.dane[i] = ucinanieWartość;
                else if (ucinaniePomnożoneMinus >= input.dane[i])
                    input.dane[i] = ucinanieWartośćMinus;
                else
                    input.dane[i] = input.dane[i] * głośność;
            }
        }
        else*/ if (głośność != 1)
        {
            if (wyjście[0].DrógiModół.getClass() == granie.class)
            {
                input.głośność = input.głośność * głośność;
                    /*for (var i = 0; i < input.dane.Length; i++)
                    {
                        input.dane[i] = input.dane[i];
                    }*/
            }
            else
            //input.głośność = input.głośność * 0.99f;
            {
                for (int i = 0; i < input.dane.length; i++)
                {
                    input.dane[i] = input.dane[i] * głośność;
                }
            }
        }
        wyjście[0].DrógiModół.działaj(input);
    }
    public nuta działaj(nuta input, float[] jak)
    {
        int iJak = input.generujOd;
        for (int i = 0; i < input.dane.length; i++)
        {
            input.dane[i] = input.dane[i] * głośność * jak[(iJak + i) % jak.length];

        }

        return input;
    }
    @Override public String toString(){
        return "Głośność";
    }
}
