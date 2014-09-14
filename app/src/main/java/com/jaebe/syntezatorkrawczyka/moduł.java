package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 13.08.14.
 */ /// <summary>
/// interfejs dla każdego modułu (oscylator, filtry itd.)
/// </summary>
public interface moduł {
    /* UserControl UI
     {
         get;
     }*/
    ArrayList<Typ> getWejście();

    /// <summary>
    /// Elementy, do których dalej będą przekazywane dane
    /// </summary>
    Typ[] getWyjście();

    Hashtable<String, String> getUstawienia();

    // void setUstawienia(Hashtable<String,String> u);
    Node getXML();

    void setXML(Node n);

    //Hashtable<String,String> ustawienia=new Hashtable<String,String>();
    //public Node XML =null;
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

    Class UI();
}
