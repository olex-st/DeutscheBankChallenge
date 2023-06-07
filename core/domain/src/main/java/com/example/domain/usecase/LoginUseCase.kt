package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.base.BaseLoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val postsRepository: PostsBaseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseLoginUseCase {
    override suspend operator fun invoke(userId: String): User {
        return withContext(dispatcher) {
            postsRepository.login(userId)
        }
    }
}