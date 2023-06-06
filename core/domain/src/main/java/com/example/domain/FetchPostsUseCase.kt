package com.example.domain

import com.example.domain.repository.PostsBaseRepository

class FetchPostsUseCase (
    private val postsRepository: PostsBaseRepository
) {
    suspend operator fun invoke(userId: String) {
        return postsRepository.fetchPosts(userId)
    }
}