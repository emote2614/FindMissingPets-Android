package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.DataModels.RequestDataModel3;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter3 extends RecyclerView.Adapter<RequestAdapter3.ViewHolder> {

    private List<RequestDataModel3> dataSet3;
    private Context context3;

    public RequestAdapter3(
            List<RequestDataModel3> dataSet3, Context context3) {
        this.dataSet3 = dataSet3;
        this.context3 = context3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_layout3, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,
                                 final int position) {

        holder.message3.setText(dataSet3.get(position).getMessage3());
        holder.num.setText(dataSet3.get(position).getNum());
        Glide.with(context3).load(dataSet3.get(position).getUrl()).into(holder.imageView3);

    }


    @Override
    public int getItemCount() {
        return dataSet3.size();
    }

    public void filterList(ArrayList<RequestDataModel3> filteredList){
        dataSet3 = filteredList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView message3, num;
        ImageView imageView3;

        ViewHolder(final View itemView) {
            super(itemView);
            message3 = itemView.findViewById(R.id.message3);
            num = itemView.findViewById(R.id.num);
            imageView3 = itemView.findViewById(R.id.image3);
        }

    }

}