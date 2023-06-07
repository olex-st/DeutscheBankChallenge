package com.example.repository

import com.example.api.RemoteDataSource
import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse
import com.example.database.LocalDataSource
import com.example.database.model.entity.PostEntity
import com.example.repository.fake.TestLocalDataSource
import com.example.repository.fake.TestRemoteDataSource
import com.example.repository.mapper.Mapper
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class PostsRepositoryTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: PostsRepository
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSourceMock: RemoteDataSource

    @Mock
    private lateinit var localDataSourceMock: LocalDataSource

    @Mock
    private lateinit var mapperMock: Mapper

    @Before
    fun setUp() {
        mapperMock = Mockito.mock(Mapper::class.java)
        localDataSourceMock = Mockito.mock(LocalDataSource::class.java)
        remoteDataSourceMock = Mockito.mock(RemoteDataSource::class.java)
    }

    @Test
    fun login_loginWithExistingUser_mapperCalled() = runTest{
        remoteDataSource = TestRemoteDataSource()
        repository = PostsRepository(remoteDataSource, mapperMock, localDataSourceMock)

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
        val userId = "1"

        repository.login(userId)

        verify(mapperMock, times(1))
            .mapUserResponseToUser(userResponse)
    }

    @Test
    fun toggleFavorite_togglingPost_dataSourceMethodCalled() = runTest {
        repository = PostsRepository(remoteDataSourceMock, mapperMock, localDataSourceMock)
        val postId = "0"
        val isFavorite = false

        repository.toggleFavorite(postId, isFavorite)

        verify(localDataSourceMock, times(1))
            .updateFavoritePost(postId, isFavorite)
    }

    @Test
    fun fetchPosts_GettingTwoPosts_mapperCalledTwice()= runTest() {
        remoteDataSource = TestRemoteDataSource()
        repository = PostsRepository(remoteDataSource, mapperMock, localDataSourceMock)

        val userId = "0"
        val postResponse = PostResponse(1, 2, "", "")

        repository.fetchPosts(userId)

        verify(mapperMock, times(2))
            .mapPostResponseToPostEntity(postResponse)
    }

    @Test
    fun getComments_gettingListOfComments_mapperCalledAsMuchTimesAsListSize() = runTest{
        remoteDataSource = TestRemoteDataSource()
        repository = PostsRepository(remoteDataSource, mapperMock, localDataSourceMock)

        val postId = "0"
        val commentResponse = CommentResponse(1, 2, "", "", "")

        repository.getComments(postId)

        verify(mapperMock, times(2))
            .mapCommentResponseToComment(commentResponse )
    }

    @Test
    fun getPost_GettingOnePost_mapperCalledOnce() = runTest {
        localDataSource = TestLocalDataSource()
        repository = PostsRepository(remoteDataSourceMock, mapperMock, localDataSource)

        val postId = "0"
        val postEntity = PostEntity(0, 0, "", "")

        repository.getPost(postId)

        verify(mapperMock, times(1))
            .mapPostEntityToPost(postEntity)
    }
}