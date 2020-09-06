package com.example.snavadogru.Kendin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.snavadogru.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addingPopUp extends AppCompatDialogFragment {
    Button hourUp,hourDown,minUp,minDown;
    EditText hourEdit,minEdit;
    dialog listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View view = layout.inflate(R.layout.time_adding,null);

        hourUp=view.findViewById(R.id.hourUp);
        hourDown=view.findViewById(R.id.hourDown);
        minUp=view.findViewById(R.id.minUp);
        minDown=view.findViewById(R.id.minDown);
        hourEdit=view.findViewById(R.id.getHourView);
        minEdit=view.findViewById(R.id.getMinView);
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setView(view);
        ad.setPositiveButton("Ekle", (dialog, which) -> {
            listener.set();
        });
        ad.setNegativeButton("İptal",((dialog, which) -> dismiss()));
        Log.d("Dialog","alert");
        hourUp.setOnClickListener(v-> {
            try {
                if ((Integer.parseInt(hourEdit.getText()+"")==23))
                    hourEdit.setText("00");
                else if ((Integer.parseInt(hourEdit.getText()+"")<10))
                    hourEdit.setText("0"+(Integer.parseInt(hourEdit.getText()+"")+1));
                else
                    hourEdit.setText((Integer.parseInt(hourEdit.getText()+"")+1)+"");
            }catch (Exception e){
                Toast.makeText(getContext(), "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
            }
        });
        hourDown.setOnClickListener(v-> {
            try {
                if ((Integer.parseInt(hourEdit.getText()+"")==0))
                    hourEdit.setText("23");
                else if ((Integer.parseInt(hourEdit.getText()+"")<10))
                    hourEdit.setText("0"+(Integer.parseInt(hourEdit.getText()+"")-1));
                else
                    hourEdit.setText((Integer.parseInt(hourEdit.getText()+"")-1)+"");
            }catch (Exception e){
                Toast.makeText(getContext(), "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
            }
        });
        minUp.setOnClickListener(v-> {
            try {
                if ((Integer.parseInt(minEdit.getText()+"")==59))
                    minEdit.setText("0");
                else if ((Integer.parseInt(minEdit.getText()+"")<10))
                    minEdit.setText("0"+(Integer.parseInt(minEdit.getText()+"")+1));
                else
                    minEdit.setText((Integer.parseInt(minEdit.getText()+"")+1)+"");
            }catch (Exception e){
                Toast.makeText(getContext(), "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
            }
        });
        minDown.setOnClickListener(v-> {
            try {
                if ((Integer.parseInt(minEdit.getText()+"")==0))
                    minEdit.setText("59");
                else if ((Integer.parseInt(minEdit.getText()+"")<10))
                    minEdit.setText("0"+(Integer.parseInt(minEdit.getText()+"")-1));
                else
                    minEdit.setText((Integer.parseInt(minEdit.getText()+"")-1)+"");
            }catch (Exception e){
                Toast.makeText(getContext(), "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
            }
        });
        return ad.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {listener = (dialog) context;
        }catch (ClassCastException e){ throw new ClassCastException(getContext().toString()+" must implement dialog");}
    }
    interface dialog {
        void set();
    }
}
