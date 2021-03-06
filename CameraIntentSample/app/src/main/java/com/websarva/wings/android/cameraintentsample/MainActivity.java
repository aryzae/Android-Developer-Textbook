package com.websarva.wings.android.cameraintentsample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // 保存された画像のURI
    private Uri _imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // カメラアプリとの連携からの戻りでかつ撮影成功時
        if (requestCode == 200 && resultCode == RESULT_OK){
            // 画像を表示するImageViewを取得
            ImageView ivCamera = findViewById(R.id.ivCamera);
            // 撮影された画像をImageViewに設定
            ivCamera.setImageURI(_imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // パーミッションダイアログが許可された時
        if (requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // カメラアプリを起動
            ImageView ivCamera = findViewById(R.id.ivCamera);
            onCameraImageClick(ivCamera);
        }
    }

    // ImageViewがタップされた時
    public void onCameraImageClick(View view) {
        // WRITE_EXTERNAL_STORAGEの許可がない時
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 許可を求めるダイアログを表示
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 2000);
            return;
        }

        // 日時データをyyyyMMddHHmmss形式に
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 現在日時を取得
        Date now = new Date(System.currentTimeMillis());
        // 取得日時をフォーマット形式に整形
        String nowStr = dateFormat.format(now);
        // ストレージに格納する画像ファイル名を生成
        String fileName = "UseCameraActivityPhoto_" + nowStr + ".jpg";

        // ContentValuesオブジェクトを生成
        ContentValues values = new ContentValues();
        // 画像ファイル名を設定
        values.put(MediaStore.Images.Media.TITLE, fileName);
        // 画像ファイルの種類を設定
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // ContentResolverオブジェクトを生成
        ContentResolver resolver = getContentResolver();
        // ContentResolverを使ってURIオブジェクトを生成
        _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Intentオブジェクトを生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // intentにuri設定
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
        // Activity起動(Pixel 3 default cameraでも起動確認)
        startActivityForResult(intent, 200);
    }
}
