package com.xsims.contactlist.di

import com.xsims.contactlist.api.ContactService
import com.xsims.contactlist.db.ContactDao
import com.xsims.contactlist.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
    contactService: ContactService,
    contactDao: ContactDao
  ): MainRepository {
    return MainRepository(contactService, contactDao)
  }
}
