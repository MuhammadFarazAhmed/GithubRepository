package com.app.interfaces.models.common

data class PagingItem<Item>(val pageInfo: PageInfo, val items: List<Item> = listOf())