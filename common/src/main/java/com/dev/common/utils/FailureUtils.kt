/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 6:30 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

package com.dev.common.utils

import retrofit2.Call
import java.io.IOException


 class FailureUtils {

    fun parseError(call: Call<*>?, t: Throwable?): AppException {


        try {
            var ve: AppException? = null
            if (call != null) {
                if (call.isCanceled) {
                    ve = AppException("Canceled By User")
                } else {
                    if (t != null) {
                        if (t is IOException) {
                            ve = AppException("Network failure. Please retry")

                        } else {
                            ve = AppException(t.cause?.message)
                        }
                    }
                }
            } else {
                ve = AppException(t?.message)
            }


            if (ve == null) {
                ve = AppException(" Unknown Error ")
            }
            return ve
        } catch (ex: Exception) {
            return AppException("Error Loading Data")

        }


    }
}