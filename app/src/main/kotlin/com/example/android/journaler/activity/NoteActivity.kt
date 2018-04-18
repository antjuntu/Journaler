package com.example.android.journaler.activity

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import com.example.android.journaler.R
import com.example.android.journaler.database.Db
import com.example.android.journaler.execution.TaskExecutor
import com.example.android.journaler.location.LocationProvider
import com.example.android.journaler.model.Note
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : ItemActivity() {

    private var note: Note? = null
    override val tag = "Note activity"
    private var location: Location? = null
    override fun getLayout() = R.layout.activity_note

    private val executor = TaskExecutor.getInstance(1)

    private var handler: Handler? = null

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            updateNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location?) {
            p0?.let {
                LocationProvider.unsubscribe(this)
                location = p0
                val title = getNoteTitle()
                val content = getNoteContent()
                note = Note(title, content, p0)
                executor.execute {
                    val param = note
                    var result = false
                    param?.let {
                        result = Db.NOTE.insert(param) > 0
                    }
                    if (result)
                        Log.i(tag, "Note inserted.")
                    else
                        Log.e(tag, "Note not inserted.")

                    handler?.post {
                        var color = R.color.vermilion
                        if (result)
                            color = R.color.green
                        indicator.setBackgroundColor(
                            ContextCompat.getColor(this@NoteActivity, color)
                        )
                    }
                }
//                val task = object : AsyncTask<Note, Void, Boolean>() {
//                    override fun doInBackground(vararg params: Note?): Boolean {
//                        if (!params.isEmpty()) {
//                            val param = params[0]
//                            param?.let {
//                                return Db.NOTE.insert(param) > 0
//                            }
//                        }
//                        return false
//                    }
//
//                    override fun onPostExecute(result: Boolean?) {
//                        result?.let {
//                            if (result) {
//                                Log.i(tag, "Note inserted.")
//                            } else {
//                                Log.e(tag, "Note not inserted.")
//                            }
//                        }
//                    }
//                }
//                task.execute(note)
            }
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

        override fun onProviderEnabled(p0: String?) {}

        override fun onProviderDisabled(p0: String?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message?) {
                msg?.let {
                    var color = R.color.vermilion
                    if (msg.arg1 > 0)
                        color = R.color.green
                    indicator.setBackgroundColor(
                        ContextCompat.getColor(
                            this@NoteActivity,
                            color
                        ))
                }
                super.handleMessage(msg)
            }
        }
        note_title.addTextChangedListener(textWatcher)
        note_content.addTextChangedListener(textWatcher)
    }

    private fun updateNote() {
        if (note == null) {
            if (!TextUtils.isEmpty(getNoteTitle()) && !TextUtils.isEmpty(getNoteContent())) {
                LocationProvider.subscribe(locationListener)
            }
        } else {
            note?.title = getNoteTitle()
            note?.message = getNoteContent()
            executor.execute {
                val param = note
                var result = false
                param?.let {
                    result = Db.NOTE.update(param) > 0
                }
                if (result) {
                    Log.i(tag, "Note updated.")
                } else {
                    Log.e(tag, "Note not updated.")
                }


            }
//            val task = object : AsyncTask<Note, Void, Boolean>() {
//                override fun doInBackground(vararg params: Note?): Boolean {
//                    if (!params.isEmpty()) {
//                        val param = params[0]
//                        param?.let {
//                            return Db.NOTE.update(param) > 0
//                        }
//                    }
//                    return false
//                }
//
//                override fun onPostExecute(result: Boolean?) {
//                    result?.let {
//                        if (result) {
//                            Log.i(tag, "Note updated.")
//                        } else {
//                            Log.e(tag, "Note not updated.")
//                        }
//                    }
//                }
//            }
//            task.execute(note)
        }
    }

    private fun sendMessage(result: Boolean) {
        val msg = handler?.obtainMessage()
        if (result)
            msg?.arg1 = 1
        else
            msg?.arg1 = 0
        handler?.sendMessage(msg)
    }

    private fun getNoteContent(): String {
        return note_content.text.toString()
    }

    private fun getNoteTitle(): String {
        return note_title.text.toString()
    }

}







































