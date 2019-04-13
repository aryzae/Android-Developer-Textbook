package android.wings.websarva.com.listviewsample2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListViewオブジェクトを取得。
        ListView lvMenu = findViewById(R.id.lvMenu);
        // ListViewに表示するリストデータ用オブジェクトを作成。
        List<String> menuList = new ArrayList<>();
        // リストデータの登録。
        menuList.add("から揚げ定食");
        menuList.add("ハンバーグ定食");
        menuList.add("生姜焼き定食");
        menuList.add("ステーキ定食");
        menuList.add("野菜炒め定食");
        menuList.add("とんかつ定食");
        menuList.add("ミンチかつ定食");
        menuList.add("チキンカツ定食");
        menuList.add("コロッケ定食");
        menuList.add("焼き魚定食");
        menuList.add("焼肉定食");
        // アダプタオブジェクトを生成。
        // android.R.layout.simple_list_item_1は、デフォでOS側に用意されているListViewのCellのテンプレート
        // android.Rクラスをインポートすると自動生成されるRクラスが読み込まれなくなるので、呼び出す時はandroid.Rで呼び出す
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, menuList);
        // ListViewにアダプタオブジェクトを設定。
        lvMenu.setAdapter(adapter);

        // ListViewにリスナーを設定。
        lvMenu.setOnItemClickListener(new ListItemClickListener());
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 注文確認ダイアログフラグメントオブジェクトを生成。
            OrderConfirmDialogFragment dialogFragment = new OrderConfirmDialogFragment();
            // ダイアログ表示。
            dialogFragment.show(getSupportFragmentManager(), "OrderConfirmDialogFragment");
        }
    }
}
