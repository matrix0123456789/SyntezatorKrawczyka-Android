package com.jaebe.syntezatorkrawczyka;

import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

/// <summary>
/// Odpowiada pojedyńczej nucie, zawiera informacje i ewentualnie gotowy dźwięk
/// </summary>
public class nuta implements Cloneable {
    /// <summary>
/// długość jednego przebiegu fali
/// </summary>
    public double ilepróbek;
    public boolean czyPogłos = false;
    public double ilepróbekNaStarcie;
    /// <summary>
/// Długość wciśnięcia klawisza (bez opadania dźwięku i pogłosu)
/// </summary>
    public int długość;
    /// <summary>
/// dźwięk
/// </summary>
    public float[] dane;
    /// <summary>
/// ile czasu po starcie melodii dana nuta ma być słyszana
/// </summary>
    public int opuznienie = 0;
    static int ilenut = 0;

    public static int getNowyid() {
        return ilenut++;
    }

    public long wygenerowanoWcześniej = 0;
    public Date start;
    /// <summary>
/// identyfikator
/// </summary>
    public long id = 0;
    public long idOryginalne = 0;
    /// <summary>
/// liczba losowa, urzywana np. przy flangerze
/// </summary>
    public int los = (int) (losowanie.nextInt(100000) + 10000);
    static Random losowanie = new Random();

    public int generujOd = 0;
    public int generujDo = -1;
    /// <summary>
/// przesunięcie rozpoczęnia grania w danych
/// </summary>
    public int grajOd = 0;
    /// <summary>
/// przesunięcie zakończenia grania w danych
/// </summary>
    public int grajDo = 0;
    public float głośność = 1;
    //głośność w prawym i lewym kanale
    public float balans0 = 1;
    public float balans1 = 1;
    public byte kopiaInnaId = 0;///<summary>Zawiera liczbę jaka jest dodawana do id np. przy pogłosie</summary>
    public soundStart sekw;
    public Hashtable<Long, gra> grająLokalne;
    public Boolean czyGotowe = false;

    //public static
    public nuta() {
        idOryginalne = id = ilenut++;
        start = new Date();

    }


    public nuta(double ilepróbek, int długość) {
        this.ilepróbek = ilepróbekNaStarcie = ilepróbek;
        this.długość = długość;
        idOryginalne = id = ilenut++;
        start = new Date();
    }

    public nuta(double ilepróbek, int długość, int opuznienie) {
        this.ilepróbek = ilepróbekNaStarcie = ilepróbek;
        this.długość = długość;
        this.opuznienie = opuznienie;
        idOryginalne = id = ilenut++;
        start = new Date();

    }

    /*nuta Clone(short oktawy) throws CloneNotSupportedException {
        nuta ret = (nuta) clone();

        ret.ilepróbek = ret.ilepróbek / (Math.pow(2, oktawy));
        return ret;
    }*/

    public static int sortuj(nuta a, nuta b) {
        return (int) (a.opuznienie - b.opuznienie);
    }

    public static long nowyid() {
        ilenut++;
        return ilenut;
    }

    public nuta Clone() throws CloneNotSupportedException {
        return (nuta) clone();
    }
}