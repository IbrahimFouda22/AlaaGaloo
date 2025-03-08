package com.alaa.data.dto

data class BaseDto<T>(
    val statusCode: Int,
    val data: T
)
