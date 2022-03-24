package com.eol.memosql

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase

    // 오늘 별로 한게 없다. 디비 추가시 새로고침하는것도 버거워..리사이클뷰 공부해보기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memoList = findViewById<TextView>(R.id.memoList)
        val writeMemoBtn = findViewById<Button>(R.id.writeMemoBtn)
        val writeMemoText = findViewById<EditText>(R.id.writeMemoText)

        dbHelper = DBHelper(this, "newdb.db", null, 1)
        database = dbHelper.writableDatabase

        var query = "SELECT * FROM mytable;"
        var c = database.rawQuery(query,null)
        while(c.moveToNext()){
            System.out.println("txt : " + c.getString(c.getColumnIndexOrThrow("txt")))
            memoList.append(c.getString(c.getColumnIndexOrThrow("txt")) + "\n")
        }

        var test = 0

        // 메모 입력 테스트
        writeMemoBtn.setOnClickListener{

            var contentValues = ContentValues()
            var memoContent:String = writeMemoText.text.toString()
            contentValues.put("txt", memoContent)
            Toast.makeText(this, memoContent, Toast.LENGTH_SHORT).show()
            database.insert("mytable",null,contentValues)

        }
    }


}