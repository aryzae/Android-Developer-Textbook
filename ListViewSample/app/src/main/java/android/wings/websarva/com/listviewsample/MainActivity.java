package android.wings.websarva.com.listviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ListViewオブジェクトを取得。
        ListView lvMenu = findViewById(R.id.lvMenu);
        // ListViewにリストを設定。
        lvMenu.setOnItemClickListener(new ListItemClickListener());
    }

    /*
     * リストがタップされたときの処理が記述されたメンバクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた定食名を取得。
            String item = (String) parent.getItemAtPosition(position);
            // トーストで表示する文字列を生成。
            String show = "あなたが選んだ定食: " + item;
            // トーストの表示。(Toast.LENGTH_LONGのToast.は省略可能)
            Toast.makeText(MainActivity.this, show, Toast.LENGTH_LONG).show();
        }
    }
}
