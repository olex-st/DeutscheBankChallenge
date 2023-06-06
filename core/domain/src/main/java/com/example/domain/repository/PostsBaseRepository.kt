package com.example.domain.repository

import com.example.domain.model.Post
import com.example.domain.model.Text
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PostsBaseRepository {

    suspend fun login(userId: String): User

    suspend fun toggleFavorite(postId: String, isFavorite: Boolean)

    suspend fun getPostsFlow(userId: String, favoritesOnly: Boolean): Flow<List<Post>>

    suspend fun fetchPosts(userId: String)

    suspend fun getComments(postId: String): List<Text>

}