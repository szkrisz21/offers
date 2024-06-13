package com.vodafone.offers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.offers.api.OffersAPI
import com.vodafone.offers.data.OfferDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferDetailsViewModel : ViewModel() {

    val isLoading = MutableLiveData(false)

    val offerDetails = MutableLiveData<OfferDetails>(null)


    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            OffersAPI.Builder().offerDetailsAPI.getOfferDetails().enqueue(object : Callback<OfferDetails> {
                override fun onResponse(p0: Call<OfferDetails>, p1: Response<OfferDetails>) {
                    this@OfferDetailsViewModel.offerDetails.postValue(p1.body())
                    isLoading.postValue(false)
                }

                override fun onFailure(p0: Call<OfferDetails>, p1: Throwable) {
                    isLoading.postValue(false)
                }
            })
        }
    }

}