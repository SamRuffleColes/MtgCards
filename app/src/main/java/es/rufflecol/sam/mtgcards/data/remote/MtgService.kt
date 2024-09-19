package es.rufflecol.sam.mtgcards.data.remote

import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardListResponseDto
import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MtgService {

    @GET("cards")
    suspend fun fetchCards(@Query("page") page: Int = 0,
                   @Query("pageSize") pageSize: Int = 100,
                   @Query("contains") fieldName: String = "imageUrl"
    ): MtgCardListResponseDto

    @GET("cards/{id}")
    suspend fun fetchCard(@Path("id") id: String): MtgCardResponseDto

}