package com.project.testone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.testone.R
import com.project.testone.dataClass.ItemsViewModelHomePage

class HomePageAdapter(private val mList: List<ItemsViewModelHomePage>) :
    RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_recycler_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.imageOne.setImageResource(ItemsViewModel.image)

        holder.imageTwo.setImageResource((ItemsViewModel.image))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageOne: ImageView = itemView.findViewById(R.id.firstImage)
        val imageTwo: ImageView = itemView.findViewById(R.id.secondImage)
    }

}