package com.xsims.contactlist.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.xsims.contactlist.api.ContactService
import com.xsims.contactlist.api.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideImageLoader(
    @ApplicationContext context: Context
  ): ImageLoader {
    return ImageLoader.Builder(context)
      .diskCache {
        DiskCache.Builder()
          .directory(context.cacheDir.resolve("image_cache"))
          .build()
      }
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(ContactService.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideContactService(retrofit: Retrofit): ContactService {
    return retrofit.create(ContactService::class.java)
  }
}