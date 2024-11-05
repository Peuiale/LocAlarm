package com.UMDSG.localarm

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosApp(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    val create_user_table = "CREATE TABLE Usuarios" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "NOMBRE TEXT,"+
            "PASSWORD TEXT)"

    override fun onCreate(database: SQLiteDatabase) {

        val insert_data_table_users = "INSERT INTO Usuarios (ID, NOMBRE, PASSWORD) VALUES (1, 'Daniela', '12345')"

        database.execSQL(create_user_table)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}