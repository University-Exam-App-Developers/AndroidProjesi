package ykskoc.example.snavadogru.FirstScreen;

import java.util.ArrayList;

public class tytDenemesi {
    public Double Turkce;
    public Double Mat;
    public Double Fen;
    public Double Sosyal;
    public Double totalNet;
    public String Name;
    public String Yayin;
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
