package com.example.android.journaler.preferences

import android.content.Context
import android.content.SharedPreferences


class PreferenceProvider : PreferenceProviderAbstract(){
    override fun obtain(configuration: PreferenceConfiguration, ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences(configuration.key, configuration.mode)
    }
}