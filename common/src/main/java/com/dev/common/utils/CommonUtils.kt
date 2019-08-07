package com.dev.common.utils

import android.content.Context
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.models.custom.SearchModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

class CommonUtils {

    fun isGPSEnabled(activity: AppCompatActivity): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun loadImageUrl(context: Context,url: String?,imageView: ImageView){

        Glide.with(context).load(url).into(imageView)

    }
    fun <T> loadImage(context: Context,url: T?,imageView: ImageView){


        Glide.with(context).load(url).into(imageView)

    }

    fun <T> searchDialog(
        context: Context,
        title: String,
        searchHint: String,
        searchModels: ArrayList<SearchModel<T>>, cancelable: Boolean,
        searchDialogListener: SearchDialogListener<T>
    ) {

        Log.d("sdsfs",Gson().toJson(searchModels))

        val s = TSearchDialogCompat(context, title, searchHint, null, searchModels,
            SearchResultListener<SearchModel<T>> { dialog, item, position ->

                dialog?.dismiss()

                searchDialogListener.onResults(item, position)

            })

        s.setCancelable(cancelable)
        s.setCanceledOnTouchOutside(cancelable)
        s.show()


    }


    fun <T> withImageSearchDialog(
        context: Context,
        title: String,
        searchHint: String,
        searchModels: ArrayList<SearchModel<T>>, cancelable: Boolean,
        searchDialogListener: SearchDialogListener<T>
    ) {


        var s = WithImageSearchDialogCompat(context, title, searchHint, null, searchModels,
            SearchResultListener<SearchModel<T>> { dialog, item, position ->
                dialog?.dismiss()
                searchDialogListener.onResults(item, position)

            })

        s.setCancelable(cancelable)
        s.setCanceledOnTouchOutside(cancelable)
        s.show()


    }


    fun <T> simpleSearchDialog(context: Context,
                               title: String,
                               searchHint: String,
                               searchModels: ArrayList<SearchModel<T>>,
                               cancelable: Boolean,
                               searchDialogListener: SearchDialogListener<T>
    ) {


        var s = SimpleSearchDialogCompat(
            context, title, searchHint, null, searchModels,
            SearchResultListener<SearchModel<T>> { dialog, item, position ->

               // s?.dismiss()
                dialog?.dismiss()
                searchDialogListener.onResults(item,position)

            })

        s.setCancelable(cancelable)
        s.setCanceledOnTouchOutside(cancelable)
        s.show()



    }


    fun uriFromString(value: String?): Uri? {

        return if (value == null) {
            null
        } else Uri.parse(value)
    }


    fun uriToString(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }
        return uri.toString()
    }

    fun encode(imageUri: Uri, context: Context): String? {
//        val input = context.contentResolver.openInputStream(imageUri)
//        val image = BitmapFactory.decodeStream(input , null, null)
//        //encode image to base64 string
//        val baos = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val imageBytes = baos.toByteArray()
//        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return uriToString(uri = imageUri)
    }

    fun decode(imageString: String?): String? {


        return imageString
        //  return uriFromString(imageString)
//        try {
//            Log.d("BTI643,",imageString)
//            val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
//            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//        }catch (e: Exception){
//
//            return  null
//        }
        //decode base64 string to image

        //imageview.setImageBitmap(decodedImage)
    }

}


