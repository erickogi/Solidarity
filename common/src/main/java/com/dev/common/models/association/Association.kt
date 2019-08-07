package com.dev.common.models.association

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Association : Serializable{
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("sector_id")
    @Expose
    var sector_id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("membership_fee")
    @Expose
    var membership_fee: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null





}