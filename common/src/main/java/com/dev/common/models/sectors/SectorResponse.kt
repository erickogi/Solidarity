package com.dev.common.models.sectors

import androidx.room.Ignore
import com.dev.common.models.association.Association
import com.dev.common.models.sectors.Sector
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SectorResponse : Serializable{
    @SerializedName("error")
    @Expose
    var error: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var sectors: List<Sector>? = null





    @Ignore constructor(sector: List<Sector>?) {
        this.sectors = sector
    }

    constructor()


}