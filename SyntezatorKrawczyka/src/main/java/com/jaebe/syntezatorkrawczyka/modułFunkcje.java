package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.Hashtable;

class modułFunkcje
{
    static public void czytajXML(Hashtable<String, String> ustawienia, Node XML)
    {
        for (int i = 0; i < XML.getAttributes().getLength(); i++)
        {
            if (!XML.getAttributes().item(i).getNodeName().equals("id") && !XML.getAttributes().item(i).getNodeName().equals("name") && !XML.getAttributes().item(i).getNodeName().equals("output"))
            {
                if(ustawienia.containsKey(XML.getAttributes().item(i).getNodeName()))
                    ustawienia.remove(XML.getAttributes().item(i).getNodeName());

                    ustawienia.put(XML.getAttributes().item(i).getNodeName(), XML.getAttributes().item(i).getNodeValue());
            }
        }
    }
    static public void zapiszXML(Hashtable<String, String> ustawienia, Node XML)
    {
                for(int i=0;i<ustawienia.size();i++)
        {
            String Key=ustawienia.keys().nextElement();
            String Value=ustawienia.get(Key);
            try
            {
                XML.getAttributes().getNamedItem(Key).setNodeValue(Value);
            }
            catch(Throwable e)
            {
                Node atr = XML.getOwnerDocument().createAttribute(Key);
                atr.setNodeValue(Value);
                XML.getAttributes().setNamedItem(atr);

            }
        }
    }
}