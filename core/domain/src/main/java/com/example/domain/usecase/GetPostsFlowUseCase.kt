package com.example.domain.usecase

import com.example.domain.model.Post
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.base.BaseGetPostsFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetPostsFlowUseCase(
    private val postsRepository: PostsBaseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseGetPostsFlowUseCase {
    override suspend operator fun invoke(userId: String): Flow<List<Post>> {
        return withContext(dispatcher) {
            postsRepository.getPostsFlow(userId)
        }
    }
}