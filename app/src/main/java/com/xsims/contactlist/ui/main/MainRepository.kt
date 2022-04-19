package com.xsims.contactlist.ui.main

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.xsims.contactlist.api.ContactService
import com.xsims.contactlist.db.ContactDao
import com.xsims.contactlist.ui.main.MainViewModel.ContactListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
  private val contactService: ContactService,
  private val contactDao: ContactDao
) {

  init {
    Timber.d("Injection MainRepository")
  }

  private suspend inline fun FlowCollector<ContactListUiState>.getContactListFromDatabaseOrEmitError(
    message: String,
  ) {
    val contactList = contactDao.getContactList()
    this.emit(
      if (contactList.isEmpty()) ContactListUiState.Error(message)
      else ContactListUiState.Success(contactList)
    )
  }

  private suspend inline fun FlowCollector<ContactListUiState>.getContactListFromDatabaseOrEmitEmpty() {
    val contactList = contactDao.getContactList()
    this.emit(
      if (contactList.isEmpty()) ContactListUiState.Empty
      else ContactListUiState.Success(contactList)
    )
  }

  @WorkerThread
  fun getContacts(): Flow<ContactListUiState> = flow {
    contactService.fetchContactList()
      .suspendOnSuccess {
        if (data.results.isEmpty()) {
          getContactListFromDatabaseOrEmitEmpty()
        } else {
          contactDao.insertContactList(data.results)
          emit(ContactListUiState.Success(data.results))
        }
      }
      .suspendOnError {
        getContactListFromDatabaseOrEmitError(message())
      }
      .suspendOnException {
        getContactListFromDatabaseOrEmitError(message())
      }
  }.onStart { ContactListUiState.Loading }.flowOn(Dispatchers.IO)
}