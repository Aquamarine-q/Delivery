@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.delivery.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.delivery.R
import com.example.delivery.data.model.Category
import com.example.delivery.domain.model.Product
import com.example.delivery.domain.model.Tag
import com.example.delivery.navigation.Screen
import com.example.delivery.presentation.viewmodel.HomeViewModel
import com.example.delivery.presentation.viewstate.HomeViewState
import com.example.delivery.ui.theme.LightGray
import com.example.delivery.ui.theme.Orange40
import com.example.delivery.ui.theme.White

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    val model by viewModel.viewState.observeAsState(HomeViewState())
    HomeContent(
        categories = model.categories,
        products = model.products,
        tags = model.tags,
        isSheetOpen = model.isSheetOpen,
        isSearchViewOpened = model.isSearchViewOpened,
        searchText = model.searchText,
        onSheetStateChanged = { state -> viewModel.onSheetStateChanged(state) },
        onCheckedChange = { tag, checked -> viewModel.onCheckedChanged(tag, checked) },
        onTextChange = { text -> viewModel.onSearchTextChanged(text) },
        onSearchViewStateChanged = { state -> viewModel.onSearchViewStateChanged(state) },
        selectedCategory = model.selectedCategory,
        onCategoryChanged = { category -> viewModel.onCategoryChanged(category) },
        onAddBasketItem = { product -> viewModel.onAddBasketItem(product) },
        onRemoveBasketItem = { product -> viewModel.onRemoveBasketItem(product) },
        basketProducts = model.basketProducts,
        cost = model.cost,
        navController = navController,
    )
    LaunchedEffect(Unit) {
        viewModel.onScreenOpened()
    }
}

@Composable
fun HomeContent(
    categories: List<Category>,
    products: List<Product>,
    tags: List<Tag>,
    isSheetOpen: Boolean,
    isSearchViewOpened: Boolean,
    searchText: String,
    onSheetStateChanged: (Boolean) -> Unit,
    onCheckedChange: (Tag, Boolean) -> Unit,
    onTextChange: (String) -> Unit,
    onSearchViewStateChanged: (Boolean) -> Unit,
    selectedCategory: Category,
    onCategoryChanged: (Category) -> Unit,
    onAddBasketItem: (Product) -> Unit,
    onRemoveBasketItem: (Product) -> Unit,
    basketProducts: List<Product>,
    cost: Int,
    navController: NavHostController,
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        if (isSearchViewOpened) {
            SearchAppBar(
                text = searchText,
                onTextChange = onTextChange,
                onSearchViewStateChanged = onSearchViewStateChanged,
            )
        } else {
            DefaultAppBar(
                tags = tags,
                onSheetStateChanged = onSheetStateChanged,
                onSearchViewStateChanged = onSearchViewStateChanged
            )
        }
    }) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            CategoryChips(selectedCategory, categories, onCategoryChanged)
            if (products.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        DishCard(
                            product,
                            basketProducts,
                            onAddBasketItem,
                            onRemoveBasketItem,
                            navController,
                        )
                    }
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.Basket.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(Orange40)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = "Cart",
                        tint = White
                    )
                    Text(text = String.format("  %d ₽", cost), color = White)
                }
            } else {
                NothingFoundScreen(isSearchViewOpened)
            }
            if (isSheetOpen) {
                BottomSheet(onSheetStateChanged, tags, onCheckedChange)
            }
        }
    }
}

@Composable
fun BottomSheet(
    onSheetStateChanged: (Boolean) -> Unit,
    tags: List<Tag>,
    onCheckedChange: (Tag, Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onSheetStateChanged(false) },
    ) {
        Text(
            text = "Подобрать блюда",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        LazyColumn {
            items(tags) { tag ->
                Row {
                    Text(text = tag.name, modifier = Modifier.padding(top = 16.dp, start = 16.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = tag.isSelected,
                        onCheckedChange = { checked -> onCheckedChange(tag, checked) },
                        colors = CheckboxDefaults.colors(Orange40)
                    )
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
        Button(
            onClick = { onSheetStateChanged(false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Orange40)
        ) {
            Text(text = "Готово", color = White)
        }
    }
}

@Composable
fun DefaultAppBar(
    tags: List<Tag>,
    onSheetStateChanged: (Boolean) -> Unit,
    onSearchViewStateChanged: (Boolean) -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Foodies logo",
                tint = Orange40
            )
        },
        navigationIcon = {
            BadgedBox(badge = {
                if (tags.any { tag -> tag.isSelected }) {
                    Badge {
                        Text(text = tags.count { tag -> tag.isSelected }.toString())
                    }
                }
            }) {
                IconButton(onClick = { onSheetStateChanged(true) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter icon"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { onSearchViewStateChanged(true) }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search icon"
                )
            }
        },
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchViewStateChanged: (Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = White,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text ->
                onTextChange(text)
            },
            placeholder = {
                Text(text = "Найти блюдо")
            },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {
                    onTextChange("")
                    onSearchViewStateChanged(false)
                }) {
                    Icon(
                        painterResource(R.drawable.arrowleft),
                        contentDescription = "Back icon",
                        tint = Orange40,
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onSearchViewStateChanged(false)
                    }
                }) {
                    Icon(painterResource(R.drawable.cancel), contentDescription = "Cancel icon")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onTextChange(text) }),
        )
    }
}

