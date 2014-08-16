package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

/**
 * Created by Mateusz on 08.08.14.
 */
public class DrumJeden
{
    public Node xml;
    public nuta nuta = null;
    public soundStart sekw;
    public float wysokość;
    public short oktawy;
    public float czestotliwosc;
    public DrumJeden() { }
    public DrumJeden(Node n)
    {
        this.xml = n;
        wysokość = Float.parseFloat(n.getAttributes().getNamedItem("note").getNodeValue());
        oktawy = Short.parseShort(n.getAttributes().getNamedItem("oktawy").getNodeValue());
        czestotliwosc = Float.parseFloat(n.getAttributes().getNamedItem("frequency").getNodeValue());
    }

    void nowyXML()
{
    xml = Statyczne.otwartyplik.xml.createElement("drum");
    xml.getAttributes().setNamedItem(Statyczne.otwartyplik.xml.createAttribute("note"));
    xml.getAttributes().setNamedItem(Statyczne.otwartyplik.xml.createAttribute("sound"));
}

}