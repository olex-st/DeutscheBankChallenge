package com.example.api

import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse

interface RemoteDataSource {

    suspend fun login(userId: String): UserResponse

    suspend fun getPosts(userId: String): List<PostResponse>

    suspend fun getComments(postId: String): List<CommentResponse>

}
