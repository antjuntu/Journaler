package com.example.android.journaler

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.android.journaler.service.MainService


class Journaler : Application(){

    companion object {
        val tag = "Journaler"
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        Log.v(tag, "[ ON CREATE ]")
        startService()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.v(tag, "[ ON LOW MEMORY ]")
        // If we get low on memory we will stop service if running
        // Since the service is started as sticky, if we explicitly kill
        // our application, the service will restart.
        stopService()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.v(tag, "[ ON TRIM MEMORY ]")
    }

    private fun startService() {
        val serviceIntent = Intent(this, MainService::class.java)
        startService(serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, MainService::class.java)
        stopService(serviceIntent)
    }
}