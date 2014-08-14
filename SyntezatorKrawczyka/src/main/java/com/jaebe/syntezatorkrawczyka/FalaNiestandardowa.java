package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Mateusz on 13.08.14.
 */
interface FalaNiestandardowa
{
    float[] generujJedenPrzebieg(long długość);
 public Node getXml();
    public String getNazwa();
    public ArrayList<Integer> getGpgpu() ;
}
