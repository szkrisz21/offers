package com.vodafone.offers.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.vodafone.offers.databinding.LayoutOfferTileBinding
import com.vodafone.offers.databinding.LayoutOfferTypeTitleBinding

class OfferListAdapterItemViewHolder(private val adapter: OfferListAdapter, private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: OfferListAdapterItem) {
        if(binding is LayoutOfferTypeTitleBinding && !item.title.isNullOrBlank()) {
            binding.root.visibility = View.VISIBLE
            binding.title.text = item.title
        } else if(binding is LayoutOfferTileBinding && item.offer != null) {
            binding.root.visibility = View.VISIBLE
            binding.offer = item.offer
            binding.adapter = adapter
        } else {
            binding.root.visibility = View.GONE
        }
    }
}