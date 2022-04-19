package com.xsims.contactlist.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Contact (
  @PrimaryKey(autoGenerate = false)
  val email: String,
  val gender: String,
  val registered: Long,
  val dob: Long,
  val phone: String,
  val cell: String,
  val nat: String,

  val name: Name,
  val location: Location,
  val login: Login,
  val id: ID,
  val picture: Picture,
) {

  @Ignore
  val fullNameWithTitle = name.title + ". " + name.first + " " + name.last

  @Entity
  @Immutable
  class Name (
    title: String,
    first: String,
    last: String
  ) {
    val title: String = title
      get() = field.replaceFirstChar(Char::titlecase)
    val first: String = first
      get() = field.replaceFirstChar(Char::titlecase)
    val last: String = last
      get() = field.uppercase()

    override fun toString(): String {
      return "Name(title=$title, first=$first, last=$last)"
    }
  }

  @Entity
  @Immutable
  data class Location (
    val street: String,
    val city: String,
    val state: String,
    val postcode: Long
  )

  @Entity
  @Immutable
  data class Login (
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
  )

  @Entity
  @Immutable
  data class ID (
    val name: String,
    val value: String
  )

  @Entity
  @Immutable
  data class Picture (
    val large: String,
    val medium: String,
    val thumbnail: String
  )
}