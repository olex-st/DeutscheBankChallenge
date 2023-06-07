package com.example.repository.mapper

import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse
import com.example.database.model.entity.PostEntity
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User
import org.junit.Assert.*

import org.junit.Test

class MapperTest {

    lateinit var mapper: Mapper

    @Test
    fun mapUserResponseToUser_correctUserResponse_returnsUser() {
        mapper = Mapper()
        val company = UserResponse.Company(
            "Yost and Sons",
            "Switchable contextually-based project",
            "aggregate real-time technologies"
        )
        val geo = UserResponse.Address.Geo("24.6463", "-168.8889")
        val address = UserResponse.Address(
            "Dayna Park",
            "Suite 449",
            "Bartholomebury",
            "76495-3109",
            geo
        )
        val userResponse = UserResponse(
            9,
            "Glenna Reichert",
            "Delphine",
            "Chaim_McDermott@dana.io",
            address,
            "(775)976-6794 x41206",
            "conrad.com",
            company
        )
        val expectedUser = User(9)

        val actualUser = mapper.mapUserResponseToUser(userResponse)

        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun mapPostResponseToPost_correctPostResponse_returnsPost() {
        // GIVEN
        mapper = Mapper()
        val postResponse = PostResponse(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        val expectedPost = Post(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        // WHEN
        val actualPost = mapper.mapPostResponseToPost(postResponse)
        // THEN
        assertEquals(expectedPost, actualPost)
    }

    @Test
    fun mapCommentResponseToComment_correctCommentResponse_returnsComment() {
        // GIVEN
        mapper = Mapper()
        val commentResponse = CommentResponse(
            1,
            2,
            "quo vero reiciendis velit similique earum",
            "Jayne_Kuhic@sydney.com",
            "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
        )
        val expectedComment = Comment(
            1,
            2,
            "quo vero reiciendis velit similique earum",
            "Jayne_Kuhic@sydney.com",
            "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
        )

        // WHEN
        val actualComment = mapper.mapCommentResponseToComment(commentResponse)
        // THEN
        assertEquals(expectedComment, actualComment)
    }

    @Test
    fun mapPostResponseToPostEntity_correctPostResponse_returnsPostEntity() {
        // GIVEN
        mapper = Mapper()
        val postResponse = PostResponse(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        val expectedPostEntity = PostEntity(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        // WHEN
        val actualPostEntity = mapper.mapPostResponseToPostEntity(postResponse)
        // THEN
        assertEquals(expectedPostEntity, actualPostEntity)
    }

    @Test
    fun mapPostEntityToPost_correctPostEntity_returnsPost() {
        // GIVEN
        mapper = Mapper()
        val postEntity = PostEntity(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        val expectedPost = Post(
            1,
            2,
            "qui est esse",
            "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
        )
        // WHEN
        val actualPost = mapper.mapPostEntityToPost(postEntity)
        // THEN
        assertEquals(expectedPost, actualPost)
    }
}