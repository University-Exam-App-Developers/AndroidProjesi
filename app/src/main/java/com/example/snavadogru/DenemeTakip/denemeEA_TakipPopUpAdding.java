package com.example.snavadogru.DenemeTakip;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.example.snavadogru.R;

import java.util.ArrayList;

public class denemeEA_TakipPopUpAdding extends AppCompatDialogFragment {
    ArrayList<EditText> edits = new ArrayList<>(10);
    ArrayList<String> Info = new ArrayList<>(2);
    ArrayList<String> netler = new ArrayList<>(4);
    Button posButton,negButton;
    RelativeLayout eaLayout;
    eaDenemesi eklenenDeneme;
    private int increment;
    boolean comfirm=false;
    ImageView popUpimage;
    dialog listener;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TextView first,second,third,fourth;
        super.onCreate(savedInstanceState);
        LayoutInflater layout = LayoutInflater.from(getContext());
        View view = layout.inflate(R.layout.activity_denemetakip_4lessonpopupadding,null);
        Dialog ad = new Dialog(getContext());
        ad.setContentView(view);

        popUpimage=view.findViewById(R.id.lesson4PopUpimageView);
        popUpimage.setImageDrawable(getResources().getDrawable(R.drawable.book5));
        first=view.findViewById(R.id.deneme4les_first);
        first.setText("Türk Dili Ve Edebiyatı");
        first.setTextSize(23);
        second=view.findViewById(R.id.deneme4les_second);
        second.setText("Tarih");
        third=view.findViewById(R.id.deneme4les_third);
        third.setText("Coğrafya");
        fourth=view.findViewById(R.id.deneme4les_fourth);
        fourth.setText("Matematik");


        edits.add(view.findViewById(R.id.takeNameEdit));
        edits.get(0).setHint(increment+""+edits.get(0).getHint());
        edits.add(view.findViewById(R.id.yayinlar));

        posButton=view.findViewById(R.id.yesButton);
        negButton=view.findViewById(R.id.noButton);

        edits.add(view.findViewById(R.id.deneme4les_first4Dogru));
        edits.add(view.findViewById(R.id.deneme4les_first4Yanlis));
        edits.add(view.findViewById(R.id.deneme4les_second4Dogru));
        edits.add(view.findViewById(R.id.deneme4les_second4Yanlis));
        edits.add(view.findViewById(R.id.deneme4les_third4Dogru));
        edits.add(view.findViewById(R.id.deneme4les_third4Yanlis));
        edits.add(view.findViewById(R.id.deneme4les_fourth4Dogru));
        edits.add(view.findViewById(R.id.deneme4les_fourth4Yanlis));
        eaLayout=view.findViewById(R.id.lesson4RelativeLayout);
        eaLayout.setBackgroundResource(R.drawable.alertdialog_ea);

        edits.get(2).addTextChangedListener(new GenericTextWatcher(2));
        edits.get(3).addTextChangedListener(new GenericTextWatcher(3));
        edits.get(4).addTextChangedListener(new GenericTextWatcher(4));
        edits.get(5).addTextChangedListener(new GenericTextWatcher(5));
        edits.get(6).addTextChangedListener(new GenericTextWatcher(6));
        edits.get(7).addTextChangedListener(new GenericTextWatcher(7));
        edits.get(8).addTextChangedListener(new GenericTextWatcher(8));
        edits.get(9).addTextChangedListener(new GenericTextWatcher(9));

        posButton.setOnClickListener(v->{
            if (edits.get(0).getText().toString().length()==0)
                Info.add(increment+"");
            else
                Info.add(edits.get(0).getText().toString().trim());
            if (edits.get(1).getText().toString().length()==0)
                Info.add(" ");
            else
                Info.add(edits.get(1).getText().toString());

            for (int i = 2; i <edits.size() ; i++)
                if (edits.get(i).getText().toString().length()==0)
                    edits.get(i).setText("0");

            netler.add((Integer.parseInt(edits.get(2).getText().toString()))-(Integer.parseInt(edits.get(3).getText().toString())*0.25)+"");
            netler.add((Integer.parseInt(edits.get(4).getText().toString()))-(Integer.parseInt(edits.get(5).getText().toString())*0.25)+"");
            netler.add((Integer.parseInt(edits.get(6).getText().toString()))-(Integer.parseInt(edits.get(7).getText().toString())*0.25)+"");
            netler.add((Integer.parseInt(edits.get(8).getText().toString()))-(Integer.parseInt(edits.get(9).getText().toString())*0.25)+"");
            eklenenDeneme = new eaDenemesi(Info,netler,increment);
            comfirm=true;
            listener.set();
            ad.dismiss();
        });
        negButton.setOnClickListener(v->{ comfirm=false;  ad.dismiss();});
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ad.show();

        return ad;
    }
    public eaDenemesi getDeneme(){
        return eklenenDeneme;
    }
    public boolean doComfirm(){return comfirm;}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {listener = (dialog) context;
        }catch (ClassCastException e){ throw new ClassCastException(getContext().toString()+" must implement dialog");}
    }

    public void setNameIncrement(int number) { increment=number;}

    interface dialog {
        public void set();
    }
    public void toastFirst(int a, EditText editText) {
        int limit = getLimit(a);
        try {
            if (Integer.parseInt("0" + editText.getText()) > limit) {
                editText.setError("" + "0-" + limit + " arası bir değer giriniz");
                editText.setText(Integer.parseInt("0" + editText.getText()) / 10 + "");
            }
        } catch (Exception e) {
            editText.setError("Lütfen " + "0-" + limit + " arası bir" + " bir değer giriniz");
            editText.setHint("0");
        }
    }

    public void toastTotal(int a, int ds, int ys, EditText editText) {
        int limit = getLimit(a);
        if (ds + ys > limit) {
            editText.setError("Soru Sayısını Aştınız");
            editText.setText((limit - ds) + "");
        }
    }

    public int getLimit(int s) {
        switch (s) {
            case 0:
            case 1: return 24;
            case 2:
            case 3: return 10;
            case 4:
            case 5: return 6;
            case 6:
            case 7: return 40;
        }
        return 0;
    }

    class GenericTextWatcher implements TextWatcher {
        int ID;
        public GenericTextWatcher(int index)
        { ID =index; }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            Log.d("onTextChanged",ID+"-> "+edits.get(ID).getText().toString()+" -> "+charSequence+"-> "+edits.get(ID).getText());
            toastFirst(ID-2,edits.get(ID));
            if (ID%2==0)
                toastTotal(ID-2,Integer.parseInt("0"+edits.get(ID).getText()),Integer.parseInt("0"+edits.get(ID+1).getText()),edits.get(ID));
            else
                toastTotal(ID-2,Integer.parseInt("0"+edits.get(ID-1).getText()),Integer.parseInt("0"+edits.get(ID).getText()),edits.get(ID));

        }
        public void afterTextChanged(Editable editable) {}
    }
}

