package com.websarva.wings.android.servicesample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

public class SoundManageService extends Service {
    // メディアプレーヤー
    private MediaPlayer _player;

    @Override
    public void onCreate() {
        // メディアプレーヤーオブジェクトを生成
        _player = new MediaPlayer();
        // 通知チャネルのID文字列を用意
        String id = "soundmanageservice_notification_channel";
        // 通知チャネル名をstring.xmlから取得
        String name = getString(R.string.notification_channel_name);
        // 通知チャネルの重要度を標準に設定
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        // 通知チャネルを生成
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        // NotificationManagerオブジェクトを取得
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知チャネルを設定
        manager.createNotificationChannel(channel);
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
        // プレーヤーが再生中なら停止(Android Oから挙動変更あり https://qiita.com/nukka123/items/791bc4f9043764789ee6 )
        if (_player.isPlaying()) {
            _player.stop();
        }
        // プレーヤー解放
        _player.reset();
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
            // Notificationを作成するBuilderクラス生成
            NotificationCompat.Builder builder = new NotificationCompat.Builder(SoundManageService.this, "soundmanageservice_notification_channel");

            // 通知エリアに表示されるアイコンの設定
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            // 通知ドロワーでの表示タイトルを設定
            builder.setContentTitle(getString(R.string.msg_notification_title_start));
            // 通知ドロワーでの表示メッセージを設定
            builder.setContentText(getString(R.string.msg_notification_text_start));
            // 起動先Activityクラスを指定したIntentオブジェクトを生成
            Intent intent = new Intent(SoundManageService.this, MainActivity.class);
            // 起動先Activityに引き継ぎデータを格納
            intent.putExtra("fromNotification", true);
            // PendingIntentオブジェクトを取得
            PendingIntent stopServiceIntent = PendingIntent.getActivity(SoundManageService.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            // PendingIntentオブジェクトをBuilderに設定
            builder.setContentIntent(stopServiceIntent);
            // タップされた通知メッセージを自動的に消去する設定
            builder.setAutoCancel(true);
            // BuilderからNotificationオブジェクトを生成
            Notification notification = builder.build();
            // NotificationManagerオブジェクトを取得
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知
            manager.notify(1, notification);
        }
    }

    // メディア再生が終了
    private class PlayerCompletionListener implements  MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // Notificationを作成するBuilderクラス生成
            NotificationCompat.Builder builder = new NotificationCompat.Builder(SoundManageService.this, "soundmanageservice_notification_channel");
            // 通知エリアに表示されるアイコンの設定
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            // 通知ドロワーでの表示タイトルを設定
            builder.setContentTitle(getString(R.string.msg_notification_title_finish));
            // 通知ドロワーでの表示メッセージを設定
            builder.setContentText(getString(R.string.msg_notification_text_finish));
            // BuilderからNotificationオブジェクトを生成
            Notification notification = builder.build();
            // NotificationManagerオブジェクトを取得
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知
            manager.notify(0, notification);

            // service終了
            stopSelf();
        }
    }
}
