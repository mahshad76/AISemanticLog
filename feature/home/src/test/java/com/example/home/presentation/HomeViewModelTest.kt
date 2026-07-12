package com.example.home.presentation

import app.cash.turbine.test
import com.example.common.stringresolver.StringResolver
import com.example.home.domain.model.Log
import com.example.home.domain.model.LogEntry
import com.example.home.domain.model.LogGroup
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

    @Test
    fun `homeViewModel onConsumeErrorMessage shows error state with null message`() = runTest {
        viewModel.onEvent(HomeEvent.ConsumeErrorMessage)

        assertThat(HomeUiState.Error(null)).isEqualTo(viewModel.homeUiState.value)
    }

    @Test
    fun `homeViewModel onGetLogs should not call api again when home ui state is already success`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(logs = null)

            viewModel.onEvent(HomeEvent.GetLogs)

            viewModel.homeUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(HomeUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(HomeUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel onGetLogs should call api again when home ui state is already error`() =
        runTest {
            viewModel.setHomeUiStateToError("")
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
    fun `homeViewModel on search a query which is in list should return filtered logs`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(
                listOf(
                    LogGroup(
                        timestamp = "timestamp",
                        entries = listOf(LogEntry.DEFAULT)
                    )
                )
            )

            viewModel.onEvent(HomeEvent.Search("message"))

            viewModel.filteredLogs.test {
                assertThat(awaitItem()).isEmpty()
                val filteredItem = awaitItem()
                assertThat(filteredItem).hasSize(1)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel on search a query which is not in list should return empty list`() =
        runTest {
            viewModel.setHomeUiStateToSuccess(
                listOf(
                    LogGroup(
                        timestamp = "timestamp",
                        entries = listOf(LogEntry.DEFAULT)
                    )
                )
            )

            viewModel.onEvent(HomeEvent.Search("x"))

            viewModel.filteredLogs.test {
                val item = awaitItem()
                assertThat(item).hasSize(0)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `homeViewModel on search a query when api is failed should return empty list`() =
        runTest {
            viewModel.setHomeUiStateToError("")

            viewModel.onEvent(HomeEvent.Search("x"))

            viewModel.filteredLogs.test {
                val item = awaitItem()
                assertThat(item).hasSize(0)

                cancelAndIgnoreRemainingEvents()
            }
        }
}