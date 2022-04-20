package com.xsims.contactlist.ui.details

import androidx.annotation.WorkerThread
import com.xsims.contactlist.db.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepository @Inject constructor(
  private val contactDao: ContactDao
) {

  @WorkerThread
  fun getContactByEmail(email: String) = flow {
    val contact = contactDao.getContact(email)
    emit(contact)
  }.flowOn(Dispatchers.IO)
}