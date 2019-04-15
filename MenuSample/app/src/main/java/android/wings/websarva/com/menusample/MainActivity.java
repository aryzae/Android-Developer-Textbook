package android.wings.websarva.com.menusample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // リストビューを表すフィールド。
    private ListView _lvMenu;
    // リストビューに表示するリストデータ。
    private List<Map<String, Object>> _menuList;
    // SimpleAdapterの第4引数fromに使用する定数フィールド。
    private static final String[] FROM = {"name", "price"};
    // SimpleAdapterの第5引数toに使用する定数フィールド。
    private static final int[] TO = {R.id.tvMenuName, R.id.tvMenuPrice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 画面部品ListViewを取得し、フィールドに格納。
        _lvMenu = findViewById(R.id.lvMenu);
        // 定食メニューListオブジェクトをprivateメソッドを利用して用意し、フィールドに格納。
        _menuList = createTeishokuList();
        // SimpleAdapterを生成。
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, _menuList, R.layout.row, FROM, TO);
        // Adapterの登録。
        _lvMenu.setAdapter(adapter);
        // リストタップのリスナークラス登録。
        _lvMenu.setOnItemClickListener(new ListItemClickListener());
    }

    private List<Map<String, Object>> createTeishokuList() {
        // SimpleAdapterで使用するListオブジェクトを用意。
        List<Map<String, Object>> menuList = new ArrayList<>();
        // 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        Map<String, Object> menu = new HashMap<>();
        menu.put("name", "から揚げ定食");
        menu.put("price", 800);
        menu.put("desc", "若鳥のから揚げにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", 850);
        menu.put("desc", "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "生姜焼き定食");
        menu.put("price", 850);
        menu.put("desc", "すりおろし生姜を使った生姜焼きにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ステーキ定食");
        menu.put("price", 1000);
        menu.put("desc", "国産牛のステーキにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "野菜炒め定食");
        menu.put("price", 750);
        menu.put("desc", "季節の野菜炒めにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "とんかつ定食");
        menu.put("price", 900);
        menu.put("desc", "ロースとんかつにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ミンチかつ定食");
        menu.put("price", 850);
        menu.put("desc", "手ごねミンチカツにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "チキンカツ定食");
        menu.put("price", 900);
        menu.put("desc", "ボリュームたっぷりチキンカツにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "コロッケ定食");
        menu.put("price", 850);
        menu.put("desc", "北海道ポテトコロッケにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼き魚定食");
        menu.put("price", 850);
        menu.put("desc", "鰆の塩焼きにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼肉定食");
        menu.put("price", 950);
        menu.put("desc", "特性たれの焼肉にサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        return menuList;
    }

    /*
     * リストがタップされたときの処理が記述されたメンバークラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMap型
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
            // 定食名と金額を取得
            String menuName = (String) item.get("name");
            Integer menuPrice = (Integer) item.get("price");
            // インテントオブジェクトを生成。
            Intent intent = new Intent(MainActivity.this, MenuThanksActivity.class);
            // 第2画面に送るデータを格納。
            intent.putExtra("menuName", menuName);
            // 金額に円を追加
            intent.putExtra("menuPrice", menuPrice + "円");
            // 第2画面の起動。
            startActivity(intent);
        }

    }
}
