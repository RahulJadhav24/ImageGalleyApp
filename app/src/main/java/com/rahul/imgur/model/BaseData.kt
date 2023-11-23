package com.rahul.imgur.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseData(
    @SerializedName("data") var data: List<Data>? = null,
    @SerializedName("success") var success: Boolean = false,
    @SerializedName("status") var status: Int = 0
) : Serializable