package com.example.snavadogru.DenemeTakip;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import com.example.snavadogru.R;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;

public class libraryRecyclerViewAdapter extends RecyclerView.Adapter<libraryRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> YayinAdi = new ArrayList<>();
    private ArrayList<String> TotalNet = new ArrayList<>();
    private ArrayList<dilDenemesi> dilDenemesiArrayList = new ArrayList<>();
    private ArrayList<tytDenemesi> tytDenemesiArrayList = new ArrayList<>();
    private ArrayList<sayDenemesi> sayDenemesiArrayList = new ArrayList<>();
    private ArrayList<eaDenemesi> eaDenemesiArrayList = new ArrayList<>();
    private ArrayList<sozDenemesi> sozDenemesiArrayList = new ArrayList<>();
    Context context;
    Button delete1;
    int index;
    private RecyclerViewAdapter.OnItemClickListener mylistener;
    public libraryRecyclerViewAdapter(Context c){
        context=c;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener listener){
        mylistener=listener; Log.d("OnItemClickListener"," DOLDRUDUM ABI ");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.deneme_takip_library_item,parent,false);
        delete1 = view.findViewById(R.id.deleteDenemeColumn1);

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
            delete=itemView.findViewById(R.id.deleteDenemeColumn1);
            name=itemView.findViewById(R.id.denemNameColumn1);
            yayinName=itemView.findViewById(R.id.yayinNameColumn1);
            netler=itemView.findViewById(R.id.netSayisiColumn1);

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
        Log.d("MASSAGE  ", "say SIKINTI MUHTEELEN BURADA " + sayDenemesiArrayList.size());
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
