package service;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vo.PersonVo;

public class SQLitePersonDao {
    static final String dbName="person_db";
    static final String tbName="person_tb";
    Context context;

    public SQLitePersonDao(Context context) {
        this.context = context;
    }

    SQLiteDatabase getDatabase(){
        SQLiteDatabase db = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);

        //테이블이 존재하지 않으면 새로 생성합니다.
        db .execSQL("CREATE TABLE IF NOT EXISTS " + tbName
                + " ( idx Integer primary key autoincrement,name text,tel text);");

        return db;
    }

    //insert
    public int insert(PersonVo vo){
        int res = 0;
        SQLiteDatabase db = null;
        try {
            db = getDatabase();
            String sql = String.format("insert into %s(name,tel) values('%s','%s')",
                                             tbName, vo.getName(),vo.getTel()
                    );
            db.execSQL(sql);
            res = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(db!=null)
                db.close();
        }
        return res;
    }

    //delete
    public int delete(int idx){
        int res = 0;
        SQLiteDatabase db = null;
        try {
            db = getDatabase();
            String sql = String.format("delete from %s where idx=%d",tbName, idx);
            db.execSQL(sql);
            res = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(db!=null)
                db.close();
        }
        return res;
    }

    //update
    public int update(PersonVo vo){
        int res = 0;
        SQLiteDatabase db = null;
        try {
            db = getDatabase();
            String sql = String.format("update %s set name='%s',tel='%s'  where idx=%d",
                                              tbName,  vo.getName(), vo.getTel(), vo.getIdx()  );
            db.execSQL(sql);
            res = 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(db!=null)
                db.close();
        }
        return res;
    }

    public ArrayList<PersonVo> selectList() {

        ArrayList<PersonVo> list = new ArrayList<PersonVo>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        String [] col_names = {"idx","name","tel"};


        try {
            db = getDatabase();
            //                                                where
            cursor = db.query(tbName, col_names, null, null, null, null, "idx desc");
            if(cursor!=null){

                if(cursor.moveToFirst()){ //첫번째 레코드이동
                    do{

                        int idx     = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String tel  = cursor.getString(2);

                        //vo포장후 ArrayList넣기
                        list.add(new PersonVo(idx,name,tel));

                        //Logging...
                        String str = String.format("%d/%s/%s", idx,name,tel);
                        Log.d("MY",str);

                    }while(cursor.moveToNext());//다음레코드로 이동
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null)
                cursor.close();
            if(db!=null)
                db.close();
        }

        return list;
    }
}
