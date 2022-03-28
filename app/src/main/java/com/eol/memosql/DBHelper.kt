package com.eol.memosql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        var createDB : String = "CREATE TABLE if not exists memo (memoNum integer primary key autoincrement, memoContent text, dateTime integer);";
        db?.execSQL(createDB)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
        val sql : String = "DROP TABLE if exists memo"


    //insert 메소드
    fun insertMemo(memo:Memo){
        val values = ContentValues()
        //넘겨줄 컬럼의 매개변수 지정
        values.put("content",memo.memoContent)
        values.put("datetime",memo.dateTime)
        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.insert("memo",null,values)
        //사용이 끝나면 반드시 close()를 사용하여 메모리누수 가 되지않도록 합시다.
        wd.close()
    }


    //select 메소드
    fun selectMemo():MutableList<Memo>{
        val list = mutableListOf<Memo>()
        //전체조회
        val selectAll = "select * from memo"
        //읽기전용 데이터베이스 변수
        val rd = readableDatabase
        //데이터를 받아 줍니다.
        val cursor = rd.rawQuery(selectAll,null)

        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val memoNum = cursor.getLong(cursor.getColumnIndexOrThrow("memoNum"))
            val memoContent = cursor.getString(cursor.getColumnIndexOrThrow("memoContent"))
            val dateTime = cursor.getLong(cursor.getColumnIndexOrThrow("dateTime"))

            list.add(Memo(memoNum,memoContent,dateTime))
        }
        cursor.close()
        rd.close()

        return list
    }

    //update 메소드
    fun updateMemo(memo:Memo){
        val values = ContentValues()

        values.put("content",memo.memoContent)
        values.put("datetime",memo.dateTime)

        val wd = writableDatabase
        wd.update("memo",values,"id=${memo.memoNum}",null)
        wd.close()

    }

    //delete 메소드
    fun deleteMemo(memo:Memo){
        val delete = "delete from Memo where memoNum = ${memo.memoNum}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }
}

