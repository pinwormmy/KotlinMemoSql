package com.eol.memosql

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    private val helper = DBHelper(this,"memo",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = RecyclerViewAdapter()
        adapter.listData.addAll(helper.selectMemo())
        adapter.helper = helper

        val recyclerMemoList = findViewById<RecyclerView>(R.id.recyclerMemoList)
        val editMemo = findViewById<EditText>(R.id.editMemo)

        recyclerMemoList.adapter = adapter
        recyclerMemoList.layoutManager = LinearLayoutManager(this)

        //저장버튼을 누를시 이벤트
        val writeMemoButton = findViewById<Button>(R.id.writeMemoButton)
        writeMemoButton.setOnClickListener {

            if(editMemo.text.toString().isNotEmpty()){
                val memo = Memo(null,editMemo.text.toString(),System.currentTimeMillis())
                helper.insertMemo(memo)
            }
            adapter.listData.clear()
            adapter.listData.addAll(helper.selectMemo())

            //데이터가 추가된 다음 리사이클러뷰에 반영해 주기위한 함수
            //데이터가 먼저 생성되고 리사이클러뷰가 다음에 호촐되면 사용하지 않아도 됩니다.
            adapter.notifyDataSetChanged()
            editMemo.setText("")

        }



    }
}