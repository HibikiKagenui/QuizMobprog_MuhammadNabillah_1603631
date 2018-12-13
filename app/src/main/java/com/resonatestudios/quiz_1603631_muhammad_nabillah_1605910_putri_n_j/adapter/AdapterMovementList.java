package com.resonatestudios.quiz_1603631_muhammad_nabillah_1605910_putri_n_j.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resonatestudios.quiz_1603631_muhammad_nabillah_1605910_putri_n_j.R;

import java.util.ArrayList;

public class AdapterMovementList extends RecyclerView.Adapter<AdapterMovementList.Holder> {
    Context context;
    ArrayList<String> listDirection;

    public AdapterMovementList(Context context) {
        this.context = context;
        listDirection = new ArrayList<>();
    }

    public ArrayList<String> getListDirection() {
        return listDirection;
    }

    public void setListDirection(ArrayList<String> listDirection) {
        this.listDirection = listDirection;
    }

    public void addToList(String item) {
        // untuk menambahkan item dan update recyclerview
        listDirection.add(0, item);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view_movement, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        String direction = getListDirection().get(i);
        holder.textViewDirection.setText(direction);
    }

    @Override
    public int getItemCount() {
        return getListDirection().size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewDirection;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewDirection = itemView.findViewById(R.id.text_view_direction);
        }
    }
}
