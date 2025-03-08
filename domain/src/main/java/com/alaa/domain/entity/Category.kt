package com.alaa.domain.entity


data class Category(
    val id:Int,
    val name:String,
    val accountCustomers:List<AccountCustomer>
)


