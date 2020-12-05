package com.example.stuffexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stuffexchange.handlers.AdHandler
import com.example.stuffexchange.models.Ad

class EditAdActivity : AppCompatActivity() {
    lateinit var editNameText: EditText
    lateinit var editPriceText: EditText
    lateinit var editDescText: EditText
    lateinit var editAdBtn: Button
    lateinit var cancelEditAdBtn: Button
    lateinit var adHandler: AdHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ad)

        editNameText = findViewById(R.id.editNameText)
        editPriceText = findViewById(R.id.editPriceText)
        editDescText = findViewById(R.id.editDescText)
        editAdBtn = findViewById(R.id.editAdBtn)
        cancelEditAdBtn = findViewById(R.id.cancelEditAdBtn)
        adHandler = AdHandler()

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")

        editNameText.setText(name)
        editPriceText.setText(price)
        editDescText.setText(description)

        editAdBtn.setOnClickListener{
            val name = editNameText.text.toString()
            val price = editPriceText.text.toString().toInt()
            val description = editDescText.text.toString()

            val ad = Ad(id = id, name = name, price = price, description = description)

            if(adHandler.update(ad)) {
                Toast.makeText(applicationContext, "Ad updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
    }


}