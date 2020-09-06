package com.example.snavadogru.DenemeTakip;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

import java.util.ArrayList;

public class denemetakip_deleting extends AppCompatDialogFragment {
    private ArrayList<dilDenemesi> dilDenemeleri;
    private ArrayList<tytDenemesi> tytDenemeleri;
    private ArrayList<eaDenemesi>  eaDenemeleri;
    private ArrayList<sayDenemesi> sayDenemeleri;
    private ArrayList<sozDenemesi> sozDenemeleri;
    dialogDeleting listener;
    RecyclerViewAdapter myAdapter;
    RecyclerView rV;
    View view;
    boolean comfirm;
    int indexToBeDeleted;

    public denemetakip_deleting(){}
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layout = LayoutInflater.from(getContext());
        comfirm=false;
        view = layout.inflate(R.layout.activity_denemetakip_deleting,null);
       // myAdapter = new RecyclerViewAdapter(getContext(),dilDenemeleri);
        rV =  view.findViewById(R.id.recyclerView);
        buildRecyclerView();

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Deneme List");
        ad.setView(view);
        return ad.create();
    }

    public void removeItem(int position){
        indexToBeDeleted=position;
        comfirm=true;
        myAdapter.notifyItemRemoved(position);
    }
    public boolean doComfirm(){return comfirm;}
    public void buildRecyclerView(){
        if (dilDenemeleri!=null){myAdapter = new RecyclerViewAdapter(view.getContext());
            myAdapter.setDilDenemesiArrayList(dilDenemeleri);}
        if (tytDenemeleri!=null){myAdapter = new RecyclerViewAdapter(view.getContext());
            myAdapter.setTytDenemesiArrayList(tytDenemeleri);}
        if (eaDenemeleri!=null){ myAdapter = new RecyclerViewAdapter(view.getContext());
            myAdapter.setEaDenemesiArrayList(eaDenemeleri);}
        if (sayDenemeleri!=null){ myAdapter = new RecyclerViewAdapter(view.getContext());
            myAdapter.setSayDenemesiArrayList(sayDenemeleri);}
        if (sozDenemeleri!=null){ myAdapter = new RecyclerViewAdapter(view.getContext());
            myAdapter.setSozDenemesiArrayList(sozDenemeleri);}
       // myAdapter.setDilDenemesiArrayList(dilDenemeleri);
        rV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rV.setHasFixedSize(true);
        rV.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(position -> {
            removeItem(position);
                listener.setDeleted();
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {listener = (dialogDeleting) context;
        }catch (ClassCastException e){ throw new ClassCastException(getContext().toString()+" must implement dialog");}
    }
    interface dialogDeleting {
        public void setDeleted();
    }

    void setDilDenemeleri(ArrayList<dilDenemesi> dil) { dilDenemeleri = dil;/* Log.d("denemetakip_deletinleri"," dilDenemesi name"+dilDenemeleri.get(0).Name);*/}
    void setTytDenemeleri(ArrayList<tytDenemesi> tyt) { tytDenemeleri = tyt; /*Log.d("denemetakip_deletinleri"," tytdenemeleri name"+tytDenemeleri.get(0).Name);*/}
    void setEaDenemeleri(ArrayList<eaDenemesi> ea)    { eaDenemeleri = ea; }
    void setSayDenemeleri(ArrayList<sayDenemesi> say) { sayDenemeleri = say;}
    void setSozDenemeleri(ArrayList<sozDenemesi> soz) { sozDenemeleri = soz;}

    public int deletableIndex (){return indexToBeDeleted;}

}
