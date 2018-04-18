package com.example.android.journaler.navigation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.example.android.journaler.R
import kotlinx.android.synthetic.main.adapter_navigation_drawer.view.*


class NavigationDrawerAdapter(val ctx: Context, val items: List<NavigationDrawerItem>) : BaseAdapter() {

    private val tag = "Nav. drw. adptr."
    override fun getView(position: Int, v: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(ctx)
        var view = v

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_navigation_drawer, null) as LinearLayout
        }

        val item = items[position]
        val title = view.drawer_item
        title.text = item.title
        title.setOnClickListener {
            if (item.enabled)
                item.onClick.run()
            else
                Log.w(tag, "Item is disabled: $item")
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size
}
























