package com.alaa.domain.usecase

import com.alaa.domain.repository.IRepository
import java.io.File
import javax.inject.Inject

class ManageAccountsUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getCategoriesAndUsers() = repository.getCategoriesAndUsers()
    suspend fun addCategory(name: String) = repository.addCategory(name)
    suspend fun getAccountsInvoices(clientId: Int, sort: String) =
        repository.getAccountsInvoices(clientId, sort)
    suspend fun uploadOperation(
        customerId:Int,
        amount:String,
        dateTime:String,
        type:String,
        additionalNote:String,
        image: File
    ) = repository.uploadOperation(
        customerId,
        amount,
        dateTime,
        type,
        additionalNote,
        image
    )
}