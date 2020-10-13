package ykskoc.example.snavadogru.DenemeTakip;

import java.util.ArrayList;

public class eaDenemesi {
    Double Mat,edebiyat,cografya,tarih,totalNet;
    String Name,Yayin;
    private int denemeNumber;
    public eaDenemesi(ArrayList<String> Info, ArrayList<String> Netler,int number){
        Name=Info.get(0); Yayin=Info.get(1);
        Mat=Double.parseDouble(Netler.get(0));
        edebiyat=Double.parseDouble(Netler.get(1));
        cografya=Double.parseDouble(Netler.get(3));
        tarih=Double.parseDouble(Netler.get(2));
        totalNet=Mat+edebiyat+cografya+tarih;
        denemeNumber=number;
    }

    public int getDenemeNumber() {
        return denemeNumber;
    }
}
