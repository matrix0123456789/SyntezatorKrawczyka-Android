package com.jaebe.syntezatorkrawczyka;

/**
 * Created by Mateusz on 09.08.14.
 */
public class sample
        {
public String plik;
public float note;
public int częstotliwość;
public float accept;
public float[,] fala;
public byte kanały = 1;
private string p;

public sample(string p)
        {
        {
        plik = p;
        System.Threading.ThreadPool.QueueUserWorkItem((Action) =>
        {
        BinaryReader zawartość;
        try
        {
        zawartość = new BinaryReader((new StreamReader((new Regex("\\\\([^\\\\]*)$")).Replace(Syntezator_Krawczyka.plik.URLStatyczne, "\\" + plik), Encoding.ASCII)).BaseStream);
        }
        catch (Exception e)
        {
        try
        {
        zawartość = new BinaryReader((new StreamReader(plik, Encoding.ASCII)).BaseStream);
        }
        catch (Exception e2)
        {
        MessageBox.Show(e2.ToString(), "Błąd", MessageBoxButton.OK, MessageBoxImage.Error);
        return;
        }
        }
        zawartość.BaseStream.Position = 24;
        częstotliwość = zawartość.ReadInt32();
        bitrate = 8 * zawartość.ReadInt32() / częstotliwość;

        zawartość.BaseStream.Position = 44;
        if (bitrate == 32)
        {
        fala = new float[1, (zawartość.BaseStream.Length - zawartość.BaseStream.Position) / 4];
        for (i = 0; i < fala.Length; i++)
        {
        fala[0, i] = zawartość.ReadInt32() / (256f * 256f * 128f);
        }
        }
        if (bitrate == 16)
        {
        fala = new float[1, (zawartość.BaseStream.Length - zawartość.BaseStream.Position) / 2];
        for (i = 0; i < fala.Length; i++)
        {
        fala[0, i] = zawartość.ReadInt16() / 32768f;
        }
        }
        if (bitrate == 8)
        {
        fala = new float[1, zawartość.BaseStream.Length - zawartość.BaseStream.Position];
        for (i = 0; i < fala.Length; i++)
        {
        fala[0, i] = zawartość.ReadSByte() / 128f;
        }
        }
        });
        }
        }
        long i = 0;
public int bitrate { get; set; }
public long value
        {
        get
        {
        return i;
        }
        }
public long max
        {
        get
        {
        if (fala == null)
        return 0;
        return fala.GetLongLength(1);
        }
        }
        }