package com.example.domain.repository

import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PostsBaseRepository {

    suspend fun login(userId: String): User

    suspend fun toggleFavorite(postId: String, isFavorite: Boolean)

    suspend fun getPostsFlow(userId: String): Flow<List<Post>>

    suspend fun fetchPosts(userId: String)

    suspend fun getComments(postId: String): List<Comment>

    suspend fun getPost(postId: String): Post

}