package com.xsims.contactlist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xsims.contactlist.model.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = true)
@TypeConverters(value = [ContactTypeConverters::class])
abstract class LocalDatabase : RoomDatabase() {

  abstract fun contactDao(): ContactDao
}