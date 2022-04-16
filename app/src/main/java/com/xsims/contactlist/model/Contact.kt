package com.xsims.contactlist.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
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

  @Entity
  @Immutable
  data class Name (
    @PrimaryKey(autoGenerate = false)
    val userId: Long,
    val title: String,
    val first: String,
    val last: String
  )

  @Entity
  @Immutable
  data class Location (
    @PrimaryKey(autoGenerate = false)
    val userId: Long,
    val street: String,
    val city: String,
    val state: String,
    val postcode: Long
  )

  @Entity
  @Immutable
  data class Login (
    @PrimaryKey(autoGenerate = false)
    val userId: Long,
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
    @PrimaryKey(autoGenerate = false)
    val userId: Long,
    val name: String,
    val value: String
  )

  @Entity
  @Immutable
  data class Picture (
    @PrimaryKey(autoGenerate = false)
    val userId: Long,
    val large: String,
    val medium: String,
    val thumbnail: String
  )
}