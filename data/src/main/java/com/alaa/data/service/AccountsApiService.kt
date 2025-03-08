package com.alaa.data.service

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.BaseDto
import com.alaa.data.dto.CategoryDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountsApiService {
    @GET("accounts/categories")
    suspend fun getCategoriesAndUsers(): Response<BaseDto<List<CategoryDto>>>

    @FormUrlEncoded
    @POST("accounts/categories")
    suspend fun addCategory(
        @Field("name") name: String
    ): Response<Any>

    @GET("accounts/customers/{id}")
    suspend fun getAccountsInvoices(
        @Path("id") id: Int,
        @Query("sort") sort: String = "asc",
    ): Response<BaseDto<AccountInvoiceDto>>

    @POST("accounts/invoices")
    suspend fun uploadOperation(
        @Body data: RequestBody
    ): Response<BaseDto<Any>>
}