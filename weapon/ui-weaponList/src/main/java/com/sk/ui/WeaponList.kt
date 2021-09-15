package com.sk.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sk.components.WeaponListItem
import com.sk.core.domain.ProgressBarState

@Composable
fun WeaponList(
    state : WeaponListState
)
{
    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            items(state.weapons) {
                WeaponListItem(it,{})
            }
        }

        if (state.progressBarState == ProgressBarState.Loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }

}