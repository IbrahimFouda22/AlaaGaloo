package com.alaa.data.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    val id:Int,
    val name:String,
    @SerializedName("account_customers")
    val accountCustomers:List<AccountCustomerDto>
)


