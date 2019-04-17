package com.websarva.wings.android.asyncsample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 画面部品ListViewを取得
        ListView lvCityList = findViewById(R.id.lvCityList);
        // SimpleAdapterで使用するListオブジェクトを用意
        List<Map<String, String>> cityList = new ArrayList<>();
        // 都市データを格納するMapオブジェクトの用意とcityListへのデータ登録
        Map<String, String> city = new HashMap<>();
        city.put("name", "大阪");
        city.put("id", "270000");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "神戸");
        city.put("id", "280010");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "豊岡");
        city.put("id", "280020");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "京都");
        city.put("id", "260010");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "舞鶴");
        city.put("id", "260020");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "奈良");
        city.put("id", "290010");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "風屋");
        city.put("id", "290020");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "和歌山");
        city.put("id", "300010");
        cityList.add(city);

        city = new HashMap<>();
        city.put("name", "潮岬");
        city.put("id", "300020");
        cityList.add(city);

        // SimpleAdapterで使用するfrom-to用変数の用意
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        // SimpleAdapterを生成
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, cityList, android.R.layout.simple_expandable_list_item_1, from, to);
        // ListViewにSimpleAdapterを設定
        lvCityList.setAdapter(adapter);
        // ListViewにリスナーを設定
        lvCityList.setOnItemClickListener(new ListItemClickListener());
    }

    // リストが選択されたときの処理が記述されたメンバークラス
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ListViewでタップされた行の都市名とIDを取得
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            String cityName = item.get("name");
            String cityId = item.get("id");
            // 取得した都市名をtvCityNameに設定
            TextView tvCityName = findViewById(R.id.tvCityName);
            tvCityName.setText(cityName + "の天気： ");

            // 天気情報を表示するTextViewを取得
            TextView tvWeatherTelop = findViewById(R.id.tvWeatherTelop);
            // 天気詳細情報を表示するTextViewを取得
            TextView tvWeatherDesc = findViewById(R.id.tvWeatherDesc);
            // WeatherInfoReceiverをnew。引数とした上で取得したTextViewを渡す
            WeatherInfoReceiver receiver = new WeatherInfoReceiver(tvWeatherTelop, tvWeatherDesc);
            // WeatherInfoReceiverを実行
            receiver.execute(cityId);
        }
    }

    private class WeatherInfoReceiver extends AsyncTask<String, String, String> {
        // 現在の天気を表示する画面部品フィールド
        private TextView _tvWeatherTelop;
        // 天気の詳細を表示する画面部品フィールド
        private TextView _tvWeatherDesc;

        // コンストラクタ
        public WeatherInfoReceiver(TextView tvWeatherTelop, TextView tvWeatherDesc) {
            _tvWeatherTelop = tvWeatherTelop;
            _tvWeatherDesc = tvWeatherDesc;
        }

        @Override
        protected String doInBackground(String... params) {
            // 可変長引数の1個目を取得(ID)
            String id = params[0];
            // 都市IDを使って接続URL文字列を作成
            String urlStr = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + id;
            // 天気情報サービスから取得したJSON文字列を格納変数
            String result = "";
            // HTTP接続を行うHttpURLConnectionオブジェクトを宣言。finallyで確実に解放するためにtry外で宣言
            HttpURLConnection connection = null;
            // HTTP接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言。
            InputStream stream = null;

            try {
                // URLオブジェクトを生成
                URL url = new URL(urlStr);
                // URLオブジェクトからHttpURLConnectionオブジェクトを取得
                connection = (HttpURLConnection) url.openConnection();
                // HTTP接続メソッド設定
                connection.setRequestMethod("GET");
                // 接続
                connection.connect();
                // HttpURLConnectionオブジェクトからレスポンスデータを取得
                stream = connection.getInputStream();
                // レスポンスデータであるInputStreamオブジェクトを文字列に変換
                result = ipuntStreamToString(stream);
            } catch (MalformedURLException ex) {
                Log.d("Exception", ex.toString());
            } catch (IOException ex) {
                Log.d("Exception", ex.toString());
            } finally {
                // HttpURLConnectionオブジェクトがnullでないなら解放
                if (connection != null) {
                    connection.disconnect();
                }
                // InputStreamオブジェクトがnullでないなら解放
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Log.d("Exception", ex.toString());
                    }
                }
            }

            // JSON文字列を返す
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // 天気情報用文字列変数を用意
            String telop = "";
            String desc = "";

            // 天気情報JSON文字列を解析
            try {
                // JSON文字列からJSONObjectを生成。これをルートJSONオブジェクトとする
                JSONObject rootJSON = new JSONObject(result);
                // ルートJSON直下のdescriptionを取得
                JSONObject descriptionJSON = rootJSON.getJSONObject("description");
                // description直下のtext(天気概況文)を取得
                desc = descriptionJSON.getString("text");

                // ルートJSON直下のforecastsを取得
                JSONArray forecasts = rootJSON.getJSONArray("forecasts");
                // forecastsのindex0からJSONオブジェクトを取得
                JSONObject forecastNow = forecasts.getJSONObject(0);
                // forecastsからtelop(天気)を取得
                telop = forecastNow.getString("telop");
            } catch (JSONException ex) {
                Log.d("Exception", ex.toString());
            }

            // 天気情報用文字列をTextViewにセット
            _tvWeatherTelop.setText(telop);
            _tvWeatherDesc.setText(desc);
        }

        // InputStreamをStringに変換するJAVAの定型文のようなもの
        private String ipuntStreamToString(InputStream inputStream) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while (0 <= (line = reader.read(b))) {
                stringBuffer.append(b, 0, line);
            }
            return stringBuffer.toString();
        }
    }
}
