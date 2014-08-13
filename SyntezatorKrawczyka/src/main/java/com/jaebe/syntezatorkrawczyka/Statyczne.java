package com.jaebe.syntezatorkrawczyka;

/**
 * Created by Mateusz on 28.07.14.
 */
public class Statyczne {
    public static PolaczenieHTTP serwer=new PolaczenieHTTP();
    public static plik otwartyplik=new plik("<?xml version=\"1.0\" encoding=\"UTF-8\"?><file tempo=\"120\"><sound type=\"syntezator-krawczyka\" id=\"proste\"><module type=\"sekwencer\" id=\"player1\" output=\"oscylator1\" oktawy=\"0\"></module><module type=\"oscylator\" id=\"oscylator1\" output=\"granie1\" typ=\"trójkątna\" balans=\"0\" gladkosc=\"1\" A=\"0\" D=\"0\" S=\"0.2\" R=\"0\"></module><module type=\"granie\" id=\"granie1\" głośność=\"1.0\"></module></sound><sound type=\"syntezator-krawczyka\" id=\"proste1\"><module type=\"sekwencer\" id=\"player1\" output=\"oscylator1\" oktawy=\"0\" /><module type=\"oscylator\" id=\"oscylator1\" output=\"granie1\" typ=\"sinusoidalna\" balans=\"0\" gladkosc=\"1\" A=\"0\" D=\"0\" S=\"0.2\" R=\"0\" /><module type=\"granie\" id=\"granie1\" głośność=\"1.0\" /></sound></file>", true);
}
