package com.xsims.contactlist.db

import com.xsims.contactlist.TestUtils.mockContact
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], manifest = Config.NONE)
class ContactDaoTest : TestDatabase() {

  private lateinit var contactDao: ContactDao

  @Before
  fun init() {
    contactDao = db.contactDao()
  }

  @Test
  fun insertAndLoadContactListTest() = runBlocking {
    val mockDataList = listOf(mockContact())
    contactDao.insertContactList(mockDataList)

    val loadFromDB = contactDao.getContactList().first()
    MatcherAssert.assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))

    val mockData = mockContact()
    MatcherAssert.assertThat(loadFromDB[0].toString(), `is`(mockData.toString()))
  }
}