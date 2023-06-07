package com.example.domain.usecase

import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.base.BaseFetchPostsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchPostsUseCase(
    private val postsRepository: PostsBaseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseFetchPostsUseCase {
    override suspend operator fun invoke(userId: String) {
        return withContext(dispatcher) {
            postsRepository.fetchPosts(userId)
        }
    }
}