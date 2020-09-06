package com.example.snavadogru.KonuTakip;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class KonuTakipArrayAdapt extends RecyclerView.Adapter<KonuTakipArrayAdapt.ViewHolder> implements takeanote.dialog{
    private Context context;
    private String[] dersler;
    private takeanote popUp;
    private FragmentManager fragmentManager;
    private boolean[] confirmBoolean;
    private String[] notes;
    private int[] counter= new int[14];
    private int dersId;
    private int index=0;
    public KonuTakipArrayAdapt(Context c,String[] lessons,FragmentManager fragmentmanage,int id){
        context=c;
        dersId=id;
        dersler=lessons;
        fragmentManager=fragmentmanage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.konutakip_adding,parent,false);
        Log.d("onCreateViewHolder","kurul");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadData();
        Log.d("OnBindViewHolder","position "+position);
        holder.textView.setText(dersler[position]);
        if (confirmBoolean[position]) {
            holder.takeaNote.setEnabled(true);
            holder.switchButton.setChecked(true);
        }
        else{
            holder.takeaNote.setEnabled(false);
            holder.switchButton.setChecked(false);}
            holder.switchButton.setOnCheckedChangeListener((compoundButton, b)-> {
            index=position;
            if (b) {
                holder.takeaNote.setEnabled(true);
                confirmBoolean[position]=true;
                counter[dersId]++;
                saveData();
            } else{
                holder.takeaNote.setEnabled(false);
                confirmBoolean[position]=false;
                counter[dersId]--;
                saveData();
            }
        });
        holder.takeaNote.setOnClickListener(view -> {
            Log.d("text",notes[position]+" "+position);
            popUp = new takeanote(notes[position],position);
            popUp.show(fragmentManager, "Sinava Dogru");
        });
    }

    @Override
    public int getItemCount() {return dersler.length;}

    @Override
    public void set() {
        index=popUp.Id;
        notes[index]=popUp.getEditText;
        Log.d("Kaydedecem","oyy "+notes[index]+" "+index);
        saveData();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         TextView textView;
         Button takeaNote;
         Switch switchButton;
         ViewHolder(@NonNull View itemView) {
             super(itemView);
             Log.d("ViewHolder","kurul");
             textView = itemView.findViewById(R.id.text_konu);
             takeaNote = itemView.findViewById(R.id.takeaNoteButton);
             switchButton = itemView.findViewById(R.id.switchButton);
         }
     }

    private void saveData() {
        // if Sp is null, sp attach with TytKonuSosyal
        SharedPreferences sP=null;
        SharedPreferences sPCounter= context.getSharedPreferences("progressProportionSave", Context.MODE_PRIVATE);
        Log.d("Geliyorum Kaydetmeye"," = "+context.getClass().getName());
        if (dersId==0)
            sP = context.getSharedPreferences("tytKonuTurkceSave", Context.MODE_PRIVATE);
        else if (dersId==1)
            sP = context.getSharedPreferences("tytKonuSosyalSave", Context.MODE_PRIVATE);
        else if (dersId==2)
                    sP = context.getSharedPreferences("tytKonuMatSave", Context.MODE_PRIVATE);
        else if (dersId==3)
                    sP = context.getSharedPreferences("tytKonuFenSave", Context.MODE_PRIVATE);
        else if (dersId==4)
                sP = context.getSharedPreferences("aytKonuMatSave", Context.MODE_PRIVATE);
        else if (dersId==5)
                sP = context.getSharedPreferences("aytKonuFizSave", Context.MODE_PRIVATE);
        else if (dersId==6)
                sP = context.getSharedPreferences("aytKonuKimSave", Context.MODE_PRIVATE);
        else if (dersId==7)
                sP = context.getSharedPreferences("aytKonuBioSave", Context.MODE_PRIVATE);
        else if (dersId==8)
                sP = context.getSharedPreferences("aytKonuEdebSave", Context.MODE_PRIVATE);
        else if (dersId==9)
                sP = context.getSharedPreferences("aytKonuCogSave", Context.MODE_PRIVATE);
        else if (dersId==10)
                sP = context.getSharedPreferences("aytKonuTarSave", Context.MODE_PRIVATE);
        else if (dersId==11)
                sP = context.getSharedPreferences("aytKonuFelsefeSave", Context.MODE_PRIVATE);
        else if (dersId==12)
                sP = context.getSharedPreferences("aytKonuDinSave", Context.MODE_PRIVATE);
        else if (dersId==13)
                sP = context.getSharedPreferences("aytKonuDilSave", Context.MODE_PRIVATE);

      try {
            SharedPreferences.Editor editor = sP.edit();
            Gson gson = new Gson();
            String json= gson.toJson(confirmBoolean);
            String json2= gson.toJson(notes);

          SharedPreferences.Editor editorCounter = sPCounter.edit();
          String jsonCount= gson.toJson(counter);
          editorCounter.putString("prop",jsonCount);
          editorCounter.apply();

            editor.putString("ConfirmBoolean",json);
            editor.putString("Note",json2);

            editor.apply();
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void loadData() {
        // if Sp is null, sp attach with TytKonuSosyal
        SharedPreferences sP=null;
        SharedPreferences sPCounter= context.getSharedPreferences("progressProportionSave", Context.MODE_PRIVATE);

        if (dersId==0)
            sP = context.getSharedPreferences("tytKonuTurkceSave", Context.MODE_PRIVATE);
        else if (dersId==1)
            sP = context.getSharedPreferences("tytKonuSosyalSave", Context.MODE_PRIVATE);
        else if (dersId==2)
            sP = context.getSharedPreferences("tytKonuMatSave", Context.MODE_PRIVATE);
        else if (dersId==3)
            sP = context.getSharedPreferences("tytKonuFenSave", Context.MODE_PRIVATE);
        else if (dersId==4)
            sP = context.getSharedPreferences("aytKonuMatSave", Context.MODE_PRIVATE);
        else if (dersId==5)
            sP = context.getSharedPreferences("aytKonuFizSave", Context.MODE_PRIVATE);
        else if (dersId==6)
            sP = context.getSharedPreferences("aytKonuKimSave", Context.MODE_PRIVATE);
        else if (dersId==7)
            sP = context.getSharedPreferences("aytKonuBioSave", Context.MODE_PRIVATE);
        else if (dersId==8)
            sP = context.getSharedPreferences("aytKonuEdebSave", Context.MODE_PRIVATE);
        else if (dersId==9)
            sP = context.getSharedPreferences("aytKonuCogSave", Context.MODE_PRIVATE);
        else if (dersId==10)
            sP = context.getSharedPreferences("aytKonuTarSave", Context.MODE_PRIVATE);
        else if (dersId==11)
            sP = context.getSharedPreferences("aytKonuFelsefeSave", Context.MODE_PRIVATE);
        else if (dersId==12)
            sP = context.getSharedPreferences("aytKonuDinSave", Context.MODE_PRIVATE);
        else if (dersId==13)
            sP = context.getSharedPreferences("aytKonuDilSave", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sP.getString("ConfirmBoolean", null);
        String json2= sP.getString("Note",null);
        confirmBoolean = gson.fromJson(json, (Type) boolean[].class);
        notes = gson.fromJson(json2, (Type) String[].class);

       String count= sPCounter.getString("prop",null);
       counter=gson.fromJson(count, (Type) int[].class);

        if (counter==null)
        counter= new int[14];
        if (confirmBoolean == null)confirmBoolean = new boolean[dersler.length];
        if (notes == null) notes = new String[dersler.length];
    }
}
