package android.wings.websarva.com.fragmentsample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuListFragment extends Fragment {

    /*
     * 大画面かどうかの判定フラグ
     * 判定ロジックは同一画面に注文完了表示用FrameLayoutが存在するかで判断
     */
    private boolean _isLayoutXLarge = true;
    private Activity _parentActivity;

    public MenuListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 親クラスのメソッドを呼び出し
        super.onCreate(savedInstanceState);
        // 所属するアクティビティオブジェクトを取得
        _parentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);

        // 画面部品ListViewを取得。
        ListView lvMenu = view.findViewById(R.id.lvMenu);
        // SimpleAdapterで使用するListオブジェクトを用意。
        List<Map<String, String>> menuList = new ArrayList<>();
        // 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        Map<String, String> menu = new HashMap<>();
        menu.put("name", "から揚げ定食");
        menu.put("price", "800円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "生姜焼き定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ステーキ定食");
        menu.put("price", "1000円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "野菜炒め定食");
        menu.put("price", "750円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "とんかつ定食");
        menu.put("price", "900円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "ミンチかつ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "チキンカツ定食");
        menu.put("price", "900円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "コロッケ定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼き魚定食");
        menu.put("price", "850円");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name", "焼肉定食");
        menu.put("price", "950円");
        menuList.add(menu);

        // SimpleAdapter第4引数from用データの用意。
        String[] from = {"name", "price"};
        // SimpleAdapter第5引数to用データの用意。
        int[] to = {android.R.id.text1, android.R.id.text2};
        // SimpleAdapterを生成。
        SimpleAdapter adapter = new SimpleAdapter(_parentActivity, menuList, android.R.layout.simple_list_item_2, from, to);
        // Adapterの登録。
        lvMenu.setAdapter(adapter);

        // リスナーの登録
        lvMenu.setOnItemClickListener(new ListItemClickListener());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // 親クラスのメソッド
        super.onActivityCreated(savedInstanceState);
        // 所属するアクティビティからmenuThanksFrameを取得
        View menuThanksFrame = _parentActivity.findViewById(R.id.menuThanksFrame);
        // 画面サイズの判定
        if (menuThanksFrame == null) {
            _isLayoutXLarge = false;
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMap型
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            // 定食名と金額を取得
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            // 引き継ぎデータを格納するBundleオブジェクトの生成
            Bundle bundle = new Bundle();
            bundle.putString("menuName", menuName);
            bundle.putString("menuPrice", menuPrice);

            // 大画面(X-Large)の場合
            if (_isLayoutXLarge) {
                // フラグ面とマネージャーの取得
                FragmentManager manager = getFragmentManager();
                // フラグメントトランザクションの開始
                FragmentTransaction transaction = manager.beginTransaction();
                // 注文完了フラグメントを生成
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                // 引き継ぎデータを注文完了フラグメントにセット
                menuThanksFragment.setArguments(bundle);
                // 生成した注文完了フラグメントをmenuThanksFrameレイアウト部品に置き換え
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                // フラグメントトランザクションのコミット
                transaction.commit();
            } else {
                // インテントオブジェクトを生成。
                Intent intent = new Intent(_parentActivity, MenuThanksActivity.class);
                // 第2画面に送るデータを格納。
                intent.putExtras(bundle);
                // 第2画面の起動。
                startActivity(intent);
            }
        }
    }
}
