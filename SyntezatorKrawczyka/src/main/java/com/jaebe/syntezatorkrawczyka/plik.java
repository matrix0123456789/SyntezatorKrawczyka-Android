package com.jaebe.syntezatorkrawczyka;

import android.util.Xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mateusz on 07.08.14.
 */
public class plik {
    public static String URLStatyczne;
    public String URL;
    public Document xml;
    public Hashtable<String, sound> moduły = new Hashtable<String, sound>();
    public ArrayList<sciezka> sciezki = new ArrayList<sciezka>();
    public Hashtable<String, sciezka> scieżkiZId = new Hashtable<String, sciezka>();
    public static float tempo = 120;
    public static float kHz = 48f;
    int pusteID = 0;
    public static float Hz = kHz * 1000;
    public ArrayList<DrumJeden> DrumLista = new ArrayList<DrumJeden>();
    public Hashtable<String, FalaNiestandardowa> fale = new Hashtable<String, FalaNiestandardowa>();

    public plik(String a, boolean czyXML)
    {
        if (a != "")
        {
            this.URL = URLStatyczne  = a;
            try
            {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                xml = dBuilder.parse(new InputSource(new StringReader(a)));
            }
            catch (Throwable e)
            {
return;//TODO info o błędzie
            }
            try
            {
                tempo = Float.parseFloat (xml.getElementsByTagName("file").item(0).getAttributes().getNamedItem("tempo").getNodeValue());
            }
            catch(Throwable e) {tempo=120; }
            dekoduj();
        }
    }
}
