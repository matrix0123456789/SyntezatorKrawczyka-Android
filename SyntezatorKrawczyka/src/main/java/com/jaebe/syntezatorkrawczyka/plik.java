package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mateusz on 07.08.14.
 */
public class plik {
    public static String URLStatyczne;
    public String URL;
    public Document xml;
    public Hashtable<String, sound> moduły = new Hashtable<String, sound>();
    public ArrayList<sciezka> sciezki = new ArrayList<sciezka>();
    public Hashtable<String, sciezka> scieżkiZId = new Hashtable<String, sciezka>();
    public static float tempo = 120;
    public static float kHz = 48f;
    int pusteID = 0;
    public static float Hz = kHz * 1000;
    public ArrayList<jedenSample> sameSample = new ArrayList<jedenSample>();
    public ArrayList<DrumJeden> DrumLista = new ArrayList<DrumJeden>();
    public Hashtable<String, FalaNiestandardowa> fale = new Hashtable<String, FalaNiestandardowa>();

    public plik(String a, boolean czyXML)
    {
        if (a != "")
        {
            this.URL = URLStatyczne  = a;
            try
            {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                xml = dBuilder.parse(new InputSource(new StringReader(a)));
            }
            catch (Throwable e)
            {
return;//TODO info o błędzie
            }
            try
            {
                tempo = Float.parseFloat (xml.getElementsByTagName("file").item(0).getAttributes().getNamedItem("tempo").getNodeValue());
            }
            catch(Throwable e) {tempo=120; }
            dekoduj();
        }
    }public void dekoduj()
    {
        Statyczne.otwartyplik = this;
        if (Statyczne.otwartyplik != null)
        {
            moduły.clear();
            sciezki.clear();
            scieżkiZId.clear();
            /*MainWindow.dispat.BeginInvoke(DispatcherPriority.Send, (ThreadStart)delegate()
            {
                for (int i = MainWindow.thi.pokaz.Children.Count - 1; i >= 0; i--)
                {
                    if (MainWindow.thi.pokaz.Children[i].GetType() != typeof(KlawiaturaKomputeraUI))
                    {
                        MainWindow.thi.pokaz.Children.Remove((UIElement)MainWindow.thi.pokaz.Children[i]);
                    }
                }
            });*///TODO interace
        }
        //MainWindow.ileScierzekWyswietla = 0;
        synchronized  (Statyczne.otwartyplik){
        try
        {
            NodeList listaSameSample = xml.getElementsByTagName("sample");
            for(int i=0;i<listaSameSample.getLength();i++)
            {
                if(listaSameSample.item(i).getAttributes().getNamedItem("file")!=null)
                {
                    jedenSample a = new jedenSample(listaSameSample.item(i));
                    Statyczne.otwartyplik.sameSample.add(a);
                }
            }
            NodeList listaWave = xml.getElementsByTagName("wave");
            for (int i = 0; i < listaWave.getLength(); i++)
            {
                if (listaWave.item(i).getAttributes().getNamedItem("type").getNodeValue() == "skladoweharmoniczne" && !fale.containsKey(listaWave.item(i).getAttributes().getNamedItem("name").getNodeValue()))
                    fale.put (listaWave.item(i).getAttributes().getNamedItem("name").getNodeValue(), new SkładoweHarmoniczne(listaWave.item(i)));
            }
            ArrayList<granie> granieLista = new ArrayList<granie>();
            dekoduj(xml.getElementsByTagName("sound"));
            ArrayList<Object[]> doSkopiowania = new ArrayList<Object[]>();
NodeList el=xml.getElementsByTagName("track");
                    for(int i=0;i<el.getLength();i++)
                        
            {
                Node n=el.item(i);
                boolean kopia = false;
                String id;
                if (n.getAttributes().getNamedItem("copy") != null)
                {
                    kopia = true;
                    id = n.getAttributes().getNamedItem("copy").getNodeValue();
                }
                else if (n.getAttributes().getNamedItem("id") == null)
                {
                    id = "track" + (pusteID++);
                }
                else
                    id = n.getAttributes().getNamedItem("id").getNodeValue();
                sciezka scie = new sciezka(id, n, kopia);
                sciezki.add(scie);
                if (n.getAttributes().getNamedItem("delay") != null)
                    scie.setDelay((int)(Float.parseFloat(n.getAttributes().getNamedItem("delay").getNodeValue()) * plik.Hz * 60 / tempo));
                if (kopia)
                {
                    Object[] ob = new Object[2];
                    ob[0] = scie;
                    ob[1] = n;
                    doSkopiowania.add(ob);
                }
                else
                    scieżkiZId.put(id, scie);
                        for(int i2=0;i2<n.getChildNodes().getLength();i2++)
                {
                    Node nutax=n.getChildNodes().item(i2);
                    if (nutax.getNodeName() == "nute")
                    {
                        try
                        {
                            nuta nu = new nuta(plik.Hz / funkcje.częstotliwość(Short.parseShort(nutax.getAttributes().getNamedItem("octave").Value, CultureInfo.InvariantCulture), float.Parse(nutax.getAttributes().getNamedItem("note").Value, CultureInfo.InvariantCulture)), (long)(float.Parse(nutax.getAttributes().getNamedItem("duration").Value, CultureInfo.InvariantCulture) * plik.Hz * 60 / tempo), (long)(float.Parse(nutax.getAttributes().getNamedItem("delay").Value, CultureInfo.InvariantCulture) * plik.Hz * 60 / tempo) + scie.delay);
                            scie.nuty.add(nu);
                        }
                        catch(Throwable e) { }
                    }
                }
                if (n.getAttributes().getNamedItem("sound") != null){
                            String[] elem=n.getAttributes().getNamedItem("sound").getNodeValue().split(" ");
                    for(int i2=0;i2<elem.length;i2++)
                {

                    try
                    {
                        // ((sekwencer)moduły[sound]["<sekwencer"]).sciezkaa = scie;
                        scie.sekw = (moduły.get(elem[i2]).sekw);
                        break;
                    }
                    catch (Throwable e) { }
                }
                }

            }
                    for(int i2=0;i2<doSkopiowania.size();i2++)
            {
                Object[] n=doSkopiowania.get(i2);
                if (((Node)n[1]).getAttributes().getNamedItem("copy") != null)
                if (scieżkiZId.containsKey(((Node)n[1]).getAttributes().getNamedItem("copy").getNodeValue()))
                {
                    ((sciezka)n[0]).kopia = true;
                    long delay;
                    if (((Node)n[1]).getAttributes().getNamedItem("delay") != null)
                    delay = (long)(Float.parseFloat((((Node)n[1]).getAttributes().getNamedItem("delay").getNodeValue())) * plik.Hz * 60 / tempo) - scieżkiZId[((Node)n[1]).getAttributes().getNamedItem("copy").getNodeValue()].delay;
                    else
                    delay = -scieżkiZId.get(((Node)n[1]).getAttributes().getNamedItem("copy").getNodeValue()).delay;

                    ((sciezka)n[0]).oryginał = scieżkiZId.get(((Node) n[1]).getAttributes().getNamedItem("copy").getNodeValue());
                            ArrayList<nuta> scZIdN=scieżkiZId.get(((Node)n[1]).getAttributes().getNamedItem("copy").getNodeValue()).nuty;
                            for(int i3=0;i3<scZIdN.size();i3++)
                    {
                        nuta xx = (nuta)scZIdN.get(i3).Clone();
                        xx.id = nuta.nowyid;
                        xx.opuznienie += delay;
                        ((sciezka)n[0]).nuty.add(xx);
                    }
                }
            }
            /*MainWindow.dispat.BeginInvoke(DispatcherPriority.Send, (ThreadStart)delegate()
            {
                        Object[] modT=moduły.values().toArray();
                        for(int i=0;i<modT.length;i++)
                {
sound z=(sound)modT[i];
                    z.UI = new Instrument(z.nazwa, z);
                    if (z.sekw.getClass() == InstrumentMidi.class)
                    {
                        z.UI.wewnętrzny.Children.add((z.sekw as InstrumentMidi).UI);
                    }
                    else
                    {
                        Object[] tabl=z.values().toArray();
                                for(int i2=0;i2<z.values().size();i2++)
                    {
                        try
                        {
                            z.UI.Children.add(((moduł)tabl[i2]).UI);
                        }
                        catch (Throwable e)
                        {

                        }
                    }}
                    if (!MainWindow.thi.pokaz.Children.Contains(z.UI))
                    {

                        MainWindow.thi.pokaz.Children.add(z.UI);
                    }
                }
            });*/
            for (int i = 0; i < granieLista.size(); i++)
                granieLista.get(i).analizujIleNutMusiByć();





                    NodeList elementy=xml.getElementsByTagName("drum");
            for(int i2=0;i2<elementy.getLength();i2++)
            {
                DrumJeden dr = new DrumJeden(elementy.item(i2));
                DrumLista.add(dr);
            }
        }
        catch (Exception e)
        {
            //MessageBox.Show(e.ToString(), "Błąd przy przetwarzaniu pliku", MessageBoxButton.OK, MessageBoxImage.Error);
        }

        //MainWindow.ileScierzekWyswietla = 0;
    }}


