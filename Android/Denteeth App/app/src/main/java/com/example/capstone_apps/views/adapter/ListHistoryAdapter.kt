package com.example.capstone_apps.views.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.DataHistory
import com.example.capstone_apps.helper.DateFormatter
import java.util.*

class ListHistoryAdapter(private val listHistory: List<DataHistory>): RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
    return ListViewHolder(view)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    holder.status.text = listHistory[position].status
    holder.date.text = DateFormatter.formatDate(listHistory[position].createdAt, TimeZone.getDefault().id)
  }

  override fun getItemCount(): Int = listHistory.size

  class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var status: TextView = itemView.findViewById(R.id.tv_item_Title)
    var date: TextView = itemView.findViewById(R.id.tv_item_tanggal)
  }
}