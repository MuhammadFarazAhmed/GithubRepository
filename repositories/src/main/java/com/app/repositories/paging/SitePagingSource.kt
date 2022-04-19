package com.app.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.interfaces.models.common.PagingItem


//class SitePagingSource(
//    val apiFun: suspend (page: Int, itemsPerPage: Int) -> PagingItem<SiteOutput>,
//) : PagingSource<Int, SiteOutput>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SiteOutput> {
//        try {
//            // Start refresh at page 1 if undefined.
//            val nextPage = params.key ?: 1
//            val response = apiFun(nextPage, 20)
//
//            return LoadResult.Page(
//                data = response.items,
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = response.pageInfo.page?.plus(1)
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, SiteOutput>): Int? {
//        return state.anchorPosition
//    }
//}