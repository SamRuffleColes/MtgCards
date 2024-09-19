package es.rufflecol.sam.mtgcards.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import es.rufflecol.sam.mtgcards.data.remote.MtgService
import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardDto

class MtgCardsPagingDataSource(
    private val service: MtgService,
) : PagingSource<Int, MtgCardDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MtgCardDto> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val cards = service.fetchCards(page = page, pageSize = PAGING_SIZE).cards
            LoadResult.Page(
                data = cards,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MtgCardDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        const val PAGING_SIZE = 25
    }
}