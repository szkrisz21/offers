package com.vodafone.offers.converter

import com.vodafone.offers.data.Offer
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class OfferListConverter : Converter<ResponseBody, Array<Offer>> {

    override fun convert(responseBody: ResponseBody): Array<Offer> {
        val result = mutableListOf<Offer>()
        JSONObject(responseBody.string()).optJSONArray("offers")?.also { offersArray ->
            for (i in 0 until offersArray.length()) {
                result.add(Offer(offersArray.getJSONObject(i)))
            }
        }
        return result.toTypedArray()
    }

}

class OfferListConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        return OfferListConverter()
    }
}