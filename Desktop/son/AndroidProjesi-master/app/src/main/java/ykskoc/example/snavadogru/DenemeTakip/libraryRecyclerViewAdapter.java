package ykskoc.example.snavadogru.DenemeTakip;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import com.ykskoc.R;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import android.view.View;

import ykskoc.example.snavadogru.FirstScreen.tytDenemesi;

public class    libraryRecyclerViewAdapter extends RecyclerView.Adapter<libraryRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> YayinAdi = new ArrayList<>();
    private ArrayList<String> TotalNet = new ArrayList<>();
    private ArrayList<dilDenemesi> dilDenemesiArrayList = new ArrayList<>();
    private ArrayList<tytDenemesi> tytDenemesiArrayList = new ArrayList<>();
    private ArrayList<sayDenemesi> sayDenemesiArrayList = new ArrayList<>();
    private ArrayList<eaDenemesi> eaDenemesiArrayList = new ArrayList<>();
    private ArrayList<sozDenemesi> sozDenemesiArrayList = new ArrayList<>();
    Context context;
    private int index,drawable;
    private OnItemClickListener mylistener;
    public libraryRecyclerViewAdapter(Context c){
        context=c;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mylistener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.deneme_takip_library_item,parent,false);
        return new MyViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText("Deneme : "+Name.get(position));
        holder.yayinName.setText("Yayın : "+YayinAdi.get(position));
        holder.netler.setText("Toplam Net: "+TotalNet.get(position));
        holder.relativeLayout.setBackgroundResource(drawable);
   //     index=position;
    }

    @Override
    public int getItemCount() {
        return Name.size();
    }
    //public int getPosition(){ return index; }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,yayinName,netler;
        RelativeLayout relativeLayout;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.takip_libraryRelativeLayout);
            delete=itemView.findViewById(R.id.deleteDenemeColumn1);
            yayinName=itemView.findViewById(R.id.yayinNameColumn1);
            netler=itemView.findViewById(R.id.netSayisiColumn1);
            name=itemView.findViewById(R.id.denemNameColumn1);
            if (index>1 && index<4)
            {
                name.setTextColor(Color.WHITE);
                yayinName.setTextColor(Color.WHITE);
                netler.setTextColor(Color.WHITE);
            }
            delete.setOnClickListener(view -> {
                if(mylistener !=null)
                {
                    delete.setEnabled(false);
                    int position = getAdapterPosition();
                    if (position !=RecyclerView.NO_POSITION)
                        mylistener.onItemClick(position);
                    Name.remove(position);
                    YayinAdi.remove(position);
                    TotalNet.remove(position);
                }
            });
        }
    }

    void setDilDenemesiArrayList(ArrayList<dilDenemesi> dil) {
        dilDenemesiArrayList = dil;
        for (int i = 0; i < dilDenemesiArrayList.size(); i++) {
            Name.add(dilDenemesiArrayList.get(i).Name);
            YayinAdi.add(dilDenemesiArrayList.get(i).Yayin);
            TotalNet.add(dilDenemesiArrayList.get(i).totalNet + ""); }
        drawable=R.drawable.denemelist_dil;
        index=3;
    }
    void setEaDenemesiArrayList(ArrayList<eaDenemesi> ea) {
        eaDenemesiArrayList = ea;
        for (int i = 0; i < eaDenemesiArrayList.size(); i++) {
            Name.add(eaDenemesiArrayList.get(i).Name);
            YayinAdi.add(eaDenemesiArrayList.get(i).Yayin);
            TotalNet.add(eaDenemesiArrayList.get(i).totalNet + "");}
            drawable=R.drawable.denemelist_ea;
        index=2;
    }
    void setSayDenemesiArrayList(ArrayList<sayDenemesi> say) {
        sayDenemesiArrayList = say;
        for (int i = 0; i < sayDenemesiArrayList.size(); i++) {
            Name.add(sayDenemesiArrayList.get(i).Name);
            YayinAdi.add(sayDenemesiArrayList.get(i).Yayin);
            TotalNet.add(sayDenemesiArrayList.get(i).totalNet + "");}
        drawable=R.drawable.denemelist_say;
        index=1;
    }
    void setTytDenemesiArrayList(ArrayList<tytDenemesi> tyt) {
        tytDenemesiArrayList = tyt;
        for (int i = 0; i < tytDenemesiArrayList.size(); i++) {
            Name.add(tytDenemesiArrayList.get(i).Name);
            YayinAdi.add(tytDenemesiArrayList.get(i).Yayin);
            TotalNet.add(tytDenemesiArrayList.get(i).totalNet + "");}
        drawable=R.drawable.denemelist_tyt;
        index=0;
    }
    void setSozDenemesiArrayList(ArrayList<sozDenemesi> soz) {
        sozDenemesiArrayList = soz;
        for (int i = 0; i < sozDenemesiArrayList.size(); i++) {
            Name.add(sozDenemesiArrayList.get(i).Name);
            YayinAdi.add(sozDenemesiArrayList.get(i).Yayin);
            TotalNet.add(sozDenemesiArrayList.get(i).totalNet + "");}
        drawable=R.drawable.denemelist_soz;
        index=4;
    }
}
