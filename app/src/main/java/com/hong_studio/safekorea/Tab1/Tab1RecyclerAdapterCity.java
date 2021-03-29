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

public class Tab1RecyclerAdapterCity extends RecyclerView.Adapter<Tab1RecyclerAdapterCity.VH> {

    Context context;
    ArrayList<Tab1RecyclerCityItem> items;

    public Tab1RecyclerAdapterCity(Context context, ArrayList<Tab1RecyclerCityItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.tab1_recycler_item3, parent, false);
        VH vh= new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Tab1RecyclerCityItem item= items.get(position);
        holder.tvCityName.setText(item.cityName);
        holder.tvCityTotalCase.setText(item.cityTotalCase);
        holder.tvCityRecovered.setText(item.cityRecovered);
        holder.tvCityDeath.setText(item.cityDeath);
        holder.tvCityRecovering.setText(item.cityRecovering);

        holder.tvCityTotalCase.setTextColor(0xFFFDC16A);
        holder.tvCityRecovered.setTextColor(0xFF22CA80);
        holder.tvCityDeath.setTextColor(0xFFFF4F70);
        holder.tvCityRecovering.setTextColor(0xFF5F76E8);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvCityName, tvCityTotalCase, tvCityRecovered, tvCityDeath, tvCityRecovering;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvCityName= itemView.findViewById(R.id.tv_cityName);
            tvCityTotalCase= itemView.findViewById(R.id.tv_cityTotalCase);
            tvCityRecovered= itemView.findViewById(R.id.tv_cityRecovered);
            tvCityDeath= itemView.findViewById(R.id.tv_cityDeath);
            tvCityRecovering= itemView.findViewById(R.id.tv_cityRecovering);
        }
    }
}
