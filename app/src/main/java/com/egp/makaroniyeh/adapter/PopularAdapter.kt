package com.egp.makaroniyeh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.egp.makaroniyeh.R
import com.egp.makaroniyeh.helper.Helpers
import com.egp.makaroniyeh.model.Data

class PopularAdapter(var listBs : ArrayList<Data>) : RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var image: ImageView = itemview.findViewById(R.id.popularimage)
        var name: TextView = itemview.findViewById(R.id.popularName)
        var cat: TextView = itemview.findViewById(R.id.popularcat)
        var price: TextView = itemview.findViewById(R.id.popularPrice)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.itempopular, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val bs = listBs[position]
        Glide.with(holder.itemView.context)
            .load("http://192.168.100.12:8000/images/" + bs.image)
            .apply(RequestOptions().override(175, 120))
            .into(holder.image)
        holder.name.text = bs.name
        holder.cat.text = bs.category.name
        holder.price.text = bs.price.toDouble().let { Helpers.getCurrencyIDR(it) }
    }

    override fun getItemCount(): Int {
        return listBs.size
    }

}