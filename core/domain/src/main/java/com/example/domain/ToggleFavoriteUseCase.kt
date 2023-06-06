package com.example.domain

import com.example.domain.repository.PostsBaseRepository

class ToggleFavoriteUseCase (
    private val postsRepository: PostsBaseRepository
) {
    suspend operator fun invoke(postId: String, isChecked: Boolean) {
        postsRepository.toggleFavorite(postId, isChecked)
    }
}