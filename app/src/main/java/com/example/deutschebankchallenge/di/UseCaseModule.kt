package com.example.deutschebankchallenge.di

import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.FetchPostsUseCase
import com.example.domain.usecase.GetCommentsUseCase
import com.example.domain.usecase.GetPostsFlowUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.ToggleFavoriteUseCase
import com.example.domain.usecase.base.BaseFetchPostsUseCase
import com.example.domain.usecase.base.BaseGetCommentsUseCase
import com.example.domain.usecase.base.BaseGetPostsFlowUseCase
import com.example.domain.usecase.base.BaseLoginUseCase
import com.example.domain.usecase.base.BaseToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(postsRepository: PostsBaseRepository): BaseLoginUseCase {
        return LoginUseCase(postsRepository)
    }

    @Provides
    fun provideGetPostsFlowUserCase(postsRepository: PostsBaseRepository): BaseGetPostsFlowUseCase {
        return GetPostsFlowUseCase(postsRepository)
    }

    @Provides
    fun provideGetCommentsUseCase(postsRepository: PostsBaseRepository): BaseGetCommentsUseCase {
        return GetCommentsUseCase(postsRepository)
    }

    @Provides
    fun provideFetchPostsUseCase(postsRepository: PostsBaseRepository): BaseFetchPostsUseCase {
        return FetchPostsUseCase(postsRepository)
    }

    @Provides
    fun provideToggleFavoriteUseCase(postsRepository: PostsBaseRepository): BaseToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(postsRepository)
    }
}