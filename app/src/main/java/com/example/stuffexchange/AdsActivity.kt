package com.example.stuffexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stuffexchange.handlers.AdHandler
import com.example.stuffexchange.models.Ad

class AdsActivity : AppCompatActivity() {
    lateinit var adNameText: EditText
    lateinit var adPriceText: EditText
    lateinit var adDescText: EditText
    lateinit var cancelAdBtn: Button
    lateinit var createAdBtn: Button
    lateinit var adHandler: AdHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)

        adNameText = findViewById(R.id.adNameText)
        adPriceText = findViewById(R.id.adPriceText)
        adDescText = findViewById(R.id.adDescText)
        cancelAdBtn = findViewById(R.id.cancelAdBtn)
        createAdBtn = findViewById(R.id.createAdBtn)
        adHandler = AdHandler()

        cancelAdBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        createAdBtn.setOnClickListener{
            val name = adNameText.text.toString()
            val price = adPriceText.text.toString().toInt()
            val description = adDescText.text.toString()

            val ad = Ad(name = name, price = price, description = description)

            if(adHandler.create(ad)){
                Toast.makeText(applicationContext, "Ad created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
    }
}