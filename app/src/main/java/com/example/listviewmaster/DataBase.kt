package com.example.listviewmaster

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase (context : Context) : SQLiteOpenHelper(context,"DBOrdinateur",null,1)  {
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE ordi_table (id INTEGER PRIMARY KEY, " +
                "name TEXT, price REAL, " +
                "image TEXT)")

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Ordinateur")
        onCreate(db)
    }

    fun addOrdi(ordi : Ordinateur):Long {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id",ordi.id)
        values.put("name",ordi.name)
        values.put("price",ordi.price)
        values.put("image",ordi.image)


        return db.insert("ordi_table", null, values)
    }
    fun updateOrdi(ordi: Ordinateur): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id",ordi.id)
        values.put("name",ordi.name)
        values.put("price",ordi.price)
        values.put("image",ordi.image)
        return db.update("ordi_table", values, "id= ?",
            arrayOf(ordi.id.toString())).toLong()
    }
    fun getAllOrdi():ArrayList<Ordinateur> {

        var ordisList = ArrayList<Ordinateur>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ordi_table ", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val price = cursor.getDouble(2)
                val  image= cursor.getString(3)


                val ordi = Ordinateur(id,name,price,image)

                ordisList.add(ordi)

            } while (cursor.moveToNext())
        }

        return ordisList

    }
    fun searchById(id: Int): ArrayList<Ordinateur> {
        var ordiListItem = ArrayList<Ordinateur>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ordi_table WHERE id = ?", arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val price = cursor.getDouble(2)
                val image = cursor.getString(3)
                val ordi = Ordinateur(id,name,price,image)
                ordiListItem.add(ordi)
            } while (cursor.moveToNext())
        }else{
            ordiListItem.size
        }
        return ordiListItem
    }

    fun deletePC(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("ordi_table", "id= ?", arrayOf(id.toString()))
    }
}