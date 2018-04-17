package com.example.android.journaler.database

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.android.journaler.Journaler


class DbHelper(val dbName: String, val version: Int) : SQLiteOpenHelper(Journaler.ctx, dbName, null, version) {

    companion object {
        val ID = "_id"
        val TABLE_TODOS = "todos"
        val TABLE_NOTES = "notes"
        val COLUMN_TITLE = "title"
        val COLUMN_MESSAGE = "message"
        val COLUMN_SCHEDULED = "scheduled"
        val COLUMN_LOCATION_LATITUDE = "latitude"
        val COLUMN_LOCATION_LONGITUDE = "longitude"
    }

    private val tag = "DbHelper"

    private val createTableNotes = """
                                    CREATE TABLE if not exists $TABLE_NOTES
                                    (
                                        $ID integer PRIMARY KEY autoincrement,
                                        $COLUMN_TITLE text,
                                        $COLUMN_MESSAGE text,
                                        $COLUMN_LOCATION_LATITUDE real,
                                        $COLUMN_LOCATION_LONGITUDE real
                                    )
                                    """

    private val createTableTodos = """
                                    CREATE TABLE if not exists $TABLE_TODOS
                                    (
                                        $ID integer PRIMARY KEY autoincrement,
                                        $COLUMN_TITLE text,
                                        $COLUMN_MESSAGE text,
                                        $COLUMN_SCHEDULED integer
                                        $COLUMN_LOCATION_LATITUDE real,
                                        $COLUMN_LOCATION_LONGITUDE real
                                    )
                                    """

    override fun onCreate(db: SQLiteDatabase) {

        Log.d(tag, "Database [ CREATING ]")

        db.execSQL(createTableNotes)
        db.execSQL(createTableTodos)

        Log.d(tag, "Database [ CREATED ]")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Ignore for now.
    }

}

























