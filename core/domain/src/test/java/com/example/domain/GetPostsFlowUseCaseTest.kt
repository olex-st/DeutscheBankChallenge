package com.example.domain

import com.example.domain.fake.TestPostsRepository
import com.example.domain.model.Post
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.GetPostsFlowUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPostsFlowUseCaseTest {

    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var getPostsFlowUseCase: GetPostsFlowUseCase

    @Before
    fun setUp() {
        testPostsRepository = TestPostsRepository()
        getPostsFlowUseCase = GetPostsFlowUseCase(testPostsRepository)
    }

    @Test
    fun invoke_callGetPostsFlowUseCase_repositoryMethodCalled() = runTest{
        val userId = "0"
        val expectedFlow = flowOf(listOf(
            Post(1, 10, "test", "test").apply { favorite = true },
            Post(1, 11, "test", "test").apply { favorite = false }
        ))

        val flow = getPostsFlowUseCase(userId)

        assert(flow.toList() == expectedFlow.toList())
    }
}