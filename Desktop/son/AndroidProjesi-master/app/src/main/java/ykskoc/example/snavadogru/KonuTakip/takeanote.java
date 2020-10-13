package ykskoc.example.snavadogru.KonuTakip;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.ykskoc.R;

public class takeanote extends AppCompatDialogFragment {
    dialog listener;
    private EditText inputEditText;
    private String inText;
    String getEditText;
    int Id;
    takeanote(String text,int id){
        inText=text;
        Id=id;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater layout = LayoutInflater.from(getContext());
        View view = layout.inflate(R.layout.konu_takip_notal,null);
        inputEditText = view.findViewById(R.id.multiLineText);

        inputEditText.setText(inText);
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Not Al");
        ad.setView(view);
        ad.setPositiveButton("Kaydet",((dialogInterface, i) -> {
            getEditText=inputEditText.getText().toString();
            listener.set();
        }));
        ad.setNegativeButton("Iptal",(dialogInterface, i) -> dismiss());

        return ad.create();
    }

     @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {listener = (dialog ) context;}
        catch (ClassCastException e){ throw new ClassCastException(getActivity().toString() +" must implement dialog");}
    }
    public interface dialog {
         void set();
    }
}