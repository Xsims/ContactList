package com.xsims.contactlist.ui.main

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.xsims.contactlist.api.ContactService
import com.xsims.contactlist.db.ContactDao
import com.xsims.contactlist.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
  private val contactService: ContactService,
  private val contactDao: ContactDao
) {

  init {
    Timber.d("Injection MainRepository")
  }

  fun getContactList() =
    contactDao.getContactList()
      .map {
        Timber.d("Get ${it.size} contacts from database")
        if (it.isEmpty()) UiState.Empty else UiState.Success(it)
      }
      .catch { UiState.Error(it.message.toString()) }

  @WorkerThread
  suspend fun loadContacts(
    page: Int,
    onError: (String) -> Unit
  ) {
    contactService.fetchContactList(page = page)
      .onSuccess {
        CoroutineScope(Dispatchers.IO).launch {
          contactDao.insertContactList(data.results)
        }
        Timber.d("load ${data.results.size} contacts")
      }
      .onError {
        onError(message())
      }
      .onException {
        onError(message())
      }
  }

  suspend fun deleteAllContact() {
    contactDao.deleteAllContact()
  }
}