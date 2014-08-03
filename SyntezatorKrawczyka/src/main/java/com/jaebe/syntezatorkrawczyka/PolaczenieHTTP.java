package com.jaebe.syntezatorkrawczyka;

/**
 * Created by Mateusz on 28.07.14.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mateusz on 26.07.14.
 */
public class PolaczenieHTTP {
    public String loguj(String login, String haslo)
    {
        try{
            Socket soc = new Socket("syntezator.aq.pl", 80);
            OutputStream output = soc.getOutputStream();
            InputStream input = soc.getInputStream();
            output.write(("POST /json.php HTTP/1.1\r\n" +
                    "Host: syntezator.aq.pl\r\n" +
                    "User-Agent: Syntezator Krawczyka (Android)\r\n" +
                    "\r\nb=" + "{\"login\":\"" + login + "\",\"haslo\":\"" + haslo + "\"}").getBytes());
            output.close();
            Scanner sc=new Scanner(input);
            String linia;
            while(( linia=sc.nextLine()).length()>2)
            {

            }
            String ret=sc.nextLine();
            Matcher match=Regexlogowanie.matcher(ret);
            String odp=match.group(1);
            return odp;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "blad";

    }
    final static Pattern Regexlogowanie =  Pattern.compile("{\"status\":\"([^\"]*)\"(,\"id\":\"([0-9]+)\")?}");
    final static Pattern RegexUtwory = Pattern.compile("\"utwory\":\\[(.*)\\]");
}
