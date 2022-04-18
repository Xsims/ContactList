package com.xsims.contactlist.api

import com.skydoves.sandwich.ApiResponse
import com.xsims.contactlist.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactService {

  @GET(".")
  suspend fun fetchContactList(
    @Query("seed") seed: String = DEFAULT_SEED,
    @Query("results") results: Int = DEFAULT_RESULTS,
    @Query("page") page: Int = DEFAULT_PAGE,
  ): ApiResponse<RandomUserResponse>

  companion object {
    const val BASE_URL = "https://randomuser.me/api/1.0/"
    const val DEFAULT_SEED = "lydia"
    const val DEFAULT_RESULTS = 10
    const val DEFAULT_PAGE = 1
  }
}