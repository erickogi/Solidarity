/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * @author kogi
 */
object RequestService {
    var gson = GsonBuilder()
        .setLenient()
        .create()


    private fun getRetrofit(token: String, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient(token, "", ""))
            .build()
    }

    private fun getRetrofit(baseUrl: String, consumerKey: String, secretKey: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient("", consumerKey, secretKey))
            .build()
    }

    private fun getClient(token: String, consumerKey: String, secretKey: String): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("consumerKey", consumerKey)
                .addHeader("secretKey", secretKey)
                .build()
            chain.proceed(newRequest)
        }.build()
    }


    fun getService( token: String? ): EndPoints {
        if(token==null){
            return getRetrofit("", BaseUrls().LIVE).create(EndPoints::class.java)

        }else{
            return getRetrofit(token, BaseUrls().LIVE).create(EndPoints::class.java)

        }
    }


    fun getImageService(token: String?): EndPoints {
        if (token == null) {
            return getRetrofit("", BaseUrls().IMAGE).create(EndPoints::class.java)

        } else {
            return getRetrofit(token, BaseUrls().IMAGE).create(EndPoints::class.java)

        }
    }


}
