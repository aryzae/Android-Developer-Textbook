package com.websarva.wings.android.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // メディアプレーヤーフィールド
    private MediaPlayer _player;
    // 再生・一時停止ボタン
    private Button _btPlay;
    // 戻るボタン
    private Button _btBack;
    // 進むボタン
    private Button _btForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 各ボタンの取得
        _btPlay = findViewById(R.id.btPlay);
        _btBack = findViewById(R.id.btBack);
        _btForward = findViewById(R.id.btForward);
        // プレイヤーオブジェクトの生成
        _player = new MediaPlayer();
        // 音声ファイルのURI文字列を作成
        String mediaFileUriStr = "android.resource://" + getPackageName() + "/" + R.raw.music1;
        // 音声ファイルのURI文字列をもとにURIオブジェクトを生成
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);

        try {
            // メディアプレーヤーに音声ファイルを指定
            _player.setDataSource(MainActivity.this, mediaFileUri);
            // 非同期でのメディア再生準備が完了した際のリスナーを設定
            _player.setOnPreparedListener(new PlayerPreparedListener());
            // メディア再生が終了した際のリスナーを設定
            _player.setOnCompletionListener(new PlayerCompletionListener());
            // 非同期でメディア再生を準備
            _player.prepareAsync();
        } catch (IOException ex) {
            Log.d("Exception", ex.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // プレーヤーが再生中
        if (_player.isPlaying()) {
            // プレーヤーを停止
            _player.stop();
        }
        // プレーヤーを解放
        _player.release();
        _player = null;
    }

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            // 各ボタンタップ可能に設定
            _btPlay.setEnabled(true);
            _btBack.setEnabled(true);
            _btForward.setEnabled(true);
        }
    }

    private class PlayerCompletionListener implements  MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 再生ボタンのラベルを"再生"に設定
            _btPlay.setText(R.string.bt_play_play);
        }
    }

    public void onPlayButtonClick(View view) {
        // プレーヤー再生中の時
        if (_player.isPlaying()) {
            // プレーヤーを一時停止
            _player.pause();
            // 再生ボタンのラベルを"再生"に設定
            _btPlay.setText(R.string.bt_play_play);
        } else {
            // プレーヤーを再生
            _player.start();
            // 再生ボタンのラベルを"一時停止"に設定
            _btPlay.setText(R.string.bt_play_pause);
        }
    }

    public void onBackButtonClick(View view) {
        // 再生位置を先頭に
        _player.seekTo(0);
    }

    public void onForwardButtonClick(View view) {
        // 現在再生中のメディアファイルの長さを取得
        int duration = _player.getDuration();
        // 再生位置を終端に
        _player.seekTo(duration);
        // 再生中出ない時は再生開始
        if (!_player.isPlaying()) {
            _player.start();
        }
    }
}
