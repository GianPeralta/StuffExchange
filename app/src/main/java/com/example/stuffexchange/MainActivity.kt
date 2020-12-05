package com.example.stuffexchange

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stuffexchange.handlers.AdHandler
import com.example.stuffexchange.models.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var adHandler: AdHandler
    lateinit var ads:ArrayList<Ad>
    lateinit var adListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adHandler = AdHandler()
        ads = ArrayList()
        adListView = findViewById(R.id.adListView)


    }

    override fun onStart() {
        super.onStart()
        adHandler.adRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                ads.clear()
                snapshot.children.forEach {
                    it -> val ad = it.getValue(Ad::class.java)
                    ads.add(ad!!)
                }

                val adapter = ArrayAdapter<Ad>(applicationContext, android.R.layout.simple_list_item_1, ads)
                adListView.adapter = adapter


            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_ad -> {
                startActivity(Intent(this, AdsActivity::class.java))
                true
            }R.id.my_ads -> {
                startActivity(Intent(this, MyAdsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}