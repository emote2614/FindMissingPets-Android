package com.example.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.DataModels.RequestDataModel;
import com.example.project.R;


import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RequestDataModel> dataSet;
    private Context context;

    public RequestAdapter(
            List<RequestDataModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,
                                 final int position) {

        holder.message0.setText(dataSet.get(position).getMessage0());
        holder.message1.setText(dataSet.get(position).getMessage1());
        holder.message2.setText(dataSet.get(position).getMessage2());
        Glide.with(context).load(dataSet.get(position).getUrl()).into(holder.imageView);

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "지역 : " + holder.message0.getText().toString() + "\n\n" + "전화번호 : " + holder.message1.getText().toString() + "\n\n" + "내용 : " + holder.message2.getText().toString());
                intent2.putExtra(Intent.EXTRA_SUBJECT,"유기동물을 도와주세요.");
                context.startActivity(Intent.createChooser(intent2, "Share..."));
            }
        });

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + holder.message1.getText()));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filterList(ArrayList<RequestDataModel> filteredList){
        dataSet = filteredList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView message0, message1, message2;
        ImageView imageView, callButton, shareButton;

        ViewHolder(final View itemView) {
            super(itemView);
            message0 = itemView.findViewById(R.id.message0);
            message1 = itemView.findViewById(R.id.message1);
            message2 = itemView.findViewById(R.id.message2);
            imageView = itemView.findViewById(R.id.image);
            callButton = itemView.findViewById(R.id.call_button);
            shareButton = itemView.findViewById(R.id.share_button);
        }

    }

}