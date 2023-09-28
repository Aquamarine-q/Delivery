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

fun com.example.delivery.domain.model.Product.toData() = Product(
    id = id,
    category_id = categoryId,
    name = name,
    description = description,
    image = image,
    price_current = currentPrice,
    price_old = oldPrice,
    measure = measure,
    measure_unit = measureUnit,
    energy_per_100_grams = energyPer100Grams,
    proteins_per_100_grams = proteinsPer100Grams,
    fats_per_100_grams = fatsPer100Grams,
    carbohydrates_per_100_grams = carbohydratesPer100Grams,
    tag_ids = tagIds
)