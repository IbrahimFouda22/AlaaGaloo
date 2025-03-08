package com.alaa.domain.repository

import com.alaa.domain.entity.AccountInvoice
import com.alaa.domain.entity.Invoice
import com.alaa.domain.entity.Category
import java.io.File


interface IRepository {
    suspend fun getCategoriesAndUsers(): List<Category>
    suspend fun addCategory(name: String): Boolean
    suspend fun getAccountsInvoices(clientId: Int, sort: String):AccountInvoice
    suspend fun uploadOperation(
        customerId:Int,
        amount:String,
        dateTime:String,
        type:String,
        additionalNote:String,
        image:File
    ):Boolean
}