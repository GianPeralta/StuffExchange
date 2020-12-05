package com.example.stuffexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.stuffexchange.handlers.AdHandler
import com.example.stuffexchange.models.Ad
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyAdsActivity : AppCompatActivity() {
    lateinit var adHandler: AdHandler
    lateinit var ads:ArrayList<Ad>
    lateinit var myAdsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ads)

        adHandler = AdHandler()
        ads = ArrayList()
        myAdsListView = findViewById(R.id.myAdsListView)

        registerForContextMenu(myAdsListView)
    }

    override fun onStart() {
        super.onStart()
        adHandler.adRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                ads.clear()
                snapshot.children.forEach {
                        it -> val ad = it.getValue(Ad::class.java)
                    ads.add(ad!!)
                }

                val adapter = ArrayAdapter<Ad>(applicationContext, android.R.layout.simple_list_item_1, ads)
                myAdsListView.adapter = adapter


            }

        })
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId) {
            R.id.edit_ad -> {
                val ad = ads[info.position]
                val adId = ad.id
                val adName = ad.name
                val adPrice = ad.price.toString()
                val adDescription = ad.description

                val intent = Intent(applicationContext, EditAdActivity::class.java)
                intent.putExtra("id", adId)
                intent.putExtra("name", adName)
                intent.putExtra("price", adPrice)
                intent.putExtra("description", adDescription)
                startActivity(intent)
                true
            }R.id.delete_ad -> {
                val ad = ads[info.position]
                if(adHandler.delete(ad)){
                    Toast.makeText(applicationContext, "Ad deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else ->super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.another_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.marketplace -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }R.id.create_ad -> {
                startActivity(Intent(this, AdsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}