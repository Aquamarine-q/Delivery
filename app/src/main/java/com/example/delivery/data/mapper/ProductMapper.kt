package com.example.delivery.data.mapper

import com.example.delivery.data.model.Product

fun Product.toDomain() = com.example.delivery.domain.model.Product(
    id = id,
    categoryId = category_id,
    name = name,
    description = description,
    image = image,
    currentPrice = price_current,
    oldPrice = price_old,
    measure = measure,
    measureUnit = measure_unit,
    energyPer100Grams = energy_per_100_grams,
    proteinsPer100Grams = proteins_per_100_grams,
    fatsPer100Grams = fats_per_100_grams,
    carbohydratesPer100Grams = carbohydrates_per_100_grams,
    tagIds = tag_ids
)