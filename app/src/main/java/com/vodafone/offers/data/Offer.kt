package com.vodafone.offers.data

import org.json.JSONObject

data class Offer(private val jsonObject: JSONObject) {

    val id: String?
        get() {
            return jsonObject.opt("id") as? String  }
    val rank: Int?
        get() {
            return jsonObject.opt("rank") as? Int }
    val isSpecial: Boolean
        get() { return jsonObject.getBoolean("isSpecial") }
    val name: String
        get() { return jsonObject.getString("name") }
    val shortDescription: String
        get() { return jsonObject.getString("shortDescription") }
}