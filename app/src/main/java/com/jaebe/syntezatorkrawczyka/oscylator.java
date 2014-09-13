package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 16.08.14.
 */
public class oscylator implements moduł {

    public ArrayList<Typ> wejście = new ArrayList<Typ>();

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

    public FalaNiestandardowa niestandardowa = null;

    public enum typFali {sinusoidalna, trójkątna, prostokątka, piłokształtna, niestandardowa}

    public oscylator() {
        wyjście[0] = new Typ();
        wyjście[1] = new Typ();
        ustawienia = new Hashtable<String, String>();
        ustawienia.put("typ", "trójkątna");
        ustawienia.put("balans", "0");
        ustawienia.put("gladkosc", "1");
        ustawienia.put("A", "0");
        ustawienia.put("D", "0");
        ustawienia.put("S", "0.2");
        ustawienia.put("R", "0");
        akt();
    }

    public void akt() {

        Balans = Float.parseFloat(ustawienia.get("balans"));
        A = Float.parseFloat(ustawienia.get("A"));
        D = Float.parseFloat(ustawienia.get("D"));
        S = Float.parseFloat(ustawienia.get("S"));
        R = Float.parseFloat(ustawienia.get("R"));
        gladkosc = Float.parseFloat(ustawienia.get("gladkosc"));
        if (ustawienia.get("typ").equals("piłokształtna"))
            typ = typFali.piłokształtna;
        else if (ustawienia.get("typ").equals("prostokątna"))
            typ = typFali.prostokątka;
        else if (ustawienia.get("typ").equals("trójkątna"))
            typ = typFali.trójkątna;
        else if (ustawienia.get("typ").equals("sinusoidalna"))
            typ = typFali.sinusoidalna;
        else {

            typ = typFali.niestandardowa;
            if (Statyczne.otwartyplik.fale.containsKey(ustawienia.get("typ")))
                niestandardowa = Statyczne.otwartyplik.fale.get(ustawienia.get("typ"));
        }
    }

    /// <summary>
    /// Zawiera wynik funkcji generujJedenPrzebieg zapisany w celu optymalizacji
    /// </summary>
    Hashtable<Long, float[]> zapisanePojedyńczePrzebiegi = new Hashtable<Long, float[]>();
    static Hashtable<Short, float[]> zapisanePojedyńczePrzebiegiTrojkatna = new Hashtable<Short, float[]>();

    public static float[] generujJedenPrzebiegStatyczny(typFali typ, int ilePróbek, float gladkość) {
        switch (typ) {
            case sinusoidalna:
                return sinusoidalna((float) ilePróbek, ilePróbek);

            case trójkątna:
                return trójkątna(ilePróbek);

            case piłokształtna:
                return piłokształtna((float) ilePróbek, ilePróbek, gladkość);

            case prostokątka:
                return prostokątna(ilePróbek, gladkość);

            default:
                return null;

        }

    }

    public long symuluj(long p) {
        return wyjście[0].DrógiModół.symuluj(p + (long) (Float.parseFloat(ustawienia.get("R")) * plik.kHz));
    }

