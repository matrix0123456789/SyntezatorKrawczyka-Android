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
        wysokość = float.Parse(n.getAttributes().GetNamedItem("note").Value);
        oktawy = short.Parse(n.getAttributes().GetNamedItem("oktawy").Value);
        czestotliwosc = float.Parse(n.getAttributes().GetNamedItem("frequency").Value);
    }

    void nowyXML()
{
    xml = Statyczne.otwartyplik.xml.CreateElement("drum");
    xml.getAttributes().Append(Statyczne.otwartyplik.xml.CreateAttribute("note"));
    xml.getAttributes().Append(Statyczne.otwartyplik.xml.CreateAttribute("sound"));
}

}