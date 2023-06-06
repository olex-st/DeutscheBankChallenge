package com.example.database.model.entity

import androidx.room.*

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
){
    @ColumnInfo
    var favorite: Boolean = false
}