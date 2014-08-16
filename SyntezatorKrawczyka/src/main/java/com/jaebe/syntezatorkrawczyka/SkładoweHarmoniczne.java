package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Mateusz on 09.08.14.
 */
public class SkładoweHarmoniczne implements FalaNiestandardowa
        {

/*public ArrayList<Integer> gpgpu
        {
        get
        {
        var dane = new List<int>();
        for (int i = 0; i < Składowe.Count; i++)
        {
        dane.Add(Składowe[i].GetHashCode());
        }
        return dane;
        }
        }*/
public ArrayList<Float> Składowe = new ArrayList<Float>();
public String nazwa;
public Node getXml()
        {
        {
        Node ret = Statyczne.otwartyplik.xml.createElement("wave");
        Node atrTyp = Statyczne.otwartyplik.xml.createAttribute("type");
        atrTyp.setNodeValue("skladoweharmoniczne");
        ret.getAttributes().setNamedItem(atrTyp);
        if (nazwa != null)
        {
        Node atrNazwa = Statyczne.otwartyplik.xml.createAttribute("name");
        atrNazwa.setNodeValue( nazwa);
        ret.getAttributes().setNamedItem(atrNazwa);
        }
        for (int i = 0; i < Składowe.size(); i++)
        {
        Node skl = Statyczne.otwartyplik.xml.createElement("skladowa");
        Node atrNr = Statyczne.otwartyplik.xml.createAttribute("number");
        atrNr.setNodeValue(((Integer) i).toString());
        skl.getAttributes().setNamedItem(atrNr);
        Node atrValue = Statyczne.otwartyplik.xml.createAttribute("value");
        atrValue.setNodeValue(Składowe.get(i).toString());
        skl.getAttributes().setNamedItem(atrValue);
        ret.appendChild(skl);
        } zapisanePojedyńczePrzebiegi = new Hashtable<Short, float[]>();
        return ret;

        }
        }
public SkładoweHarmoniczne()
        {
        Składowe.add(1f);
        zapisanePojedyńczePrzebiegi = new Hashtable<Short, float[]>();
        }
public SkładoweHarmoniczne(Node xml)
        {

        nazwa = xml.getAttributes().getNamedItem("name").getNodeValue();
            Hashtable<Integer, Float> słownik = new Hashtable<Integer, Float>();
        for (int i = 0; i < xml.getChildNodes().getLength(); i++)
        {
        słownik.put(Integer.parseInt(xml.getChildNodes().item(i).getAttributes().getNamedItem("number").getNodeValue()), float.Parse(xml.ChildNodes[i].Attributes["value"].Value, CultureInfo.InvariantCulture));
        }


        var max = słownik.Keys.Max();
        for (var i = 0; i <= max; i++)
        {
        Składowe.Add(słownik[i]);
        }
        }
public void czyść()
        {
        zapisanePojedyńczePrzebiegi = new Hashtable<Short, float[]>();
        }
        Hashtable<Short, float[]> zapisanePojedyńczePrzebiegi = new Hashtable<Short, float[]>();
public float[] generujJedenPrzebieg(long długość)
        {

        if (zapisanePojedyńczePrzebiegi.containsKey((short)długość))
        return zapisanePojedyńczePrzebiegi[(short)długość];
        var ret = new float[długość];
        for (int i = 0; i < Składowe.Count; i++)
        {
        var stała = Math.PI * 2 / długość * (i + 1);
        var głośność = Składowe[i];
        for (int i2 = 0; i2 < długość; i2++)
        {
        ret[i2] += (float)Math.Sin(i2 * stała) * głośność;
        }
        }
        try { zapisanePojedyńczePrzebiegi.Add((short)długość, ret); }
        catch (Throwable e) { }
        return ret;
        }

        }
