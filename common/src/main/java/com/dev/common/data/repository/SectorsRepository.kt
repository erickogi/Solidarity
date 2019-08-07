package com.dev.common.data.repository

import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.data.local.SolidarityDatabase
import com.dev.common.data.local.daos.ProfileDao
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.models.oauth.Profile
import com.dev.common.models.sectors.SectorResponse
import com.dev.common.R
import com.dev.common.models.custom.Resource
import com.dev.common.utils.AppException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.google.gson.Gson
import com.dev.common.data.local.PrefrenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SectorsRepository(application: Application) {
    private val profileDao: ProfileDao

    private val db: SolidarityDatabase = SolidarityDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val sectorObservable = MutableLiveData<Resource<SectorResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }



    fun sectors() {
        setIsLoading(Observable.SECTORS)
        if (NetworkUtils.isConnected(context)) {
            excuteSectors()
        } else {
            setNetworkError(Observable.SECTORS)
        }
    }



    private fun excuteSectors() {
        Log.d("CallOnStart","")
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").sectors()
            call.enqueue(object : Callback<SectorResponse> {
                override fun onFailure(call: Call<SectorResponse>?, t: Throwable?) {
                    onFailure(Observable.SECTORS,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<SectorResponse>?, response: Response<SectorResponse>?) {
                    onResponse(response, Observable.SECTORS)
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
    private fun onResponse(response: Response<SectorResponse>?, observable: Observable){
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
            Observable.SECTORS -> sectorObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.SECTORS -> sectorObservable.postValue(Resource.success(data as SectorResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.SECTORS -> sectorObservable.postValue(Resource.error(message, null,exception))

        }
    }

    enum class Observable {
        SECTORS,

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
