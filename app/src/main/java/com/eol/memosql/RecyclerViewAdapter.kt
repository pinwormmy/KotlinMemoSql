package com.eol.memosql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class RecyclerViewAdapter:RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {
    var listData = ArrayList<Memo>()
    var helper:DBHelper? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memoitemlist,parent,false)



        return Holder(view).apply {

            //삭제버튼 클릭시 이벤트
            itemView.buttonDelete.setOnClickListener {

                var cursor = adapterPosition

                //강제로 null을 허용하기 위해 !! 사용
                helper?.deleteMemo(listData.get(cursor))
                listData.remove(listData.get(cursor))
                notifyDataSetChanged()
            }
        }

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo:Memo = listData.get(position)
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun setMemo(memo:Memo){
            itemView.textMemoNum.text = memo.memoNum.toString()
            itemView.textMemoContent.text = memo.memoContent.toString()
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itemView.textDateTime.text = "${sdf.format(memo.dateTime)}"

        }

    }

}