package com.dev.common.models.custom

import com.dev.common.utils.AppException

class Resource<T>
private constructor(val status: Status, val data: T?, val message: String?, val exception: AppException?) {
    companion object {


        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null,null)
        }

        fun <T> error(msg: String, data: T?,exception: AppException): Resource<T> {
            return Resource(Status.ERROR, data, msg,exception)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null,null)
        }
    }
}