package com.xsims.contactlist.ui.main

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.xsims.contactlist.api.ContactService
import com.xsims.contactlist.db.ContactDao
import com.xsims.contactlist.model.Contact
import com.xsims.contactlist.utils.UiState
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

  private suspend inline fun FlowCollector<UiState<out List<Contact>>>.emitContactsFromDatabaseOrEmit(
    contactListUiState: UiState<out List<Contact>>,
  ) {
    val contactList = contactDao.getContactList()
    this.emit(
      if (contactList.isEmpty()) contactListUiState
      else UiState.Success(contactList))
  }

  @WorkerThread
  fun getContacts(): Flow<UiState<out List<Contact>>> = flow {
    contactService.fetchContactList()
      .suspendOnSuccess {
        if (data.results.isEmpty()) {
          emitContactsFromDatabaseOrEmit(UiState.Empty)
        } else {
          contactDao.insertContactList(data.results)
          emit(UiState.Success(data.results))
        }
      }
      .suspendOnError {
        emitContactsFromDatabaseOrEmit(UiState.Error(message()))
      }
      .suspendOnException {
        emitContactsFromDatabaseOrEmit(UiState.Error(message()))
      }
  }.onStart { UiState.Loading }.flowOn(Dispatchers.IO)
}