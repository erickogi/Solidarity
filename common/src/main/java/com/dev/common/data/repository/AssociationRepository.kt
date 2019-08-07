package com.dev.common.data.repository

import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.data.local.SolidarityDatabase
import com.dev.common.data.local.daos.ProfileDao
import com.agriclinic.common.models.oauth.ImageUploadResponse
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.models.oauth.Profile
import com.dev.common.R
import com.dev.common.models.custom.Resource
import com.dev.common.utils.AppException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.google.gson.Gson
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.models.association.AssociationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AssociationRepository(application: Application) {
    private val profileDao: ProfileDao

    private val db: SolidarityDatabase = SolidarityDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val associationObservable = MutableLiveData<Resource<AssociationResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }



    fun association(parameters: String) {
        setIsLoading(Observable.ASSOCIATIONS)
        if (NetworkUtils.isConnected(context)) {
            excuteAssociations(parameters)
        } else {
            setNetworkError(Observable.ASSOCIATIONS)
        }
    }



    private fun excuteAssociations(parameters: String) {
        Log.d("CallOnStart",Gson().toJson(parameters))
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").associations(parameters)
            call.enqueue(object : Callback<AssociationResponse> {
                override fun onFailure(call: Call<AssociationResponse>?, t: Throwable?) {
                    onFailure(Observable.ASSOCIATIONS,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<AssociationResponse>?, response: Response<AssociationResponse>?) {
                    onResponse(response, Observable.ASSOCIATIONS)
                }
            })
        }    }



    private fun setNetworkError(observable: Observable) {
        setIsError(
            observable, context.getString(R.string.network_issue_message),
            AppException(context.getString(R.string.network_issue_message))
        )
    }
    private fun onFailure(observable: Observable, t: Throwable?, agriException: AppException){
        Log.d("CallOnFailure",""+observable.name+" ----- "+Gson().toJson(t))
        setIsError(observable,t.toString(), agriException)
    }
    private fun onResponse(response: Response<AssociationResponse>?, observable: Observable){
        Log.d("CallOnResponse",""+observable.name+" ----- "+Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.error!! == 0 ) {
                    setIsSuccesful(observable,response.body()!!)


                } else {
                    response.body()?.message?.let { setIsError(
                        observable,it,
                        AppException(it,it,null)
                    ) }
                }
            } else {
                setIsError(observable,"", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable,"", AppException("Error Loading Data"))
        }
    }






    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.ASSOCIATIONS -> associationObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.ASSOCIATIONS -> associationObservable.postValue(Resource.success(data as AssociationResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.ASSOCIATIONS -> associationObservable.postValue(Resource.error(message, null,exception))

        }
    }

    enum class Observable {
        ASSOCIATIONS,

    }

    fun saveProfile(data: Oauth) {
        Log.d("AgriDb",Gson().toJson(data))
        profileDao.delete()
        data.profile?.let { profileDao.insertProfile(it) }
        prefrenceManager.saveProfile(data)
    }

    fun getProfile(): LiveData<Profile> {
        return profileDao.getProfile()
    }

}
