package com.hong_studio.safekorea.Tab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hong_studio.safekorea.R;

import java.util.ArrayList;

public class Tab1RecyclerAdapter2 extends RecyclerView.Adapter<Tab1RecyclerAdapter2.VH> {

    Context context;
    ArrayList<Tab1RecyclerItem> items;

    public Tab1RecyclerAdapter2(Context context, ArrayList<Tab1RecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.tab1_recycler_item2, parent, false);
        VH vh= new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Tab1RecyclerItem item= items.get(position);
        holder.tvDataName.setText(item.dataName);
        holder.tvDataNumber.setText(item.dataNumber);

//        switch (position){
//            case 0:
//                holder.tvDataNumber.setTextColor(0xFFFDC16A);
//                break;
//            case 1:
//                holder.tvDataNumber.setTextColor(0xFF22CA80);
//                break;
//            case 2:
//                holder.tvDataNumber.setTextColor(0xFFFF4F70);
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvDataName, tvDataNumber;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvDataName= itemView.findViewById(R.id.tv_dataName);
            tvDataNumber= itemView.findViewById(R.id.tv_dataNumber);
        }
    }
}
