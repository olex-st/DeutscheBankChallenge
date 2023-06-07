package com.example.typicodebook.vm

import com.example.typicodebook.fake.TestGetCommentsUseCase
import com.example.typicodebook.fake.TestToggleFavoriteUseCase
import com.example.typicodebook.state.CommentsUiState
import com.google.samples.apps.nowinandroid.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var testGetCommentsUseCase: TestGetCommentsUseCase
    private lateinit var testToggleFavoriteUseCase: TestToggleFavoriteUseCase

    private lateinit var commentsViewModel: CommentsViewModel

    @Before
    fun setUp() {
        testToggleFavoriteUseCase = TestToggleFavoriteUseCase()
        testGetCommentsUseCase = TestGetCommentsUseCase()

        commentsViewModel = CommentsViewModel(
            getCommentsUseCase = testGetCommentsUseCase,
            toggleFavoriteUseCase = testToggleFavoriteUseCase
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchComments_sizeOfCommentsListIsValid()= runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { commentsViewModel.uiState.collect() }

        commentsViewModel.fetchComments("10")
        val uiState = commentsViewModel.uiState.value

        assertEquals(true, uiState is CommentsUiState.Success)

        var size = 0
        if (uiState is CommentsUiState.Success) {
            size = uiState.comments.size
        }
        assertEquals(3, size)

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toggleFavorite_startFetching_uiStateIsSuccessButNoFetching() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { commentsViewModel.uiState.collect() }

        commentsViewModel.toggleFavorite("10", true)
        val uiState = commentsViewModel.uiState.value

        assertEquals(true, uiState is CommentsUiState.Success)

        collectJob.cancel()
    }

}