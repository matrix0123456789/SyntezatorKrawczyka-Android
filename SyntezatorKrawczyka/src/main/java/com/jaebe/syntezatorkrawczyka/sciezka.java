package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Mateusz on 08.08.14.
 */
interface IodDo
{
    public long getDelay();
    public long getDlugosc();

}
interface wejście
{
   // UIElement UI{get;}
   public soundStart sekw=null;
   public void działaj();
}
public class sciezka implements wejście, IodDo
        {
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
public sciezka()
        {
        }
public sciezka(String Nazwa, Node xml)
        {
        nazwa = Nazwa;
        this.xml = xml;
        }
public sciezka(String Nazwa, Node xml, boolean kopia)
        {

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
public void działaj()
        {
        synchronized (granie.grają)
        {
        granie.liczbaGenerowanychMax += nuty.Count;
        granie.liczbaGenerowanych += nuty.Count;
        foreach (var prz in nuty)
        {
        var tabl = (nuta)prz.Clone();
        tabl.grajDo = long.MaxValue;
        System.Threading.ThreadPool.QueueUserWorkItem((o) =>
        {
        if (sekw != null)

        sekw.działaj(tabl);
        lock (granie.liczbaGenerowanychBlokada)
        {
        granie.liczbaGenerowanych--;
        if (!granie.można && granie.liczbaGenerowanych == 0)

        granie.grajcale(false);
        }
        }, tabl);
        }
        }
        }

private Long _delay = null;
public long getDelay()
        {
        if (_delay == null)
        _delay = 0L;
        return (long)_delay;
        }

       public void setDelay(long value)
        {
        if (_delay == null)
        _delay = value;
        else if (value != _delay)
        {
        long roznica = (value - (long)_delay);
                for(int i=0;i<nuty.size();i++)
        {
        nuty.get(i).opuznienie += roznica;
        }
        _delay = value;
        }
        }


public int CompareTo(sciezka other)
        {
        if (delay - other.delay > 0)
        return 1;
        else
        return -1;
        }

public sciezka oryginał;
public long getdlugosc()


        {
        long akt = 0;
            for(int i=0;i<nuty.size();i++)
        {
        if (nuty.get(i).opuznienie + nuty.get(i).ilepróbekNaStarcie > akt)
        akt = nuty.get(i).opuznienie + nuty.get(i).długość;
        }
        return akt-delay;
        }
        }
        }