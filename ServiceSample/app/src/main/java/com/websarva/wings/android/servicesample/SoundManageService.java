package com.websarva.wings.android.servicesample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class SoundManageService extends Service {
    // メディアプレーヤー
    private MediaPlayer _player;

    @Override
    public void onCreate() {
        // メディアプレーヤーオブジェクトを生成
        _player = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 音声ファイルのURI文字列作成
        String mediaFileUriStr = "android.resource://" + getPackageName() + "/" + R.raw.music;
        // 音声ファイルのURI作成
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);

        try {
            // メディアプレーヤーに音声ファイルを設定
            _player.setDataSource(SoundManageService.this, mediaFileUri);
            // リスナー設定
            _player.setOnPreparedListener(new PlayerPreparedListener());
            _player.setOnCompletionListener(new PlayerCompletionListener());
            // 非同期でメディア再生を準備
            _player.prepareAsync();
        } catch (IOException e) {
            Log.d("", e.toString());
        }
        // 定数を返す
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // プレーヤーが再生中なら停止
        if (_player.isPlaying()) {
            _player.stop();
        }
        // プレーヤー解放
        _player.release();
        _player = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // メディア再生準備が完了
    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    }

    // メディア再生が終了
    private class PlayerCompletionListener implements  MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopSelf();
        }
    }
}
