package ykskoc.example.snavadogru.DenemeTakip;

import java.util.ArrayList;

public class sozDenemesi {
    Double edebiyat,tarih_1,tarih_2,cog_1,cog_2,felsefe,din,totalNet;
    String Name,Yayin;
    private int denemeNumber;
    public sozDenemesi(ArrayList<String> Info, ArrayList<String> Netler, int counter){
        denemeNumber=counter;
        Name=Info.get(0); Yayin=Info.get(1);
        edebiyat=Double.parseDouble(Netler.get(0));
        tarih_1=Double.parseDouble(Netler.get(1));
        cog_1=Double.parseDouble(Netler.get(2));
        tarih_2=Double.parseDouble(Netler.get(3));
        cog_2=Double.parseDouble(Netler.get(4));
        felsefe=Double.parseDouble(Netler.get(5));
        din=Double.parseDouble(Netler.get(6));
        totalNet=edebiyat+tarih_1+cog_1+tarih_2+cog_2+felsefe+din;
    }

    public int getDenemeNumber() {
        return denemeNumber;
    }
}
