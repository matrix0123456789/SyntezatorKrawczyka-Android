package com.jaebe.syntezatorkrawczyka;

/**
 * Created by Mateusz on 16.08.14.
 */
public class funkcje {

    static public boolean graj(float[][] fala)
    {
        byte[] bufor = new byte[fala.length*fala[0].length * 2];
        for (int i = 0; i < fala[0].length; i++)
        {
            if (fala[0][i] > 1)
            fala[0][i] = 1;
            else if (fala[0][i] < -1)
            fala[0][ i] = -1;
            if (fala[1][ i] > 1)
            fala[1][ i] = 1;
            else if (fala[1][ i] < -1)
            fala[1][ i] = -1;
            short liczba;
            liczba = (short)(fala[0][i] * 32766);
            bufor[4 * i + 1] = (byte)Math.floor(liczba / 256f);
            bufor[4 * i] = (byte)(liczba % 256);
            liczba = (short)(fala[1][i] * Short.MAX_VALUE);
            bufor[4 * i + 3] = (byte)Math.floor(liczba / 256f);
            bufor[4 * i+2] = (byte)(liczba % 256);
        }
        bufordodaj:
        try
        {
            Statyczne.bufor.AddSamples(bufor, 0, fala.Length * 2);
        }
        catch (InvalidOperationException)
        {
            Statyczne.bufor.ClearBuffer();
            goto bufordodaj;
        }

        return false;


    }
    /// <summary>
    /// Zapisuje dźwięk do pliku wave
    /// </summary>
    /// <param name="fala"></param>
    /// <param name="głośność"></param>
    /// <param name="plik">ścierzka do pliku</param>
   /* static public void zapisz(float[][] fala, String plik) throws IOException {

        wave(fala, (new FileWriter(plik)));



    }*/
    /// <summary>
    /// Tworzy plik wave
    /// </summary>
    /// <param name="writer">Strumień do zapisu</param>
    /// <param name="fala">fala dżwiękowa stereo</param>
    /// <returns>Tablica odpowiadająca plikowi wave</returns>

   /* static public void wave(float[][] fala, FileWriter writer)
    {
        char[] pus = Syntezator_Krawczyka.Properties.Resources.czysty.ToCharArray();
        byte[] puste = new byte[pus.Length-1];
        var pusteLength = pus.Length + fala.Length * 2 - 2;
        for (int i = 0; i < pus.Length && i < puste.Length; i++)
        {
            puste[i] = (byte)pus[i];
        }
        byte[] rozmiar = BitConverter.GetBytes(pusteLength - 8);
        puste[4] = rozmiar[0];
        puste[5] = rozmiar[1];
        puste[6] = rozmiar[2];
        puste[7] = rozmiar[3];
        byte[] rozmiar2 = BitConverter.GetBytes(fala.Length * 2);
        puste[40] = rozmiar2[0];
        puste[41] = rozmiar2[1];
        puste[42] = rozmiar2[2];
        puste[43] = rozmiar2[3];


        byte[] czestotliwosc = BitConverter.GetBytes((int)plik.Hz);
        puste[24] = czestotliwosc[0];
        puste[25] = czestotliwosc[1];
        puste[26] = czestotliwosc[2];
        puste[27] = czestotliwosc[3];
        byte[] czestotliwosc2 = BitConverter.GetBytes((int)plik.Hz * 4);
        puste[28] = czestotliwosc2[0];
        puste[29] = czestotliwosc2[1];
        puste[30] = czestotliwosc2[2];
        puste[31] = czestotliwosc2[3];
        puste[32] = 4;



        puste[22] = 2;

        writer.Write(puste);
        long falai = 0;
        for (int z = pus.Length + 2; z < pusteLength && fala.LongLength/2 > falai; z = z + 4)
        {

            if (fala[0, falai] > 1)
            fala[0, falai] = 1;
            else if (fala[0, falai] < -1)
            fala[0, falai] = -1;
            if (fala[1, falai] > 1)
            fala[1, falai] = 1;
            else if (fala[1, falai] < -1)
            fala[1, falai] = -1;

               /* writer.Write((byte)((fala[0, falai] * 128 * 256) % 256));
                if (fala[0, falai] >= 0)
                    writer.Write((byte)(fala[0, falai] * 128));
                else
                    writer.Write((byte)((1 + fala[0, falai]) * 128 + 128));
                writer.Write((byte)((fala[1, falai] * 128 * 256) % 256));
                if (fala[1, falai] >= 0)
                    writer.Write((byte)(fala[1, falai] * 128));
                else
                    writer.Write((byte)((1 + fala[1, falai]) * 128 + 128));*//*
            writer.Write((short)(fala[0, falai] * 32767));
            writer.Write((short)(fala[1, falai] * 32767));
            //puste[z + 1] = 0;
            falai++;

        }
        writer.Flush();
        writer.Close();
        //return puste;
    }*/
    /// <summary>
    /// Wylicza częstotliwość nuty na podstawie oktawy i tonu
    /// </summary>
    /// <param name="oktawa"></param>
    /// <param name="ton">Ilość całych tonów powyrzej C (C to 0)</param>
    /// <returns>Częstotliwość w hercach</returns>
    public static double częstotliwość(short oktawa, float ton)
    {
        return 130.812783 * Math.pow(2, (oktawa) + (ton / 6));
    }
    public static double ilepróbek(short oktawa, float ton)
    {
        return plik.Hz/(130.812783 * Math.pow(2, (oktawa) + (ton / 6)));
    }
    public static double ton(double ileprobek)
    {
        return Math.log10(plik.Hz / (130.812783 * ileprobek))/Math.log10(2)*6;
    }

    /*public static Node klonujXML(Document doc, System.Xml.XmlNode wej)
    {
        if(wej.NodeType==XmlNodeType.Element)
        {
            var ret = doc.CreateElement(wej.Name);
            foreach (XmlAttribute x in wej.Attributes)
            {
                XmlAttribute nowyAtr=doc.CreateAttribute(x.Name);
                nowyAtr.Value=x.Value;
                ret.Attributes.Append(nowyAtr);
            }
            foreach (XmlNode x in wej.ChildNodes)
            {
                ret.AppendChild(klonujXML(doc, x));
            }
            return ret;
        }
        else return null;
    }*/

    /* static String sekundy(int p)
    {
        String ret="";
        var sek = (int)(p / plik.Hz);
        var min = sek / 60;
        sek = sek % 60;
        var godz = min / 60;
        min = min % 60;
        if (godz > 9)
            ret = godz.ToString() + ':';
        else if (godz > 0)
            ret = '0' + godz.ToString() + ':';
        if (min > 9)
            ret += min.ToString() + ':';
        else
            ret += '0' + min.ToString() + ':';
        if (sek > 9)
            ret += sek.ToString();
        else
            ret += '0' + sek.ToString();
        return ret;

    }*/
}

