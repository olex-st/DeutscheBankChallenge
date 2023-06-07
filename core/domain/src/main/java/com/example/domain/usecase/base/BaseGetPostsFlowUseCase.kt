package com.example.domain.usecase.base

import com.example.domain.model.Post
import kotlinx.coroutines.flow.Flow
//TODO: improve inheritance
interface BaseGetPostsFlowUseCase {

    suspend operator fun invoke(userId: String): Flow<List<Post>>
}
