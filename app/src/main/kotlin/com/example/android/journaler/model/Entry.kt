package com.example.android.journaler.model

import android.location.Location
import com.example.android.journaler.database.DbModel


abstract class Entry(
    var title: String,
    var message: String,
    var location: Location) : DbModel()