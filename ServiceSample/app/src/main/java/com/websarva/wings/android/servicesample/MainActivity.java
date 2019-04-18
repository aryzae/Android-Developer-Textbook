package com.websarva.wings.android.servicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Intentオブジェクトを取得
        Intent intent = getIntent();
        // 通知のタップからの引き継ぎデータを取得
        boolean fromNotification = intent.getBooleanExtra("fromNotification", false);
        // 引き継ぎデータが存在する(通知をタップしての起動)時の処理
        if (fromNotification) {
            // 再生ボタンをタップ不可にし、停止ボタンをタップ可能に変更
            Button btPlay = findViewById(R.id.btPlay);
            Button btStop = findViewById(R.id.btStop);
            btPlay.setEnabled(false);
            btStop.setEnabled(true);
        }
    }

    public void onPlayButtonClick(View view) {
        // インテントオブジェクトを生成
        Intent intent = new Intent(MainActivity.this, SoundManageService.class);
        // サービスを起動
        startService(intent);
        // 再生ボタンをタップ不可にし、停止ボタンをタップ可能に変更
        Button btPlay = findViewById(R.id.btPlay);
        Button btStop = findViewById(R.id.btStop);
        btPlay.setEnabled(false);
        btStop.setEnabled(true);
    }

    public void onStopButtonClick(View view) {
        // インテントオブジェクトを生成
        Intent intent = new Intent(MainActivity.this, SoundManageService.class);
        // サービスを起動
        stopService(intent);
        // 再生ボタンをタップ可能にし、停止ボタンをタップ不可に変更
        Button btPlay = findViewById(R.id.btPlay);
        Button btStop = findViewById(R.id.btStop);
        btPlay.setEnabled(true);
        btStop.setEnabled(false);
    }
}
