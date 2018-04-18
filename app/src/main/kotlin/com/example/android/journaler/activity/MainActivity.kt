package com.example.android.journaler.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem
import com.example.android.journaler.R
import com.example.android.journaler.fragment.ItemsFragment
import com.example.android.journaler.navigation.NavigationDrawerAdapter
import com.example.android.journaler.navigation.NavigationDrawerItem
import com.example.android.journaler.preferences.PreferenceConfiguration
import com.example.android.journaler.preferences.PreferenceProvider
import com.example.android.journaler.service.MainService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override val tag = "Main activity"
    override fun getLayout() = R.layout.activity_main
    override fun getActivityTitle() = R.string.app_name

    private val keyPagePosition = "key_page_positopn"

    private var service: MainService? = null

    private val synchronize: NavigationDrawerItem by lazy {
        NavigationDrawerItem(
            getString(R.string.synchronize),
            Runnable { service?.synchronize() },
            false
        )
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            synchronize.enabled = false
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            if (binder is MainService.MainServiceBinder) {
                service = binder.getService()
                service?.let {
                    synchronize.enabled = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = PreferenceProvider()
        val config = PreferenceConfiguration("journaler_prefs", Context.MODE_PRIVATE)
        val preferences = provider.obtain(config, this)


        pager.adapter = ViewPagerAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Ignore
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Ignore
            }

            override fun onPageSelected(position: Int) {
                Log.v(tag, "Page [ $position ]")
                preferences.edit().putInt(keyPagePosition, position).apply()
            }
        })

        val pagerPosition = preferences.getInt(keyPagePosition, 0)
        pager.setCurrentItem(pagerPosition, true)

        val menuItems = mutableListOf<NavigationDrawerItem>()

        val today = NavigationDrawerItem(
            getString(R.string.today),
            Runnable { pager.setCurrentItem(0, true) }
        )

        val next7Days = NavigationDrawerItem(
            getString(R.string.next_seven_days),
            Runnable {
                pager.setCurrentItem(1, true)
            }
        )

        val todos = NavigationDrawerItem(
            getString(R.string.todos),
            Runnable {
                pager.setCurrentItem(2, true)
            }
        )

        val notes = NavigationDrawerItem(
            getString(R.string.notes),
            Runnable {
                pager.setCurrentItem(3, true)
            }
        )

        menuItems.add(today)
        menuItems.add(next7Days)
        menuItems.add(todos)
        menuItems.add(notes)
        menuItems.add(synchronize)

        val navigationDrawerAdapter = NavigationDrawerAdapter(this, menuItems)
        left_drawer.adapter = navigationDrawerAdapter
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MainService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        unbindService(serviceConnection)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.drawing_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.options_menu -> {
                Log.v(tag, "Options menu.")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager) {

        override fun getItem(position: Int): Fragment {
            return ItemsFragment()
        }

        override fun getCount(): Int {
            return 5
        }
    }

}

// ViewPager makes it possible to swipe between different fragments as a
// part of the fragment collection