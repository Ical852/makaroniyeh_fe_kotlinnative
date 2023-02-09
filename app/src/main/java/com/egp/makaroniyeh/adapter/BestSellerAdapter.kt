package com.egp.makaroniyeh.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.egp.makaroniyeh.R
import com.egp.makaroniyeh.helper.Helpers
import com.egp.makaroniyeh.model.Data

class BestSellerAdapter(var listBs : ArrayList<Data>) : RecyclerView.Adapter<BestSellerAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var image: ImageView = itemview.findViewById(R.id.bestSellerimage)
        var name: TextView = itemview.findViewById(R.id.bestSellerName)
        var cat: TextView = itemview.findViewById(R.id.bestSellercat)
        var price: TextView = itemview.findViewById(R.id.bestSellerPrice)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.itembestseller, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val bs = listBs[position]
        Glide.with(holder.itemView.context)
            .load(bs.productImage[0].image)
            .into(holder.image)
        holder.name.text = bs.name
        holder.cat.text = bs.category.name
        holder.price.text = bs.price.toDouble().let { Helpers.getCurrencyIDR(it) }

        holder.name.maxLines = 1
        holder.name.ellipsize
    }

    override fun getItemCount(): Int {
        return listBs.size
    }
}