package com.sk.ui_weapondetail.ui.skin_detail

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sk.ui_weapondetail.ui.composable.ChromaDisplay
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.LevelItemType

@Composable
fun WeaponSkinDetail(
    state: WeaponSkinDetailState,
    imageLoader: ImageLoader
) {

    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.playWhenReady = false
                }
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.playWhenReady = true
                }
                Lifecycle.Event.ON_DESTROY -> {
                    exoPlayer.run {
                        stop()
                        release()
                    }
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val currentlyPlayingItem = remember { mutableStateOf(state.levels?.let { it[0] }) }
    updateCurrentlyPlayingItem(exoPlayer = exoPlayer, level = currentlyPlayingItem.value)

    Column {
        state.skin?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 8.dp),
                text = it.displayName,
                style = MaterialTheme.typography.h1
            )
            ChromaDisplay(chromaList = it.chromas, imageLoader = imageLoader, true)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            state.levels?.forEach { level ->
                when (level.levelItemType) {
                    is LevelItemType.VFX -> {
                        Button(onClick = {
                            //check should always pass
                            currentlyPlayingItem.value = level
                        }
                        ) {
                            Text(text = LevelItemType.VFX.uiValue)
                        }
                    }
                    is LevelItemType.Animation -> {
                        Button(onClick = {
                            //check should always pass
                            currentlyPlayingItem.value = level
                        }) {
                            Text(text = LevelItemType.Animation.uiValue)
                        }

                    }
                    is LevelItemType.Finisher -> {
                        Button(onClick = {
                            //check should always pass
                            currentlyPlayingItem.value = level
                        }) {
                            Text(text = LevelItemType.Finisher.uiValue)
                        }

                    }
                    is LevelItemType.KillCounter -> {
                        Button(onClick = {
                            //check should always pass
                            currentlyPlayingItem.value = level
                        }) {
                            Text(text = LevelItemType.KillCounter.uiValue)
                        }

                    }

                    else -> {
                        //Do nothing
                    }
                }
            }
        }

        Box {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                }
            )
        }
    }
}


@Composable
private fun updateCurrentlyPlayingItem(exoPlayer: SimpleExoPlayer, level: Level?) {
    val context = LocalContext.current

    LaunchedEffect(level) {
        exoPlayer.apply {
            level?.let {
                val dataSourceFactory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(level.streamedVideo)))

                setMediaSource(source)
                prepare()
                playWhenReady = true
            } ?: stop()
        }
    }


}

