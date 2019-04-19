package com.websarva.wings.android.cameraintentsample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // カメラアプリとの連携からの戻りでかつ撮影成功時
        if (requestCode == 200 && resultCode == RESULT_OK){
            // 撮影された画像データの取得
            Bitmap bitmap = data.getParcelableExtra("data");
            // 画像を表示するImageViewを取得
            ImageView ivCamera = findViewById(R.id.ivCamera);
            // 撮影された画像をImageViewに設定
            ivCamera.setImageBitmap(bitmap);
        }
    }

    // ImageViewがタップされた時
    public void onCameraImageClick(View view) {
        // Intentオブジェクトを生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Activity起動(Pixel 3 default cameraだとクラッシュするので、別のカメラアプリ推奨)
        startActivityForResult(intent, 200);
    }
}
