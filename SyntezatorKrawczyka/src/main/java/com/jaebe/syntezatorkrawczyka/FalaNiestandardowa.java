package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

/**
 * Created by Mateusz on 13.08.14.
 */
interface FalaNiestandardowa {
    float[] generujJedenPrzebieg(int długość);

    public Node getXml();

    public String getNazwa();
}
