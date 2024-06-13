package com.vodafone.offers.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vodafone.offers.R
import com.vodafone.offers.databinding.ActivityOfferDetailsBinding
import com.vodafone.offers.viewmodels.OfferDetailsViewModel
import kotlinx.coroutines.launch

class OfferDetailsActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_OFFER_ID: String = "OfferDetailsActivity.EXTRA_OFFER_ID"
        const val EXTRA_RESULT_MESSAGE: String = "OfferDetailsActivity.EXTRA_RESULT_MESSAGE"
    }

    private var offerId: String? = null
    private lateinit var rootBinding: ActivityOfferDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offerId = intent.getStringExtra(EXTRA_OFFER_ID)
        if(offerId == null) {
            val resultData = Intent()
            resultData.putExtra(EXTRA_RESULT_MESSAGE, getString(R.string.msg_offer_id_not_passed))
            setResult(Activity.RESULT_CANCELED, resultData)
            finish()
        }
        enableEdgeToEdge()
        rootBinding = ActivityOfferDetailsBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)
        setSupportActionBar(rootBinding.toolbar)
        rootBinding.toolbar.setNavigationOnClickListener { this.finish() }
        enableEdgeToEdge()
        val viewModel: OfferDetailsViewModel by viewModels()
        if(viewModel.offerDetails.value == null) {
            viewModel.load()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.offerDetails.observe(this@OfferDetailsActivity) { offerDetails ->
                    rootBinding.offerDetails = offerDetails
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_item_close) {
            setResult(Activity.RESULT_CANCELED)
            this.finish()
        }
        return true
    }

}