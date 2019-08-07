package com.dev.common.models.association

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AssociationResponse : Serializable{
    @SerializedName("error")
    @Expose
    var error: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var associations: List<Association>? = null





    @Ignore constructor(association:  List<Association>?) {
        this.associations = associations
    }

    constructor()


}