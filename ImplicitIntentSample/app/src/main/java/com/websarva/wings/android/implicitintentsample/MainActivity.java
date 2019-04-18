package com.websarva.wings.android.implicitintentsample;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    // 緯度を表示するTextView
    private TextView _tvLatitude;
    // 軽度を表示するTextView
    private TextView _tvLongitude;
    // 緯度
    private double _latitude = 0;
    // 軽度
    private double _longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 緯度と軽度を表示するTextViewの中身を取得
        _tvLatitude = findViewById(R.id.tvLatitude);
        _tvLongitude = findViewById(R.id.tvLongitude);
        // LocationManagerオブジェクトを取得
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 位置情報が更新された際のリスナーオブジェクトを生成
        GPSLocationListener locationListerner = new GPSLocationListener();
        // 位置情報の追跡を開始
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListerner);
    }

    public void onMapSearchButtonClick(View view) {
        // 入力欄に入力されたキーワード文字列を取得
        EditText etSearchWord = findViewById(R.id.etSearchWord);
        String searchWord = etSearchWord.getText().toString();

        try {
            // 入力されたキーワードをURLエンコード
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
            // マップアプリと連携するURI文字列を生成
            String uriStr = "geo:0,0?q=" + searchWord;
            // URI文字列からURIオブジェクトを生成
            Uri uri = Uri.parse(uriStr);
            // Intentオブジェクトを生成
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // Activityを起動
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            Log.e("IntentStartActivity", "検索キーワード変換失敗", e);
        }
    }

    public void onMapShowCurrentButtonClick(View view) {
        // フィールドの緯度と軽度の値をもおとにマップアプリと連携するURI文字列を生成
        String uriStr = "geo:" + _latitude + "," + _longitude;
        // URI文字列からURIオブジェクトを生成
        Uri uri = Uri.parse(uriStr);
        // Intentオブジェクトを生成
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Activity開始
        startActivity(intent);
    }

    private class GPSLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            // locationオブジェクトから緯度軽度を取得
            _latitude = location.getLatitude();
            _longitude = location.getLongitude();
            // 取得した緯度軽度をTextViewに表示
            _tvLatitude.setText(Double.toString(_latitude));
            _tvLongitude.setText(Double.toString(_longitude));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
