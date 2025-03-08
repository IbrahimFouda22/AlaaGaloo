package com.alaa.data.source

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.CategoryDto
import com.alaa.data.service.AccountsApiService
import com.alaa.domain.exceptions.NoInternetException
import com.alaa.domain.exceptions.NotFoundException
import com.alaa.domain.exceptions.NullResultException
import com.alaa.domain.exceptions.ServerErrorException
import okhttp3.RequestBody

import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: AccountsApiService,
) : IRemoteDataSource {

    override suspend fun getCategoriesAndUsers(): List<CategoryDto> {
        return wrapApiResponse {
            apiService.getCategoriesAndUsers()
        }.data
    }

    override suspend fun addCategory(name: String): Boolean {
        wrapApiResponse {
            apiService.addCategory(name)
        }
        return true
    }

    override suspend fun getAccountsInvoices(clientId: Int, sort: String): AccountInvoiceDto {
        return wrapApiResponse {
            apiService.getAccountsInvoices(clientId, sort)
        }.data
    }

    override suspend fun uploadOperation(data: RequestBody): Boolean {
        wrapApiResponse {
            apiService.uploadOperation(data)
        }
        return true
    }


    private suspend fun <T> wrapApiResponse(
        function: suspend () -> Response<T>,
    ): T {
        try {
            val response = function()
            if (response.isSuccessful)
                return response.body() ?: throw NullResultException("No Data")
            else {
                val jObjError = JSONObject(
                    response.errorBody()!!.string()
                )
//                val errorObject = jObjError.getJSONObject("error")
                throw when (response.code()) {
                    404 -> NotFoundException(jObjError.getString("message"))
                    else -> {
                        ServerErrorException(jObjError.getString("message"))
                    }
                }
            }
        } catch (e: UnknownHostException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (e: SocketTimeoutException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (e: SocketException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (io: IOException) {
            throw NoInternetException(io.message.toString())
        }
    }


}

