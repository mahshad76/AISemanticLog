package com.example.home.data.di

import com.example.home.data.datasource.remote.DefaultLogRemoteDatasource
import com.example.home.data.datasource.remote.LogRemoteDatasource
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
}