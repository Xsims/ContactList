package com.xsims.contactlist.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xsims.contactlist.model.Contact
import com.xsims.contactlist.ui.composable.UiStateView
import com.xsims.contactlist.ui.theme.ContactListTheme
import dagger.hilt.android.AndroidEntryPoint

typealias FirstCharAndContacts = Map<Char, List<Contact>>

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
  UiStateView(uiStateData = vm.contactListUi) {
    ContactList(vm.groupByFirstCharAndContacts(it))
  }
}

@Composable
fun ContactList(firstCharAndContacts: FirstCharAndContacts){
  LazyColumn {
    firstCharAndContacts.forEach { (initial, contactsForInitial) ->
      stickyHeader {
        CharacterHeader(initial)
      }

      items(contactsForInitial) { contact ->
        ContactListItem(contact)
      }
    }
  }
}

@Composable
fun CharacterHeader(initial: Char) {
  Box(
    modifier = Modifier
      .background(Color.LightGray)
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Text(initial.toString(), modifier = Modifier.padding(start = 16.dp))
  }
}

@Composable
fun ContactListItem(contact: Contact) {
  Box(
    modifier = Modifier
      .background(Color.White)
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Text(contact.fullNameWithTitle,
      color = Color.Black,
      modifier = Modifier.padding(start = 16.dp))
  }
}