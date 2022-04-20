package com.xsims.contactlist.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xsims.contactlist.model.Contact

@Composable
fun DetailsScreen(
  email: String,
  viewModel: DetailViewModel,
  pressOnBack: () -> Unit = {}
) {
  LaunchedEffect(key1 = email) {
    viewModel.loadContactByEmail(email)
  }

  val details: Contact? by viewModel.contactDetailsFlow.collectAsState(initial = null)
  details?.let { contact ->
    ContactDetailsBody(contact, pressOnBack)
  }
}

@Composable
private fun ContactDetailsBody(
  contact: Contact,
  pressOnBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .background(MaterialTheme.colors.background)
      .fillMaxHeight()
  ) {
    Box(contentAlignment = Alignment.TopStart) {
      AsyncImage(
        modifier = Modifier
          .height(250.dp)
          .fillMaxWidth(),
        model = contact.picture.large,
        contentDescription = null,
        contentScale = ContentScale.FillWidth
      )
      Icon(
        imageVector = Filled.ArrowBack,
        tint = Color.White,
        contentDescription = null,
        modifier = Modifier
          .padding(12.dp)
          .clickable(onClick = { pressOnBack() })
      )
    }
    ItemInfoDetails(info = contact.name.first)
    ItemInfoDetails(info = contact.email)
    ItemInfoDetails(info = contact.cell)
    ItemInfoDetails(info = contact.gender)
    ItemInfoDetails(info = contact.nat)
    ItemInfoDetails(info = contact.location.city)
    ItemInfoDetails(info = contact.location.postcode)
    ItemInfoDetails(info = contact.location.state)
    ItemInfoDetails(info = contact.location.street)
  }
}

@Composable
fun ItemInfoDetails(info: String){
  Text(info)
}