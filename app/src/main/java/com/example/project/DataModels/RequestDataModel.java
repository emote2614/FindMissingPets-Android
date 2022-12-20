package com.example.project.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestDataModel {

    @SerializedName("num")
    @Expose
    private String num;
    @SerializedName("message0")
    @Expose
    private String message0;
    @SerializedName("message1")
    @Expose
    private String message1;
    @SerializedName("message2")
    @Expose
    private String message2;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private String id;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMessage0() {
        return message0;
    }

    public void setMessage0(String message0) {
        this.message0 = message0;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {return message2;}

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
