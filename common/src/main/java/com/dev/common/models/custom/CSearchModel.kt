package com.dev.common.models.custom

import ir.mirrajabi.searchdialog.core.Searchable


class CSearchModel<T> : Searchable {
    // val checked: Boolean
    var mTitle: String? = null
    var mid: String? = null
    var mImage: String? = null
    var mchecked: Boolean? = null
    var mserices: T? = null

    constructor (
        title: String? = null,
        id: String? = null,
        image: String? = null,
        checked: Boolean? = false,
        serices: T? = null
    ) {
        mTitle = title
        mid = id
        mImage = image
        mchecked = checked
        mserices = serices
    }

    override fun getTitle(): String? {
        return mTitle
    }

    fun getServices(): T? {
        return mserices
    }


    fun getId(): String? {
        return mid
    }

    fun getImage(): String? {
        return mImage
    }


    fun set(
        title: String? = null,
        id: String? = null,
        image: String? = null,
        checked: Boolean? = false,
        serices: T? = null
    ): CSearchModel<T> {
        mTitle = title
        mid = id
        mImage = image

        mchecked = checked
        mserices = serices
        return this
    }
}