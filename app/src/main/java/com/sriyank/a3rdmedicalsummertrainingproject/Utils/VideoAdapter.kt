package com.sriyank.a3rdmedicalsummertrainingproject.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.videoitems.view.*

class VideoAdapter (var myList: ArrayList<VideoData>):
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun openAction(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.videoitems, parent, false
        )

        return ViewHolder(v, mListener)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = myList[position]

        holder.doctorName.text = "${data.firstName} ${data.lastName}"
        holder.doctorEmail.text = data.Email
        holder.title.text = data.title
        holder.date.text = data.date
        holder.discription.text = data.discription

    }

    class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var doctorName = itemView.findViewById(R.id.doctorNameVideo) as TextView
        var doctorEmail = itemView.findViewById(R.id.doctorEmailVideo) as TextView
        var title = itemView.findViewById(R.id.titleVideo) as TextView
        var date = itemView.findViewById(R.id.dateVideo) as TextView
        var discription = itemView.findViewById(R.id.discVideo) as TextView

        init {

            itemView.openVideo.setOnClickListener {

                listener.openAction(adapterPosition)

            }
        }
    }
}