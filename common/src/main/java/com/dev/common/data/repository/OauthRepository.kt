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


class OauthRepository(application: Application) {
    private val profileDao: ProfileDao

    private val db: SolidarityDatabase = SolidarityDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val verifyPhoneObserve = MutableLiveData<Resource<Oauth>>()
    val signInObserver = MutableLiveData<Resource<Oauth>>()
    val requestOtpObservable = MutableLiveData<Resource<Oauth>>()
    val confirmOtpObservable = MutableLiveData<Resource<Oauth>>()
    val updateProfileObservable = MutableLiveData<Resource<Oauth>>()
    val createProfileObservable = MutableLiveData<Resource<Oauth>>()
    val updatePasswordObservable = MutableLiveData<Resource<Oauth>>()
    val uploadImageObservable = MutableLiveData<Resource<ImageUploadResponse>>()


    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }
    fun updatePassword(oauth: Oauth) {
        setIsLoading(Observable.UPDATE_PASSWORD)
        if (NetworkUtils.isConnected(context)) {
           /// excuteUpdatePassword(oauth)
        } else {
            setNetworkError(Observable.UPDATE_PASSWORD)

        }
    }



    fun signIn(parameters: Oauth) {
        setIsLoading(Observable.SIGNIN)
        if (NetworkUtils.isConnected(context)) {
            excuteSignIn(parameters)
        } else {
            setNetworkError(Observable.SIGNIN)
        }
    }
    fun verifyPhone(parameters: Oauth) {
        setIsLoading(Observable.VERIFYID)
        if (NetworkUtils.isConnected(context)) {
            excuteVerifyPhone(parameters)
        } else {
            setNetworkError(Observable.VERIFYID)

        }
    }



    fun requestOtp(parameters: Oauth) {
        setIsLoading(Observable.REQUEST_OTP)
        if (NetworkUtils.isConnected(context)) {
            excuteRequestOtp(parameters)
        } else {
            setNetworkError(Observable.REQUEST_OTP)

        }
    }

    fun confirmOtp(parameters: Oauth) {
        setIsLoading(Observable.CONFIRM_OTP)
        if (NetworkUtils.isConnected(context)) {
            excuteConfirmOtp(parameters)
        } else {
            setNetworkError(Observable.CONFIRM_OTP)

        }
    }
    fun createAccount(parameters: Oauth) {
        setIsLoading(Observable.CREATE_PROFILE)
        if (NetworkUtils.isConnected(context)) {
            excuteCreateAccount(parameters)
        } else {
            setNetworkError(Observable.CREATE_PROFILE)

        }
    }
    fun updateAccount(parameters: Oauth) {
        setIsLoading(Observable.UPDATE_PROFILE)
        if (NetworkUtils.isConnected(context)) {
            excuteUpdateAccount(parameters)
        } else {
            setNetworkError(Observable.UPDATE_PROFILE)

        }
    }


    private fun excuteSignIn(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").signIn(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.SIGNIN,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.SIGNIN)
                }
            })
        }    }
    private fun excuteVerifyPhone(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").verifyPhone(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.VERIFYID,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.VERIFYID)
                }
            })
        }    }
    private fun excuteRequestOtp(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").requestOtp(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.REQUEST_OTP,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                   onResponse(response, Observable.REQUEST_OTP)
                }
            })
        }
    }
    private fun excuteConfirmOtp(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").confirmOtp(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.CONFIRM_OTP,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                   onResponse(response, Observable.CONFIRM_OTP)
                }
            })
        }
    }
    private fun excuteCreateAccount(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").createAccount(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.CREATE_PROFILE,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                   onResponse(response, Observable.CREATE_PROFILE)
                }
            })
        }
    }
    private fun excuteUpdateAccount(parameters: Oauth) {
        Log.d("CallOnStart",Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(parameters.profile?.token).updateAccount(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    Log.d("CallOnFailure", "" + Observable.UPDATE_PROFILE.name + " ----- " + Gson().toJson(call))

                    onFailure(Observable.UPDATE_PROFILE,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                   onResponse(response, Observable.UPDATE_PROFILE)
                }
            })
        }
    }


    private fun excuteUpLoadImage(parameters: Uri) {
        Log.d("CallOnFailure", Gson().toJson(parameters))

        var file = File(getPath(parameters))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val body = MultipartBody.Part.createFormData(
            "methodName[headerData][relation][relative_image]",
            file.name,
            requestFile
        )


        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)


        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getImageService("").upload(1, filename, fileToUpload)
            call.enqueue(object : Callback<ImageUploadResponse> {
                override fun onFailure(call: Call<ImageUploadResponse>?, t: Throwable?) {
                    onFailure(Observable.UPLOAD_IMAGE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ImageUploadResponse>?, response: Response<ImageUploadResponse>?) {
                    onResponseImage(response, Observable.UPLOAD_IMAGE)
                }
            })
        }
    }

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
    private fun onResponse(response: Response<Oauth>?, observable: Observable){
        Log.d("CallOnResponse",""+observable.name+" ----- "+Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.error!! == 0 ) {
                    setIsSuccesful(observable,response.body()!!)

                    if(observable== Observable.CREATE_PROFILE ||observable== Observable.UPDATE_PROFILE){
                        saveProfile(response.body() as Oauth)
                    }
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

    private fun onResponseImage(response: Response<ImageUploadResponse>?, observable: Observable) {
        Log.d("CallOnResponse", "" + observable.name + " ----- " + Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.statusCode!! > 0 && response.body()?.success!!) {
                    setIsSuccesful(observable, response.body()!!)


                } else {
                    response.body()?.statusMessage?.let {
                        setIsError(
                            observable, it,
                            AppException(it, it, response.body()?.errors)
                        )
                    }
                }
            } else {
                setIsError(observable, "", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable, "", AppException("Error Loading Data"))
        }
    }





    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.loading(null))

            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.loading(null))
            Observable.SIGNIN -> signInObserver.postValue(Resource.loading(null))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.loading(null))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.loading(null))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.loading(null))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.loading(null))
            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.success(data as Oauth))

            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.success(data as Oauth))
            Observable.SIGNIN -> signInObserver.postValue(Resource.success(data as Oauth))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.success(data as Oauth))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.success(data as Oauth))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.success(data as Oauth))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.success(data as ImageUploadResponse))
            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.success(data as Oauth))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.error(message, null,exception))
            Observable.SIGNIN -> signInObserver.postValue(Resource.error(message, null,exception))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.error(message, null,exception))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.error(message, null,exception))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.error(message, null,exception))
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.error(message, null, exception))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.error(message, null, exception))

            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.error(message, null,exception))
        }
    }

    enum class Observable {
        VERIFYID,
        SIGNIN,
        REQUEST_OTP,
        CONFIRM_OTP,
        UPDATE_PROFILE,
        UPDATE_PASSWORD,
        UPLOAD_IMAGE,
        CREATE_PROFILE
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

    fun fetchProfile(): Profile {
        var profile= profileDao.fetch()
        if(profile==null){
            profile= Profile()
        }
        return profile
    }

    fun logOut() {
        prefrenceManager.setLoginStatus(0)
        prefrenceManager.clearUser()
        profileDao.delete()
    }


    fun getPath(uri: Uri): String? {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = context.contentResolver.query(uri, projection, null, null, null) ?: return null
//        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor.moveToFirst()
//        val s = cursor.getString(columnIndex)
//        cursor.close()
        return uri.path
    }

    fun uploadImage(uri: Uri) {
        setIsLoading(Observable.UPLOAD_IMAGE)
        if (NetworkUtils.isConnected(context)) {
            excuteUpLoadImage(uri)
        } else {
            setNetworkError(Observable.UPLOAD_IMAGE)

        }


    }
}
