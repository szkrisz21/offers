package com.vodafone.offers.data

import org.json.JSONObject

data class OfferDetails(private val jsonObject: JSONObject) {

    val id: String?
        get() { return jsonObject.optString("id") }
    val name: String
        get() { return jsonObject.getString("name") }
    val shortDescription: String
        get() { return jsonObject.getString("shortDescription") }
    val description: String
        get() { return jsonObject.getString("description") }
}