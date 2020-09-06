package com.example.snavadogru.DenemeTakip;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.snavadogru.R;

import java.util.ArrayList;

public class denemeSOZ_TakipPopUpAdding extends AppCompatDialogFragment {
    ArrayList<EditText> edits = new ArrayList<>(10);
    ArrayList<String> netler = new ArrayList<>(4);
    ArrayList<String> Info = new ArrayList<>(2);

    dialog listener;
    sozDenemesi eklenenDeneme;
    private int increment;
    boolean comfirm=false;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layout = LayoutInflater.from(getContext());
        View view = layout.inflate(R.layout.activity_denemetakip_7lessonpopupadding,null);
        edits.add(view.findViewById(R.id.deneme7les_takeNameEdit));
        edits.get(0).setHint(increment+""+edits.get(0).getHint());
        edits.add(view.findViewById(R.id.deneme7les_yayinlar));

        edits.add(view.findViewById(R.id.deneme7les_first7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_first7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_second7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_second7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_third7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_third7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_fourth7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_fourth7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_fifth7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_fifth7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_sixth7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_sixth7Yanlis));
        edits.add(view.findViewById(R.id.deneme7les_seventh7Dogru));
        edits.add(view.findViewById(R.id.deneme7les_seventh7Yanlis));


        edits.get(2).addTextChangedListener(new GenericTextWatcher(2));
        edits.get(3).addTextChangedListener(new GenericTextWatcher(3));
        edits.get(4).addTextChangedListener(new GenericTextWatcher(4));
        edits.get(5).addTextChangedListener(new GenericTextWatcher(5));
        edits.get(6).addTextChangedListener(new GenericTextWatcher(6));
        edits.get(7).addTextChangedListener(new GenericTextWatcher(7));
        edits.get(8).addTextChangedListener(new GenericTextWatcher(8));
        edits.get(9).addTextChangedListener(new GenericTextWatcher(9));
        edits.get(10).addTextChangedListener(new GenericTextWatcher(10));
        edits.get(11).addTextChangedListener(new GenericTextWatcher(11));
        edits.get(12).addTextChangedListener(new GenericTextWatcher(12));
        edits.get(13).addTextChangedListener(new GenericTextWatcher(13));
        edits.get(14).addTextChangedListener(new GenericTextWatcher(14));
        edits.get(15).addTextChangedListener(new GenericTextWatcher(15));

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Deneme ekle");
        ad.setView(view);
        ad.setPositiveButton("ekle", (dialogInterface, which) -> {
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
            netler.add((Integer.parseInt(edits.get(10).getText().toString()))-(Integer.parseInt(edits.get(11).getText().toString())*0.25)+"");
            netler.add((Integer.parseInt(edits.get(12).getText().toString()))-(Integer.parseInt(edits.get(13).getText().toString())*0.25)+"");
            netler.add((Integer.parseInt(edits.get(14).getText().toString()))-(Integer.parseInt(edits.get(15).getText().toString())*0.25)+"");
            eklenenDeneme = new sozDenemesi(Info,netler,increment);
            comfirm=true;
            listener.set();
        });
        ad.setNegativeButton("iptal", (dialog, which) -> { comfirm=false; ad.create().dismiss(); });
        return ad.create();
    }
    public sozDenemesi getDeneme(){
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
        void set();
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
            case 7: return 11;
            case 8:
            case 9: return 11;
            case 10:
            case 11: return 12;
            case 12:
            case 13: return 6;
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
