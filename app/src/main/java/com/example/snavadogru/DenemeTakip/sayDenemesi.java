package com.example.snavadogru.DenemeTakip;

import java.util.ArrayList;

public class sayDenemesi {
    Double Mat,Fizik,Kimya,Bio,totalNet;
    String Name,Yayin;
    private int denemeNumber;
    public sayDenemesi(ArrayList<String> Info, ArrayList<String> Netler,int counter){
        denemeNumber=counter;
        Name=Info.get(0); Yayin=Info.get(1);
        Mat=Double.parseDouble(Netler.get(0));
        Fizik=Double.parseDouble(Netler.get(1));
        Kimya=Double.parseDouble(Netler.get(2));
        Bio=Double.parseDouble(Netler.get(3));
        totalNet=Mat+Fizik+Kimya+Bio;
    }

    public int getDenemeNumber() {
        return denemeNumber;
    }
}
