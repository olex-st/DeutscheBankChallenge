package com.example.database

import com.example.database.model.entity.PostEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertPosts(postEntities: List<PostEntity>)

    suspend fun updateFavoritePost(postId: String, favorite: Boolean)

    suspend fun getPost(postId: String): PostEntity

    fun getAllPostsFlow(userId: String): Flow<List<PostEntity>>
}
