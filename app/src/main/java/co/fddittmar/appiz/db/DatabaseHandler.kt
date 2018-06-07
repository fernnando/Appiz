package co.fddittmar.appiz.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import co.fddittmar.appiz.model.Place

val DATABASE_NAME ="AppizDB"
val TABLE_NAME="Places"
val COL_ID = "id"
val COL_NAME = "name"
val COL_PRICE = "price"
val COL_LATITUDE = "latitude"
val COL_LONGITUDE = "longitude"


class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_PRICE +" INTEGER," +
                COL_LATITUDE + " REAL," +
                COL_LONGITUDE + " REAL)"


        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(place : Place){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME,place.name)
        cv.put(COL_PRICE,place.price)
        cv.put(COL_LATITUDE, place.latitude)
        cv.put(COL_LONGITUDE, place.longitude)
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }

    fun readData() : MutableList<Place>{
        var list : MutableList<Place> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var place = Place("", 0.0f, "")
                place.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                place.name = result.getString(result.getColumnIndex(COL_NAME))
                place.price = result.getString(result.getColumnIndex(COL_PRICE)).toFloat()
                place.latitude = result.getString(result.getColumnIndex(COL_LATITUDE)).toDouble()
                place.longitude = result.getString(result.getColumnIndex(COL_LONGITUDE)).toDouble()
                list.add(place)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }


    fun updateData() {
        val db = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_PRICE,(result.getInt(result.getColumnIndex(COL_PRICE))+1))
                db.update(TABLE_NAME,cv,COL_ID + "=? AND " + COL_NAME + "=?",
                        arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                                result.getString(result.getColumnIndex(COL_NAME))))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }


}