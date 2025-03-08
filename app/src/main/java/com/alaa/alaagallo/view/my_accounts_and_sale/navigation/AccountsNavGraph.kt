package com.alaa.alaagallo.view.my_accounts_and_sale.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts.accountsRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details.userDetailsRoute

@Composable
fun AccountsNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(
        navController = navController,
        startDestination = Graph.HOME,
    ) {
        accountsGraph()
        accountsRoute()
        userDetailsRoute()
    }
}