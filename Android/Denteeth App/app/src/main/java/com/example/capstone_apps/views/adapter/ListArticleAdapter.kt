package com.example.capstone_apps.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.DataItem
import com.example.capstone_apps.helper.Key

class ListArticleAdapter(private val listMedic: List<DataItem>) : RecyclerView.Adapter<ListArticleAdapter.ListViewHolder>() {
  private lateinit var onItemClickCallback: OnItemClickCallback

  fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
    this.onItemClickCallback = onItemClickCallback
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
    return ListViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    holder.title.text = listMedic[position].title
    holder.description.text = listMedic[position].description
    Glide.with(holder.itemView.context)
      .load(Key.BASE_URL+listMedic[position].image)
      .transform(CenterInside(), RoundedCorners(25))
      .into(holder.imgPhoto)

    holder.itemView.setOnClickListener {
      onItemClickCallback.onItemClicked(listMedic[holder.adapterPosition])
    }
  }

  override fun getItemCount(): Int = listMedic.size

  class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.findViewById(R.id.tv_item_Title)
    var description: TextView = itemView.findViewById(R.id.tv_item_description)
    var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
  }

  interface OnItemClickCallback {
    fun onItemClicked(list: DataItem)
  }
}