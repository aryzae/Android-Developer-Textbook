package android.wings.websarva.com.fragmentsample;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        return view;
    }

}
