package com.xsims.contactlist.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.xsims.contactlist.extension.OnBottomReached
import com.xsims.contactlist.model.Contact
import com.xsims.contactlist.ui.composable.UiStateView
import com.xsims.contactlist.ui.details.DetailsScreen
import com.xsims.contactlist.ui.theme.ContactListTheme
import com.xsims.contactlist.utils.NavScreen
import com.xsims.contactlist.utils.NavScreen.DetailsScreen
import com.xsims.contactlist.utils.UiState.Empty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ContactListTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          MainScreen(vm = hiltViewModel())
        }
      }
    }
  }
}

@Composable
fun MainScreen(vm: MainViewModel) {
  val isLoading by vm.isLoading
  Timber.d("data is loading : $isLoading")
  val data by vm.contactListUi.collectAsState()
  val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.MainScreen.route) {
      composable(NavScreen.MainScreen.route) {
        if (data is Empty)
          vm.loadMoreContacts()
        else
          UiStateView(uiStateData = vm.contactListUi) {
            ContactList(
              contacts = it,
              vm = vm,
              onClick = { email ->
                navController.navigate("${DetailsScreen.route}/$email")
              }
            )
          }
      }
      composable(
        route = DetailsScreen.routeWithArgument,
        arguments = listOf(
          navArgument(DetailsScreen.argument0) { type = NavType.StringType }
        )
      ) { backStackEntry ->
        val email =
          backStackEntry.arguments?.getString(DetailsScreen.argument0) ?: return@composable

        DetailsScreen(email = email, viewModel = hiltViewModel()) {
          navController.navigateUp()
        }
      }
    }
}

@Composable
fun ContactList(contacts: List<Contact>, vm: MainViewModel,
  onClick: (String) -> Unit) {
  val listState = rememberLazyListState()
  val isLoading by vm.isLoading

  Column {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(40.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = "${contacts.size} contacts")
      Button(onClick = { vm.deleteAllContact() }) {
        Text("DELETE DATA")
      }
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
    ) {

      LazyColumn(
        modifier = Modifier.weight(1f), state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(10.dp)
      ) {
        items(contacts) { contact ->
          ContactListItem(contact, onClick = {
            onClick(it)
          })
        }
      }
      AnimatedVisibility(visible = isLoading) {
        Timber.d("Animated visibility triggered")
        Box(
          modifier = Modifier
            .height(90.dp)
            .background(Color.White)
            .fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator(
            modifier = Modifier.padding(16.dp)
          )
        }
      }

      listState.OnBottomReached {
        vm.loadMoreContacts()
      }
    }
  }
}

@Composable
fun ContactListItem(contact: Contact,
  onClick: (String) -> Unit) {
  Card(modifier = Modifier.clickable {
    onClick(contact.email)
  }) {
    Row(
      modifier = Modifier
        .background(Color.White)
        .padding(6.dp)
        .fillMaxWidth()
    ) {
      AsyncImage(
        modifier = Modifier
          .size(60.dp)
          .clip(CircleShape),
        model = contact.picture.thumbnail,
        contentDescription = null,
        contentScale = ContentScale.Crop,
      )
      Column {
        Text(
          contact.fullNameWithTitle,
          color = Color.Black,
          modifier = Modifier.padding(start = 16.dp)
        )
        Text(
          contact.email,
          color = Color.Black,
          modifier = Modifier.padding(start = 16.dp)
        )
      }
    }
  }
}