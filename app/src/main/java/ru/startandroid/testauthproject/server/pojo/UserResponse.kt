package ru.startandroid.testauthproject.server.pojo

import com.google.gson.annotations.SerializedName
import ru.startandroid.testauthproject.repository.entity.UserEntity

class UserResponse {
    @SerializedName("records")
    var records: List<UserEntity>? = null
}