package com.example.domain

import com.example.domain.model.User
import com.example.domain.repository.PostsBaseRepository

class LoginUseCase(
    private val postsRepository: PostsBaseRepository
) {
    suspend operator fun invoke(userId: String): User {
        return postsRepository.login(userId)
    }
}