   /* public plik()
    {
        URL = null;
        xml = new XmlDocument();
        xml.LoadXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?><file></file>");
    }*/
    /// <summary>
    /// Wykorzystywane w matodzie zapisz()
    /// </summary>
    void uaktualnij()
    {
        foreach (var x in moduły)
        {
            foreach (var y in x.Value)
            {
                modułFunkcje.zapiszXML(y.Value.ustawienia, y.Value.XML);
            }
        }
        var listaWave = xml.GetElementsByTagName("wave");
        for (var i = listaWave.Count - 1; i >= 0; i--)
        {
            listaWave.Item(i).ParentNode.RemoveChild(listaWave.Item(i));
        }
        foreach (var x in fale)
        {
            xml.DocumentElement.AppendChild(x.Value.xml);
        }
    }
    public void zapisz()
    {
        uaktualnij();
        Microsoft.Win32.SaveFileDialog dialog = new Microsoft.Win32.SaveFileDialog();
        dialog.Filter = "Plik Syntezatora Krawczyka|*.synkra";
        dialog.ShowDialog();
        if (dialog.FileName != "")
        {
            System.IO.StreamWriter zapis = new System.IO.StreamWriter(dialog.FileName);
            zapis.Write(xml.OuterXml);
            zapis.Close();
        }
    }
    public void zapisz(string path)
    {
        uaktualnij();

        System.IO.StreamWriter zapis = new System.IO.StreamWriter(path);
        zapis.Write(xml.OuterXml);
        zapis.Close();

    }
    public byte[] zapiszDoZmiennej()
    {
        uaktualnij();
        return System.Text.Encoding.UTF8.GetBytes(xml.OuterXml);
    }
    public void dekoduj(NodeList a)
    {
                for(int i=0;i<a.getLength();i++)
        {
            dekoduj1(a.item(i));

        }
                NodeList b=xml.getElementsByTagName("sound");
        for(int i=0;i<b.getLength();i++)
        {
            dekoduj2(b.item(i));

        }
    }
    public void dekoduj(Node a)
    {
        dekoduj1(a);

        dekoduj2(a);


    }
    void dekoduj1(Node n)
    {
        if (n.getAttributes().getNamedItem("type").getNodeValue().equals("syntezator-krawczyka"))
        {
            moduły.put(n.getAttributes().getNamedItem("id").getNodeValue(), new sound(n.getAttributes().getNamedItem("id").getNodeValue(), n));

                    for(int i=0;i<n.getChildNodes().getLength();i++)
            {
                Node nn=n.getChildNodes().item(i);
                if (nn.getNodeName().equals("module"))
                {
                    switch (nn.getAttributes().getNamedItem("type").getNodeValue())
                    {
                        case "sekwencer":

                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw = new sekwencer();
                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw as sekwencer);
                            // moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())["<sekwencer"] = moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()];
                            break;
                        case "player":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new player());
                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put("<player", moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).get(nn.getAttributes().getNamedItem("id").getNodeValue()));
                            break;
                        case "granie":
                            var gr = new granie();

                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), gr);
                            // granieLista.add(gr);
                            break;
                        case "oscylator":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new oscylator());
                            break;
                        case "flanger":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new flanger());
                            break;
                        case "rozdzielacz":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new rozdzielacz());
                            break;
                        case "mikser":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new mikser());
                            break;
                        case "lfo":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new lfo());
                            break;
                        case "zmianaWysokości":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new zmianaWysokości());
                            break;
                        case "glosnosc":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new glosnosc());
                            break;
                        case "poglos":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new pogłos());
                            break;
                        case "cutoff":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new cutoff());
                            break;
                        case "generatorObwiedniFiltru":


                            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).put(nn.getAttributes().getNamedItem("id").getNodeValue(), new generatorObwiedniFiltru());
                            break;
                        default:
                            continue;
                    }
                    moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()].XML = nn;
                    if (moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()].ustawienia == null)
                    { }
                    modułFunkcje.czytajXML(moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()].ustawienia, nn);
                    moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()].akt();
                }
            }
        }
        else if (n.getAttributes().getNamedItem("type").getNodeValue().equals("samples"))
        {
            moduły.put(n.getAttributes().getNamedItem("id").Value, new sound(n.getAttributes().getNamedItem("id").Value, n));
            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw = new sampler();
            if (n.getAttributes().getNamedItem("volume") != null)
                (moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw as sampler).głośność = float.Parse(n.getAttributes().getNamedItem("volume").Value, CultureInfo.InvariantCulture);

            for(int i=0;i<n.getChildNodes().getLength();i++)
            {
                Node nn=n.getChildNodes().item(i);

                if (nn.getNodeName() == "sample")
                {
                    sample sam;
                    if (nn.getAttributes().getNamedItem("file") != null)
                    {
                        if (wszytskieSamplePliki.containsKey(nn.getAttributes().getNamedItem("file").Value))
                            sam = wszytskieSamplePliki[nn.getAttributes().getNamedItem("file").Value];
                        else
                        {
                            sam = new sample(nn.getAttributes().getNamedItem("file").Value);
                            wszytskieSamplePliki.add(nn.getAttributes().getNamedItem("file").Value, sam);
                        }
                        if (nn.getAttributes().getNamedItem("note") != null)
                            sam.note = float.Parse(nn.getAttributes().getNamedItem("note").Value, CultureInfo.InvariantCulture);
                        if (nn.getAttributes().getNamedItem("accept") != null)
                            sam.accept = float.Parse(nn.getAttributes().getNamedItem("accept").Value, CultureInfo.InvariantCulture);
                        (moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw as sampler).sample.add(sam);
                    }

                }
            }
        }
        else if (n.getAttributes().getNamedItem("type").getNodeValue() == "midi")
        {
            moduły.put(n.getAttributes().getNamedItem("id").getNodeValue(), new sound(n.getAttributes().getNamedItem("id").Value, n));
            moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw = new InstrumentMidi();
            if (n.getAttributes().getNamedItem("volume") != null)
                (moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).sekw as sampler).głośność = float.Parse(n.getAttributes().getNamedItem("volume").Value, CultureInfo.InvariantCulture);

        }
    }
    void dekoduj2(Node n)
    {
        if (n.getAttributes().getNamedItem("type").getNodeValue() == "syntezator-krawczyka")
        {
            for(int i=0;i<n.getChildNodes().getLength();i++)
            {
                Node nn=n.getChildNodes().item(i);
                if (nn.getNodeName() == "module")
                {

                    if (nn.getAttributes().getNamedItem("output") != null)
                    {
                        String[] exp = nn.getAttributes().getNamedItem("output").getNodeValue().Split(' ');
                        for (int az = 0; az < exp.length; az++)
                        {
                            try
                            {
                                moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).get(nn.getAttributes().getNamedItem("id").getNodeValue()).wyjście[az].DrógiModół = moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[exp[az]];
                                moduły.get(n.getAttributes().getNamedItem("id").getNodeValue()).get(exp[az]).wejście.add(new Typ(moduły.get(n.getAttributes().getNamedItem("id").getNodeValue())[nn.getAttributes().getNamedItem("id").getNodeValue()]));
                            }
                            catch (Throwable e) { }
                        }
                    }

                }
            }
        }
    }

   /* void nowaScierzka()
    {
        var scierzkaXML = Statyczne.otwartyplik.xml.CreateElement("track");
        var atrybut1 = Statyczne.otwartyplik.xml.CreateAttribute("id");
        string id;
        do
        {
            id = atrybut1.Value = "track" + (pusteID++);
        } while (scieżkiZId.containsKey(id));
        scierzkaXML.getAttributes().SetNamedItem(atrybut1);
        var atrybut2 = Statyczne.otwartyplik.xml.CreateAttribute("delay");
        atrybut2.Value = "0";
        scierzkaXML.getAttributes().SetNamedItem(atrybut2);
        Statyczne.otwartyplik.xml.DocumentElement.AppendChild(scierzkaXML);

        sciezka scie = new sciezka(id, scierzkaXML, false);
        sciezki.add(scie);
        scieżkiZId.add(id, scie);
    }
    internal void duplikujScierzke(sciezka org)
    {
        var scierzkaXML = Statyczne.otwartyplik.xml.CreateElement("track");
        var atrybut1 = Statyczne.otwartyplik.xml.CreateAttribute("copy");
        var id = atrybut1.Value = org.nazwa;
        scierzkaXML.getAttributes().SetNamedItem(atrybut1);
        var atrybut2 = Statyczne.otwartyplik.xml.CreateAttribute("delay");
        atrybut2.Value = "0";
        scierzkaXML.getAttributes().SetNamedItem(atrybut2);
        Statyczne.otwartyplik.xml.DocumentElement.AppendChild(scierzkaXML);

        sciezka scie = new sciezka(id, scierzkaXML, true);
        scie.oryginał = org;
        sciezki.add(scie);
    }*/
    /*internal void grajStart()
    {
        granie.graniePrzy = 0;



        granie.generować[0] = false;
        granie.generować = new bool[1];
        granie.generować[0] = true;
        granie.liczbaGenerowanychMax = granie.liczbaGenerowanych = 0;
        granie.można = false;
        granie.grają.Clear();
        long długość = 0;
        foreach (var x in Statyczne.otwartyplik.sciezki)
        {
            if (x.sekw != null)
            {
                long długośćStart = 0;
                for (var i = 0; i < x.nuty.Count; i++)
                {

                    if (długośćStart < x.nuty[i].opuznienie + x.nuty[i].długość)
                        długośćStart = x.nuty[i].opuznienie + x.nuty[i].długość;

                }

                long długośćTeraz = x.sekw.symuluj(długośćStart);
                if (długośćTeraz > długość)
                    długość = długośćTeraz;
            }
        }
        granie.PlikDoZapisu = null;
        granie.granieMax = (int)długość;
        granie.wynik = new float[2, długość];
        List<nuta> lista = new List<nuta>();
        foreach (var x in Statyczne.otwartyplik.sciezki)
        {
            foreach (var nuta in x.nuty)
            {
                nuta.sekw = x.sekw;
                lista.add(nuta);
            }
        }
        lista.Sort(Syntezator_Krawczyka.nuta.sortuj);
        granie.granieNuty = lista.ToArray();
        granie.graniePlay = true;
        granie.liczbaGenerowanych += granie.granieNuty.Length;
        granie.liczbaGenerowanychMax += granie.granieNuty.Length;
        foreach (var x in granie.granieNuty)
        {
            lock (granie.grają)
            {

                var tabl = (nuta)x.Clone();
                tabl.grajDo = long.MaxValue;
                System.Threading.ThreadPool.QueueUserWorkItem((o) =>
                        {

                if (((bool[])o)[0] && x.sekw != null)
                {
                    x.sekw.działaj(tabl);
                    x.czyGotowe = true;
                    // granie.liveGraj();
                }

                lock (granie.liczbaGenerowanychBlokada)
                {
                    granie.liczbaGenerowanych--;
                    //if (!granie.można && granie.liczbaGenerowanych == 0)

                    //granie.grajcale(false);
                }
                }, granie.generować);
                var watek = new Thread(() => { var gen = granie.generować; while (granie.liveGraj() && gen[0]) { Thread.Sleep(1000); } });
                watek.Start();

            }
        }
    }*/

   /* public void generuj()
    {
        try
        {
            granie.liczbaGenerowanychMax = granie.liczbaGenerowanych = 0;
            granie.można = false;
            granie.grają.Clear();
            long długość = 0;
            foreach (var x in Statyczne.otwartyplik.sciezki)
            {
                if (x.sekw != null)
                {
                    long długośćStart = 0;
                    for (var i = 0; i < x.nuty.Count; i++)
                    {

                        if (długośćStart < x.nuty[i].opuznienie + x.nuty[i].długość)
                            długośćStart = x.nuty[i].opuznienie + x.nuty[i].długość;

                    }

                    long długośćTeraz = x.sekw.symuluj(długośćStart);
                    if (długośćTeraz > długość)
                        długość = długośćTeraz;
                }
            }
            foreach (var x in Statyczne.otwartyplik.sameSample)
            {
                if (x.delay + x.dlugosc > długość)
                    długość = x.delay + x.dlugosc;
            }
            granie.PlikDoZapisu = null;
            granie.wynik = new float[2, długość];
            foreach (var x in Statyczne.otwartyplik.sciezki)
            {
                x.działaj();
                //akt(null);
            }
            foreach (var x in Statyczne.otwartyplik.sameSample)
            {
                x.działaj();

            }
            var dialog = new SaveFileDialog();
            dialog.Filter = "Plik muzyczny|*.wav;*.wave";
            dialog.ShowDialog();
            granie.PlikDoZapisu = dialog.FileName;
        }
        catch (Exception e1) { MessageBox.Show("Błąd przy zapisie dźwięku", e1.ToString()); }
    }    */


}
