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
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuThanksFragment extends Fragment {

    /*
     * 大画面かどうかの判定フラグ
     * 判定ロジックは同一画面に注文完了表示用FrameLayoutが存在するかで判断
     */
    private boolean _isLayoutXLarge = true;
    private Activity _parentActivity;

    public MenuThanksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 親クラスのメソッドの呼び出し
        super.onCreate(savedInstanceState);
        // 所属するアクティビティの取得
        _parentActivity = getActivity();

        // フラグメントマネージャーの取得
        FragmentManager manager = getFragmentManager();
        // フラグメントマネージャーからメニューリストフラグメントを取得
        MenuListFragment menuListFragment = (MenuListFragment) manager.findFragmentById(R.id.fragmentMenuList);
        // メニューリストフラグメントの有無で画面判定
        if (menuListFragment == null) {
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);

        // Bundleオブジェクト
        Bundle extras;
        // 大画面の時
        if (_isLayoutXLarge) {
            extras = getArguments();
        } else {
            // インテント取得
            Intent intent = _parentActivity.getIntent();
            // インテントから引き継ぎデータの取得
            extras = intent.getExtras();
        }

        // 注文した定食名と金額変数を用意。うまく取得できなかった時のために初期値を設定
        String menuName = "";
        String menuPrice = "";
        // 引き継ぎデータがあれば、データを設定
        if (extras != null) {
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        // 定食名と金額を表示するTextViewの取得
        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);
        // TextViewに定食名と金額を設定
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);

        // 戻るボタンを取得
        Button btBackButton = view.findViewById(R.id.btBackButton);
        btBackButton.setOnClickListener(new ButtonClickListener());

        // インフレートされた画面を返す
        return view;
    }

    /*
     * ボタンが押されたときの処理が記述されたメンバークラス
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (_isLayoutXLarge) {
                // フラグメントマネージャーの取得
                FragmentManager manager = getFragmentManager();
                // フラグメントトランザクションの開始
                FragmentTransaction transaction = manager.beginTransaction();
                // 自身の削除
                transaction.remove(MenuThanksFragment.this);
                // フラグメントトランザクションのコミット
                transaction.commit();
            } else {
                _parentActivity.finish();
            }
        }
    }

}
