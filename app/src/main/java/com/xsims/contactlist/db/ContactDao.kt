package com.xsims.contactlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsims.contactlist.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertContactList(posters: List<Contact>)

  @Query("SELECT * FROM Contact WHERE email = :email")
  suspend fun getContact(email: String): Contact?

  @Query("SELECT * FROM Contact")
  fun getContactList(): Flow<List<Contact>>

  @Query("DELETE FROM Contact ")
  suspend fun deleteAllContact()
}