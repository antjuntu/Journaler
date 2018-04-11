package com.example.android.journaler.activity

import com.example.android.journaler.R


class TodoActivity : ItemActivity() {
    override val tag = "Todo activity"
    override fun getLayout() = R.layout.activity_todo
}