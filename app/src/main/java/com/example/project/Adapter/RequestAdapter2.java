package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataModels.RequestDataModel2;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter2 extends RecyclerView.Adapter<RequestAdapter2.YoutubeViewHolder> {

    List<RequestDataModel2> youtubeVideoList;

    public RequestAdapter2() {

    }

    public RequestAdapter2(List<RequestDataModel2> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public YoutubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_layout2, parent, false);

        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeViewHolder holder, int position) {

        holder.videoWeb.loadData(youtubeVideoList.get(position).getVideoUrl(), "text/html", "utf-8");

    }


    @Override
    public int getItemCount(){ return youtubeVideoList.size(); }

    public class YoutubeViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public YoutubeViewHolder(View itemView){
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.video_view);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient(){


            });
        }


    }

}
