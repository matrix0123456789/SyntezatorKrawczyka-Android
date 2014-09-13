package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 2014-09-13.
 */
public class rozdzielacz implements  moduł {public ArrayList<Typ> wejście = new ArrayList<Typ>();

    private float tony;
    private float oktawy;

    public ArrayList<Typ> getWejście() {
        return wejście;
    }

    public Typ[] getWyjście() {
        return wyjście;
    }

    Typ[] wyjście = new Typ[8];

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


    public rozdzielacz() {
        wyjście[0] = new Typ();
        wyjście[1] = new Typ();
        wyjście[2] = new Typ();
        wyjście[3] = new Typ();
        wyjście[4] = new Typ();
        wyjście[5] = new Typ();
        wyjście[6] = new Typ();
        wyjście[7] = new Typ();
        ustawienia = new Hashtable<String, String>();
    }
    public void akt() {

    }
    Hashtable<moduł, ArrayList<Typ>> flangery=null;
    public void aktt()
    {flangery = new Hashtable<moduł, ArrayList<Typ>>();
        for (int i = 0; i < 8; i++)
        {
            if (wyjście[i].DrógiModół != null)
            {
                if (wyjście[i].DrógiModół.getClass() == flanger.class)
                {
                    if (flangery.containsKey(wyjście[i].DrógiModół.getWyjście()[0].DrógiModół))
                    {
                        flangery.get(wyjście[i].DrógiModół.getWyjście()[0].DrógiModół).add(wyjście[i]);
                    }
                    else
                    {
                        ArrayList lista = new ArrayList<Typ>();
                        lista.add(wyjście[i]);
                        flangery.put(wyjście[i].DrógiModół.getWyjście()[0].DrógiModół, lista);
                    }

                }

            }
        }
    }
    public long symuluj(long wej)
    {
        long ret = 0;
        for (int i = 0; i < 8; i++)
        {
            if (wyjście[i].DrógiModół != null)
            {
                long t = wyjście[i].DrógiModół.symuluj(wej);
                if (t > ret)
                    ret = t;
            }
        }
        return ret;
    }

    public void działaj(nuta input)
    {

        {
            if (flangery == null)
                aktt();
            for(int i=0;i<flangery.size();i++)
            {
                moduł key=flangery.keys().nextElement();
                float[] dane = new float[input.dane.length];
                for(int i2=0;i2<flangery.get(key).size();i2++)
                {
                   dane= ((flanger)flangery.get(key).get(i2).DrógiModół).działaj(input, dane);
                }
            }
            for (int i = 7; i >= 0; i--)
            {
                if (wyjście[i].DrógiModół != null)
                {
                    if (wyjście[i].DrógiModół.getClass() != flanger.class)
                    {
                        if (i == 0)
                            wyjście[i].DrógiModół.działaj(input);
                        else
                        {
                            nuta klon = null;
                            try {
                                klon = (input).Clone();

                            klon.id = klon.id * 0x1000 + i;
                            //klon.dane = new double[klon.dane.Length];
                                /*for (var ei = 0; ei < klon.dane.Length; ei++)
                                {
                                    klon.dane[ei] = input.dane[ei];
                                }*/
                            wyjście[i].DrógiModół.działaj(klon);
                            //refer[i] = klon;
                            } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        }
                    }
                }
            }
        }}
}
