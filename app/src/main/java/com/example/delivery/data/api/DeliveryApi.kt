package com.example.delivery.data.api

import com.example.delivery.data.model.Category
import com.example.delivery.data.model.Tag
import com.example.delivery.data.model.Product
import retrofit2.http.GET

interface DeliveryApi {

    @GET("512f860a-b203-4c23-8cc2-0ef264d16559")
    suspend fun getCategories(): List<Category>

    @GET("aa2c26ee-9542-4578-b63b-334dfc9a862d")
    suspend fun getProducts(): List<Product>

    @GET("e73f9338-a5c1-4e1c-9978-78a52d1aed9e")
    suspend fun getTags(): List<Tag>
}