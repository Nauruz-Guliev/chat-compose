package ru.kpfu.itis.imagepicker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.imagepicker.data.repository.ImageRepositoryImpl
import ru.kpfu.itis.imagepicker.domain.repository.ImageRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindImagePickerRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}
