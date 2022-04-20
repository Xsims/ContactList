package com.xsims.contactlist.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val detailsRepository: DetailsRepository
) : ViewModel() {

  private val contactEmailSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)

  val contactDetailsFlow = contactEmailSharedFlow.flatMapLatest {
    detailsRepository.getContactByEmail(it)
  }

  init {
    Timber.d("init DetailViewModel")
  }

  fun loadContactByEmail(email: String) = contactEmailSharedFlow.tryEmit(email)
}