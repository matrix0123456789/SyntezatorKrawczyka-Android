package com.jaebe.syntezatorkrawczyka;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mateusz on 13.08.14.
 */
public class granie implements moduł {
    public static boolean[] generować = {true};
    public Node _XML;

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

    public Node getXML() {
        return _XML;
    }

    public void setXML(Node n) {
        _XML = n;
    }

    static public int o = 4410;
    static Date sw = new Date();
    public static Hashtable<Long, gra> grają = new Hashtable<Long, gra>();
    static boolean jest = false;
    static public float[][] wynik = null;
    static public int graniePrzy = 0, granieMax = 0, granieI = 0;
    static public boolean graniePlay = false;
    static public nuta[] granieNuty;

    public void akt() {
    }

    public long symuluj(long wej) {
        return wej;
    }

    public static boolean grateraz = false;
    Hashtable<String, String> _ustawienia = new Hashtable<String, String>();
    static float[] pustaTablica = new float[0];

    public static void grajcale(boolean graj) {
        grateraz = false;
        long oz = 0;
        gra[] zz;
        float[][] fala;
        if (wynik == null) {
            synchronized (grają) {
                zz = (gra[]) grają.values().toArray();
            }
            for (gra dospr : zz) {
                if (dospr != null) {
                    dospr.zagrano = -dospr.nuta.opuznienie;
                    if (dospr.dźwięk.length - dospr.zagrano > oz)
                        oz = dospr.dźwięk.length - dospr.zagrano;
                }
            }
            fala = new float[2][(int) oz];
            for (int x = 0; x < zz.length; x++) {

                int i = 0;
                if (zz[x].zagrano < 0 && -zz[x].zagrano < oz)
                    i = (int) -zz[x].zagrano;
                else if (zz[x].zagrano < 0)
                    i = o;
                long max;
                if (oz < zz[x].dźwięk.length - zz[x].zagrano)
                    max = oz;
                else
                    max = zz[x].dźwięk.length - zz[x].zagrano;
                if (zz[x].nuta.głośność == 1 && zz[x].nuta.balans0 == 1 && zz[x].nuta.balans1 == 1) {
                    for (; i < max; i++) {


                        fala[0][i] += zz[x].dźwięk[i + zz[x].zagrano];
                        fala[1][i] += zz[x].dźwięk[i + zz[x].zagrano];

                    }
                } else {
                    float mn0 = zz[x].nuta.głośność * zz[x].nuta.balans0;
                    float mn1 = zz[x].nuta.głośność * zz[x].nuta.balans1;
                    for (; i < oz && i + zz[x].zagrano < zz[x].dźwięk.length; i++) {


                        fala[0][i] += zz[x].dźwięk[i + zz[x].zagrano] * mn0;
                        fala[1][i] += zz[x].dźwięk[i + zz[x].zagrano] * mn1;

                    }
                }
                zz[x].zagrano += oz;
                if (zz[x].zagrano >= zz[x].dźwięk.length)
                    zz[x].nuta.dane = zz[x].dźwięk = null;

            }
        } else {
            fala = wynik;
            wynik = null;
        }

        //System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        //{
            /*try
            {
                if (graj)
                    funkcje.graj(fala, double.Parse(ustawienia.get("głośność"]));
                else
                    funkcje.zapisz(fala, double.Parse(ustawienia.get("głośność"]), "C:\\Users\\Mateusz\\Desktop\\xml\\a.wav");
            }
            catch (FormatException a)
            {*/
        grają.clear();
        if (graj)
            funkcje.graj(fala);
        else {
            while (PlikDoZapisu == null) {
                try {
                    Thread.sleep(50);
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

    public granie() {
        graniestart();
        //_ustawienia.put("głośność", "1.0");
    }

    static public void graniestart() {
        if (!jest) {
            // MainWindow.WasapiWyjście.Stop();
            //new System.Threading.Timer((object ozzz) => { MainWindow.WasapiWyjście.Play(); }, null,100,0);
            //MainWindow.bufor.AddSamples(new byte[10000], 0, 10000);
            jest = true;
            //object[] wejs = new object[2];
            //wejs[0] = _ustawienia;
            //wejs[1] = grają;

            data = new Date();

            t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    grajRazCale();
                }
            }, 10, 10);
        }
    }

    public static Object grajRazLock = new Object();
    public static boolean teraz = false;

    public static void grajRazCale() {
        //Thread.Sleep(10);
        //System.Threading.Thread.Sleep(100);
        //object[] act = (object[])action;
        //Hashtable<String, String> ustawienia = (Hashtable<String, String>)act[0];
        //List<gra> grają = (List<gra>)act[1];
        //if (!grateraz)
        for (byte i = 0; liczbaGenerowanych > 0 || teraz && i < 10; i++) {
            if (i == 10)
                return;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Date dataTeraz = new Date();
        o = (int) ((dataTeraz.getTime() - data.getTime()) * plik.kHz);//TODO sprawdzić jak działa

        if (o > wielkośćBuforu)
            o = wielkośćBuforu;
        if (o > 10000)
            o = 10000;
        data = dataTeraz;
        if (można && liczbaGenerowanych == 0 && (klawiaturaKomputera.wszytskieNuty.size() > 0))
            synchronized (grają) {
                teraz = true;
                synchronized (grajRazLock) {
                    Object[] wszystNuty;
                    synchronized (klawiaturaKomputera.wszytskieNuty) {
                        wszystNuty = klawiaturaKomputera.wszytskieNuty.toArray();
                    }
                    liczbaGenerowanych += wszystNuty.length + 1;
                    for (int i = 0; i < wszystNuty.length; i++) {
                        nuta wNi = (nuta) wszystNuty[i];
                        if (grają.containsKey(wNi.id)) {
                            if (grają.get(wNi.id).zagrano < 0)
                                wNi.generujOd = 0;
                            else if (grają.get(wNi.id).zagrano < 256) {
                                wNi.generujOd = 0;
                                wNi.grajOd = grają.get(wNi.id).zagrano;
                            } else {
                                wNi.generujOd = grają.get(wNi.id).zagrano - 256;
                                wNi.grajOd = 256;
                            }
                            wNi.generujDo = grają.get(wNi.id).zagrano + o + 256;
                        } else
                            wNi.generujDo = o + 256;
                        wNi.grajDo = o + 256;
                        wNi.ilepróbek = wNi.ilepróbekNaStarcie;
                        wątekDzialaj1 w = new wątekDzialaj1();
                        w.Action = wNi;
                        w.start();
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
                    synchronized (zmianaLiczGenLock) {
                        liczbaGenerowanych--;
                    }
                    if (liczbaGenerowanych == 0)

                        grajRaz();
                }
            }
        if (liczbaGenerowanych == 0 || grają.size() > 0)

            grajRaz();
    }

    public static void grajRaz() {

        if (można && liczbaGenerowanych == 0) {
            float[][] fala;
            synchronized (grają) {
                synchronized (klawiaturaKomputera.wszytskieNuty) {
                    int i2 = 0;
                    while (i2 < klawiaturaKomputera.wszytskieNuty.size()) {
                        if (klawiaturaKomputera.wszytskieNuty.get(i2).dane != null) {
                            if (klawiaturaKomputera.wszytskieNuty.get(i2).dane.length == 0) {
                                klawiaturaKomputera.wszytskieNuty.remove(i2);
                            } else
                                i2++;
                        } else
                            i2++;
                    }
                }


                {
                    dodatkowy:
                    fala = new float[1][o];
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

                        Object[] zz = grają.values().toArray();
                        int liczIle = 0;
                        for (int x = 0; x < zz.length; x++) {
                            gra zzx = (gra) zz[x];
                            if (zzx.zagrano > zzx.nuta.dane.length + zzx.nuta.generujOd) {
                                zzx.nuta.dane = null;
                                zzx.dźwięk = null;
                                grają.remove(zzx.nuta.id);
                            } else {
                                liczIle++;
                                int i = 0;
                                if (zzx.zagrano < 0 && -zzx.zagrano < o)
                                    i = -zzx.zagrano;
                                else if (zzx.zagrano < 0)
                                    i = o;
                                //else
                                // i = zz[x].zagrano - zz[x].nuta.generujOd;
                                int opt1 = zzx.zagrano - zzx.nuta.generujOd;
                                int opt2 = zzx.dźwięk.length - opt1;
                                long opt3;
                                falaLength = fala[0].length;
                                if (o < opt2 && o < falaLength)
                                    opt3 = o;
                                else if (opt2 < falaLength)
                                    opt3 = opt2;
                                else
                                    opt3 = falaLength;
                                if (zzx.nuta.głośność == 1 && zzx.nuta.balans0 == 1 && zzx.nuta.balans1 == 1) {
                                    if (i < -opt1)
                                        i = -opt1;
                                    try {
                                        for (; i < opt3; i++) {

                                            {
                                                fala[0][i] += zzx.dźwięk[i + opt1];
                                                // fala[1][i] += zz[x].dźwięk[i + opt1];
                                            }

                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    if (i < -opt1)
                                        i = -opt1;
                                    float mn0 = zzx.nuta.głośność;// * zz[x].nuta.balans0;
                                    //float mn1 = zz[x].nuta.głośność * zz[x].nuta.balans1;
                                    for (; i < opt3; i++) {

                                        {
                                            fala[0][i] += zzx.dźwięk[i + opt1] * mn0;
                                            // fala[1][i] += zz[x].dźwięk[i + opt1] * mn1;
                                        }

                                    }
                                }
                                zzx.zagrano += o;
                            }


                        }
                    }

                    //System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
                    //{
                    //});

                }
            }


            if (grają.size() > 0)
                if (funkcje.graj(fala)) {
                }// goto dodatkowy;
            // grajRazCale();
        }

        teraz = false;
    }

    public static boolean liveGraj() {
        boolean czyJeszczeRaz = false;
        if (graniePlay) {
            synchronized (wynik) {
                int wygenerowanoDo = wynik.length / 2;
                for (nuta x : granieNuty) {
                    if (!x.czyGotowe && x.sekw != null && wygenerowanoDo > x.opuznienie) {
                        wygenerowanoDo = x.opuznienie;
                        czyJeszczeRaz = true;
                    }

                }
                int dl = wygenerowanoDo - graniePrzy;


                if (dl > 0) {

                    /*if ((Statyczne.bufor.BufferLength - Statyczne.bufor.BufferedBytes) / 4 < dl) {//TODO bufor
                        czyJeszczeRaz = true;
                        dl = (Statyczne.bufor.BufferLength - Statyczne.bufor.BufferedBytes) / 4;
                    }*/
                    if (wielkośćBuforu - Bufor.getPlaybackHeadPosition() / 4 < dl) {
                        czyJeszczeRaz = true;
                        dl = wielkośćBuforu - Bufor.getPlaybackHeadPosition() / 4;
                    }
                    float[][] falaT = new float[1][dl];
                    for (int i = 0; i < dl; i++) {
                        falaT[0][i] = wynik[0][i + graniePrzy];
                        // falaT[1][i] = wynik[1][i + graniePrzy];
                    }
                    //try {
                    if (funkcje.graj(falaT)) {
                    }// goto dodatkowy;
                    //}
                    //catch (FormatException a) { System.Windows.MessageBox.Show("błąd"); }
                    graniePrzy += (int) dl;
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

    public static void Działaj(nuta input) {
        if (wynik == null)
            synchronized (grają) {
                if (grają.containsKey(input.id)) {
                    grają.get(input.id).dźwięk = (input).dane;
                    grają.get(input.id).nuta = input;
                } else
                    grają.put(input.id, new gra(input));
            }
        else {


            int i = input.opuznienie + input.generujOd;
            int opt1 = -input.generujOd - input.opuznienie;

            int opt3 = input.dane.length - opt1;

            if (input.głośność == 1)

                if (input.balans0 == 1 && input.balans1 == 1)
                    for (; i < opt3; i++) {
                        wynik[0][i] += input.dane[i + opt1];
                        wynik[1][i] += input.dane[i + opt1];
                    }
                else
                    for (; i < opt3; i++) {
                        wynik[0][i] += input.dane[i + opt1] * input.balans0;
                        wynik[1][i] += input.dane[i + opt1] * input.balans1;
                    }
            else {
                float mn0 = input.głośność * input.balans0;
                float mn1 = input.głośność * input.balans1;
                for (; i < opt3; i++) {
                    wynik[0][i] += input.dane[i + opt1] * mn0;
                    wynik[1][i] += input.dane[i + opt1] * mn1;
                }
            }

            input.dane = null;
        }
        //try { funkcje.graj((double[])input[0], double.Parse(_ustawienia.get("głośność"])); }
        //catch { }
    }

    public static Timer t;
    short ileNutMusiByć = 0;

    static short analizujIleNutMusiByć(moduł wej) {
        if (sekwencer.class == wej.getClass()) {
            return 1;
        } else {
            ArrayList rozdzielacze = new ArrayList<Integer>();
            short ret = 0;
            for (int i = 0; i < wej.getWejście().size(); i++) {
        /*if (wej.wejście[i].DrógiModół.GetType() == typeof(flanger))//TODO flanger
        for (int x = 0; x < wej.wejście[x].DrógiModół.wejście.Count; x++)
        {
        if (wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetType() == typeof(rozdzielacz))
        {
        if (!rozdzielacze.Contains(wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetHashCode()))
        rozdzielacze.Add(wej.wejście[i].DrógiModół.wejście[x].DrógiModół.GetHashCode());



        }
        else
        ret += analizujIleNutMusiByć(wej.getWejście().get(i).DrógiModół.getWejście().get(x).DrógiModół);

        }
        else*/
                ret += analizujIleNutMusiByć(wej.getWejście().get(i).DrógiModół);
            }
            ret += (short) rozdzielacze.size();
            if (ret > 2) {
            }
            return ret;
        }
    }

    public void analizujIleNutMusiByć() {
        //ileNutMusiByć = analizujIleNutMusiByć(this);
    }

    Hashtable<Long, Object[]> grupowane = new Hashtable<Long, Object[]>();

    public void działaj(nuta n) {
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
    static int wielkośćBuforu;
    static public AudioTrack Bufor;

    static {
        wielkośćBuforu = AudioTrack.getMinBufferSize(48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        //if(wielkośćBuforu<48000*4)
        //    wielkośćBuforu=48000*4;
        Bufor = new AudioTrack(AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, wielkośćBuforu * 2, AudioTrack.MODE_STREAM);

    }
}

class wątekDzialaj1 extends Thread {
    public wątekDzialaj1() {
    }

    public nuta Action = null;

    @Override
    public void run() {
        if (Action == null) {
            System.out.print("Null w granie wątek\r\n");

        }
        (Action).sekw.działaj((Action));
        synchronized (granie.zmianaLiczGenLock) {
            granie.liczbaGenerowanych--;
        }
        if (granie.liczbaGenerowanych == 0)

            granie.grajRaz();
    }
}
