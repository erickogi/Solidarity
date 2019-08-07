package com.agriclinic.common.ui.auth


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.custom.Resource
import com.agriclinic.common.models.oauth.ImageUploadResponse
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.models.oauth.Profile
import com.dev.common.models.sectors.SectorResponse
import com.dev.common.data.repository.AssociationRepository
import com.dev.common.data.repository.SectorsRepository
import com.dev.common.models.association.AssociationResponse

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private var oauthRepository: OauthRepository = OauthRepository(application)
    private var sectorsRepository: SectorsRepository = SectorsRepository(application)
    private var associationRepository: AssociationRepository = AssociationRepository(application)


    private val verifyPhoneObservable = MediatorLiveData<Resource<Oauth>>()
    private val signInObservable = MediatorLiveData<Resource<Oauth>>()
    private val requestOtpObservable = MediatorLiveData<Resource<Oauth>>()
    private val confirmOtpObservable = MediatorLiveData<Resource<Oauth>>()
    private val createProfileObservable = MediatorLiveData<Resource<Oauth>>()
    private val updateProfileObservable = MediatorLiveData<Resource<Oauth>>()
    private val updatePasswordObservable = MediatorLiveData<Resource<Oauth>>()
    private val uploadImageObservable = MediatorLiveData<Resource<ImageUploadResponse>>()


    private val sectorObservable = MediatorLiveData<Resource<SectorResponse>>()
    private val associationObservable = MediatorLiveData<Resource<AssociationResponse>>()




    init {


        signInObservable.addSource(oauthRepository.signInObserver) { data -> signInObservable.setValue(data) }
        verifyPhoneObservable.addSource(oauthRepository.verifyPhoneObserve) { data -> verifyPhoneObservable.setValue(data) }
        requestOtpObservable.addSource(oauthRepository.requestOtpObservable) { data -> requestOtpObservable.setValue(data) }
        confirmOtpObservable.addSource(oauthRepository.confirmOtpObservable) { data -> confirmOtpObservable.setValue(data) }
        createProfileObservable.addSource(oauthRepository.createProfileObservable) { data -> createProfileObservable.setValue(data) }
        updateProfileObservable.addSource(oauthRepository.updateProfileObservable) { data -> updateProfileObservable.setValue(data) }



        updatePasswordObservable.addSource(oauthRepository.updatePasswordObservable) { data ->
            updatePasswordObservable.setValue(
                data
            )
        }
        uploadImageObservable.addSource(oauthRepository.uploadImageObservable) { data ->
            uploadImageObservable.setValue(
                data
            )
        }


        sectorObservable.addSource(sectorsRepository.sectorObservable) { data ->
            sectorObservable.setValue(
                data
            )
        }
        associationObservable.addSource(associationRepository.associationObservable) { data ->
            associationObservable.setValue(
                data
            )
        }

    }

    //SIGN IN
    fun signIn(parameters: Oauth) {
        oauthRepository.signIn(parameters)
    }
    fun observeSignIn(): LiveData<Resource<Oauth>> {
        return signInObservable
    }

    //VERIFY PHONE
    fun verifyPhone(parameters: Oauth) {
        oauthRepository.verifyPhone(parameters)
    }
    fun observeVerifyPhone(): LiveData<Resource<Oauth>> {
        return verifyPhoneObservable
    }




    //REQUEST OTP
    fun requestOtp(parameters: Oauth) {
        oauthRepository.requestOtp(parameters)
    }
    fun observeRequestOtp(): LiveData<Resource<Oauth>> {
        return requestOtpObservable
    }

    //CONFIRM OTP
    fun confirmOtp(parameters: Oauth) {
        oauthRepository.confirmOtp(parameters)
    }
    fun observeConfirmOtp(): LiveData<Resource<Oauth>> {
        return confirmOtpObservable
    }


    //UPDATE PROFILE
    fun updateProfile(parameters: Oauth) {
        oauthRepository.updateAccount(parameters)
    }
    fun observeUpdateProfile(): LiveData<Resource<Oauth>> {
        return updateProfileObservable
    }


    //CREATE PROFILE
    fun createProfile(parameters: Oauth) {
        oauthRepository.createAccount(parameters)
    }
    fun observeCreateProfile(): LiveData<Resource<Oauth>> {
        return createProfileObservable
    }





    fun getProfile(): Oauth {

        return Oauth(oauthRepository.fetchProfile())
    }
    fun liveProfile(): LiveData<Profile> {

        return oauthRepository.getProfile()
    }
    fun saveProfile(oauth: Oauth) {

        oauthRepository.saveProfile(oauth)
    }
    fun observeUpdatePassword(): LiveData<Resource<Oauth>> {
        return updatePasswordObservable
    }

    fun updatePassword(oauth: Oauth) {

        oauthRepository.updatePassword(oauth)
    }

    fun observeUploadImage(): LiveData<Resource<ImageUploadResponse>> {
        return uploadImageObservable
    }

    fun uploadImage(uri: Uri) {
        oauthRepository.uploadImage(uri)

    }


    fun observeAssociations(): LiveData<Resource<AssociationResponse>> {
        return associationObservable
    }


    fun getAssociations(sector_id: String?) {

        associationRepository.association(sector_id.toString())
    }

    fun observeSectors(): LiveData<Resource<SectorResponse>> {
        return sectorObservable
    }

    fun getSectors() {

        sectorsRepository.sectors()
    }

}
