package es.rufflecol.sam.mtgcards.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.rufflecol.sam.mtgcards.data.remote.MtgService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMtgService(): MtgService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://api.magicthegathering.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MtgService::class.java)
    }
}
