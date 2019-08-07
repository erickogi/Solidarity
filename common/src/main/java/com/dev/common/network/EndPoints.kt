/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 4:09 PM
 *
 */


import com.agriclinic.common.models.oauth.ImageUploadResponse
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.models.oauth.Profile
import com.dev.common.models.sectors.SectorResponse
import com.dev.common.models.association.AssociationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 * @author kogi
 */
interface EndPoints {


    /**VERIFY PHONE NUMBER EXISTENCE  **/
    @POST("oauth/verifyPhone.php")
    fun verifyPhone(@Body item: Profile): Call<Oauth>


    /**LOGIN WITH PHONE AND PASSWORD  **/
    @POST("login")
    fun signIn(@Body item: Profile): Call<Oauth>



    /**REQUEST AN OTP CODE FOR PHONE NUMBER VERIFICATION  **/
    @POST("oauth/requestOTP.php")
    fun requestOtp(@Body item: Profile): Call<Oauth>


    /**CONFIRM AN OTP CODE FOR PHONE NUMBER VERIFICATION  **/
    @POST("oauth/verifyOTP.php")
    fun confirmOtp(@Body item: Profile): Call<Oauth>


    /**USED TO REGISTER A USER ( AFTER PHONE VERIFICATION)
     * FOR THIS USE CASE I AM JUST SENDING THE PHONE AND PASSWORD AND WILL UPDATE THE REST WITH THE NEXT API
     * **/
    @POST("register")
    fun createAccount(@Body item: Profile): Call<Oauth>

    /**
     * EDIT / UPDATE USER ACCOUNT
     * SEND (ENCRYPTED PASSWORD ALSO )
     * **/

    @POST("accounts/edit.php")
    fun updateAccount(@Body item: Profile): Call<Oauth>

    @POST("accounts/editPassword.php")
    fun updatePassword(@Body outh: Profile): Call<Oauth>


    @GET("associations")
    fun associations(@Query("sector_id")id: String) :Call<AssociationResponse>


    @GET("sectors")
    fun sectors() :Call<SectorResponse>


    @Multipart
    @POST("v3.php")
    fun upload(
        @Part("appId") appId: Int,
        @Part("file") re: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ImageUploadResponse>



}
