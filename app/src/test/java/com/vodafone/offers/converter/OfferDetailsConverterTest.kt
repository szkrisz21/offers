package com.vodafone.offers.converter

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONException
import org.junit.Test

class OfferDetailsConverterTest {


    private var validResponse: ResponseBody = ResponseBody.create(MediaType.parse("text/json"), """
        {
            "id": "1",
            "name": "name",
            "shortDescription": "shortDescription",
            "description": "description",
            "additionalField": "notParsed"
        }
    """.trimIndent())

    private var invalidResponse: ResponseBody = ResponseBody.create(MediaType.parse("text/json"), "invalid json....")

    @Test
    fun convert() {
        val converter = OfferDetailsConverter()
        var conversionErrorCaught = false
        try {
            converter.convert(invalidResponse)
        } catch (_: JSONException) { conversionErrorCaught = true }
        val data = converter.convert(validResponse)
        assert(conversionErrorCaught)
        assert(data.id == "1")
        assert(data.name == "name")
        assert(data.shortDescription == "shortDescription")
        assert(data.description == "description")
    }
}