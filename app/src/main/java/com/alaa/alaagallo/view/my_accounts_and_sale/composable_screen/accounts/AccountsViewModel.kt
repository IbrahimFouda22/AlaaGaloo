package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val manageAccountsUseCase: ManageAccountsUseCase
) : BaseViewModel<AccountsState, AccountsEffect>(AccountsState()) {

    fun getCategories() {
        updateState {
            it.copy(isLoading = true)
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.getCategoriesAndUsers()
            },
            onSuccess = { data ->
                updateState {
                    it.copy(isLoading = false, categories = data, users = data.flatMap { category ->
                        category.accountCustomers.map { accountCustomer ->
                            accountCustomer.toUser()
                        }
                    }, categoriesUi = data.map { item ->
                        CategoryUi(
                            id = item.id,
                            name = item.name,
                            numOfAccounts = item.accountCustomers.size,
                            isSelected = item.name == state.value.selectedCategoryName
                        )
                    })
                }
                updateState {
                    it.copy(allUsers = state.value.users)
                }
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoading = false, error = m)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            }
        )
    }

    fun onClickCategory(index: Int) {
        updateState {
            it.copy(
                selectedCategoryName = state.value.categoriesUi[index].name,
                isSelectedAll = false,
                categoriesUi = state.value.categoriesUi.mapIndexed { pos, item ->
                    if (pos == index)
                        item.copy(isSelected = true)
                    else
                        item.copy(isSelected = false)
                },
                users = state.value.categories[index].accountCustomers.map { accountCustomer -> accountCustomer.toUser() }
            )
        }
    }

    fun onClickAllItem() {
        updateState {
            it.copy(
                selectedCategoryName = "الكل",
                categoriesUi = state.value.categoriesUi.mapIndexed { pos, item ->
                    item.copy(isSelected = false)
                },
                users = state.value.allUsers,
                isSelectedAll = true
            )
        }
    }

    fun resetAddCategoryStatus() {
        updateState {
            it.copy(
                isSucceedAddCategory = false
            )
        }
    }

    fun addCategory(name: String) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(isLoadingAddCategory = true)
                }
                manageAccountsUseCase.addCategory(name)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingAddCategory = false)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            },
            onSuccess = { data ->
                updateState {
                    it.copy(isLoadingAddCategory = false, isSucceedAddCategory = data)
                }
                sendNewEffect(AccountsEffect.SucceedAddCategory)
                getCategories()
            }
        )
    }
}