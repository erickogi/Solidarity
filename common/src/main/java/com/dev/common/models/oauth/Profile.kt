package com.agriclinic.common.models.oauth

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    indices = [(Index("id"))],
    primaryKeys = ["id"]
)
@JvmSuppressWildcards
class Profile : Serializable{

    @field:SerializedName("job_id")
    var jobId: String? = null

    @field:SerializedName("code")

    var code: String? = null

    @field:SerializedName("createdAt")

    var createdAt: String? = null
    @field:SerializedName("updatedAt")

    var updatedAt: String? = null

    @field:SerializedName("otpCode")

    var otpCode: String? = null



    @field:SerializedName("dob")

    var dob: String? = null


    @field:SerializedName("gender")

    var gender: String? = null


    @field:SerializedName("county")

    var county: String? = null


    @field:SerializedName("subCounty")

    var subCounty: String? = null

    @field:SerializedName("ward")

    var ward: String? = null



    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("first_name")
    @Expose
    var first_name: String? = null
    @SerializedName("last_name")
    @Expose
    var last_name: String? = null
    @SerializedName("businessName")
    @Expose
    var businessName: String? = null
    @SerializedName("phone_number")
    @Expose
    var phone_number: String? = null


    @SerializedName("username")
    @Expose
    var username: String? = null



    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("firebaseToken")
    @Expose
    var firebaseToken: String? = null
    @SerializedName("countyId")
    @Expose
    var countyId: String? = null
    @SerializedName("subCountyId")
    @Expose
    var subCountyId: String? = null
    @SerializedName("wardId")
    @Expose
    var wardId: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("roleId")
    @Expose
    var roleId: Int? = null

    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null


    @SerializedName("level_id")
    @Expose
    var level_id: String? = "2"


    @SerializedName("password_repeat")
    @Expose
    var password_repeat: String? = null



//    "first_name":"Ann",
//    "last_name":"Gen",
//    "phone_number":"716406984",
//    "gender":"female",
//    "level_id":2,
//    "password":"1234",
//    "password_repeat":"1234",
//    "sector_id":1,
//    "association_id":1,
//    "occupation": "Artisan"

    @SerializedName("lat")
    @Expose
    var lat: String? = null


    @SerializedName("lon")
    @Expose
    var lon: String? = null


    @SerializedName("sector_id")
    @Expose
    var sector_id: String? = null

    @SerializedName("id_no")
    @Expose
    var id_no: String? = null



    @SerializedName("association_id")
    @Expose
    var association_id: String? = null

    @SerializedName("occupation")
    @Expose
    var occupation: String? = null


    @SerializedName("profile_img")
    @Expose
    var profile_img: String? = null


    @SerializedName("sector")
    @Expose
    var sector: String? = null


    @SerializedName("association")
    @Expose
    var association: String? = null



    @Ignore
    var fId: String? = null


//
//    "first_name":"Ann",
//    "last_name":"Gen",
//    "phone_number":"0700111111",
//    "gender":"female",
//    "level_id":2,
//    "password":"tochange",
//    "password_repeat":"tochange",
//    "sector_id":1,
//    "association_id":1,
//    "occupation": "Artisan"
//




    var accountAction: Int?=null
    var avatarUri: Uri?=null





    @Ignore constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


    constructor()

    @Ignore constructor(email: String?) {
        this.email = email
    }

    constructor(
        gender: String?,
        first_name: String?,
        first_name1: String?,
        phone_number: String?,
        password: String?,
        sector_id: String?,
        association_id: String?,
        occupation: String?
    ) {
        this.gender = gender
        this.first_name = first_name
        this.first_name = first_name1
        this.phone_number = phone_number
        this.password = password
        this.sector_id = sector_id
        this.association_id = association_id
        this.occupation = occupation
        this.level_id = "2"
    }


}