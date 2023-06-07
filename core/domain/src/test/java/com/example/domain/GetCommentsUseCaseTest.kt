package com.example.domain

import com.example.domain.fake.TestPostsRepository
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.Text
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before

import org.junit.Test
import org.mockito.Mock

class GetCommentsUseCaseTest {

 @Mock
    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var getCommentsUseCase: GetCommentsUseCase

    @Before
    fun setUp() {
        testPostsRepository = TestPostsRepository()
        getCommentsUseCase = GetCommentsUseCase(testPostsRepository)
    }

    @Test
    fun invoke_callGetCommentsUseCase_repositoryMethodCalled() = runTest{
        val postId = "0"
        val expectedTextsList = listOf<Text>(
            Post(1, 2, "postText1", "postText2"),
            Comment(2, 7, "comm1", "comm1@text.com", "comm1"),
            Comment(2, 8, "comm1", "comm1@text.xom", "comm1"))

        val textsList = getCommentsUseCase(postId)
        assert(textsList == expectedTextsList)
    }
}