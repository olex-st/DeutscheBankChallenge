package com.example.domain

import com.example.domain.model.Text
import com.example.domain.repository.PostsBaseRepository

class GetCommentsUseCase (
    private val postsRepository: PostsBaseRepository
) {
    suspend operator fun invoke(postId: String): List<Text> {
        return postsRepository.getComments(postId)
    }
}