@Composable
fun CategoryChips(
    selectedCategory: Category,
    categories: List<Category>,
    onCategoryChanged: (Category) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories) { category ->
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .toggleable(
                        value = category == selectedCategory,
                        onValueChange = { onCategoryChanged(category) },
                    ),
                shape = RoundedCornerShape(8.dp),
                color = if (category == selectedCategory) {
                    Orange40
                } else {
                    White
                },
            ) {
                Text(
                    text = category.name,
                    modifier = Modifier.padding(8.dp),
                    color = if (category == selectedCategory) {
                        White
                    } else {
                        Color.Black
                    },
                )
            }
        }
    }
}

@Composable
fun DishCard(
    product: Product,
    basketProducts: List<Product>,
    onAddBasketItem: (Product) -> Unit,
    onRemoveBasketItem: (Product) -> Unit,
    navController: NavHostController,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            navController.navigate(
                String.format(
                    "%s/%d",
                    Screen.ProductCard.route,
                    product.id,
                )
            )
        },
        colors = CardDefaults.cardColors(containerColor = LightGray),
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            when {
                product.oldPrice != null -> {
                    Icon(
                        painter = painterResource(id = R.drawable.discount),
                        contentDescription = "Discount",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                    )
                }

                product.tagIds.contains(2) -> {
                    Icon(
                        painter = painterResource(id = R.drawable.withoutmeat),
                        contentDescription = "Without meat",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                    )
                }

                product.tagIds.contains(4) -> {
                    Icon(
                        painter = painterResource(id = R.drawable.spicy),
                        contentDescription = "Spicy",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "Photo of the dish",
            )
        }
        Text(
            text = product.name,
            modifier = Modifier.padding(16.dp),
        )
        Text(
            text = String.format("%d %s", product.measure, product.measureUnit),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )
        if (basketProducts.contains(product)) {
            ChangeProductCountButton(
                product,
                basketProducts,
                onAddBasketItem,
                onRemoveBasketItem,
            )
        } else {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(White, CutCornerShape(4.dp)),
                onClick = { onAddBasketItem(product) },
                colors = ButtonDefaults.buttonColors(Color.White),
            ) {
                if (product.oldPrice == null) {
                    Text(
                        text = String.format("%d ₽", product.currentPrice),
                        color = Color.Black,
                    )
                } else {
                    Text(
                        text = String.format("%d ₽", product.currentPrice),
                        color = Color.Black,
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = String.format("%d ₽", product.oldPrice),
                        color = Color.LightGray,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )
                }
            }
        }
    }
}

@Composable
fun ChangeProductCountButton(
    product: Product,
    basketProducts: List<Product>,
    onAddBasketItem: (Product) -> Unit,
    onRemoveBasketItem: (Product) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedIconButton(
            onClick = { onRemoveBasketItem(product) },
            modifier = Modifier
                .wrapContentSize()
                .background(White, CutCornerShape(4.dp)),
            shape = CutCornerShape(4.dp),
            border = BorderStroke(1.dp, White),
        ) {
            Icon(
                painterResource(id = R.drawable.minus),
                contentDescription = "Minus",
                tint = Orange40
            )
        }
        Text(
            text = basketProducts.count { it == product }.toString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        OutlinedIconButton(
            onClick = { onAddBasketItem(product) },
            modifier = Modifier
                .wrapContentSize()
                .background(White, CutCornerShape(4.dp)),
            shape = CutCornerShape(4.dp),
            border = BorderStroke(1.dp, White),
        ) {
            Icon(
                painterResource(id = R.drawable.plus), contentDescription = "Plus", tint = Orange40
            )
        }
    }
}

@Composable
fun NothingFoundScreen(isSearchViewOpened: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        if (isSearchViewOpened) {
            Text(
                text = "Ничего не нашлось :(",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "Таких блюд нет :(",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Попробуйте изменить фильтры",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(text = "", onTextChange = {}, onSearchViewStateChanged = {})
}