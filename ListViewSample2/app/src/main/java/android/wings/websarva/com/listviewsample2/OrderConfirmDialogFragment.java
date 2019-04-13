package android.wings.websarva.com.listviewsample2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class OrderConfirmDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログビルダーを生成。
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ダイアログのタイトルを設定。
        builder.setTitle(R.string.dialog_title);
        // ダイアログのメッセージを設定。
        builder.setMessage(R.string.dialog_msg);
        // Positive Buttonを設定。
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        // Negative Buttonを設定。
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());
        // Neutral Buttonを設定。
        builder.setNeutralButton(R.string.dialog_btn_nu, new DialogButtonClickListener());
        // ダイアログオブジェクトを生成し、リターン。
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // トーストメッセージ用文字列変数を用意。
            String msg = "";
            // タップされたアクションボタンで分岐
            switch (which) {
                // Positive Buttonの場合
                case DialogInterface.BUTTON_POSITIVE:
                    msg = getString(R.string.dialog_ok_toast);
                    break;
                // Negative Buttonの場合
                case DialogInterface.BUTTON_NEGATIVE:
                    msg = getString(R.string.dialog_ng_toast);
                    break;
                // Neutral Buttonの場合
                case DialogInterface.BUTTON_NEUTRAL:
                    msg = getString(R.string.dialog_nu_toast);
                    break;
            }
            // トーストの表示
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
