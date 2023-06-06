package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsLocalDataSource : LocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertPosts(postEntities: List<PostEntity>)

    @Query("UPDATE post_table SET favorite = :favorite WHERE id = :postId")
    override suspend fun updateFavoritePost(postId: String, favorite: Boolean)

    @Query("SELECT * FROM post_table WHERE id = :postId")
    override suspend fun getPost(postId: String):PostEntity

    @Query("SELECT * FROM post_table WHERE user_id = :userId ORDER BY id ASC")
    override fun getAllPostsFlow(userId: String): Flow<List<PostEntity>>

}