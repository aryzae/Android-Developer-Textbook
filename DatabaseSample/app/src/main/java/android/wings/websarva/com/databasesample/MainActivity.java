package android.wings.websarva.com.databasesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // 選択されたカクテルの主キーIDを表すフィールド
    int _cocktailId = -1;
    // 選択されたカクテル名を表すフィールド
    String _cocktailName = "";
    // カクテル名を表示するTextViewフィールド
    TextView _tvCocktailName;
    // 保存ボタンフィールド
    Button _btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // カクテル名を表示するTextViewを崇徳
        _tvCocktailName = findViewById(R.id.tvCocktailName);
        // 保存ボタンを取得
        _btnSave = findViewById(R.id.btnSave);
        // カクテルリスト用ListViewを取得
        ListView lvCocktail = findViewById(R.id.lvCocktail);
        // lvCocktailにリスナーを登録
        lvCocktail.setOnItemClickListener(new ListItemClickListener());
    }

    // 保存ボタンがタップされたときの処理メソッド
    public void onSaveButtonClick(View view) {
        // 感想欄を取得
        EditText etNote = findViewById(R.id.etNote);
        // カクテル名を未選択に変更
        _tvCocktailName.setText(getString(R.string.tv_name));
        // 感想欄の入力値を消去
        etNote.setText("");
        // 保存ボタンをタップできないように変更
        _btnSave.setEnabled(false);
    }

    // リストがタップされたときの処理メンバークラス
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた行番号をフィールドの主キーIDに代入
            _cocktailId = position;
            // タップされた行のデータを取得
            _cocktailName = (String) parent.getItemAtPosition(position);
            // カクテル名を表示するTextViewに表示カクテル名を設定
            _tvCocktailName.setText(_cocktailName);
            // 保存ボタンをタップできるように設定
            _btnSave.setEnabled(true);
        }
    }
}
