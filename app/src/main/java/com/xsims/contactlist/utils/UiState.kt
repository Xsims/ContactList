package com.xsims.contactlist.utils

sealed class UiState<T> {
  object Loading : UiState<Nothing>()
  object Empty : UiState<Nothing>()
  class Error(val error: String) : UiState<Nothing>()
  class Success<T>(val data: T) : UiState<T>()
}