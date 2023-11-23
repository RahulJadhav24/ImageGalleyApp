package com.rahul.imgur.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("id") var id: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("datetime") var datetime: Int = 0,
    @SerializedName("images") var images: List<Images>? = null

) : Serializable
