package com.vodafone.offers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vodafone.offers.data.Offer
import com.vodafone.offers.databinding.LayoutOfferTileBinding
import com.vodafone.offers.databinding.LayoutOfferTypeTitleBinding

class OfferListAdapter(private val onItemClickListener: OnOfferItemClickListener? = null) : RecyclerView.Adapter<OfferListAdapterItemViewHolder>() {

    private val offerItems: MutableList<OfferListAdapterItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return offerItems[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListAdapterItemViewHolder {
        val binding = if(viewType == OfferListAdapterItem.ITEM_TYPE_TITLE) {
            LayoutOfferTypeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            LayoutOfferTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return OfferListAdapterItemViewHolder(this, binding)
    }

    override fun getItemCount(): Int {
        return offerItems.size
    }

    override fun onBindViewHolder(holder: OfferListAdapterItemViewHolder, position: Int) {
        holder.bind(offerItems[position])
    }

    fun onOfferItemClick(offer: Offer) {
        onItemClickListener?.onOfferItemClicked(offer)
    }

    fun addOffers(offers: Array<Offer>) {
        val specialOffers = mutableListOf<OfferListAdapterItem>()
        val normalOffers = mutableListOf<OfferListAdapterItem>()
        offers.forEach { offer ->
            if(offer.id != null && offer.rank != null) {
                if (offer.isSpecial) {
                    specialOffers.add(OfferListAdapterItem(null, offer))
                } else {
                    normalOffers.add(OfferListAdapterItem(null, offer))
                }
            }
        }
        if (normalOffers.isNotEmpty()) {
            normalOffers.add(0, OfferListAdapterItem("Offers", null))
        }
        if (specialOffers.isNotEmpty()) {
            specialOffers.add(0, OfferListAdapterItem("Special Offers", null))
        }
        normalOffers.sortWith { a, b ->
            val aTitle = a.title
            val aRank = a.offer?.rank
            val bTitle = b.title
            val bRank = b.offer?.rank
            if (aTitle == null && bTitle != null) {
                return@sortWith 1
            }
            if (aTitle != null && bTitle == null) {
                return@sortWith -1
            }
            if (aTitle != null && bTitle != null) {
                return@sortWith aTitle.compareTo(bTitle)
            } else if (aRank != null && bRank != null) {
                return@sortWith aRank - bRank
            } else {
                return@sortWith 0
            }
        }
        specialOffers.sortWith { a, b ->
            val aTitle = a.title
            val aRank = a.offer?.rank
            val bTitle = b.title
            val bRank = b.offer?.rank
            if (aTitle == null && bTitle != null) {
                return@sortWith 1
            }
            if (aTitle != null && bTitle == null) {
                return@sortWith -1
            }
            if (aTitle != null && bTitle != null) {
                return@sortWith aTitle.compareTo(bTitle)
            } else if (aRank != null && bRank != null) {
                return@sortWith aRank - bRank
            } else {
                return@sortWith 0
            }
        }
        val itemList = mutableListOf<OfferListAdapterItem>()
        itemList.addAll(normalOffers)
        itemList.addAll(specialOffers)
        offerItems.addAll(itemList)
        notifyDataSetChanged()
    }

    fun setOffers(offers: Array<Offer>) {
        offerItems.clear()
        addOffers(offers)
    }

    fun clear() {
        offerItems.clear()
        notifyDataSetChanged()
    }
}