package com.websarva.wings.android.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // メディアプレーヤーフィールド
    private MediaPlayer _player;
    // 再生・一時停止ボタン
    private Button _btPlay;
    // 戻るボタン
    private Button _btBack;
    // 進むボタン
    private Button _btForward;
    // 選択中の音楽index
    private int musicIndex = 0;
    // 音楽ファイルリスト(rawフォルダにmusic1〜5がある想定)
    private int[] musics = {R.raw.music1, R.raw.music2, R.raw.music3, R.raw.music4, R.raw.music5};
    // 次の曲行く際、継続して再生するかの状態を持つ
    private boolean _keepPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 各ボタンの取得
        _btPlay = findViewById(R.id.btPlay);
        _btBack = findViewById(R.id.btBack);
        _btForward = findViewById(R.id.btForward);

        // 音声ファイルのURI文字列をもとにURIオブジェクトを生成
        Uri mediaFileUri = getCurrentMusicUri();
        // MediaPlayer作成
        createMediaPlayer(mediaFileUri);

        // スイッチを取得
        Switch loopSwitch = findViewById(R.id.swLoop);
        // スイッチにリスナーを設定
        loopSwitch.setOnCheckedChangeListener(new LoopSwitchChangedListener());
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

    private Uri getNextMusicUri() {
        musicIndex += 1;
        if (musicIndex >= 5) {
            musicIndex = 0;
        }
        return getCurrentMusicUri();
    }

    private Uri getCurrentMusicUri() {
        // 音声ファイルのURI文字列を作成
        String mediaFileUriStr = "android.resource://" + getPackageName() + "/" + musics[musicIndex];
        // 音声ファイルのURI文字列をもとにURIオブジェクトを生成
        return Uri.parse(mediaFileUriStr);
    }

    private void createMediaPlayer(Uri mediaFileUri) {
        // プレイヤーオブジェクトの生成
        _player = new MediaPlayer();

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

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            // 各ボタンタップ可能に設定
            _btPlay.setEnabled(true);
            _btBack.setEnabled(true);
            _btForward.setEnabled(true);

            if (_keepPlaying) {
                _player.start();
                // 再生ボタンのラベルを"一時停止"に設定
                _btPlay.setText(R.string.bt_play_pause);
            }
        }
    }

    private class PlayerCompletionListener implements  MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // ループ設定がされていない時、再生ボタンのラベルを"再生"に設定
            if (!_player.isLooping() && !_keepPlaying) {
                _btPlay.setText(R.string.bt_play_play);
            }
            // 次の曲のための準備
            _player.stop();
            _player.release();
            _player = new MediaPlayer();

            Uri nextMediaFileUri = getNextMusicUri();
            createMediaPlayer(nextMediaFileUri);
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
        // 再生状態を保持
        _keepPlaying = _player.isPlaying();

        // 現在再生中のメディアファイルの長さを取得
        int duration = _player.getDuration();
        // 再生位置を終端に
        _player.seekTo(duration);
        // 再生中出ない時は再生開始
        if (!_player.isPlaying()) {
            _player.start();
        }
    }

    private class LoopSwitchChangedListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // ループするかどうかを設定
            _player.setLooping(isChecked);
        }
    }
}