    public /*unsafe*/ void działaj(nuta input) {

        {

            if (Balans < 0)
                input.balans1 *= (1 + Balans);
            else
                input.balans0 *= (1 - Balans);
            {
                synchronized (input) {
                    input.ilepróbek = Math.floor(input.ilepróbek);
                    input.długość = (int) (Math.floor(input.długość / input.ilepróbek) * input.ilepróbek);
                    if (wyjście[0].DrógiModół != null && (D != 0 || S != 0)) {
                        Object[] wy = new Object[2];
                        float[] jedenPrzebieg;
                        if (typ == typFali.niestandardowa) {
                            if (niestandardowa != null)
                                jedenPrzebieg = niestandardowa.generujJedenPrzebieg((int) Math.floor(input.ilepróbek));
                            else
                                jedenPrzebieg = new float[(int) Math.floor(input.ilepróbek)];
                        } else
                            jedenPrzebieg = generujJedenPrzebiegStatyczny(typ, (int) Math.floor(input.ilepróbek), gladkosc);
                        long długośćCała = (long) (Math.floor((input.długość) / input.ilepróbek) * input.ilepróbek + R * plik.kHz);
                        if (input.długość + R * plik.kHz - input.generujOd > 0) {
                            if (input.generujDo < 0)
                                input.dane = new float[(int) (Math.floor((input.długość) / input.ilepróbek) * input.ilepróbek + R * plik.kHz)];
                            else if (input.generujDo - input.generujOd < (int) (Math.floor((input.długość) / input.ilepróbek) * input.ilepróbek + R * plik.kHz))
                                input.dane = new float[(int) (input.generujDo - input.generujOd)];
                            else
                                input.dane = new float[(int) (Math.floor((input.długość) / input.ilepróbek) * input.ilepróbek + R * plik.kHz)];


                        } else
                            input.dane = new float[0];//koniec wykonywania
                        float aProcent, dProcent;
                        float rProcent = 1;
                        aProcent = 0;
                        float aMax = A * plik.kHz;
                        float dMax = D * plik.kHz;
                        float rMax = R * plik.kHz;
                        float s = S;
                        aProcent = 1;
                        float Max1 = długośćCała - input.generujOd - rMax;
                        float Max2 = aMax - (int) input.generujOd;
                        float Max3 = dMax - (int) input.generujOd;
                        float Acc1 = dMax * (1 - s);
                        int genInt = (int) input.generujOd;
                        for (int i = 0; i < input.dane.length; i++) {
                            if (Max1 > i)
                                if (Max2 > i)
                                    aProcent = (i + (int) input.generujOd) / aMax;
                                else
                                    aProcent = 1;
                            else {
                                rProcent = (długośćCała - i - input.generujOd) / rMax;
                                //aProcent = 1;
                            }
                            if (Max3 > i) {
                                dProcent = s + (Max3 - i) / Acc1;
                            } else
                                dProcent = s;
                            if (dProcent == 0)
                                break;//sprawdzić, czi nie powoduje problemów

                            input.dane[i] = jedenPrzebieg[(i + genInt) % jedenPrzebieg.length] * aProcent * rProcent * dProcent;

                        }
                        wyjście[0].DrógiModół.działaj(input);
                    }
                }
            }
        }

    }

    public void działaj(nuta input, float[] jak) {
        nuta n = input;

        {
            synchronized (n) {
                n.ilepróbek = Math.floor(n.ilepróbek);
                n.długość = (int) (Math.floor(n.długość / n.ilepróbek) * n.ilepróbek);
                if (wyjście[0].DrógiModół != null && (Float.parseFloat(ustawienia.get("D")) != 0 || Float.parseFloat(ustawienia.get("S")) != 0)) {
                    Object[] wy = new Object[2];
                    long długośćCała = (int) (Math.floor((n.długość) / n.ilepróbek) * n.ilepróbek + Float.parseFloat(ustawienia.get("R")) * plik.kHz);
                    if (n.długość + Float.parseFloat(ustawienia.get("R")) * plik.kHz - n.generujOd > 0) {
                        if (n.generujDo < 0)
                            n.dane = new float[(int) (Math.floor((n.długość) / n.ilepróbek) * n.ilepróbek + Float.parseFloat(ustawienia.get("R")) * plik.kHz)];
                        else if (n.generujDo - n.generujOd < (int) (Math.floor((n.długość) / n.ilepróbek) * n.ilepróbek + Float.parseFloat(ustawienia.get("R")) * plik.kHz))
                            n.dane = new float[(int) (n.generujDo - n.generujOd)];
                        else
                            n.dane = new float[(int) (Math.floor((n.długość) / n.ilepróbek) * n.ilepróbek + Float.parseFloat(ustawienia.get("R")) * plik.kHz)];


                    } else
                        n.dane = new float[0];//koniec wykonywania
                    float aProcent, dProcent;
                    float rProcent = 1;
                    aProcent = 0;
                    float aMax = Float.parseFloat(ustawienia.get("A")) * plik.kHz;
                    float dMax = Float.parseFloat(ustawienia.get("D")) * plik.kHz;
                    float rMax = Float.parseFloat(ustawienia.get("R")) * plik.kHz;
                    float s = Float.parseFloat(ustawienia.get("S"));
                    aProcent = 1;

                    String typ = ustawienia.get("typ");
                    typFali jakaFala = typFali.trójkątna;
                    if (typ.equals("trójkątna"))
                        jakaFala = typFali.trójkątna;
                    else if (typ.equals("prostokątna"))
                        jakaFala = typFali.prostokątka;
                    else if (typ.equals("sinusoidalna"))
                        jakaFala = typFali.sinusoidalna;
                    else if (typ.equals("piłokształtna"))
                        jakaFala = typFali.piłokształtna;
                    else
                        jakaFala = typFali.prostokątka;
                    float gladkosc = Float.parseFloat(ustawienia.get("gladkosc"));
                    float niski = -2 * gladkosc;
                    float wysoki = 2f + niski;

                    float granica = (float) (n.ilepróbek * gladkosc);
                    float pozycja = 0;//przy podziale nuty zrobić zapamiętywanie
                    for (int i = 0; i < n.dane.length; i++) {
                        if (długośćCała - i - n.generujOd > rMax)
                            if (aMax > i + (int) n.generujOd)
                                aProcent = (i + (int) n.generujOd) / aMax;
                            else
                                aProcent = 1;
                        else {
                            rProcent = (długośćCała - i - n.generujOd) / rMax;
                            //aProcent = 1;
                        }
                        if (dMax > i + (int) n.generujOd) {
                            dProcent = s + (dMax - i - (int) n.generujOd) / dMax * (1 - s);
                        } else
                            dProcent = s;
                        pozycja += jak[(i + (int) n.generujOd) % jak.length] + 1;

                        if (jakaFala == typFali.sinusoidalna)
                            n.dane[i] = (float) Math.sin(pozycja / n.ilepróbek * 2 * Math.PI) * (aProcent * rProcent * dProcent);
                        else if (jakaFala == typFali.trójkątna) {
                            float z = (float) (pozycja % n.ilepróbek / n.ilepróbek);
                            if (z < 0.25)
                                n.dane[i] = z * 4 * (aProcent * rProcent * dProcent);
                            else if (z < 0.75)
                                n.dane[i] = (0.5f - z) * 4f * (aProcent * rProcent * dProcent);
                            else
                                n.dane[i] = (1 - z) * -4 * (aProcent * rProcent * dProcent);
                        } else if (jakaFala == typFali.prostokątka) {

                            if (pozycja % n.ilepróbek < n.ilepróbek * gladkosc)
                                n.dane[i] = wysoki * (aProcent * rProcent * dProcent);
                            else
                                n.dane[i] = niski * (aProcent * rProcent * dProcent);

                        } else if (jakaFala == typFali.piłokształtna) {
                            float a = (float) (((n.ilepróbek / 3) + i) % (n.ilepróbek));
                            if (a < granica)
                                n.dane[i] += ((a / granica) * 2 - 1);
                            else
                                n.dane[i] += (float) ((a - granica) / (n.ilepróbek - granica) * -2 + 1);
                        }

                    }
                    wyjście[0].DrógiModół.działaj(n);
                }
            }
        }
    }

