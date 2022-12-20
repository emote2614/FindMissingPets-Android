package com.example.project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Adapter.RequestAdapter2;
import com.example.project.DataModels.RequestDataModel2;
import com.example.project.R;

import java.util.ArrayList;
import java.util.Vector;

public class ListActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;

    Vector<RequestDataModel2> youtubeVideos = new Vector<RequestDataModel2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        recyclerView = findViewById(R.id.recycleView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bhpJSr25dR0\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mnPlK6NR92U\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/cTLN6633F_8\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/S2-NvwCCFH0\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/tUwZYTfw9EE\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add( new RequestDataModel2("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/2WsRbkH5X58\" frameborder=\"0\" allowfullscreen></iframe>"));

        RequestAdapter2 requestAdapter2 = new RequestAdapter2(youtubeVideos);

        recyclerView.setAdapter(requestAdapter2);

    }
}