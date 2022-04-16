package com.xsims.contactlist.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xsims.contactlist.model.Contact

class ContactTypeConverters {
  private val gson = Gson()

  @TypeConverter
  fun nameToString(name: Contact.Name): String = gson.toJson(name)

  @TypeConverter
  fun stringToName(string: String): Contact.Name = gson.fromJson(string, Contact.Name::class.java)

  @TypeConverter
  fun locationToString(location: Contact.Location): String = gson.toJson(location)

  @TypeConverter
  fun stringToLocation(string: String): Contact.Location = gson.fromJson(string, Contact.Location::class.java)

  @TypeConverter
  fun loginToString(login: Contact.Login): String = gson.toJson(login)

  @TypeConverter
  fun stringToLogin(string: String): Contact.Login = gson.fromJson(string, Contact.Login::class.java)

  @TypeConverter
  fun idToString(id: Contact.ID): String = Gson().toJson(id)

  @TypeConverter
  fun stringToId(string: String): Contact.ID = Gson().fromJson(string, Contact.ID::class.java)

  @TypeConverter
  fun pictureToString(picture: Contact.Picture): String = gson.toJson(picture)

  @TypeConverter
  fun stringToPicture(string: String): Contact.Picture = gson.fromJson(string, Contact.Picture::class.java)
}