    public static float[] prostokątna(int długość, float gladkosc) {
        float[] ret = new float[długość];
        float wysoki = 2f - 2 * gladkosc;
        float niski = 0 - 2 * gladkosc;
        int i = 0;
        float dłGl = długość * gladkosc;
        for (; i < dłGl; i++) {
            ret[i] = wysoki;
        }
        for (; i < długość; i++) {
            ret[i] = niski;
        }
        return ret;
    }

    public static float[] trójkątna(int ilepróbek) {
        if (ilepróbek > 100) {
            if (zapisanePojedyńczePrzebiegiTrojkatna.containsKey((Short) (short) ilepróbek))
                return zapisanePojedyńczePrzebiegiTrojkatna.get((Short) (short) ilepróbek);

        }
        float[] ret = new float[ilepróbek];
        int i = 0;
        float ilepróbekFloat = (float) ilepróbek;
        float długość1 = ilepróbek / 4;
        float długość2 = długość1 * 3;
        for (; i < długość1; i++) {
            ret[i] = i / ilepróbekFloat * 4;

        }
        for (; i < długość2; i++) {
            ret[i] = (0.5f - i / ilepróbekFloat) * 4f;

        }
        for (; i < ilepróbek; i++) {
            ret[i] = (1f - i / ilepróbekFloat) * -4f;

        }
        if (ilepróbek > 100)
            zapisanePojedyńczePrzebiegiTrojkatna.put((short) ilepróbek, ret);
        return ret;
    }

    public static float[] piłokształtna(float ilepróbek, int długość, float gladkosc) {
        float[] ret = new float[długość];

        float granica = ilepróbek * gladkosc;
        for (int i = 0; i < długość; i++) {
            float a = ((ilepróbek / 3) + i) % (ilepróbek);
            if (a < granica)
                ret[i] += ((a / granica) * 2 - 1);
            else
                ret[i] += ((a - granica) / (ilepróbek - granica) * -2 + 1);
        }

        return ret;
    }

    public static float[] sinusoidalna(double ilepróbek, int długość) {
        float[] ret = new float[długość];
        for (int i = 0; i < długość; i++) {
            ret[i] = (float) Math.sin(i / ilepróbek * piRazy2);
        }
        return ret;
    }

    static final double piRazy2 = 2 * Math.PI;
    float Balans, A, D, S, R, gladkosc;
    typFali typ;
@Override public String toString(){
        return "Oscylator";
    }
}
