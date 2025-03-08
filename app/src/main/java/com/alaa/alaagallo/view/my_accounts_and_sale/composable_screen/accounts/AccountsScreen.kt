package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FABButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.HeaderText
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.NavigationDrawerCategories
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.TableContentItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountsScreen(viewModel: AccountsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    AccountsContent(state, onClickAllItem = {
        viewModel.onClickAllItem()
    }, onClickNewCategory = { text ->
        viewModel.addCategory(text)
    }) {
        viewModel.onClickCategory(it)
    }
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AccountsEffect.ShowToastError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is AccountsEffect.SucceedAddCategory -> {
                    Toast.makeText(context, "تم اضافة التصنيف بنجاح", Toast.LENGTH_SHORT).show()
                    delay(1000)
                    viewModel.resetAddCategoryStatus()
                }

                else -> TODO()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun AccountsContent(
    state: AccountsState,
    onClickAllItem: () -> Unit = {},
    onClickNewCategory: (String) -> Unit = {},
    onClickCategory: (Int) -> Unit = {}
) {
    val navController = LocalNavigationProvider.current
    val drawer = rememberDrawerState(initialValue = DrawerValue.Closed)
    var menuState by remember { mutableStateOf(false) }
    LaunchedEffect(menuState) {
        if (menuState) drawer.open()
        else drawer.close()
    }
    BackHandler {
        if (menuState) menuState = false
        else navController.popBackStack()
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalNavigationDrawer(drawerState = drawer, gesturesEnabled = false, drawerContent = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    drawerContainerColor = Color.Transparent,
                    drawerShape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                ) {
                    NavigationDrawerCategories(
                        isLoading = state.isLoading,
                        isLoadingAddCategory = state.isLoadingAddCategory,
                        isSucceedAddCategory = state.isSucceedAddCategory,
                        list = state.categoriesUi,
                        onClickDismiss = {
                            menuState = false
                        },
                        onClickItem = { index ->
                            onClickCategory(index)
                            menuState = false
                        },
                        onClickAllItem = {
                            menuState = false
                            onClickAllItem()
                        },
                        isSelectedAll = state.isSelectedAll,
                        numOfAllUsers = state.allUsers.size,
                        onClickNewCategory = { text ->
                            onClickNewCategory(text)
                        })
                }
            }
        }) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Scaffold { _ ->
                    Box(Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(Color.Black)
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 23.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(painter = painterResource(id = R.drawable.ic_back),
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .pointerInput(Unit) {
                                                detectTapGestures {
                                                    navController.popBackStack()
                                                }
                                            })
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        "حساباتي",
                                        style = Theme.typography.headerMainTitle,
                                        color = Color.White
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        state.selectedCategoryName,
                                        style = Theme.typography.headerMainTitle,
                                        color = Color.White
                                    )
                                    Spacer(Modifier.width(30.dp))
                                    Icon(painter = painterResource(id = R.drawable.ic_menu),
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .pointerInput(Unit) {
                                                detectTapGestures {
                                                    menuState = true
                                                }
                                            })
                                }
                            }
                            AnimatedContent(state.isLoading, label = "") {
                                if (it) {
                                    Box(
                                        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                                    ) {
                                        LoadingIndicator()
                                    }
                                } else {
                                    Column(Modifier) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .background(Theme.colors.yellow)
                                                .padding(horizontal = 20.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "اجمالي المستحقات: 1000",
                                                style = Theme.typography.tableHeader.copy(
                                                    fontSize = 13.sp, lineHeight = 24.36.sp
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                "اجمالي الديون: 0",
                                                style = Theme.typography.tableHeader.copy(
                                                    fontSize = 13.sp, lineHeight = 24.36.sp
                                                ),
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.End
                                            )
                                        }
                                        Spacer(Modifier.height(10.dp))
                                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                            item {
                                                Row(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .height(48.dp)
                                                        .background(Theme.colors.greyTableHeader)
                                                        .padding(horizontal = 20.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "اسم العميل"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "رقم التليفون"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "عدد العمليات"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(0.5f),
                                                        text = "المبلغ"
                                                    )
                                                }
                                            }
                                            items(state.users) { user ->
                                                TableContentItem(user){
                                                    navController.navigateToUserDetails(user.id, user.mobile,user.name)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        FABButton(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = "اضافة عميل",
                            icon = R.drawable.ic_add_client
                        ) {

                        }
                    }
                }
            }
        }
    }

}