package com.vodafone.offers.converter

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONException
import org.junit.Test

class OfferListConverterTest {


    private var validResponse: ResponseBody = ResponseBody.create(MediaType.parse("text/json"), """
        {
            "offers": [
                {
                    "id": "1",
                    "rank": 2,
                    "isSpecial": true,
                    "name": "name",
                    "shortDescription": "shortDescription"
                },
                {
                    "id": null,
                    "rank": null,
                    "isSpecial": false,
                    "name": "name",
                    "shortDescription": "shortDescription"
                }
            ]
        }
    """.trimIndent())

    private var invalidResponse: ResponseBody = ResponseBody.create(MediaType.parse("text/json"), "invalid json....")

    @Test
    fun convert() {
        val converter = OfferListConverter()
        var conversionErrorCaught = false
        try {
            converter.convert(invalidResponse)
        } catch (_: JSONException) { conversionErrorCaught = true }
        val data = converter.convert(validResponse)
        assert(conversionErrorCaught)
        assert(data.count() == 2)
        assert(data[0].id == "1")
        assert(data[0].rank == 2)
        assert(data[0].name == "name")
        assert(data[0].shortDescription == "shortDescription")
        assert(data[1].id == null)
        assert(data[1].rank == null)
        assert(data[1].name == "name")
        assert(data[1].shortDescription == "shortDescription")
    }
}