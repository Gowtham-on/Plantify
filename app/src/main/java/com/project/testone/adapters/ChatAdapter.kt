package com.project.testone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.testone.R
import com.project.testone.dataClass.Chat
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(
    private val context: Context,
    private val chatList: ArrayList<String>
) :  RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUsername: TextView = view.findViewById(R.id.tvMessage)
        val imgUser: CircleImageView = view.findViewById(R.id.usrImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_right, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]

        holder.txtUsername.text = chat
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}