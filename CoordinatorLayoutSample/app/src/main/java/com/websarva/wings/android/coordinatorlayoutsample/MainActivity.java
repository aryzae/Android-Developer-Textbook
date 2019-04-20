package com.websarva.wings.android.coordinatorlayoutsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbarを取得
        Toolbar toolbar = findViewById(R.id.toolbar);
        // ツールバーにロゴ設定
        toolbar.setLogo(R.mipmap.ic_launcher);
        // ツールバーにタイトル設定
        toolbar.setTitle(R.string.toolbar_title);
        // ツールバーにサブタイトル設定
        toolbar.setSubtitle(R.string.toolbar_subtitle);
        // ツールバーのサブタイトルの文字色設定
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        // アクションバーにツールバーを設定
        setSupportActionBar(toolbar);
    }
}
