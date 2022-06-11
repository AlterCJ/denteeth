package com.example.capstone_apps.data.remote.retrofit

import com.example.capstone_apps.data.remote.response.*
import com.example.capstone_apps.helper.Key
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
  @FormUrlEncoded
  @POST("api/v1/auth/signin")
  fun requestLogin(
    @Field("email") email: String,
    @Field("password") password: String
  ): Call<ResponseLogin>

  @FormUrlEncoded
  @POST("api/v1/auth/signup")
  fun requestRegister(
    @Field("username") username: String,
    @Field("email") email: String,
    @Field("password") password: String
  ): Call<ResponseRegister>

  @GET("api/v1/article")
  fun requestGetArticle() : Call<ResponseArticle>

  @GET("api/v1/profile/{id}")
  fun requestGetDetailProfile(
    @Header("token") token:String,
    @Path("id") id: Int
  ): Call<ResponseProfile>

  @FormUrlEncoded
  @PUT("api/v1/profile")
  fun requestUpdateProfile(
    @Header("token") token: String,
    @Field("imageprofile") imageProfile: String,
    @Field("fullname") fullName: String,
    @Field("data") data: String,
    @Field("location") location: String,
    @Field("gender") gender: String
  ): Call<ResponseProfile>

  @GET("api/v1/benners")
  fun requestGetBenners() : Call<ResponseBenners>
}