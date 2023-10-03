package com.example.delivery.data.repository

import com.example.delivery.data.api.DeliveryApi
import com.example.delivery.data.mapper.toDomain
import com.example.delivery.data.model.Category
import com.example.delivery.domain.model.Product
import com.example.delivery.domain.model.Tag
import javax.inject.Inject

class ProductRepository
@Inject constructor(private val api: DeliveryApi) {

    suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }

    suspend fun getProducts(): List<Product> {
        return api.getProducts().map { product -> product.toDomain() }
    }

    suspend fun getTags(): List<Tag> {
        return api.getTags().map { product -> product.toDomain() }
    }
}