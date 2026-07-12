package com.example.home.presentation

import app.cash.turbine.test
import com.example.common.stringresolver.StringResolver
import com.example.home.domain.model.Log
import com.example.home.domain.usecase.FetchLogUseCase
import com.example.unit.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchLogUseCase = mockk<FetchLogUseCase>()
    private val stringResolver = mockk<StringResolver>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            fetchLogUseCase = fetchLogUseCase,
            stringResolver = stringResolver,
            defaultDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `homeViewModel initial state is idle`() = runTest {
        assertThat(HomeUiState.Idle).isEqualTo(viewModel.homeUiState.value)
    }

    @Test
    fun `homeViewModel onGetLogs shows loading and success states`() = runTest {
        coEvery { fetchLogUseCase() } coAnswers {
            delay(1L)
            Result.success(Log.DEFAULT)
        }

        viewModel.onEvent(HomeEvent.GetLogs)

        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `homeViewModel onGetLogs shows loading and error states`() = runTest {
        coEvery { fetchLogUseCase() } coAnswers {
            delay(1L)
            Result.failure<Log>(Exception(""))
        }

        viewModel.onEvent(HomeEvent.GetLogs)

        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}