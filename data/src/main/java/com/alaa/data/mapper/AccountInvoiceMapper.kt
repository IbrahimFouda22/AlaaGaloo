package com.alaa.data.mapper

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.InvoiceDto
import com.alaa.domain.entity.AccountInvoice
import com.alaa.domain.entity.Invoice

fun AccountInvoiceDto.toEntity() = AccountInvoice(
    id = id,
    accountInvoicesCount = accountInvoicesCount ?: 0,
    accountTotalInvoice = accountTotalInvoice,
    invoices = invoices.map { item ->
      item.toEntity()
    }
)

fun InvoiceDto.toEntity() = Invoice(
    id,
    accountCustomerId,
    amount,
    dateTime,
    updatedAt,
    createdAt,
    type,
    media ?: "",
    moreInfo ?: ""
)