package com.example.repository.di

import com.example.domain.repository.PostsBaseRepository
import com.example.repository.PostsRepository
import com.example.repository.mapper.BaseMapper
import com.example.repository.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryAbstractModule {

    @Binds
    @Singleton
    abstract fun bindsMapper(mapper: Mapper) : BaseMapper

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: PostsRepository) : PostsBaseRepository
}