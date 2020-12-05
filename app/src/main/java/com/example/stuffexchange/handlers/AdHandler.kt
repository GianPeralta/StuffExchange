package com.example.stuffexchange.handlers

import com.example.stuffexchange.models.Ad
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdHandler {
    var database: FirebaseDatabase
    var adRef: DatabaseReference

    init{
        database = FirebaseDatabase.getInstance()
        adRef = database.getReference("ads")
    }

    fun create(ad: Ad): Boolean{
        val id = adRef.push().key
        ad.id = id

        adRef.child(id!!).setValue(ad)

        return true
    }

    fun update(ad: Ad): Boolean{
        adRef.child(ad.id!!).setValue(ad)
        return true
    }

    fun delete(ad: Ad): Boolean {
        adRef.child(ad.id!!).removeValue()
        return true
    }
}