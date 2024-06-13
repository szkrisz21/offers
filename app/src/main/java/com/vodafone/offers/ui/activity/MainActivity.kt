package com.vodafone.offers.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.vodafone.offers.ui.adapter.OfferListAdapter
import com.vodafone.offers.ui.adapter.OfferListAdapterItem
import com.vodafone.offers.ui.adapter.OnOfferItemClickListener
import com.vodafone.offers.data.Offer
import com.vodafone.offers.databinding.ActivityMainBinding
import com.vodafone.offers.viewmodels.OfferListViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnOfferItemClickListener {
    
    private lateinit var rootBinding: ActivityMainBinding
    private lateinit var offerDetailsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        offerDetailsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val message = result.data?.getStringExtra(OfferDetailsActivity.EXTRA_RESULT_MESSAGE)
            if (this::rootBinding.isInitialized && result.resultCode == RESULT_CANCELED && !message.isNullOrBlank()) {
                Snackbar.make(rootBinding.root, message, Snackbar.LENGTH_LONG).show()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        rootBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)
        setSupportActionBar(rootBinding.toolbar)
        rootBinding.offersList.adapter =
            OfferListAdapter(this@MainActivity)
        rootBinding.swipeRefresh.setOnRefreshListener {
            loadData(true)
        }
        loadData()
    }

    fun loadData(forced: Boolean = false) {
        val viewModel: OfferListViewModel by viewModels()
        if(forced || viewModel.offers.value?.isEmpty() != false) {
            (rootBinding.offersList.adapter as? OfferListAdapter)?.clear()
            viewModel.load()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.observe(this@MainActivity) { isLoading->
                    rootBinding.swipeRefresh.isRefreshing = isLoading
                }
                viewModel.offers.observe(this@MainActivity) { offers ->
                    (rootBinding.offersList.adapter as? OfferListAdapter)?.setOffers(offers)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOfferItemClicked(offer: Offer) {
        val intent = Intent(this, OfferDetailsActivity::class.java)
        intent.putExtra(OfferDetailsActivity.EXTRA_OFFER_ID, offer.id)
        offerDetailsLauncher.launch(intent)
    }
}