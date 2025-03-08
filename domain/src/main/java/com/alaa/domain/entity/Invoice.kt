package com.alaa.domain.entity



data class AccountInvoice(
    val id: Int,
    val accountTotalInvoice: Double,
    val accountInvoicesCount: Int,
    val invoices: List<Invoice>,
)

data class Invoice(
    val id: Int,
    val accountCustomerId: Int,
    val amount: Double,
    val dateTime: String,
    val updatedAt: String,
    val createdAt: String,
    val type: String,
    val media: String,
    val moreInfo: String,
)
