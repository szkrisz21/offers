package com.vodafone.offers.api

import com.vodafone.offers.converter.OfferDetailsConverterFactory
import com.vodafone.offers.converter.OfferListConverterFactory
import com.vodafone.offers.data.Offer
import com.vodafone.offers.data.OfferDetails
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET



interface OffersAPI {

    class Builder {
        val offerListAPI: OffersAPI get() {
            val retrofit = Retrofit.Builder().baseUrl("https://api.npoint.io/")
                .addConverterFactory(OfferListConverterFactory()).build()
            return retrofit.create(OffersAPI::class.java)
        }
        val offerDetailsAPI: OffersAPI get() {
            val retrofit = Retrofit.Builder().baseUrl("https://api.npoint.io/")
                .addConverterFactory(OfferDetailsConverterFactory()).build()
            return retrofit.create(OffersAPI::class.java)
        }
    }

    //https://api.npoint.io/9e8aefd6337b7be86c59
    //https://www.npoint.io/docs/9e8aefd6337b7be86c59
    @GET("9e8aefd6337b7be86c59")
    fun getOfferList(): Call<Array<Offer>>

    //https://api.npoint.io/aafbb9d8035c7139401a
    //https://www.npoint.io/docs/aafbb9d8035c7139401a
    @GET("aafbb9d8035c7139401a")
    fun getOfferDetails(): Call<OfferDetails>
}