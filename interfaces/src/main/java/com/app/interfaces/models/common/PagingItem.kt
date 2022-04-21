package com.app.interfaces.models.common

data class PagingItem<Item>(val totalCount: Int,
                            val incompleteResults: Boolean,
                            val items: List<Item> = listOf())