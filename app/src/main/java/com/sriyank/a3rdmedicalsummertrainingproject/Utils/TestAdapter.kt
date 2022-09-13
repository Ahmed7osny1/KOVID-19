package com.sriyank.a3rdmedicalsummertrainingproject.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.testitems.view.*

class TestAdapter (var myList: ArrayList<testData>):
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun deleteAction(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.testitems, parent, false
        )

        return ViewHolder(v, mListener)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = myList[position]

        holder.testName.text = data.testName
        holder.testDate.text = data.testDate
        holder.testTime.text = data.testTime
        holder.testLocation.text = data.testLocation

    }

    class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var testName = itemView.findViewById(R.id.testName) as TextView
        var testDate = itemView.findViewById(R.id.testDate) as TextView
        var testTime = itemView.findViewById(R.id.testTime) as TextView
        var testLocation = itemView.findViewById(R.id.testLocation) as TextView

        init {

            itemView.deleteTest.setOnClickListener {

                listener.deleteAction(adapterPosition)

            }
        }
    }
}