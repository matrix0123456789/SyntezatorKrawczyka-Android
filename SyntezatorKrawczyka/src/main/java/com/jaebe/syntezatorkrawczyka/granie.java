package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Mateusz on 13.08.14.
 */
public class granie implements moduł
        {
public static boolean[] generować = { true };
public Node _XML;
            public Node getXML(){return _XML;}
            public void setXML(Node n){_XML=n;}
static public int o = 4410;
static Date sw = new Date();
public static Hashtable<Long, gra> grają = new Hashtable<Long, gra>();
static boolean jest = false;
static public float[][] wynik = null;
static public int graniePrzy = 0, granieMax = 0, granieI = 0;
static public boolean graniePlay = false;
static public nuta[] granieNuty;

public void akt() { }
public long symuluj(long wej)
        {
        return wej;
        }
public static boolean grateraz = false;
        Hashtable<String, String> _ustawienia;
static float[] pustaTablica = new float[0];
public static void grajcale(boolean graj)
        {
        grateraz = false;
        long oz = 0;
        gra[] zz;
        float[][] fala;
        if (wynik == null)
        {
        synchronized (grają)
        {
        zz = (gra[])grają.values().toArray();
        } for (gra dospr : zz)
        {
        if (dospr != null)
        {
        dospr.zagrano = -dospr.nuta.opuznienie;
        if (dospr.dźwięk.length - dospr.zagrano > oz)
        oz = dospr.dźwięk.length - dospr.zagrano;
        }
        }
        fala = new float[2][(int)oz];
        for (int x = 0; x < zz.length; x++)
        {

        int i = 0;
        if (zz[x].zagrano < 0 && -zz[x].zagrano < oz)
        i = (int)-zz[x].zagrano;
        else if (zz[x].zagrano < 0)
        i = o;
        long max;
        if (oz < zz[x].dźwięk.length - zz[x].zagrano)
        max = oz;
        else
        max = zz[x].dźwięk.length - zz[x].zagrano;
        if (zz[x].nuta.głośność == 1 && zz[x].nuta.balans0 == 1 && zz[x].nuta.balans1 == 1)
        {
        for (; i < max; i++)
        {


        fala[0][i] += zz[x].dźwięk[i + zz[x].zagrano];
        fala[1][i] += zz[x].dźwięk[i + zz[x].zagrano];

        }
        }
        else
        {
        float mn0 = zz[x].nuta.głośność * zz[x].nuta.balans0;
            float mn1 = zz[x].nuta.głośność * zz[x].nuta.balans1;
        for (; i < oz && i + zz[x].zagrano < zz[x].dźwięk.length; i++)
        {


        fala[0][i] += zz[x].dźwięk[i + zz[x].zagrano] * mn0;
        fala[1][i] += zz[x].dźwięk[i + zz[x].zagrano] * mn1;

        }
        }
        zz[x].zagrano += oz;
        if (zz[x].zagrano >= zz[x].dźwięk.length)
        zz[x].nuta.dane = zz[x].dźwięk = null;

        }
        }
        else
        {
        fala = wynik;
        wynik = null;
        }

        //System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        //{
            /*try
            {
                if (graj)
                    funkcje.graj(fala, double.Parse(ustawienia["głośność"]));
                else
                    funkcje.zapisz(fala, double.Parse(ustawienia["głośność"]), "C:\\Users\\Mateusz\\Desktop\\xml\\a.wav");
            }
            catch (FormatException a)
            {*/
        grają.clear();
        if (graj)
        funkcje.graj(fala);
        else
        {
        while (PlikDoZapisu == null)
        {
        try{Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
      /*  if (!PlikDoZapisu.equals(""))
        funkcje.zapisz(fala, PlikDoZapisu);*///TODO eksportowanie
        }
        można = true;
        //}
        //});
        }
static Date data;
public static int liczbaGenerowanych = 0;
public static int liczbaGenerowanychMax = 0;
public static Object liczbaGenerowanychBlokada = new Object();
public static Object obLock = new Object();
public static boolean można = true;
public granie()
        {
        graniestart();
        _ustawienia.put("głośność", "1.0");
        }
static public void graniestart()
        {
        if (!jest)
        {
        // MainWindow.WasapiWyjście.Stop();
        //new System.Threading.Timer((object ozzz) => { MainWindow.WasapiWyjście.Play(); }, null,100,0);
        //MainWindow.bufor.AddSamples(new byte[10000], 0, 10000);
        jest = true;
        //object[] wejs = new object[2];
        //wejs[0] = _ustawienia;
        //wejs[1] = grają;

        data = new Date();

        //System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        t = new System.Threading.Timer((action) =>
        {
        grajRazCale();
        }, data, 10, 10);
        }
        }
public static Object grajRazLock = new Object();
public static boolean teraz = false;
public static void grajRazCale()
        {
        //Thread.Sleep(10);
        //System.Threading.Thread.Sleep(100);
        //object[] act = (object[])action;
        //Dictionary<string, string> ustawienia = (Dictionary<string, string>)act[0];
        //List<gra> grają = (List<gra>)act[1];
        //if (!grateraz)
        for (byte i = 0; liczbaGenerowanych > 0 || teraz && i < 10; i++)
        {
        if (i == 10)
        return;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Date dataTeraz =new Date();
        o = (int)((dataTeraz.getTime() - data.getTime()) * plik.Hz);//TODO sprawdzić jak działa

        if (o > 10000)
        o = 10000;
        data = dataTeraz;
        if (można && liczbaGenerowanych == 0 && (klawiaturaKomputera.wszytskieNuty.Count > 0))
            synchronized (grają)
        {
        teraz = true;
        synchronized (grajRazLock)
        {
        nuta[] wszystNuty;
            synchronized (klawiaturaKomputera.wszytskieNuty)
        {
        wszystNuty = (nuta[])klawiaturaKomputera.wszytskieNuty.toArray();//TODO czy zadziała
        }
        liczbaGenerowanych += wszystNuty.length + 1;
        for (int i = 0; i < wszystNuty.length; i++)
        {
            if(grają.containsKey(wszystNuty[i].id))
        {
        if (grają.get(wszystNuty[i].id).zagrano < 0)
        wszystNuty[i].generujOd = 0;
        else if (grają.get(wszystNuty[i].id).zagrano < 256)
        {
        wszystNuty[i].generujOd = 0;
        wszystNuty[i].grajOd = grają.get(wszystNuty[i].id).zagrano;
        }
        else
        {
        wszystNuty[i].generujOd = grają.get(wszystNuty[i].id).zagrano - 256;
        wszystNuty[i].grajOd = 256;
        }
        wszystNuty[i].generujDo = grają.get(wszystNuty[i].id).zagrano + o + 256;
        }
        else
        wszystNuty[i].generujDo = o + 256;
        wszystNuty[i].grajDo = o + 256;
        wszystNuty[i].ilepróbek = wszystNuty[i].ilepróbekNaStarcie;
        System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        {
        (Action as nuta).sekw.działaj((Action as nuta));
        lock (zmianaLiczGenLock) { liczbaGenerowanych--; }
        if (liczbaGenerowanych == 0)

        grajRaz();
        }, wszystNuty[i]);
        }
                        /*if (graniePlay)
                        {
                            graniePrzy += o;
                            while (granieNuty.Length > granieI && granieNuty[granieI].opuznienie <= graniePrzy+2*o)
                            {
                                granieNuty[granieI].opuznienie -= graniePrzy;
                                lock (zmianaLiczGenLock) { liczbaGenerowanych++; }
                                System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
                                {
                                    if ((Action as nuta).sekw != null)
                                    {
                                        (Action as nuta).sekw.działaj((Action as nuta));
                                        lock (zmianaLiczGenLock) { liczbaGenerowanych--; }
                                        if (liczbaGenerowanych == 0)

                                            grajRaz();
                                    }
                                }, granieNuty[granieI]);
                                granieI++;
                            }
                        }*/
        synchronized (zmianaLiczGenLock) { liczbaGenerowanych--; }
        if (liczbaGenerowanych == 0)

        grajRaz();
        }
        }
        if (liczbaGenerowanych == 0 || grają.size() > 0)

        grajRaz();
        }
public static void grajRaz()
        {

        if (można && liczbaGenerowanych == 0)
        {
        float[][] fala;
        synchronized (grają)
        {
        synchronized (klawiaturaKomputera.wszytskieNuty)
        {
        int i2 = 0;
        while (i2 < klawiaturaKomputera.wszytskieNuty.size())
        {
        if (klawiaturaKomputera.wszytskieNuty.get(i2).dane != null)
        {
        if (klawiaturaKomputera.wszytskieNuty.get(i2).dane.length == 0)
        {
        klawiaturaKomputera.wszytskieNuty.remove(i2);
        }
        else
        i2++;
        }
        else
        i2++;
        }
        }




        {
        dodatkowy:
        fala = new float[2][o];
        int falaLength = o;
                        /*if (MainWindow.gpgpu)
                        {


                                fixed (float* _fala = &fala[0])
                                {
                                    square_array(_fala, fala.Length);
                                }

                        }
                        else*/
        {

        gra[] zz = grają.Values.ToArray();
        int liczIle = 0;
        for (int x = 0; x < zz.Length; x++)
        {

        if (zz[x].zagrano > zz[x].nuta.dane.Length + zz[x].nuta.generujOd)
        {
        zz[x].nuta.dane = null;
        zz[x].dźwięk = null;
        grają.Remove(zz[x].nuta.id);
        }

        else
        {
        liczIle++;
        long i = 0;
        if (zz[x].zagrano < 0 && -zz[x].zagrano < o)
        i = -zz[x].zagrano;
        else if (zz[x].zagrano < 0)
        i = o;
        //else
        // i = zz[x].zagrano - zz[x].nuta.generujOd;
        var opt1 = zz[x].zagrano - zz[x].nuta.generujOd;
        var opt2 = zz[x].dźwięk.LongLength - opt1;
        long opt3;
        if (o < opt2 && o < falaLength)
        opt3 = o;
        else if (opt2 < falaLength)
        opt3 = opt2;
        else
        opt3 = falaLength;
        if (zz[x].nuta.głośność == 1 && zz[x].nuta.balans0 == 1 && zz[x].nuta.balans1 == 1)
        {
        if (i < -opt1)
        i = -opt1;
        for (; i < opt3; i++)
        {

        {
        fala[0, i] += zz[x].dźwięk[i + opt1];
        fala[1, i] += zz[x].dźwięk[i + opt1];
        }

        }
        }
        else
        {
        if (i < -opt1)
        i = -opt1;
        var mn0 = zz[x].nuta.głośność * zz[x].nuta.balans0;
        var mn1 = zz[x].nuta.głośność * zz[x].nuta.balans1;
        for (; i < opt3; i++)
        {

        {
        fala[0, i] += zz[x].dźwięk[i + opt1] * mn0;
        fala[1, i] += zz[x].dźwięk[i + opt1] * mn1;
        }

        }
        }
        zz[x].zagrano += o;
        }


        }
        }

        //System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        //{
        //});

        }
        }
        try
        {
        if (grają.Count > 0)
        if (funkcje.graj(fala))
        { }// goto dodatkowy;
        }
        catch (FormatException a) { funkcje.graj(fala); }
        // grajRazCale();
        }

        teraz = false;
        }
public static bool liveGraj()
        {
        bool czyJeszczeRaz = false; if (graniePlay)
        {
        lock (wynik)
        {
        long wygenerowanoDo = wynik.Length / 2;
        foreach (var x in granieNuty)
        {
        if (!x.czyGotowe && x.sekw != null && wygenerowanoDo > x.opuznienie)
        {
        wygenerowanoDo = x.opuznienie;
        czyJeszczeRaz = true;
        }

        }
        var dl = wygenerowanoDo - graniePrzy;


        if (dl > 0)
        {
        if ((Statyczne.bufor.BufferLength - Statyczne.bufor.BufferedBytes) / 4 < dl)
        {
        czyJeszczeRaz = true;
        dl = (Statyczne.bufor.BufferLength - Statyczne.bufor.BufferedBytes) / 4;
        }
        var falaT = new float[2, dl];
        for (var i = 0; i < dl; i++)
        {
        falaT[0, i] = wynik[0, i + graniePrzy];
        falaT[1, i] = wynik[1, i + graniePrzy];
        }
        try
        {
        if (funkcje.graj(falaT))
        { }// goto dodatkowy;
        }
        catch (FormatException a) { System.Windows.MessageBox.Show("błąd"); }
        graniePrzy += (int)dl;
        }
        }
                /*if(czyJeszczeRaz)
                {
                    //Thread.Sleep(100);
                    jedenTimer = new Timer((o) => { liveGraj(); }, null, 100, 0);

                }*/

        }
        return czyJeszczeRaz;
        }
static Timer jedenTimer;
public static void Działaj(nuta input)
        {
        if (wynik == null)
        lock (grają)
        {
        if (grają.ContainsKey(input.id))
        {
        grają[input.id].dźwięk = (input).dane;
        grają[input.id].nuta = input;
        }
        else
        grają.Add(input.id, new gra(input));
        }
        else
        {


        long i = input.opuznienie + input.generujOd;
        var opt1 = -input.generujOd - input.opuznienie;

        var opt3 = input.dane.Length - opt1;
        try
        {
        if (input.głośność == 1)

        if (input.balans0 == 1 && input.balans1 == 1)
        for (; i < opt3; i++)
        {
        wynik[0, i] += input.dane[i + opt1];
        wynik[1, i] += input.dane[i + opt1];
        }
        else
        for (; i < opt3; i++)
        {
        wynik[0, i] += input.dane[i + opt1] * input.balans0;
        wynik[1, i] += input.dane[i + opt1] * input.balans1;
        }
        else
        {
        var mn0 = input.głośność * input.balans0;
        var mn1 = input.głośność * input.balans1;
        for (; i < opt3; i++)
        {
        wynik[0, i] += input.dane[i + opt1] * mn0;
        wynik[1, i] += input.dane[i + opt1] * mn1;
        }
        }
        }
        catch (IndexOutOfRangeException) { }

        input.dane = null;
        }
        //try { funkcje.graj((double[])input[0], double.Parse(_ustawienia["głośność"])); }
        //catch { }
        }
public static System.Threading.Timer t;
        short ileNutMusiByć = 0;
static short analizujIleNutMusiByć(moduł wej)
        {
        if (typeof(sekwencer) == wej.GetType())
        {
        return 1;
        }

        else
        {
        var rozdzielacze = new List<int>();
        short ret = 0;
        for (int i = 0; i < wej.wejście.Count; i++)
        {
        if (wej.wejście[i].DrógiModół.GetType() == typeof(flanger))
        for (var x = 0; x < wej.wejście[x].DrógiModół.wejście.Count; x++)
        {
        if (wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetType() == typeof(rozdzielacz))
        {
        if (!rozdzielacze.Contains(wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetHashCode()))
        rozdzielacze.Add(wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetHashCode());



        }
        else
        ret += analizujIleNutMusiByć(wej.wejście[i].DrógiModół.wejście[x].DrógiModół);

        }
        else
        ret += analizujIleNutMusiByć(wej.wejście[i].DrógiModół);
        }
        ret += (short)rozdzielacze.Count;
        if (ret > 2)
        { }
        return ret;
        }
        }
public void analizujIleNutMusiByć()
        {
        //ileNutMusiByć = analizujIleNutMusiByć(this);
        }
        Dictionary<long, Object[]> grupowane = new Dictionary<long, object[]>();
public void działaj(nuta n)
        {
        //if (ileNutMusiByć == 0||n.czyPogłos||!MainWindow.czyGC)
        Działaj(n);
            /*else
            {
                lock (grupowane)
                {
                    if (!grupowane.ContainsKey(n.idOryginalne))
                    {
                        object[] tablica = new object[ileNutMusiByć + 1];
                        tablica[0] = 1;
                        tablica[1] = n;
                        grupowane.Add(n.idOryginalne, tablica);
                    }
                    else
                    {
                        (grupowane[n.idOryginalne][0]) = ((int)grupowane[n.idOryginalne][0]) + 1;
                        grupowane[n.idOryginalne][((int)grupowane[n.idOryginalne][0])] = n;
                    }
                    if (((int)grupowane[n.idOryginalne][0]) == grupowane[n.idOryginalne].Length - 1)
                    {
                        long opuznienie = 0;
                        long długość = 0;
                        for (int i = 1; i < grupowane[n.idOryginalne].Length; i++)
                        {
                            if ((grupowane[n.idOryginalne][i] as nuta).opuznienie + (grupowane[n.idOryginalne][i] as nuta).generujOd < opuznienie)
                                opuznienie = (grupowane[n.idOryginalne][i] as nuta).opuznienie + (grupowane[n.idOryginalne][i] as nuta).generujOd;
                            if ((grupowane[n.idOryginalne][i] as nuta).opuznienie + (grupowane[n.idOryginalne][i] as nuta).dane.Length > długość)
                                długość = (grupowane[n.idOryginalne][i] as nuta).opuznienie + (grupowane[n.idOryginalne][i] as nuta).dane.Length;
                        }
                        var dane = new float[długość - opuznienie];
                        for (int i = 1; i < grupowane[n.idOryginalne].Length; i++)
                        {
                            long opuznienieAkt = -opuznienie + (grupowane[n.idOryginalne][i] as nuta).opuznienie;
                            for (long x = (grupowane[n.idOryginalne][i] as nuta).generujOd; x < (grupowane[n.idOryginalne][i] as nuta).dane.Length; x++)
                            {
                                dane[x + opuznienieAkt] += (grupowane[n.idOryginalne][i] as nuta).dane[x] * (grupowane[n.idOryginalne][i] as nuta).głośność;
                            }
                        }
                        var nutaKoniec = (grupowane[n.idOryginalne][1] as nuta);
                        nutaKoniec.id = nutaKoniec.idOryginalne;
                        nutaKoniec.dane = dane;
                        nutaKoniec.generujOd = 0;
                        nutaKoniec.opuznienie = opuznienie;
                        Działaj(nutaKoniec);

                        grupowane.Remove(n.idOryginalne);
                    }
                }
            }*/
        }

public static Object zmianaLiczGenLock = new Object();

public static String PlikDoZapisu = null;

        }
