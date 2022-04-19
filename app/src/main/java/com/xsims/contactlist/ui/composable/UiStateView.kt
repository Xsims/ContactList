package com.xsims.contactlist.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.xsims.contactlist.utils.UiState
import com.xsims.contactlist.utils.UiState.Empty
import com.xsims.contactlist.utils.UiState.Error
import com.xsims.contactlist.utils.UiState.Loading
import com.xsims.contactlist.utils.UiState.Success
import kotlinx.coroutines.flow.StateFlow

@Suppress("UnnecessaryVariable")
@Composable
fun <T> UiStateView(
  uiStateData: StateFlow<UiState< out T>>,
  errorView: @Composable ((String) -> Unit) = { ErrorView(it) },
  loadingView: @Composable (() -> Unit) = { LoadingView() },
  emptyView: @Composable (() -> Unit) = { EmptyView() },
  content: @Composable ((T) -> Unit),
) {
  val uiState by uiStateData.collectAsState()

  when(val sealedUiState = uiState) {
    is Loading -> loadingView()
    is Empty -> emptyView()
    is Error -> errorView(sealedUiState.error)
    is Success -> content(sealedUiState.data)
  }
}