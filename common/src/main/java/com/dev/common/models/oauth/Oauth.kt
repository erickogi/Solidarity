package com.agriclinic.common.models.oauth

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Oauth : Serializable{
    @SerializedName("error")
    @Expose
    var error: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var profile: Profile? = null





    @Ignore constructor(profile: Profile?) {
        this.profile = profile
    }

    constructor()


}