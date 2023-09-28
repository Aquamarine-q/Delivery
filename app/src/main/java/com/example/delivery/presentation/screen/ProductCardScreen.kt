package com.example.delivery.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.delivery.R
import com.example.delivery.domain.model.Product
import com.example.delivery.navigation.Screen
import com.example.delivery.presentation.viewmodel.HomeViewModel
import com.example.delivery.presentation.viewstate.HomeViewState
import com.example.delivery.ui.theme.LightGray

@Composable
fun ProductCardScreen(
    navController: NavHostController,
    productId: String?,
    viewModelFactory: () -> ViewModelProvider.Factory,
    homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory()),
) {
    val model by homeViewModel.viewState.observeAsState(HomeViewState())
    if (productId != null) {
        ProductCard(
            navController = navController,
            basketProducts = model.basketProducts,
            product = model.products.findLast { product -> product.id == productId.toInt() }
        )
    }
}

@Composable
fun ProductCard(
    navController: NavHostController,
    basketProducts: List<Product>,
    product: Product?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            OutlinedIconButton(modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopStart)
                .padding(16.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, LightGray),
                onClick = { navController.navigate(Screen.Home.route) }) {
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
                Text(text = "400 г")
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Энерг. ценность", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "198,8 ккал")
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Белки", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "10 г")
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Жиры", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "8,5 г")
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Углеводы", color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "19,7 г")
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Composable
@Preview
fun CardProductScreenPreview() {
    //ProductCardScreen()
}