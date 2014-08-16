package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Created by Mateusz on 09.08.14.
 */
public class jedenSample implements  IodDo {
    public sample sample;
    public float głośność = 1;
    public int delay;
    public Node xml;

    public jedenSample()
    {
        granie.graniestart();
        xml = Statyczne.otwartyplik.xml.createElement("sample");
        Statyczne.otwartyplik.xml.getDocumentElement().appendChild(xml);
    }

    public jedenSample(String x)

    {
        this();
        if (Statyczne.otwartyplik.wszytskieSamplePliki.containsKey(x))
            sample = Statyczne.otwartyplik.wszytskieSamplePliki.get(x);
        else
        {
            sample = new sample(x);
            Statyczne.otwartyplik.wszytskieSamplePliki.put(x, sample);
        }

        Attr atrybut = Statyczne.otwartyplik.xml.createAttribute("file");
        atrybut.setNodeValue(x);
        xml.getAttributes().setNamedItem(atrybut);
    }

    public jedenSample(Node xml)

    {
        this();
        granie.graniestart();
        this.xml = xml;

        if (xml.getAttributes().getNamedItem("delay") != null)
        {
            delay = (int)(Float.parseFloat((xml.getAttributes().getNamedItem("delay").getNodeValue())) * plik.Hz * 60 / plik.tempo);
        }

        if (Statyczne.otwartyplik.wszytskieSamplePliki.containsKey(xml.getAttributes().getNamedItem("file").getNodeValue()))
            sample = Statyczne.otwartyplik.wszytskieSamplePliki.get(xml.getAttributes().getNamedItem("file").getNodeValue());
        else
        {
            sample = new sample(xml.getAttributes().getNamedItem("file").getNodeValue());
            Statyczne.otwartyplik.wszytskieSamplePliki.put(xml.getAttributes().getNamedItem("file").getNodeValue(), sample);
        }
    }
     void działaj()
    {

        float[][] dane;
        int dl;
        while (sample.fala == null)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        int l = sample.fala[0].length;
        float zmianaCzęstotliwości = plik.Hz / sample.częstotliwość;
        if (zmianaCzęstotliwości == 1)
        {
            dane = sample.fala;
            dl = l;
        }
        else
        {
            dl = (int)Math.ceil(sample.fala[0].length * zmianaCzęstotliwości);
            dane = new float[sample.fala.length][dl];
            for (byte k = 0; k < sample.kanały; k++)
                for (int i2 = 0; l > i2; i2++)
                {
                    float dz = (i2) / zmianaCzęstotliwości;
                    if (dz + 1 < l)
                        dane[k][i2] = ((sample.fala[k][(int)Math.floor(dz)] * ((i2 / zmianaCzęstotliwości) % 1)) + (sample.fala[k][(int)Math.ceil(dz)] * (1 - (i2 / zmianaCzęstotliwości) % 1))) * głośność;

                    else if ((int)Math.floor(dz) + 1 < l)
                {
                    dane[k][i2] = (sample.fala[k][(int)Math.floor(dz)]) * głośność;
                }
                    //debugowanie


                }
        }


        if (granie.wynik == null) { }
            /*lock (granie.grają)
            {
                if (granie.grają.containsKey(input.id))
                {
                    grają[input.id].dźwięk = (input).dane;
                    grają[input.id].nuta = input;
                }
                else
                    grają.add(input.id, new gra(input));
            }*/
        else
        {


            int i = delay;
            int opt1 = -delay;

            int opt3 = dl - opt1;

            {
                    /*if (głośność == 1)

                        if (input.balans0 == 1 && input.balans1 == 1)
                            for (; i < opt3; i++)
                            {
                                wynik[0, i] += input.dane[i + opt1];
                                wynik[1, i] += input.dane[i + opt1];
                            }
                        else
                            for (; i < opt3; i++)
                            {
                                wynik[0, i] += input.dane[i + opt1] * input.balans0;
                                wynik[1, i] += input.dane[i + opt1] * input.balans1;
                            }
                    else
                    {
                        var mn0 = input.głośność * input.balans0;
                        var mn1 = input.głośność * input.balans1;
                        for (; i < opt3; i++)
                        {
                            wynik[0, i] += input.dane[i + opt1] * mn0;
                            wynik[1, i] += input.dane[i + opt1] * mn1;
                        }
                    }*/
                if (sample.kanały == 1)
                {
                    for (; i < opt3; i++)
                    {
                        granie.wynik[0][i] += dane[0][ i + opt1] * głośność;
                        granie.wynik[1][i] += dane[0][ i + opt1] * głośność;
                    }
                }
                else
                {
                    for (; i < opt3; i++)
                    {
                        granie.wynik[0][ i] += dane[0][ i + opt1] * głośność;
                        granie.wynik[1][ i] += dane[1][ i + opt1] * głośność;
                    }
                }



        }

    }}


    @Override
    public long getDelay() {
        return delay;
    }

    public long getDlugosc()
    {

            if (sample.fala == null)
                return 0;
            return sample.fala[0].length;

    }}
