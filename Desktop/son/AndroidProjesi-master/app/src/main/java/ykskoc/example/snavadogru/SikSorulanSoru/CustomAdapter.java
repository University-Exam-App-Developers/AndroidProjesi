package ykskoc.example.snavadogru.SikSorulanSoru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ykskoc.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
   ArrayList<Sik_Soru> arrayListsorular = new ArrayList<>();
   LayoutInflater layoutInflater;
   Context context;

    public CustomAdapter(ArrayList<Sik_Soru> arrayListsorular, Context context) {
        this.arrayListsorular = arrayListsorular;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //her satır için temsil edilecek arayüz seçilir
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.soru_list_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.soru.setText(arrayListsorular.get(position).getSoru());
        holder.linearLayout.setTag(holder);
        holder.soru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListsorular.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView soru;
        CardView linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            soru = itemView.findViewById(R.id.item_sss_soru);
            linearLayout = itemView.findViewById(R.id.sss_item_linearlayout);
        }
    }
    public void showpopup(int position)
    {
        LayoutInflater layout = LayoutInflater.from(context);
        View tasarim = layout.inflate(R.layout.popup_cevap_sss,null);

        final TextView textView= tasarim.findViewById(R.id.sss_item_textview);
        textView.setText(arrayListsorular.get(position).getCevap());


        Dialog ad = new Dialog(context);
        ad.setContentView(tasarim);
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ad.show();
    }
}
