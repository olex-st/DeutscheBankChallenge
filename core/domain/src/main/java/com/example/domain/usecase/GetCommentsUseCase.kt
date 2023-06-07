package com.example.domain.usecase

import com.example.domain.model.Text
import com.example.domain.repository.PostsBaseRepository
import com.example.domain.usecase.base.BaseGetCommentsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCommentsUseCase(
    private val postsRepository: PostsBaseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseGetCommentsUseCase {
    override suspend operator fun invoke(postId: String): List<Text> {
        return withContext(dispatcher) {
            val comments = postsRepository.getComments(postId)
            val post = postsRepository.getPost(postId)
            val texts = mutableListOf<Text>().apply {
                add(post)
                addAll(comments)
            }
            texts
        }
    }
}