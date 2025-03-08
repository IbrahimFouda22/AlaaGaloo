package com.alaa.data.repository

import com.alaa.data.mapper.toEntity
import com.alaa.data.source.RemoteDataSource
import com.alaa.domain.repository.IRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : IRepository {
    override suspend fun getCategoriesAndUsers() =
        remoteDataSource.getCategoriesAndUsers().map { item ->
            item.toEntity()
        }

    override suspend fun addCategory(name: String) = remoteDataSource.addCategory(name)
    override suspend fun getAccountsInvoices(clientId: Int, sort: String) =
        remoteDataSource.getAccountsInvoices(clientId, sort).toEntity()

    override suspend fun uploadOperation(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ) = remoteDataSource.uploadOperation(
        prepareRequestBody(
            customerId,
            amount,
            dateTime,
            type,
            additionalNote,
            image
        )
    )

    private fun prepareRequestBody(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ):RequestBody{
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("account_customer_id", customerId.toString())
            .addFormDataPart("amount", amount)
            .addFormDataPart("date_time", dateTime)
            .addFormDataPart("type", type)
            .addFormDataPart("more_info", additionalNote)

        builder.addFormDataPart(
            "accountinvoice",
            image.getName(),
            image.asRequestBody("image/jpg".toMediaTypeOrNull())
        )
        return builder.build()
    }
}