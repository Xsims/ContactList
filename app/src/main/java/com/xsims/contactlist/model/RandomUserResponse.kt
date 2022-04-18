package com.xsims.contactlist.model

import javax.annotation.concurrent.Immutable

@Immutable
data class RandomUserResponse(
  val results: List<Contact> = emptyList(),
  val info: Info
) {
  data class Info (
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
  )
}