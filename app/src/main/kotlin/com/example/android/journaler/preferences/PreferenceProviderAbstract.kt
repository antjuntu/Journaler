package com.example.android.journaler.preferences

import android.content.Context
import android.content.SharedPreferences


abstract class PreferenceProviderAbstract {
    abstract fun obtain(configuration: PreferenceConfiguration, ctx: Context): SharedPreferences
}