package com.vodafone.offers.converter

import com.vodafone.offers.data.OfferDetails
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class OfferDetailsConverter : Converter<ResponseBody, OfferDetails> {

    override fun convert(responseBody: ResponseBody): OfferDetails {
        return OfferDetails(JSONObject(responseBody.string()))
    }

}

class OfferDetailsConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        return OfferDetailsConverter()
    }
}