package android.wings.websarva.com.databasesample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
        String note = etNote.getText().toString();

        // DBHelperオブジェクトの作成
        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
        // DBHelperオブジェクトからDB接続オブジェクトを取得
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            // リストで選択されたカクテルのメモデータを削除後にインサートを行う
            // 削除用SQLを用意
            String sqlDelete = "DELETE FROM cocktailmemo WHERE _id = ?";
            // SQL文字列をもとにプリペアドステートメントを取得
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            // 変数のバインド
            stmt.bindLong(1, _cocktailId);
            // 削除SQLの実行
            stmt.executeUpdateDelete();

            // インサート用SQLを用意
            String sqlInsert = "INSERT INTO cocktailmemo (_id, name, note) VALUES (?, ?, ?)";
            // SQL文字列をもとにプリペアドステートメントを取得
            stmt = db.compileStatement(sqlInsert);
            // 変数のバインド
            stmt.bindLong(1, _cocktailId);
            stmt.bindString(2, _cocktailName);
            stmt.bindString(3, note);
            // インサートSQLの実行
            stmt.executeInsert();
        } finally {
            // データベース接続オブジェクトの解放
            db.close();
        }

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

            // DBHelperオブジェクトの作成
            DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
            // DBHelperオブジェクトからDB接続オブジェクトを取得
            SQLiteDatabase db = helper.getWritableDatabase();

            try {
                // リストで選択されたカクテルのメモデータを削除後にインサートを行う
                // 削除用SQLを用意
                String sql = "SELECT * FROM cocktailmemo WHERE _id = " + _cocktailId;
                // SQLの実行
                Cursor cursor = db.rawQuery(sql, null);
                // DBから取得した値を格納する変数の用意。データがなかったときのための初期値も用意
                String note = "";
                // SQL実行の戻り値であるカーソルオブジェクトをループさせてDB内のデータを取得
                while (cursor.moveToNext()) {
                    // カラムのインデックス値を取得
                    int idxNote = cursor.getColumnIndex("note");
                    // カラムのインデックス値をもとに実際のデータを取得
                    note = cursor.getString(idxNote);
                }
                // 感想のEditTextの各画面部品を取得しDBの値を反映
                EditText etNote = findViewById(R.id.etNote);
                etNote.setText(note);
            } finally {
                // データベース接続オブジェクトの解放
                db.close();
            }
        }
    }
}
