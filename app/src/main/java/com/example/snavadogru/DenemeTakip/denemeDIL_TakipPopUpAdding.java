package com.example.snavadogru.DenemeTakip;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class denemeDIL_TakipPopUpAdding extends AppCompatDialogFragment {
    ArrayList<EditText> editTexts= new ArrayList<>();
    dilDenemesi eklenenDeneme;
    boolean comfirm=false;
    String totalNet;
    ArrayList<String> Info = new ArrayList<>();
    dialog listener;
    private int increment;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layout = LayoutInflater.from(getContext());
        View view = layout.inflate(R.layout.activity_denemetakip_dil_popupadding,null);

        editTexts.add(view.findViewById(R.id.denemeDil_takeNameEdit));
        editTexts.add(view.findViewById(R.id.denemeDil_yayinlarTakeEdit));
        editTexts.add(view.findViewById(R.id.denemeDil_dogru));
        editTexts.add(view.findViewById(R.id.denemeDil_yanlis));

        editTexts.get(0).setHint(increment+""+editTexts.get(0).getHint());

        editTexts.get(2).addTextChangedListener(new GenericTextWatcher(2));
        editTexts.get(2).addTextChangedListener(new GenericTextWatcher(3));

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Deneme ekle");
        ad.setView(view);
        ad.setPositiveButton("ekle", (dialog, which) -> {

            if (editTexts.get(0).getText().toString().length()==0)
                Info.add(increment+"");
            else
                Info.add(editTexts.get(0).getText().toString().trim());
            if (editTexts.get(1).getText().toString().length()==0)
                Info.add(" ");
            else
                Info.add(editTexts.get(1).getText().toString());

            for (int i = 2; i <editTexts.size() ; i++)
                if (editTexts.get(i).getText().toString().length()==0)
                    editTexts.get(i).setText("0");

            totalNet= ((Integer.parseInt(editTexts.get(2).getText().toString()))-(Integer.parseInt(editTexts.get(3).getText().toString())*0.25)+"");
            eklenenDeneme = new dilDenemesi(Info,totalNet,increment);
            comfirm=true;
            listener.set();
        });
        ad.setNegativeButton("iptal", (dialog, which) -> { comfirm=false; ad.create().dismiss(); });

        return ad.create();//ad.create().show();

    }
    public dilDenemesi getDeneme(){
        return eklenenDeneme;
    }
    public boolean doComfirm(){return comfirm;}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {listener = (dialog) context;
        }catch (ClassCastException e){ throw new ClassCastException(getContext().toString()+" must implement dialog");}
    }
    interface dialog {
        void set();
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

   public void toastFirst(int a, EditText editText) {
       try {
           if (Integer.parseInt("0" + editText.getText()) > 80) {
               editText.setError("" + "0-" + 80 + " arası bir değer giriniz");
               editText.setText(Integer.parseInt("0" + editText.getText()) / 10 + "");
           }
       } catch (Exception e) {
           editText.setError("Lütfen " + "0-" + 80 + " arası bir" + " bir değer giriniz");
           editText.setHint("0");
       }
    }



    public void toastTotal(int a, int ds, int ys, EditText editText) {
        int limit = 80;
        if (ds + ys > limit) {
            editText.setError("Soru Sayısını Aştınız");
            editText.setText((limit - ds) + "");
        }
    }


    class GenericTextWatcher implements TextWatcher {
        int ID;
        public GenericTextWatcher(int index)
        { ID =index; }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
           toastFirst(ID-2,editTexts.get(ID));
            if (ID%2==0)
                toastTotal(ID-2,Integer.parseInt("0"+editTexts.get(ID).getText()),Integer.parseInt("0"+editTexts.get(ID+1).getText()),editTexts.get(ID));
            else
                toastTotal(ID-2,Integer.parseInt("0"+editTexts.get(ID-1).getText()),Integer.parseInt("0"+editTexts.get(ID).getText()),editTexts.get(ID));

        }
        public void afterTextChanged(Editable editable) {}
    }
}