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

    val loadError = MutableLiveData<Throwable?>(null)

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            OffersAPI.Builder().offerDetailsAPI.getOfferDetails().enqueue(object : Callback<OfferDetails> {
                override fun onResponse(call: Call<OfferDetails>, response: Response<OfferDetails>) {
                    if(response.isSuccessful) {
                        try {
                            this@OfferDetailsViewModel.offerDetails.postValue(response.body())
                        }  catch (throwable: Throwable) {
                            loadError.postValue(throwable)
                        }
                    } else {
                        loadError.postValue(Exception("Response error: ${response.code()}"))
                    }
                    isLoading.postValue(false)
                }

                override fun onFailure(call: Call<OfferDetails>, throwable: Throwable) {
                   loadError.postValue(throwable)
                    isLoading.postValue(false)
                }
            })
        }
    }

}