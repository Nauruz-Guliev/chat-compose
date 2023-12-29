package ru.kpfu.itis.image_picker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.image_picker.data.repository.ImageRepositoryImpl
import ru.kpfu.itis.image_picker.domain.repository.ImageRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindImagePickerRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}