package com.example.snavadogru.DenemeTakip;

import java.util.ArrayList;

public class tytDenemesi {
    Double Turkce,Mat,Fen,Sosyal,totalNet;
    String Name,Yayin;
    private int denemeNumber;
    public tytDenemesi(ArrayList<String> Info, ArrayList<String> Netler,int number){
            Name=Info.get(0); Yayin=Info.get(1);
            denemeNumber=number;
            Turkce=Double.parseDouble(Netler.get(0));
            Mat=Double.parseDouble(Netler.get(2));
            Fen=Double.parseDouble(Netler.get(3));
            Sosyal=Double.parseDouble(Netler.get(1));
            totalNet=Turkce+Mat+Fen+Sosyal;
    }

    public int getDenemeNumber() {
        return denemeNumber;
    }
}
