package com.example.firebase1.di

import com.example.firebase1.firebaseRealtimeDb.repository.RealtimeDbRepository
import com.example.firebase1.firebaseRealtimeDb.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRealtimeRepository(
        repo: RealtimeDbRepository
    ): RealtimeRepository
}