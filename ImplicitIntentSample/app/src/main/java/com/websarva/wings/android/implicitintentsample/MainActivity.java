package com.websarva.wings.android.implicitintentsample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
        GPSLocationListener locationListener = new GPSLocationListener();
        // 位置情報の追跡を開始
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ACCESS_FINE_LOCATIONの許可を求めるダイアログを表示。その際リクエストコードを1000に設定
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1000);
            // onCreateを終了
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // ACCESS_FINE_LOCATIONに対するパーミッションダイアログの選択で許可したとき
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // LocationManagerオブジェクトを取得
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // 位置情報が更新された際のリスナーオブジェクトを生成
            GPSLocationListener locationListener = new GPSLocationListener();
            // 再度ACCESS_FINE_LOCATIONの許可を確認
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // 位置情報の追跡を開始
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
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
