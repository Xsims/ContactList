package com.xsims.contactlist.di

import android.app.Application
import androidx.room.Room
import com.xsims.contactlist.R
import com.xsims.contactlist.db.ContactDao
import com.xsims.contactlist.db.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideLocalDatabase(application: Application): LocalDatabase {
    return Room
      .databaseBuilder(
        application,
        LocalDatabase::class.java,
        application.getString(R.string.database)
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  @Singleton
  fun providePosterDao(localDatabase: LocalDatabase): ContactDao {
    return localDatabase.contactDao()
  }
}
