package com.dev.common.models

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {
    var gson = Gson()

//    @SerializedName("images")
//    @Expose
//    var images: List<String>? = null
//    @SerializedName("details")
//    @Expose
//    var details: List<String>? = null
//    @SerializedName("description")
//    @Expose
//    var description: String? = null


    @TypeConverter
    fun stringList(data: String?): List<String>? {
        if (data == null) {
            return Collections.emptyList()
        }


        val listType = object : TypeToken<List<String>>() {

        }.type

        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun listString(someObjects: List<String>?): String? {
        if (someObjects != null) {
            return gson.toJson(someObjects)
        }
        return null
    }



    @TypeConverter
    fun fromString(value: String?): Uri? {

        return if (value == null) {
            null
        } else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        if(uri==null)
        {
            return null
        }
        return uri.toString()
    }


}
