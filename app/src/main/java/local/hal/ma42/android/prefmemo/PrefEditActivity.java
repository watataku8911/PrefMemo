package local.hal.ma42.android.prefmemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * MA42 Androidサンプル10 Androidデータベース接続
 *
 * 第2画面表示用アクティビティクラス。
 * 都道府県メモ編集画面を表示する。
 *
 * @author Shinzo SAITO
 */
public class PrefEditActivity extends AppCompatActivity {
    /**
     * 都道府県リスト画面で選択されたリストの行番号。
     */
    private int _selectedPrefNo = 0;
    /**
     * 都道府県リスト画面で選択された都道府県名。
     */
    private String _selectedPrefName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_edit);

        Intent intent = getIntent();
        _selectedPrefNo = intent.getIntExtra("selectedPrefNo", 0);
        _selectedPrefName = intent.getStringExtra("selectedPrefName");

        String content = "";

        DatabaseHelper helper = new DatabaseHelper(PrefEditActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            content = DataAccess.findContentByPK(db, _selectedPrefNo);
        }
        catch(Exception ex) {
            Log.e("PrefMemo", ex.toString());
        }
        finally {
            db.close();
        }
        EditText etContent = findViewById(R.id.etContent);
        etContent.setText(content);

        TextView tvPref = findViewById(R.id.tvPref);
        tvPref.setText(_selectedPrefName);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new ButtonClickListener());
    }

    /**
     * ボタンがクリックされたときの処理が記述されたメンバクラス。
     * DBにデータを保存する。
     */
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            EditText etContent = findViewById(R.id.etContent);
            String content = etContent.getText().toString();

            DatabaseHelper helper = new DatabaseHelper(PrefEditActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                boolean exist = DataAccess.findRowByPK(db, _selectedPrefNo);
                if(exist) {
                    DataAccess.update(db, _selectedPrefNo, _selectedPrefName, content);
                }
                else {
                    DataAccess.insert(db, _selectedPrefNo, _selectedPrefName, content);
                }
            }
            catch (Exception ex) {
                Log.e("PrefMemo", ex.toString());
            }
            finally {
                db.close();
            }

            finish();
        }
    }
}

