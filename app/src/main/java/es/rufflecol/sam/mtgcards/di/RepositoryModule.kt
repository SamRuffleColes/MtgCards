package es.rufflecol.sam.mtgcards.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.rufflecol.sam.mtgcards.data.repository.MtgCardRepositoryImpl
import es.rufflecol.sam.mtgcards.domain.repository.MtgCardRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMtgCardRepository(
        repositoryImpl: MtgCardRepositoryImpl
    ): MtgCardRepository

}