package com.example.android.journaler.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.journaler.R
import com.example.android.journaler.activity.NoteActivity
import com.example.android.journaler.activity.TodoActivity
import com.example.android.journaler.model.MODE


class ItemsFragment : BaseFragment() {
    override val logTag = "Items fragment"
    override fun getLayout() = R.layout.fragmet_items

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayout(), container, false)
        val btn = view?.findViewById<FloatingActionButton>(R.id.new_item)

        btn?.setOnClickListener {
            val items = arrayOf(
                getString(R.string.todos),
                getString(R.string.notes)
            )
            val builder = AlertDialog.Builder(this@ItemsFragment.context)
                .setTitle(R.string.choose_a_type)
                .setItems(items, {
                    _, which ->
                    when(which) {
                        0 -> openCreateTodo()
                        1 -> openCreateNote()
                        else -> Log.e(logTag, "Unknown option delected [ $which ]")
                    }
                })
            builder.show()
        }
        return view
    }

    private fun openCreateNote() {
        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra(MODE.EXTRAS_KEY, MODE.CREATE.mode)
        startActivity(intent)
    }

    private fun openCreateTodo() {
        val intent = Intent(context, TodoActivity::class.java)
        intent.putExtra(MODE.EXTRAS_KEY, MODE.CREATE.mode)
        startActivity(intent)
    }
}






















