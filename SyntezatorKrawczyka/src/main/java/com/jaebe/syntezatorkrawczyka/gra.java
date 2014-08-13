package com.jaebe.syntezatorkrawczyka;

/**
 * Created by Mateusz on 13.08.14.
 */
public class gra
{
    public float[] dźwięk;
    public long zagrano=0;
    public nuta nuta;
    public gra(float[] dźwięk)
    { this.dźwięk = dźwięk; }
    public gra(nuta nuta)
    {
        this.nuta = nuta;
        this.dźwięk = nuta.dane;
        zagrano = -nuta.opuznienie;
    }
    public String ToString()
    {
        return nuta.toString();
    }
}