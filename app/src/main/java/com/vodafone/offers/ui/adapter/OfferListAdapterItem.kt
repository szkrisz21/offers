package com.vodafone.offers.ui.adapter

import com.vodafone.offers.data.Offer
import java.security.InvalidParameterException

class OfferListAdapterItem(val title: String?, val offer: Offer?) {
    companion object {
        const val ITEM_TYPE_TITLE = 0
        const val ITEM_TYPE_OFFER = 1
    }
    init {
        if(title.isNullOrBlank() && offer == null) {
            throw InvalidParameterException("one of title or offer must be set!")
        }
        if(!title.isNullOrBlank() && offer != null) {
            throw InvalidParameterException("one of title or offer must be set!")
        }
    }
    val itemType: Int
        get () { return if (title != null) ITEM_TYPE_TITLE else ITEM_TYPE_OFFER }
}