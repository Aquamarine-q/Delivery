@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.delivery.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.delivery.R
import com.example.delivery.navigation.Screen
import com.example.delivery.ui.theme.LightGray
import com.example.delivery.ui.theme.Orange40

@Composable
fun BasketScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Корзина",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowleft),
                        contentDescription = "Back icon",
                        tint = Orange40
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }) { values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(values)
        ) {
            items(10) {
                BasketItem()
            }
        }
    }
}

@Composable
fun BasketItem() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = "Photo of the dish",
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Fit
        )
        Column {
            Text(
                text = "Том Ям", modifier = Modifier.padding(16.dp)
            )
            Row {
                OutlinedIconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                        .background(LightGray, CutCornerShape(4.dp)),
                    shape = CutCornerShape(4.dp),
                    border = BorderStroke(1.dp, LightGray),
                ) {
                    Icon(
                        painterResource(id = R.drawable.minus),
                        contentDescription = "Minus",
                        tint = Orange40
                    )
                }
                Text(
                    text = "1",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp
                )
                OutlinedIconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                        .background(LightGray, CutCornerShape(4.dp)),
                    shape = CutCornerShape(4.dp),
                    border = BorderStroke(1.dp, LightGray),
                ) {
                    Icon(
                        painterResource(id = R.drawable.plus),
                        contentDescription = "Plus",
                        tint = Orange40
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "480 ₽",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
    Divider(color = Color.Gray, thickness = 1.dp)
}

@Composable
fun EmptyBasket() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Пусто, выберите блюда в каталоге :)",
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun BasketScreenPreview() {
    //BasketScreen()
    //EmptyBasket()
}