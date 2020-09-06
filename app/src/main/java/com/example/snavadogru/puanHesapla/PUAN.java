package com.example.snavadogru.puanHesapla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snavadogru.R;

public class PUAN extends AppCompatActivity{
    EditText diplomaNotu;
    double diploma;
    EditText[] tytEdit=new EditText[8];
    int[] tytdy = new int[8];
    EditText[] aytSayEdit=new EditText[8];
    int[] aytSaydy = new int[8];
    EditText[] aytSozEdit=new EditText[8];
    int[] aytSozdy = new int[8];
    EditText[] aytEaEdit=new EditText[6];
    int[] aytEady = new int[6];
    EditText yabanciDildogru,yabanciDilyanlis;
    int[] aytDildy = new int[2];
    Button hesapla,sifirla;
    TextView[] tytNets = new TextView[4];
    double[] tytNet = new double[4];
    TextView[] aytNets = new TextView[12];
    double[] aytNet = new double[12];
    double[] sonuclar = new double[6];

    final double tytTurkceKs=3.108,tytMatKs=3.728,tytFenKs=3.490,tytSosyalKs=3.030;
    final double aytSaymatKs=2.979,aytSayfizKs=3.109,aytSaykimyaKs=3.131,aytSayBioKs=3.082,tytSayturKs=1.23,tytSaysosKs=1.276,tytSaymatKs=1.475,tytSayfenKs=1.382;
    final double aytEamatKs=3.171,aytEaedebKs=3.004,aytEatar1Ks=2.986,aytEacog1Ks=2.401,tytEaturKs=1.309,tytEasosKs=1.276,tytEamatKs=1.573,tytEafenKs=1.472;
    final double aytSoztar2Ks=3.343,aytSozcog2Ks=2.746,aytSozfelKs=3.137,aytSozdinKs=3.322,aytSozedebKs=3.194,aytSoztar1Ks=3.177,aytSozcog1Ks=2.547,tytSozturKs=1.393,tytSozsosKs=1.357,tytSozmatKs=1.671,tytSozfenKs=1.564;
    final double aytDilKs=2.581,tytDilturKs=1.576,tytDilsosKs=1.536,tytDilmatKs=1.89,tytDilfenKs=1.771;
    final double tabanSayKs=99.630,tabanSozKs=95.114,tabanEaKs=98.215,tabanDilKs=98.124,tabanTytKs=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puan);
            initialized();
        hesapla.setOnClickListener(view -> {
            netHesapla();
            Intent sonuc = new Intent(PUAN.this, PuanSonuc.class);
            sonuc.putExtra("sonuc",sonuclar);
            startActivity(sonuc);
        });
        sifirla.setOnClickListener(view -> reset());

        class GenericTextWatcher implements TextWatcher{
            int ID,nNum,lesson;
            public GenericTextWatcher(int less,int index,int netNumb)
            { ID =index; nNum=netNumb; lesson=less;}
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            { Log.d("s,start,before,count",charSequence+"-"+i+"-"+i1+"-"+i2+"-"+this.getClass()+"- ID -"+ID);
                getText(lesson,ID,ID%2,nNum);
            }
            public void afterTextChanged(Editable editable) {}
        }

        tytEdit[0].addTextChangedListener(new GenericTextWatcher(0,0,0));
        tytEdit[1].addTextChangedListener(new GenericTextWatcher(0,1,0));
        tytEdit[2].addTextChangedListener(new GenericTextWatcher(0,2,1));
        tytEdit[3].addTextChangedListener(new GenericTextWatcher(0,3,1));
        tytEdit[4].addTextChangedListener(new GenericTextWatcher(0,4,2));
        tytEdit[5].addTextChangedListener(new GenericTextWatcher(0,5,2));
        tytEdit[6].addTextChangedListener(new GenericTextWatcher(0,6,3));
        tytEdit[7].addTextChangedListener(new GenericTextWatcher(0,7,3));

        aytSayEdit[0].addTextChangedListener(new GenericTextWatcher(1,0,0));
        aytSayEdit[1].addTextChangedListener(new GenericTextWatcher(1,1,0));
        aytSayEdit[2].addTextChangedListener(new GenericTextWatcher(1,2,1));
        aytSayEdit[3].addTextChangedListener(new GenericTextWatcher(1,3,1));
        aytSayEdit[4].addTextChangedListener(new GenericTextWatcher(1,4,2));
        aytSayEdit[5].addTextChangedListener(new GenericTextWatcher(1,5,2));
        aytSayEdit[6].addTextChangedListener(new GenericTextWatcher(1,6,3));
        aytSayEdit[7].addTextChangedListener(new GenericTextWatcher(1,7,3));

        aytEaEdit[0].addTextChangedListener(new GenericTextWatcher(2,0,4));
        aytEaEdit[1].addTextChangedListener(new GenericTextWatcher(2,1,4));
        aytEaEdit[2].addTextChangedListener(new GenericTextWatcher(2,2,5));
        aytEaEdit[3].addTextChangedListener(new GenericTextWatcher(2,3,5));
        aytEaEdit[4].addTextChangedListener(new GenericTextWatcher(2,4,6));
        aytEaEdit[5].addTextChangedListener(new GenericTextWatcher(2,5,6));

        aytSozEdit[0].addTextChangedListener(new GenericTextWatcher(3,0,7));
        aytSozEdit[1].addTextChangedListener(new GenericTextWatcher(3,1,7));
        aytSozEdit[2].addTextChangedListener(new GenericTextWatcher(3,2,8));
        aytSozEdit[3].addTextChangedListener(new GenericTextWatcher(3,3,8));
        aytSozEdit[4].addTextChangedListener(new GenericTextWatcher(3,4,9));
        aytSozEdit[5].addTextChangedListener(new GenericTextWatcher(3,5,9));
        aytSozEdit[6].addTextChangedListener(new GenericTextWatcher(3,6,10));
        aytSozEdit[7].addTextChangedListener(new GenericTextWatcher(3,7,10));



        yabanciDilyanlis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                toastFirst(4,0,yabanciDilyanlis);
                    aytDildy[0]=Integer.parseInt("0"+yabanciDildogru.getText());
                    aytDildy[1]=Integer.parseInt("0"+yabanciDilyanlis.getText());
                    aytNet[11]=aytDildy[0]-aytDildy[1];
                    aytNets[11].setText(aytNet[11]+"");
                toastTotal(4,0,aytDildy[0],aytDildy[1],yabanciDilyanlis);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        yabanciDildogru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                toastFirst(4,0,yabanciDildogru);
                    aytDildy[0]=Integer.parseInt("0"+yabanciDildogru.getText());
                    aytDildy[1]=Integer.parseInt("0"+yabanciDilyanlis.getText());
                    aytNet[11]=aytDildy[0]-aytDildy[1];
                    aytNets[11].setText(aytNet[11]+"");
                toastTotal(4,0,aytDildy[0],aytDildy[1],yabanciDilyanlis);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        diplomaNotu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toastFirst(5,0,diplomaNotu);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void netHesapla(){
            diploma=Double.parseDouble("0"+diplomaNotu.getText());
            sonuclar[0]=(diploma*0.6);

            sonuclar[1]=tytNet[0]*tytTurkceKs+tytNet[1]*tytSosyalKs+tytNet[2]*tytMatKs+tytNet[3]*tytFenKs+tabanTytKs;

            sonuclar[2]=aytNet[0]*aytSaymatKs+aytNet[1]*aytSayfizKs+aytNet[2]*aytSaykimyaKs+aytNet[3]*aytSayBioKs+tabanSayKs+
                    tytNet[0]*tytSayturKs+tytNet[1]*tytSaysosKs+tytNet[2]*tytSaymatKs+tytNet[3]*tytSayfenKs;

            sonuclar[3]=aytNet[0]*aytEamatKs+aytNet[4]*aytEaedebKs+aytNet[5]*aytEatar1Ks+aytNet[6]*aytEacog1Ks+tabanEaKs+
                    tytNet[0]*tytEaturKs+tytNet[1]*tytEasosKs+tytNet[2]*tytEamatKs+tytNet[3]*tytEafenKs;

            sonuclar[4]=aytNet[4]*aytSozedebKs+aytNet[5]*aytSoztar1Ks+aytNet[6]*aytSozcog1Ks+aytNet[7]*aytSoztar2Ks+tabanSozKs+
                    aytNet[8]*aytSozcog2Ks+aytNet[9]*aytSozfelKs+aytNet[10]*aytSozdinKs+
                    tytNet[0]*tytSozturKs+tytNet[1]*tytSozsosKs+tytNet[2]*tytSozmatKs+tytNet[3]*tytSozfenKs;

            sonuclar[5]=aytNet[11]*aytDilKs+tytNet[0]*tytDilturKs+aytNet[1]*tytDilsosKs+aytNet[2]*tytDilmatKs+aytNet[3]*tytDilfenKs+tabanDilKs;

    }
    public void getText(int s,int a,int dy,int nets){
        if (s==0){
            if (dy==0){
                toastFirst(s,a,tytEdit[a]);
                tytdy[a]=Integer.parseInt("0"+tytEdit[a].getText());
                tytdy[a+1]=Integer.parseInt("0"+tytEdit[a+1].getText());
                tytNet[nets]=tytdy[a]-(double)(tytdy[a+1])/4;
                tytNets[nets].setText(tytNet[nets]+"");
                toastTotal(s,a,tytdy[a],tytdy[a+1],tytEdit[a+1]);
            }
            else{
                toastFirst(s,a,tytEdit[a]);
                tytdy[a-1]=Integer.parseInt("0"+tytEdit[a-1].getText());
                tytdy[a]=Integer.parseInt("0"+tytEdit[a].getText());
                tytNet[nets]=tytdy[a-1]-((double)tytdy[a])/4;
                tytNets[nets].setText(tytNet[nets]+"");
                toastTotal(s,a,tytdy[a-1],tytdy[a],tytEdit[a]);
            }
        }
        else if (s==1)
        {
            if (dy==0){
                toastFirst(s,a,aytSayEdit[a]);
                aytSaydy[a]=Integer.parseInt("0"+aytSayEdit[a].getText());
                aytSaydy[a+1]=Integer.parseInt("0"+aytSayEdit[a+1].getText());
                aytNet[nets]=aytSaydy[a]-((double)aytSaydy[a+1])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytSaydy[a],aytSaydy[a+1],aytSayEdit[a+1]);
            }
            else{
                toastFirst(s,a,aytSayEdit[a]);
                aytSaydy[a-1]=Integer.parseInt("0"+aytSayEdit[a-1].getText());
                aytSaydy[a]=Integer.parseInt("0"+aytSayEdit[a].getText());
                aytNet[nets]=aytSaydy[a-1]-((double)aytSaydy[a])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytSaydy[a-1],aytSaydy[a],aytSayEdit[a]);
            }
        }
        else if (s==2)
        {
            if (dy==0){
                toastFirst(s,a,aytEaEdit[a]);
                aytEady[a]=Integer.parseInt("0"+aytEaEdit[a].getText());
                aytEady[a+1]=Integer.parseInt("0"+aytEaEdit[a+1].getText());
                aytNet[nets]=aytEady[a]-((double)aytEady[a+1])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytEady[a],aytEady[a+1],aytEaEdit[a+1]);
            }
            else{
                toastFirst(s,a,aytEaEdit[a]);
                aytEady[a-1]=Integer.parseInt("0"+aytEaEdit[a-1].getText());
                aytEady[a]=Integer.parseInt("0"+aytEaEdit[a].getText());
                aytNet[nets]=aytEady[a-1]-((double)aytEady[a])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytEady[a-1],aytEady[a],aytEaEdit[a]);
            }
        }
        else if (s==3)
        {
            if (dy==0){
                toastFirst(s,a,aytSozEdit[a]);
                aytSozdy[a]=Integer.parseInt("0"+aytSozEdit[a].getText());
                aytSozdy[a+1]=Integer.parseInt("0"+aytSozEdit[a+1].getText());
                aytNet[nets]=aytSozdy[a]-((double)aytSozdy[a+1])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytSozdy[a],aytSozdy[a+1],aytSozEdit[a+1]);
            }
            else{
                toastFirst(s,a,aytSozEdit[a]);
                aytSozdy[a-1]=Integer.parseInt("0"+aytSozEdit[a-1].getText());
                aytSozdy[a]=Integer.parseInt("0"+aytSozEdit[a].getText());
                aytNet[nets]=aytSozdy[a-1]-((double)aytSozdy[a])/4;
                aytNets[nets].setText(aytNet[nets]+"");
                toastTotal(s,a,aytSozdy[a-1],aytSozdy[a],aytSozEdit[a]);
            }
        }
    }
    public void initialized(){
        diplomaNotu=findViewById(R.id.diploma_EditText);
        tytEdit[0]=findViewById(R.id.turkce_dogru_EditText);
        tytEdit[1]=findViewById(R.id.turkce_yanlis_EditText);
        tytEdit[2]=findViewById(R.id.sosyal_dogru_EditText);
        tytEdit[3]=findViewById(R.id.sosyal_yanlis_EditText);
        tytEdit[4]=findViewById(R.id.mat_dogru_EditText);
        tytEdit[5]=findViewById(R.id.mat_yanlis_EditText);
        tytEdit[6]=findViewById(R.id.fen_dogru_EditText);
        tytEdit[7]=findViewById(R.id.fen_yanlis_EditText);

        aytSayEdit[0]=findViewById(R.id.matAyt_dogru_EditText);
        aytSayEdit[1]=findViewById(R.id.matAyt_yanlis_EditText);
        aytSayEdit[2]=findViewById(R.id.fizikAyt_dogru_EditText);
        aytSayEdit[3]=findViewById(R.id.fizikAyt_yanlis_EditText);
        aytSayEdit[4]=findViewById(R.id.kimyaAyt_dogru_EditText);
        aytSayEdit[5]=findViewById(R.id.kimyaAyt_yanlis_EditText);
        aytSayEdit[6]=findViewById(R.id.bioAyt_dogru_EditText);
        aytSayEdit[7]=findViewById(R.id.bioAyt_yanlis_EditText);


        aytEaEdit[0]=findViewById(R.id.edebiyatAyt_dogru_EditText);
        aytEaEdit[1]=findViewById(R.id.edebiyatAyt_yanlis_EditText);
        aytEaEdit[2]=findViewById(R.id.tarih1Ayt_dogru_EditText);
        aytEaEdit[3]=findViewById(R.id.tarih1Ayt_yanlis_EditText);
        aytEaEdit[4]=findViewById(R.id.cog1Ayt_dogru_EditText);
        aytEaEdit[5]=findViewById(R.id.cog1Ayt_yanlis_EditText);

        aytSozEdit[0]=findViewById(R.id.tarih2_dogru_EditText);
        aytSozEdit[1]=findViewById(R.id.tarih2_yanlis_EditText);
        aytSozEdit[2]=findViewById(R.id.cog2_dogru_EditText);
        aytSozEdit[3]=findViewById(R.id.cog2_yanlis_EditText);
        aytSozEdit[4]=findViewById(R.id.felsefe_dogru_EditText);
        aytSozEdit[5]=findViewById(R.id.felsefe_yanlis_EditText);
        aytSozEdit[6]=findViewById(R.id.din_dogru_EditText);
        aytSozEdit[7]=findViewById(R.id.din_yanlis_EditText);

        yabanciDildogru=findViewById(R.id.yabanciDil_dogru_EditText);
        yabanciDilyanlis=findViewById(R.id.yabanciDil_yanlis_EditText);

        hesapla=findViewById(R.id.hesapla);
        sifirla=findViewById(R.id.sifirla);

        tytNets[0]=findViewById(R.id.turkce_net);
        tytNets[1]=findViewById(R.id.sosyal_net);
        tytNets[2]=findViewById(R.id.mat_net);
        tytNets[3]=findViewById(R.id.fen_net);

        aytNets[0]=findViewById(R.id.matAyt_net);
        aytNets[1]=findViewById(R.id.fizikAyt_net);
        aytNets[2]=findViewById(R.id.kimyaAyt_net);
        aytNets[3]=findViewById(R.id.bioAyt_net);

        aytNets[4]=findViewById(R.id.edebiyatAyt_net);
        aytNets[5]=findViewById(R.id.tarih1Ayt_net);
        aytNets[6]=findViewById(R.id.cog1Ayt_net);
        aytNets[7]=findViewById(R.id.tarih2Ayt_net);
        aytNets[8]=findViewById(R.id.cog2Ayt_net);
        aytNets[9]=findViewById(R.id.felsefeAyt_net);
        aytNets[10]=findViewById(R.id.dinAyt_net);

        aytNets[11]=findViewById(R.id.yabanciDilAyt_net);
    }
    public void reset(){
        for (int i = 0; i <aytNets.length; i++) {
            if (i<tytNets.length) {
                tytEdit[2 * i].setText(0 + "");
                tytEdit[(2 * i) + 1].setText(0 + "");
                tytNets[i].setText(0 + "");
                aytSayEdit[2 * i].setText(0 + "");
                aytSayEdit[(2 * i) + 1].setText(0 + "");
                aytSozEdit[2 * i].setText(0 + "");
                aytSozEdit[(2 * i) + 1].setText(0 + "");
                if (i<aytEaEdit.length/2) {
                    aytEaEdit[2 * i].setText(0 + "");
                    aytEaEdit[(2 * i) + 1].setText(0 + "");
                }
            }
            aytNets[i].setText(0 + "");
        }
        yabanciDildogru.setText(0+"");
        yabanciDilyanlis.setText(0+"");
        diplomaNotu.setText(0+"");
    }
    public void toastFirst(int s,int a,EditText editText){
        int limit=getLimit(s,a);
        try {
            if (s==5){
                if (Double.parseDouble("0"+editText.getText())>limit){
                    editText.setError(""+"0-"+limit+" arası bir değer giriniz");
                    editText.setText(Double.parseDouble("0"+editText.getText())/10+"");
                }
            }
            else if (Integer.parseInt("0"+editText.getText())>limit){
                editText.setError(""+"0-"+limit+" arası bir değer giriniz");
                 editText.setText(Integer.parseInt("0"+editText.getText())/10+"");
            }
        }catch (Exception e)
        {
            editText.setError("Lütfen "+"0-"+limit+" arası bir"+" bir değer giriniz");
            editText.setHint("0");
        }
    }
    public void toastTotal(int s,int a,int ds,int ys,EditText editText){
        int limit=getLimit(s,a);
        if (ds+ys>limit){
            editText.setError("Soru Sayısını Aştınız");
            editText.setText((limit-ds)+"");
        }
    }
    public int getLimit(int s,int a){
        switch (s)
        {
            case 0: switch (a){
                case 0:
                case 1: return 40;
                case 2:
                case 3: return 20;
                case 4:
                case 5: return 40;
                case 6:
                case 7: return 20;
            }
            case 1: switch (a){
                case 0:
                case 1: return 40;
                case 2:
                case 3: return 14;
                case 4:
                case 5: return 13;
                case 6:
                case 7: return 13;
            }
            case 2: switch (a){
                case 0:
                case 1: return 24;
                case 2:
                case 3: return 10;
                case 4:
                case 5: return 6;
            }
            case 3: switch (a){
                case 0:
                case 1: return 11;
                case 2:
                case 3: return 11;
                case 4:
                case 5: return 12;
                case 6:
                case 7: return 6;
            }
            case 4: return 80;
            case 5: return 100;
        }
        return 0;
    }
}
