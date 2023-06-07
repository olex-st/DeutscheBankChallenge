package com.example.domain

import com.example.domain.fake.TestPostsRepository
import com.example.domain.model.User
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.LoginUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        testPostsRepository = TestPostsRepository()
        loginUseCase = LoginUseCase(testPostsRepository)
    }

    @Test
    fun invoke_callLoginUseCase_repositoryMethodCalled() = runTest{
        val userId = "0"
        val expectedUser = User(9)

        val user = loginUseCase(userId)

        assert(user == expectedUser)
    }
}