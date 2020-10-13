package ykskoc.example.snavadogru.SikSorulanSoru;

public class Sik_Soru {
    private  String soru;
    private String cevap;
    private int id;

    public Sik_Soru() {
        this.soru = "soru";
        this.cevap = "cevap";
        this.id = 1;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sik_Soru(String Metin) {
        String nokta = new String(".");
        String soru_isareti= new String("?");

        int noktaNum=Metin.indexOf(nokta);
        int indexsoru_isareti = Metin.indexOf(soru_isareti);

        this.soru =  Metin.substring(noktaNum+1,indexsoru_isareti+1);
        this.cevap = Metin.substring(indexsoru_isareti+1,Metin.length());
        this.id =Integer.parseInt( Metin.substring(0,noktaNum));

    }
}
