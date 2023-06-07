package com.example.typicodebook.fake

import com.example.domain.model.Post
import com.example.domain.usecase.base.BaseGetPostsFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestGetPostsFlowUseCase : BaseGetPostsFlowUseCase {
    override suspend operator fun invoke(userId: String): Flow<List<Post>> {
        return flowOf(listOf(
                Post(1, 10, "test", "test").apply { favorite = true },
                Post(1, 11, "test", "test").apply { favorite = false }
            ))
    }
}
