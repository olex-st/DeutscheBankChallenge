package com.example.domain

import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.FetchPostsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class FetchPostsUseCaseTest {

    @Mock
    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var fetchPostsUseCase: FetchPostsUseCase

    @Before
    fun setUp() {
        testPostsRepository = Mockito.mock(PostsBaseRepository::class.java)
        fetchPostsUseCase = FetchPostsUseCase(testPostsRepository)
    }

    @Test
    fun invoke_callFetchPostsUseCase_repositoryMethodCalled() = runTest{
        val userId = "0"

        fetchPostsUseCase(userId)

        verify(testPostsRepository, times(1)).fetchPosts(userId)
    }
}