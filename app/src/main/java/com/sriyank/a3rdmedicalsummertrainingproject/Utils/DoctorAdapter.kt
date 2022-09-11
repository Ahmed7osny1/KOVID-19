package com.sriyank.a3rdmedicalsummertrainingproject.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.doctorcard.view.*

class DoctorAdapter(var myList: ArrayList<DoctorData>):
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun bookAction(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.doctorcard, parent, false
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
        holder.doctorName.text = "${data.firstName} ${data.lastName}"
        holder.doctorEmail.text = data.Email
        holder.doctorPhone.text = data.Phone
        holder.doctorAge.text = data.age

    }

    class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById(R.id.doctorImage) as ImageView
        var doctorName = itemView.findViewById(R.id.doctorName) as TextView
        var doctorEmail = itemView.findViewById(R.id.doctorEmail) as TextView
        var doctorPhone = itemView.findViewById(R.id.doctorPhone) as TextView
        var doctorAge = itemView.findViewById(R.id.doctorAge) as TextView

        init {

            itemView.bookNow.setOnClickListener {

                listener.bookAction(adapterPosition)

            }
        }
    }
}