package com.jaebe.syntezatorkrawczyka;

import org.w3c.dom.Node;

import java.util.Hashtable;

/**
 * Created by Mateusz on 07.08.14.
 */
public class sound extends Hashtable<String, moduł>
{
public soundStart sekw;
public String nazwa;
//public Instrument UI;
public sound() { }
public Node xml;
public sound(String nazwa, Node n)
        {
        this.nazwa = nazwa;
        xml = n;
        }
        }

