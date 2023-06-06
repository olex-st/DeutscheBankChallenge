package com.example.deutschebankchallenge.di

import com.example.domain.FetchPostsUseCase
import com.example.domain.GetCommentsUseCase
import com.example.domain.GetPostsFlowUseCase
import com.example.domain.LoginUseCase
import com.example.domain.ToggleFavoriteUseCase
import com.example.domain.repository.PostsBaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(postsRepository: PostsBaseRepository): LoginUseCase {
        return LoginUseCase(postsRepository)
    }

    @Provides
    fun provideGetPostsFlowUserCase(postsRepository: PostsBaseRepository): GetPostsFlowUseCase {
        return GetPostsFlowUseCase(postsRepository)
    }

    @Provides
    fun provideGetCommentsUseCase(postsRepository: PostsBaseRepository): GetCommentsUseCase {
        return GetCommentsUseCase(postsRepository)
    }

    @Provides
    fun provideFetchPostsUseCase(postsRepository: PostsBaseRepository): FetchPostsUseCase {
        return FetchPostsUseCase(postsRepository)
    }

    @Provides
    fun provideToggleFavoriteUseCase(postsRepository: PostsBaseRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(postsRepository)
    }
}