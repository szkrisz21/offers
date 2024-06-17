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

    val loadError = MutableLiveData<Throwable?>(null)


    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            OffersAPI.Builder().offerListAPI.getOfferList().enqueue(object : Callback<Array<Offer>> {
                override fun onResponse(call: Call<Array<Offer>>, response: Response<Array<Offer>>) {
                    if(response.isSuccessful) {
                       try {
                           this@OfferListViewModel.offers.postValue(response.body())
                       }  catch (throwable: Throwable) {
                           loadError.postValue(throwable)
                       }
                    } else {
                        loadError.postValue(Exception("Response error: ${response.code()}"))
                    }
                    isLoading.postValue(false)
                }

                override fun onFailure(call: Call<Array<Offer>>, throwable: Throwable) {
                    loadError.postValue(throwable)
                    isLoading.postValue(false)
                }
            })
        }
    }

}