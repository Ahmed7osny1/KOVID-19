package com.sriyank.a3rdmedicalsummertrainingproject.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.messagedoctor.*
import kotlinx.android.synthetic.main.messagedoctor.view.*


class MessageAdapterDoctor (var myList: ArrayList<MessageDate>):
    RecyclerView.Adapter<MessageAdapterDoctor.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun replyAction(position: Int, replay: String)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.messagedoctor, parent, false
        )

        return ViewHolder(v, mListener)
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


        if ( holder.replay.text == "") {
            holder.img.setImageResource(R.drawable.ic_notreplayed)
            holder.replay.visibility = View.GONE
        } else {
            holder.img.setImageResource(R.drawable.ic_replayed)
            holder.replayNow.visibility = View.GONE
            holder.replayMessageText.visibility = View.GONE
        }


    }

    class ViewHolder(itemView: View,  listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById(R.id.messageImage) as ImageView
        var messageName = itemView.findViewById(R.id.messageName) as TextView
        var message = itemView.findViewById(R.id.message) as TextView
        var replay = itemView.findViewById(R.id.replay) as TextView

        var replayNow = itemView.findViewById(R.id.replayNow) as TextView
        var replayMessageText = itemView.findViewById(R.id.replayMessageText) as TextView


        init {

            itemView.replayNow.setOnClickListener {

                listener.replyAction(adapterPosition,replayMessageText.text.toString())

            }
        }


    }
}