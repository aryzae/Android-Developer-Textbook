package com.websarva.wings.android.recyclerviewsample;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
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
        // アクションバーにツールバーを設定
        setSupportActionBar(toolbar);
        // CollapsingToolbarLayoutを取得
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbarLayout);
        // タイトルを設定
        toolbarLayout.setTitle(getString(R.string.toolbar_title));
        // 通常サイズ時の文字色設定
        toolbarLayout.setExpandedTitleColor(Color.WHITE);
        // 縮小サイズ時の文字色設定
        toolbarLayout.setCollapsedTitleTextColor(Color.LTGRAY);
    }
}
