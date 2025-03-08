package com.alaa.data.source

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.CategoryDto
import okhttp3.RequestBody


interface IRemoteDataSource {
    suspend fun getCategoriesAndUsers(): List<CategoryDto>
    suspend fun addCategory(name: String): Boolean
    suspend fun getAccountsInvoices(clientId: Int, sort: String):AccountInvoiceDto
    suspend fun uploadOperation(data: RequestBody): Boolean
}