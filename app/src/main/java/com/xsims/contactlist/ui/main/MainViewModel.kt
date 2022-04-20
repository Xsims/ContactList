package com.xsims.contactlist.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.xsims.contactlist.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val mainRepository: MainRepository,
  val imageLoader: ImageLoader,
) : ViewModel() {

  var pageLoaded = 1

  private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
  val isLoading: State<Boolean> get() = _isLoading

  var contactListUi = mainRepository.getContactList()
    .stateIn(viewModelScope, SharingStarted.Lazily, UiState.Loading)

  fun loadMoreContacts() {
    _isLoading.value = true
    viewModelScope.launch {
      mainRepository.loadContacts(pageLoaded,
        onError = { Timber.d(it) }
      )
    }.invokeOnCompletion {
      pageLoaded++
      _isLoading.value = false
    }
  }

  fun deleteAllContact() {
    viewModelScope.launch {
      mainRepository.deleteAllContact()
    }
  }
}