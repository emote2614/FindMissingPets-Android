package com.example.project.Activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.Utils.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.net.URISyntaxException;

public class PostActivity extends AppCompatActivity {

    EditText message0, message1, message2;
    TextView imageText;
    ImageView image;
    Button submit_button, help_button;
    Uri imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        AndroidNetworking.initialize(getApplicationContext());
        help_button = findViewById(R.id.help_button);
        message0 = findViewById(R.id.message0);
        message1 = findViewById(R.id.message1);
        message2 = findViewById(R.id.message2);
        imageText = findViewById(R.id.imageText);
        image = findViewById(R.id.image);
        submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    uploadPost(message0.getText().toString(),message1.getText().toString(),message2.getText().toString());
                }
            }
        });

        imageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission();
            }
        });

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(PostActivity.this);
                dlg.setTitle("지역 양식");
                dlg.setMessage("<가능한 지역> \n\n서울, 부산, 대구, 인천, 광주, 대전, 울산,\n강원, 경남, 경북, 충남, 충북, 전남, 전북, 제주");
                dlg.setIcon(R.drawable.ic_help_24);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PostActivity.this, "양식을 맞춰주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });

    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    private void permission(){
        if(PermissionChecker.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE)!=PermissionChecker.PERMISSION_GRANTED){
            //asking for permission
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 401);
        }else{
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==401){
            if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED){
                //permission granted
                pickImage();
            }else{
                //permission not granted
                showMessage("PERMISSION DECLINED");
            }
        }
    }

    private void uploadPost(String message0, String message1, String message2){
        String path = "";
        try{
            path = getPath(imageUrl);
        }catch(URISyntaxException e){
            showMessage("WRONG URL");
        }
        String id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("id", "12345");
        AndroidNetworking.upload(Endpoints.upload_request)
                .addMultipartFile("file",new File(path))
                .addQueryParameter("message0", message0)
                .addQueryParameter("message1", message1)
                .addQueryParameter("message2", message2)
                .addQueryParameter("id", id)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        long progress = (bytesUploaded/totalBytes)*100;
                        submit_button.setText(String.valueOf(progress + "%"));
                        submit_button.setOnClickListener(null);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                showMessage("SUCCESSFUL");
                                PostActivity.this.finish();
                            }else{
                                showMessage(response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            if(data != null){
                imageUrl = data.getData();
                Glide.with(getApplicationContext()).load(imageUrl).into(image);
            }
        }
    }

    private boolean isValid(){
        if(message0.getText().toString().isEmpty() || message1.getText().toString().isEmpty() || message2.getText().toString().isEmpty()){
            showMessage("빈칸이 존재합니다.");
            return false;
        }else if(imageUrl == null){
            showMessage("PICK IMAGE");
            return false;
        }else if(!message0.getText().toString().equals("인천") && !message0.getText().toString().equals("서울") && !message0.getText().toString().equals("부산") && !message0.getText().toString().equals("대구") && !message0.getText().toString().equals("광주") && !message0.getText().toString().equals("대전") && !message0.getText().toString().equals("울산") && !message0.getText().toString().equals("세종") && !message0.getText().toString().equals("경남") && !message0.getText().toString().equals("경북") && !message0.getText().toString().equals("경기") && !message0.getText().toString().equals("충북") && !message0.getText().toString().equals("충남") && !message0.getText().toString().equals("전남") && !message0.getText().toString().equals("전북") && !message0.getText().toString().equals("제주") && !message0.getText().toString().equals("강원")){
            showMessage("지역 양식을 맞춰주세요.");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}