package com.example.home.data.di

import com.example.home.data.datasource.remote.DefaultLogRemoteDatasource
import com.example.home.data.datasource.remote.LogRemoteDatasource
import com.example.home.data.repository.DefaultLogRepository
import com.example.home.domain.repository.LogRepository
import com.example.home.domain.usecase.DefaultFetchLogUseCase
import com.example.home.domain.usecase.FetchLogUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Response
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    internal abstract fun bindLogRemoteDatasource(
        defaultLogRemoteDatasource:
        DefaultLogRemoteDatasource
    ): LogRemoteDatasource

    @Binds
    internal abstract fun bindLogRepository(
        defaultLogRepository: DefaultLogRepository
    ): LogRepository

    @Binds
    internal abstract fun bindFetchLogUseCase(defaultFetchLogUseCase: DefaultFetchLogUseCase):
            FetchLogUseCase
}