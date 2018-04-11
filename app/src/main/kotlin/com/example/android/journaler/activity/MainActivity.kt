package com.example.android.journaler.activity

import android.os.Bundle
import com.example.android.journaler.R
import com.example.android.journaler.fragment.ItemsFragment
import com.example.android.journaler.fragment.ManualFragment
import kotlinx.android.synthetic.main.activity_header.*


class MainActivity : BaseActivity() {

    override val tag = "Main activity"
    override fun getLayout() = R.layout.activity_main
    override fun getActivityTitle() = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = ItemsFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
        filter_menu.setText(R.string.help)
        filter_menu.setOnClickListener {
            val manualFragment = ManualFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, manualFragment)
                .addToBackStack("User manual")
                .commit()
        }
    }

}