package com.example.delivery.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.delivery.R
import com.example.delivery.domain.model.Product
import com.example.delivery.navigation.Screen
import com.example.delivery.presentation.viewmodel.HomeViewModel
import com.example.delivery.presentation.viewstate.HomeViewState
import com.example.delivery.ui.theme.LightGray
import com.example.delivery.ui.theme.Orange40
import com.example.delivery.ui.theme.White

@Composable
fun ProductCardScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    productId: Int?,
) {
    val model by viewModel.viewState.observeAsState(HomeViewState())
    if (productId != null) {
        ProductCard(
            navController = navController,
            product = model.products.first { product -> product.id == productId },
            onAddBasketItem = { product -> viewModel.onAddBasketItem(product) },
        )
    }
}

@Composable
fun ProductCard(
    navController: NavHostController,
    product: Product?,
    onAddBasketItem: (Product) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            OutlinedIconButton(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, LightGray),
                onClick = { navController.navigate(Screen.Home.route) },
            ) {
                Icon(
                    painterResource(id = R.drawable.arrowleft),
                    contentDescription = "Arrow left",
                )
            }
            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "Photo of the dish",
                modifier = Modifier.padding(8.dp)
            )
        }
        if (product != null) {
            Text(
                text = product.name,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 25.sp,
            )

            Text(
                text = product.description,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                fontSize = 18.sp,
                color = Color.Gray
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Вес", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("%s %s", product.measure.toString(), product.measureUnit))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Энерг. ценность", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("%s ккал", product.energyPer100Grams.toString()))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Белки", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("%s г", product.proteinsPer100Grams.toString()))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Жиры", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("%s г", product.fatsPer100Grams.toString()))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Углеводы", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = String.format("%s г", product.carbohydratesPer100Grams.toString()))
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Button(
                onClick = {
                    onAddBasketItem(product)
                    navController.navigate(Screen.Basket.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(Orange40)
            ) {
                Text(
                    text = String.format("В корзину за  %d ₽", product.currentPrice),
                    color = White,
                )
            }
        }
    }
}

@Composable
@Preview
fun CardProductScreenPreview() {
    //ProductCardScreen()
}