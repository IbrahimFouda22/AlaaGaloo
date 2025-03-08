package com.alaa.data.dto

import com.google.gson.annotations.SerializedName


data class AccountInvoiceDto(
    val id: Int,
    @SerializedName("account_totla_invoice")
    val accountTotalInvoice: Double,
    @SerializedName("accountinvoices_count")
    val accountInvoicesCount: Int?,
    @SerializedName("accountinvoices")
    val invoices: List<InvoiceDto>,
)

data class InvoiceDto(
    val id: Int,
    @SerializedName("account_customer_id")
    val accountCustomerId: Int,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("media")
    val media: String?,
    @SerializedName("more_info")
    val moreInfo: String?,
)
