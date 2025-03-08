package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val manageAccountsUseCase: ManageAccountsUseCase
) : BaseViewModel<UserDetailsState, UserDetailsEffect>(UserDetailsState()) {
    private val args = UserDetailsArgs(savedStateHandle)

    init {
        updateState {
            it.copy(clientId = args.clientId, userPhone = args.phoneNumber, userName = args.name)
        }
        getInvoices()
    }

    private fun getInvoices() {
        if (state.value.clientId != -1) {

            updateState {
                it.copy(isLoading = true)
            }
            tryToExecute(
                function = {
                    manageAccountsUseCase.getAccountsInvoices(
                        clientId = state.value.clientId,
                        sort = state.value.sort
                    )
                },
                onSuccess = { data ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            invoices = data.invoices,
                            totalInvoices = data.accountInvoicesCount,
                            totalAmount = data.accountTotalInvoice
                        )
                    }
                },
                onError = { _, m ->
                    updateState {
                        it.copy(isLoading = false, error = m)
                    }
                    sendNewEffect(UserDetailsEffect.ShowToastError(m))
                }
            )
        }

    }

    fun checkedChanged(value: String) {
        updateState {
            it.copy(sort = value)
        }
        getInvoices()
    }

    fun amountChange(value: String) {
        updateState {
            it.copy(amount = value)
        }
    }

    fun onChangeImage(uri: Uri?, file: File?) {
        updateState {
            it.copy(image = file, imageUri = uri)
        }
    }

    fun additionalNotesChange(additionalNotes: String) {
        updateState {
            it.copy(additionalNotes = additionalNotes)
        }
    }

    fun changeCreditor(isCreditor: Boolean) {
        updateState {
            it.copy(isCreditor = isCreditor)
        }
    }

    fun addOperation() {
        updateState {
            it.copy(
                isLoadingAddOperation = true
            )
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.uploadOperation(
                    customerId = state.value.clientId,
                    amount = state.value.amount,
                    dateTime = state.value.date,
                    type = if (state.value.isCreditor) "creditor" else "debtor",
                    additionalNote = state.value.additionalNotes,
                    image = state.value.image!!
                )
            },
            onError = { _, message ->
                sendNewEffect(UserDetailsEffect.ShowToastError(message))
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
            },
            onSuccess = {
                sendNewEffect(UserDetailsEffect.SucceedAddOperation)
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
                getInvoices()
            }
        )
    }
//
//    fun onClickCategory(index: Int) {
//        updateState {
//            it.copy(
//                selectedCategoryName = state.value.categoriesUi[index].name,
//                isSelectedAll = false,
//                categoriesUi = state.value.categoriesUi.mapIndexed { pos, item ->
//                    if (pos == index)
//                        item.copy(isSelected = true)
//                    else
//                        item.copy(isSelected = false)
//                },
//                users = state.value.categories[index].accountCustomers.map { accountCustomer -> accountCustomer.toUser() }
//            )
//        }
//    }


//    fun addCategory(name: String) {
//        tryToExecute(
//            function = {
//                updateState {
//                    it.copy(isLoadingAddCategory = true)
//                }
//                manageAccountsUseCase.addCategory(name)
//            },
//            onError = { _, m ->
//                updateState {
//                    it.copy(isLoadingAddCategory = false)
//                }
//                sendNewEffect(AccountsEffect.ShowToastError(m))
//            },
//            onSuccess = { data ->
//                updateState {
//                    it.copy(isLoadingAddCategory = false, isSucceedAddCategory = data)
//                }
//                sendNewEffect(AccountsEffect.SucceedAddCategory)
//                getCategories()
//            }
//        )
//    }
}