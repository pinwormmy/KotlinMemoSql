package com.eol.memosql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
// import kotlinx.android.synthetic.main.memoitemlist.view.* 업데이트되면서 안쓰는
import java.text.SimpleDateFormat

class RecyclerViewAdapter:RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {
    var listData = ArrayList<Memo>()
    var helper:DBHelper? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memoitemlist, parent,false)

        return Holder(view).apply {

            //삭제버튼 클릭시 이벤트
            itemView.setOnClickListener {

                var cursor = adapterPosition

                //강제로 null을 허용하기 위해 !! 사용
                helper?.deleteMemo(listData.get(cursor))
                listData.remove(listData.get(cursor))
                notifyDataSetChanged()
            }
        }

    }

    // 오류찾아 삼만리...gradle 오류는ㄴ어케 찾냐? ㄷㄷㄷ
    
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.textMemoNum.text = listData.get(position).memoNum.toString()
        holder.textMemoContent.text = listData.get(position).memoNum.toString()
        holder.textDateTime.text = listData.get(position).memoNum.toString()
    }

    override fun getItemCount(): Int {
        return listData.size

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textMemoNum = itemView.findViewById<TextView>(R.id.textMemoNum)
        val textMemoContent = itemView.findViewById<TextView>(R.id.textMemoContent)
        val textDateTime = itemView.findViewById<TextView>(R.id.textDateTime)

        /*
        fun setMemo(memo:Memo){
            itemView.textMemoNum.text = memo.memoNum.toString()
            itemView.textMemoContent.text = memo.memoContent.toString()
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itemView.textDateTime.text = "${sdf.format(memo.dateTime)}"

        }
        */
    }

}