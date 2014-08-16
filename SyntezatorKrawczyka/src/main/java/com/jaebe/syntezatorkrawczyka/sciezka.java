package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class sciezka implements wejście, IodDo {
    /// <summary>
/// Lista nut
/// </summary>
    public ArrayList<nuta> nuty = new ArrayList<nuta>();
    /// <summary>
/// sekwencer, do którego zostaną wysłane nuty
/// </summary>
//public soundStart sekw;
       /* UIElement _UI = null;//TODO interfejs
public UIElement UI
        {
        get
        {
        if (_UI == null)
        _UI = new sciezkaUI(this);
        return _UI;
        }
        }*/
/// <summary>
/// nazwa
/// </summary>
    public String nazwa = "nazwa";

    /// <summary>
/// XML przedstawiający ścierzkę
/// </summary>
    public Node xml;
    /// <summary>
/// Czy ścieżka jest kopią innej ścierzki
/// </summary>
    public boolean kopia = false;
    soundStart sekw;

    public soundStart getSekw() {
        return sekw;
    }

    public sciezka() {
    }

    public sciezka(String Nazwa, Node xml) {
        nazwa = Nazwa;
        this.xml = xml;
    }

    public sciezka(String Nazwa, Node xml, boolean kopia) {

        this.xml = xml;
        if (kopia && (Nazwa.length() < 8 || Nazwa.substring(Nazwa.length() - 8) != " (kopia)"))
            nazwa = Nazwa + " (kopia)";
        else
            nazwa = Nazwa;
        this.kopia = kopia;
    }

    /// <summary>
/// przesyła nuty do sekwencera
/// </summary>
    public void działaj() {
        synchronized (granie.grają) {
            granie.liczbaGenerowanychMax += nuty.size();
            granie.liczbaGenerowanych += nuty.size();

            for (int i = 0; i < nuty.size(); i++) {
                try {
                    nuta prz = nuty.get(i);
                    nuta tabl = (nuta) prz.Clone();
                    tabl.grajDo = Integer.MAX_VALUE;
                    //System.Threading.ThreadPool.QueueUserWorkItem((o) =>
                    //{
                    wątek1 wątek = new wątek1(tabl);
                    wątek.start();

                    //}, tabl);
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private Integer _delay = null;

    public int getDelay() {
        if (_delay == null)
            _delay = 0;
        return (int) _delay;
    }

    public void setDelay(int value) {
        if (_delay == null)
            _delay = value;
        else if (value != _delay) {
            long roznica = (value - (long) _delay);
            for (int i = 0; i < nuty.size(); i++) {
                nuty.get(i).opuznienie += roznica;
            }
            _delay = value;
        }
    }


    public int CompareTo(sciezka other) {
        if (getDelay() - other.getDelay() > 0)
            return 1;
        else
            return -1;
    }

    public sciezka oryginał;

    public int getDlugosc()


    {
        int akt = 0;
        for (int i = 0; i < nuty.size(); i++) {
            if (nuty.get(i).opuznienie + nuty.get(i).ilepróbekNaStarcie > akt)
                akt = nuty.get(i).opuznienie + nuty.get(i).długość;
        }
        return akt - getDelay();
    }

    class wątek1 extends Thread {
        public wątek1(nuta t) {
            tablWątek = t;
        }

        @Override
        public void run() {
            {
                if (sekw != null)

                    sekw.działaj(tablWątek);
                synchronized (granie.liczbaGenerowanychBlokada) {
                    granie.liczbaGenerowanych--;
                    if (!granie.można && granie.liczbaGenerowanych == 0)

                        granie.grajcale(false);
                }
            }
        }

        public nuta tablWątek;
    }
}
