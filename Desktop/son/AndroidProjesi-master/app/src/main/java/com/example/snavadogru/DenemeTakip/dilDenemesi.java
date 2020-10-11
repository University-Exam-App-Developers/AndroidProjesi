package com.example.snavadogru.DenemeTakip;

import java.util.ArrayList;

public class dilDenemesi {
    private int denemeNumber;
    float totalNet;
    String Name,Yayin;
    public dilDenemesi(ArrayList<String> Info,String  Net,int id){
        Name=Info.get(0); Yayin=Info.get(1); denemeNumber = id;
        totalNet=Float.parseFloat(Net);
    }

    public int getDenemeNumber() {
        return denemeNumber;
    }

    public void setDenemeNumber(int ID) {
        this.denemeNumber = ID;
    }
}
