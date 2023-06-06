package com.example.domain

import com.example.domain.model.Post
import com.example.domain.repository.PostsBaseRepository
import kotlinx.coroutines.flow.Flow

class GetPostsFlowUseCase (
    private val postsRepository: PostsBaseRepository
) {
    suspend operator fun invoke(userId: String, favoritesOnly: Boolean): Flow<List<Post>> {
        return postsRepository.getPostsFlow(userId, favoritesOnly)
    }
}