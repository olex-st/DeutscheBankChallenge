package com.example.database.model.entity

import androidx.room.*

@Entity(tableName = "post_table")
data class PostEntity(
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
){
    @ColumnInfo
    var favorite: Boolean = false
}