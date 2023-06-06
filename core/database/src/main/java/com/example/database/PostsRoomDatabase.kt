package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.model.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
internal abstract class PostsRoomDatabase : RoomDatabase() {

    abstract val postsLocalDataSource: PostsLocalDataSource

    companion object {

        @Volatile
        private var INSTANCE: PostsRoomDatabase? = null

        fun getInstance(context: Context): PostsRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.inMemoryDatabaseBuilder(
                        context.applicationContext,
                        PostsRoomDatabase::class.java,
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}