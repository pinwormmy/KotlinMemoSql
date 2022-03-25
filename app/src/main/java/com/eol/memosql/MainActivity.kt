package com.eol.memosql

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:RecyclerViewAdapter //adapter객체 먼저 선언해주기!

    val mDatas=mutableListOf<TxtData>()

    lateinit var dbHelper : DBHelper
    lateinit var database : SQLiteDatabase

    // 오늘 별로 한게 없다. 디비 추가시 새로고침하는것도 버거워..리사이클뷰 공부해보기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializelist()
        initTxtRecyclerView()

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



        // 메모 입력 테스트
        writeMemoBtn.setOnClickListener{

            var contentValues = ContentValues()
            var memoContent:String = writeMemoText.text.toString()
            contentValues.put("txt", memoContent)
            Toast.makeText(this, memoContent, Toast.LENGTH_SHORT).show()
            database.insert("mytable",null,contentValues)

            memoList.invalidate()

        }
    }

    fun initTxtRecyclerView(){
        val adapter=RecyclerViewAdapter() //어댑터 객체 만듦
        adapter.datalist=mDatas //데이터 넣어줌
        binding.recyclerView.adapter=adapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager=LinearLayoutManager(this) //레이아웃 매니저 연결
    }

    fun initializelist(){ //임의로 데이터 넣어서 만들어봄
        with(mDatas){
            add(TxtData(10,"test1"))
            add(TxtData(11,"test2"))

        }
    }


}