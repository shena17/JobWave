package com.example.jobwave

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobwave.R.*
import com.example.jobwave.adapter.ViewAdsAdapter
import com.example.jobwave.models.Advertisement
import com.example.jobwave.services.AdsServices
import com.google.firebase.FirebaseApp

class ViewAdsActivity : AppCompatActivity() {

    private lateinit var recyclerViewAds: RecyclerView
    private lateinit var adsServices: AdsServices
    private lateinit var adsAdapter: ViewAdsAdapter
    private lateinit var noRecordsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_view_ads)

        recyclerViewAds = findViewById(id.recyclerViewAds)
        noRecordsTextView = findViewById(id.noRecordsTextView)
        recyclerViewAds.layoutManager = LinearLayoutManager(this)

        val firebaseApp = FirebaseApp.initializeApp(this)
        if (firebaseApp != null) {
            adsServices = AdsServices(firebaseApp)
        } else {
            Toast.makeText(this, "Failed to initialize Firebase", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        adsServices.readAllAdvertisements { ads ->
            if (ads.isNotEmpty()) {
                noRecordsTextView.visibility = View.GONE
                adsAdapter = ViewAdsAdapter(ads) { ad ->
                    showDeleteConfirmationDialog(ad)
                }
                recyclerViewAds.adapter = adsAdapter
            } else {
                noRecordsTextView.visibility = View.VISIBLE
                recyclerViewAds.visibility = View.GONE
            }
        }
    }

    private fun showDeleteConfirmationDialog(ad: Advertisement) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this ad?")
            .setPositiveButton("Delete") { dialog, which ->
                adsServices.deleteAdvertisement(ad.key ?: "") { success ->
                    if (success) {
                        Toast.makeText(this, "Advertisement deleted successfully", Toast.LENGTH_SHORT).show()
                        val updatedAds = adsAdapter.ads.toMutableList().apply { remove(ad) }
                        if (updatedAds.isNotEmpty()) {
                            noRecordsTextView.visibility = View.GONE
                            adsAdapter = ViewAdsAdapter(updatedAds) { updatedAd ->
                                showDeleteConfirmationDialog(updatedAd)
                            }
                            recyclerViewAds.adapter = adsAdapter
                        } else {
                            noRecordsTextView.visibility = View.VISIBLE
                            recyclerViewAds.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(this, "Failed to delete advertisement", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
