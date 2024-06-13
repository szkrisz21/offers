package com.vodafone.offers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.offers.api.OffersAPI
import com.vodafone.offers.data.Offer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferListViewModel : ViewModel() {

    val isLoading = MutableLiveData(false)

    val offers = MutableLiveData(emptyArray<Offer>())


    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            OffersAPI.Builder().offerListAPI.getOfferList().enqueue(object : Callback<Array<Offer>> {
                override fun onResponse(p0: Call<Array<Offer>>, p1: Response<Array<Offer>>) {
                    this@OfferListViewModel.offers.postValue(p1.body())
                    isLoading.postValue(false)
                }

                override fun onFailure(p0: Call<Array<Offer>>, p1: Throwable) {
                    isLoading.postValue(false)
                }
            })
        }
    }

}