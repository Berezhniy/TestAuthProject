package ru.startandroid.testauthproject.server

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.startandroid.testauthproject.repository.entity.UserEntity
import ru.startandroid.testauthproject.server.pojo.UserResponse

interface ApiService {
    @GET("/api/android/rest/api-v2.php/records/users")
    fun getUsers(): Single<Response<UserResponse>>

    @GET("/api/android/rest/api-v2.php/records/users/{id}")
    fun getUserById(@Path("id") id: Int): Single<UserEntity>
}