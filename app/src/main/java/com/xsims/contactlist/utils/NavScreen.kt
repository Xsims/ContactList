package com.xsims.contactlist.utils

sealed class NavScreen(val route: String) {

  object MainScreen : NavScreen("MainScreen")

  object DetailsScreen : NavScreen("DetailsScreen") {

    const val routeWithArgument: String = "DetailsScreen/{email}"

    const val argument0: String = "email"
  }
}