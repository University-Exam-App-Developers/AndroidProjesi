package com.example.snavadogru.Kendin.ProgramYap;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.snavadogru.Kendin.SqLite.MyDataBaseHelper;
import com.example.snavadogru.R;
import java.util.ArrayList;


public class planRecyclerViewAdapter extends RecyclerView.Adapter<planRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private MyDataBaseHelper dataBaseHelper;
    private int week,day,realPosition;
    private ArrayList<String> gorevler;
    private ArrayList<Integer> checked;
    private OnItemClickListener mylistener;
    private AlertDialog.Builder alertDialog;
    planRecyclerViewAdapter(Context context, ArrayList<String> text, ArrayList<Integer> check,int week,int day){
        dataBaseHelper= new MyDataBaseHelper(context);
        this.context=context;
        this.week=week;
        this.day=day;
        gorevler=text; checked=check;
        Log.d("planRecyclerViewAdapter",gorevler.size()+"");
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plan_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.gorev.setText(gorevler.get(position));
        holder.checkBox.setChecked(checked.get(position)==1);


        alertDialog= new AlertDialog.Builder(context);
        Log.d("delete ad",""+alertDialog);
        alertDialog.setTitle("Görev Sil");
        alertDialog.setMessage("Görevi Silmek ister misin ?");
        alertDialog.setPositiveButton("Sil", (dialog, which) -> {
            if(mylistener !=null)
            {
                Log.d("POSITION", "onBindViewHolder: "+realPosition);
                if (position !=RecyclerView.NO_POSITION)
                    mylistener.onItemClick(realPosition);
                gorevler.remove(realPosition);
                checked.remove(realPosition);
                rebase();
            }
        });

        alertDialog.setNegativeButton("Vazgeç", (dialog, which) -> dialog.dismiss());
    }


    @Override
    public int getItemCount() {
        return gorevler.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView gorev;
        CardView deleteBackground;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gorev=itemView.findViewById(R.id.planText);
            checkBox=itemView.findViewById(R.id.planTextCheckButton);
            deleteBackground=itemView.findViewById(R.id.cardView);


            deleteBackground.setOnLongClickListener(v -> {
                realPosition = getAdapterPosition();
                alertDialog.create().show();
                return true;
            });
            checkBox.setOnClickListener(v -> {
                Log.d("onBindViewHolder","position-> "+getAdapterPosition());
                if (checkBox.isChecked())
                    updateDb(getAdapterPosition(),1);
                else
                    updateDb(getAdapterPosition(),0);
            });

        }
    }
    void setOnItemClickListener(OnItemClickListener listener){
        mylistener=listener; Log.d("OnItemClickListener"," DOLDRUDUM ABI ");
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void updateDb(int position,int Check){
        Cursor cursor=dataBaseHelper.queryItem(week,day,position);
        cursor.moveToNext();
        Log.d("position",""+position+"week "+week+"day "+day);
        dataBaseHelper.updateData(week,day,position,cursor.getString(4),Check);
        cursor.close();
    }

    private void rebase(){
        dataBaseHelper.removeAllData();
        for (int i = 0; i <gorevler.size(); i++){
            dataBaseHelper.addGorev(day,week,i,gorevler.get(i),checked.get(i));}
    }
}
