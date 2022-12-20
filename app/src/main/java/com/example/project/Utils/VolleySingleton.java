package com.example.project.Utils;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

  private static VolleySingleton mInstance;
  private RequestQueue mRequestQueue;
  private static Context mContext;

  private VolleySingleton(Context context) {
    // 어플 컨텍스트 지정
    mContext = context;
    // 요청 큐 가져오기
    mRequestQueue = getRequestQueue();
  }

  public static synchronized VolleySingleton getInstance(Context context) {
    // 인스턴스 null 이면 초기화
    if (mInstance == null) {
      mInstance = new VolleySingleton(context);
    }
    // mysingleton 반환
    return mInstance;
  }

  public RequestQueue getRequestQueue() {
    // 요청큐 null 이면 초기화
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
    }

    // 요청큐 반환
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> request) {
    // 대기열에 요청 추가
    getRequestQueue().add(request);
  }
}