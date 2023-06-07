package com.example.domain.usecase

import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.base.BaseToggleFavoriteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToggleFavoriteUseCase(
    private val postsRepository: PostsBaseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseToggleFavoriteUseCase {
    override suspend operator fun invoke(postId: String, isChecked: Boolean) {
        return withContext(dispatcher) {
            postsRepository.toggleFavorite(postId, isChecked)
        }
    }
}