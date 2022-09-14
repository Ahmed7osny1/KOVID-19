package com.sriyank.a3rdmedicalsummertrainingproject.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.a3rdmedicalsummertrainingproject.R

class MessageAdapter(var myList: ArrayList<MessageDate>):
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.messagepatient, parent, false
        )

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = myList[position]

        holder.img.setImageResource(data.Image)
        holder.messageName.text = "${data.firstName} ${data.lastName}"
        holder.message.text = data.message
        holder.replay.text = data.replay


    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById(R.id.messageImage) as ImageView
        var messageName = itemView.findViewById(R.id.messageName) as TextView
        var message = itemView.findViewById(R.id.message) as TextView
        var replay = itemView.findViewById(R.id.replay) as TextView


    }
}