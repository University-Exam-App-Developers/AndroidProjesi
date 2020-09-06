package com.example.snavadogru.DenemeTakip;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> YayinAdi = new ArrayList<>();
    private ArrayList<String> TotalNet = new ArrayList<>();
    private ArrayList<dilDenemesi> dilDenemesiArrayList = new ArrayList<>();
    private ArrayList<tytDenemesi> tytDenemesiArrayList = new ArrayList<>();
    private ArrayList<sayDenemesi> sayDenemesiArrayList = new ArrayList<>();
    private ArrayList<eaDenemesi> eaDenemesiArrayList = new ArrayList<>();
    private ArrayList<sozDenemesi> sozDenemesiArrayList = new ArrayList<>();
    Context context;
    Button delete;
    int index;
    private OnItemClickListener mylistener;
    public RecyclerViewAdapter(Context c){
        context=c;
        /*for (int i = 0; i < dilDenemesiArrayList.size(); i++) {
            Name.add(dilDenemesiArrayList.get(i).Name);
            YayinAdi.add(dilDenemesiArrayList.get(i).Yayin);
            TotalNet.add(dilDenemesiArrayList.get(i).totalNet + ""); }
        Log.d("MASSAKA ","BAKAGA "+Name.size());*/
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mylistener=listener; Log.d("OnItemClickListener"," DOLDRUDUM ABI ");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.denemetakip_popup_deleting,parent,false);
        delete = view.findViewById(R.id.delete);
        Log.d("VİEWHOLDER"," GELDİM OTuRDUM CONSTRUCTOR");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(Name.get(position));
        holder.yayinName.setText(YayinAdi.get(position));
        holder.netler.setText(TotalNet.get(position)+"");
        index=position;
        Log.d("holder"," position "+position+" name size :"+Name.size());
    }

    @Override
    public int getItemCount() {
    //    Log.d("NAME SIZE"," ITEM COUNT "+Name.size());
        return Name.size();
    }
    //public int getPosition(){ return index; }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,yayinName,netler;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.delete);
            name=itemView.findViewById(R.id.denemName);
            yayinName=itemView.findViewById(R.id.yayinName);
            netler=itemView.findViewById(R.id.netSayisi);

            delete.setOnClickListener(view -> {
                Log.d("ITEM VİEW INNER"," HELLO MADA FAKAAA");
                if(mylistener !=null)
                {
                    delete.setEnabled(false);
                    int position = getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION)
                        mylistener.onItemClick(position);
                    Name.remove(position);
                    YayinAdi.remove(position);
                    TotalNet.remove(position);
                    Log.d("DELETEDECIKARIM_YAPILDI"," name size :"+Name.size());
                }
            });
        }

    }

    void setDilDenemesiArrayList(ArrayList<dilDenemesi> dil) {
        dilDenemesiArrayList = dil;
       Log.d("MASSAGE  ","SIKINTI MUHTEELEN BURADA "+dilDenemesiArrayList.size());
        for (int i = 0; i < dilDenemesiArrayList.size(); i++) {
            Name.add(dilDenemesiArrayList.get(i).Name);
            YayinAdi.add(dilDenemesiArrayList.get(i).Yayin);
            TotalNet.add(dilDenemesiArrayList.get(i).totalNet + ""); }
    }
    void setEaDenemesiArrayList(ArrayList<eaDenemesi> ea) {
        eaDenemesiArrayList = ea;
        for (int i = 0; i < eaDenemesiArrayList.size(); i++) {
            Name.add(eaDenemesiArrayList.get(i).Name);
            YayinAdi.add(eaDenemesiArrayList.get(i).Yayin);
            TotalNet.add(eaDenemesiArrayList.get(i).totalNet + ""); }
    }
    void setSayDenemesiArrayList(ArrayList<sayDenemesi> say) {
        sayDenemesiArrayList = say;
        for (int i = 0; i < sayDenemesiArrayList.size(); i++) {
            Name.add(sayDenemesiArrayList.get(i).Name);
            YayinAdi.add(sayDenemesiArrayList.get(i).Yayin);
            TotalNet.add(sayDenemesiArrayList.get(i).totalNet + "");}
    }
    void setTytDenemesiArrayList(ArrayList<tytDenemesi> tyt) {
        tytDenemesiArrayList = tyt;
        Log.d("MASSAGE  ", "TYT SIKINTI MUHTEELEN BURADA " + tytDenemesiArrayList.size());
        for (int i = 0; i < tytDenemesiArrayList.size(); i++) {
            Name.add(tytDenemesiArrayList.get(i).Name);
            YayinAdi.add(tytDenemesiArrayList.get(i).Yayin);
            TotalNet.add(tytDenemesiArrayList.get(i).totalNet + ""); }
    }
        void setSozDenemesiArrayList(ArrayList<sozDenemesi> soz) {
            sozDenemesiArrayList = soz;
            for (int i = 0; i < sozDenemesiArrayList.size(); i++) {
                Name.add(sozDenemesiArrayList.get(i).Name);
                YayinAdi.add(sozDenemesiArrayList.get(i).Yayin);
                TotalNet.add(sozDenemesiArrayList.get(i).totalNet + "");}
        }
      //  Log.d("RecyclerViewAdapter"," setTytDenemesiArrayList name"+Name.get(0));
}
