package com.example.domain

import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ToggleFavoriteUseCaseTest {

    @Mock
    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        testPostsRepository = Mockito.mock(PostsBaseRepository::class.java)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(testPostsRepository)
    }

    @Test
    fun invoke_callToggleFavoriteUseCase_repositoryMethodCalled() = runTest{
        val postId = "0"
        val isChecked = false

        toggleFavoriteUseCase(postId, isChecked)

        Mockito.verify(testPostsRepository, Mockito.times(1))
            .toggleFavorite(postId, isChecked)
    }
}