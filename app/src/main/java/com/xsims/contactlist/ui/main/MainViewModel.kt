package com.xsims.contactlist.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xsims.contactlist.model.Contact
import com.xsims.contactlist.utils.UiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  mainRepository: MainRepository
) : ViewModel() {

  var contactListUi by mutableStateOf(
    mainRepository.getContacts()
      .distinctUntilChanged()
      .stateIn(viewModelScope, SharingStarted.Lazily, Loading)
  )

  fun groupByFirstCharAndContacts(contacts: List<Contact>) =
    contacts.sortedBy { it.name.first }.groupBy { it.name.first[0] }
}