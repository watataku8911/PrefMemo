package local.hal.ma42.android.prefmemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * MA42 Androidサンプル10 Androidデータベース接続
 *
 * データベース上のデータを処理するクラス。
 *
 * @author Shinzo SAITO
 */
public class DataAccess {
    /**
     * 主キーによるメモ内容検索メッソド。
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @return 主キーに対応するcontentカラムの値。
     */
    public static String findContentByPK(SQLiteDatabase db, int id) {
        String sql = "SELECT content FROM memos WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        String result = "";
        while(cursor.moveToNext()) {
            int idxContent = cursor.getColumnIndex("content");
            result = cursor.getString(idxContent);
        }
        return result;
    }

    /**
     * 主キーによるレコード存在チェックメソッド。
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @return 主キーに対応するcontentカラムの値。
     */
    public static boolean findRowByPK(SQLiteDatabase db, int id) {
        String sql = "SELECT COUNT(*) AS count FROM memos WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = false;
        if(cursor.moveToFirst()) {
            int idxCount = cursor.getColumnIndex("count");
            int count = cursor.getInt(idxCount);
            if(count >= 1) {
                result = true;
            }
        }
        return result;
    }

    /**
     * メモ情報を更新するメソッド。
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @param name 都道府県名。
     * @param content メモ内容。
     * @return 更新件数。
     */
    public static int update(SQLiteDatabase db, int id, String name, String content) {
        String sql = "UPDATE memos SET name = ?, content = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2, content);
        stmt.bindLong(3, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }

    /**
     * メモ情報を新規登録するメソッド。
     * @param db SQLiteDatabaseオブジェクト。
     * @param id 主キー値。
     * @param name 都道府県名。
     * @param content メモ内容。
     * @return 登録したレコードの主キー値。
     */
    public static long insert(SQLiteDatabase db, int id, String name, String content) {
        String sql = "INSERT INTO memos (_id, name, content) VALUES (?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        stmt.bindString(2, name);
        stmt.bindString(3, content);
        long insertedId = stmt.executeInsert();
        return insertedId;
    }
}
