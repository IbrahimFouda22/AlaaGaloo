package com.alaa.alaagallo.view.my_accounts_and_sale.navigation


sealed class Screen(
    val route: String,
) {
    data object AccountsAndSale : Screen("accountsAndSaleScreen")
    data object Accounts : Screen("accountsScreen")
    data object UserDetails : Screen("userDetailsScreen")
}
