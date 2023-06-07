package com.example.typicodebook.vm

import com.example.domain.repository.PostsBaseRepository
import com.example.typicodebook.fake.TestFetchPostsUseCase
import com.example.typicodebook.fake.TestGetPostsFlowUseCase
import com.example.typicodebook.fake.TestToggleFavoriteUseCase
import com.example.typicodebook.model.PostUi
import com.example.typicodebook.state.PostsUiState
import com.google.samples.apps.nowinandroid.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class PostsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var testPostsRepository: PostsBaseRepository

    private lateinit var testGetPostsFlowUseCase: TestGetPostsFlowUseCase
    private lateinit var testFetchPostsUseCase: TestFetchPostsUseCase
    private lateinit var testToggleFavoriteUseCase: TestToggleFavoriteUseCase

    private lateinit var postsViewModel: PostsViewModel

    @Before
    fun setUp() {
        testPostsRepository = mock(PostsBaseRepository::class.java)

        testGetPostsFlowUseCase = TestGetPostsFlowUseCase(testPostsRepository)
        testFetchPostsUseCase = TestFetchPostsUseCase(testPostsRepository)
        testToggleFavoriteUseCase = TestToggleFavoriteUseCase(testPostsRepository)

        postsViewModel = PostsViewModel(
            getPostsFlowUseCase = testGetPostsFlowUseCase,
            fetchPostsUseCase = testFetchPostsUseCase,
            toggleFavoriteUseCase = testToggleFavoriteUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchPosts_startFetching_stateIsFetching() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            postsViewModel.uiState.collect()
        }

        postsViewModel.fetchPosts("10")
        val uiState = postsViewModel.uiState.value

        assertEquals(true, uiState is PostsUiState.Fetching)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toggleFavorite_startFetching_uiStateIsSuccessButNoFetching() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { postsViewModel.uiState.collect() }

        postsViewModel.toggleFavorite("10", true)
        val uiState = postsViewModel.uiState.value

        assertEquals(true, uiState is PostsUiState.Success)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun subscribeToPosts_whenFilteringFav_stateAndCountAndIdAreCorrect() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { postsViewModel.uiState.collect() }

        postsViewModel.filterPosts(true)
        testGetPostsFlowUseCase("0")

        postsViewModel.subscribeToPosts("0")
        val uiState = postsViewModel.uiState.value

        assertEquals(true, uiState is PostsUiState.Success)
        var size = 0
        var element0 = PostUi(0, 0, "", "", false)
        if (uiState is PostsUiState.Success) {
            size = uiState.posts.size
            element0 = uiState.posts[0]
        }
        assertEquals(1, size)
        assertEquals(10, element0.id)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun subscribeToPosts_whenNotFilteringFav_stateAndcountAreCorrect() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { postsViewModel.uiState.collect() }

        postsViewModel.filterPosts(false)
        testGetPostsFlowUseCase("0")

        postsViewModel.subscribeToPosts("0")
        val uiState = postsViewModel.uiState.value

        assertEquals(true, uiState is PostsUiState.Success)
        var size = 0
        if (uiState is PostsUiState.Success) {
            size = uiState.posts.size
        }
        assertEquals(2, size)

        collectJob.cancel()
    }
}