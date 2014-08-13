package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 13.08.14.
 */ /// <summary>
/// interfejs dla każdego modułu (oscylator, filtry itd.)
/// </summary>
public interface moduł
{
   /* UserControl UI
    {
        get;
    }*/
    ArrayList<Typ> wejście=new ArrayList<Typ>();
    /// <summary>
    /// Elementy, do których dalej będą przekazywane dane
    /// </summary>
    Typ[] wyjście=new Typ[8];

    Hashtable<String,String> ustawienia=new Hashtable<String,String>();
    Node XML =null;
    /*void aktXML()
    {

    }*/
    /// <summary>
    ///
    /// </summary>
    /// <param name="input"></param>
    void działaj(nuta input);


    long symuluj(long p);

    void akt();
}
