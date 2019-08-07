package com.dev.common.models.sectors

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Sector : Serializable{
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("is_archived")
    @Expose
    var isArchived: String? = null
    @SerializedName("archived_at")
    @Expose
    var archivedAt: String? = null
    @SerializedName("archived_by")
    @Expose
    var archivedBy: String? = null
}