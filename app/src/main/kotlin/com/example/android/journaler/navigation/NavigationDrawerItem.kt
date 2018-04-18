package com.example.android.journaler.navigation


data class NavigationDrawerItem(
    val title: String,
    val onClick: Runnable,
    var enabled: Boolean = true
)