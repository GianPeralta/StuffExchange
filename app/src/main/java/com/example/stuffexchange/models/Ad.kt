package com.example.stuffexchange.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Ad(var id: String? = "", var name: String? = "", var price: Int? = 0, var description: String? = "") {
    override fun toString(): String {
        return "\n$name\nPrice: ₱$price\nDescription: $description\n"
    }
}