package ons.dsi.usabrowser

import android.content.Context
import android.content.SharedPreferences

class MyCache (context: Context ) {
    private var share: SharedPreferences
    init {
        share = context.getSharedPreferences("cache",Context.MODE_PRIVATE)
    }
    fun setValue(key:String, value:Long){
        val edit = share.edit()
        edit.putLong(key,value)
        edit.commit()
    }
    fun getValue(key: String): Long{
        return share.getLong(key,0)
    }

}