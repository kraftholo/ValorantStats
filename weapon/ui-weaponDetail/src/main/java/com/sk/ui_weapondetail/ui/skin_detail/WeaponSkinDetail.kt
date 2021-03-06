package com.sk.ui_weapondetail.ui.skin_detail

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.sk.ui_weapondetail.ui.composable.ChromaDisplayForSkinDetail
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.LevelItemType

@ExperimentalAnimationApi
@Composable
fun WeaponSkinDetail(
    state: WeaponSkinDetailState,
    imageLoader: ImageLoader,
    onBack : ()-> Unit
) {

    //Exoplayer
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.

        lifecycle
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


    //Recomposition (video changes) on the basis of "Levels" (VFX/Animation etc) and Chromas
    val currentlyPlayingLevel = remember { mutableStateOf(state.levels?.let { it[0] }) }
    val currentlyPlayingChroma = remember{ mutableStateOf(state.skin?.let { it.chromas[0]})}

    //Autoplay 1st Level video
    updateCurrentlyPlayingLevel(exoPlayer = exoPlayer, level = currentlyPlayingLevel.value)

    //Play all chroma videos;
    // For base chroma -> Play the base level video
    state.skin?.let { skin ->
        if (skin.chromas[0] != currentlyPlayingChroma.value) {
            updateCurrentlyPlayingChroma(
                exoPlayer = exoPlayer,
                chroma = currentlyPlayingChroma.value
            )
        } else {
            state.levels?.let { updateCurrentlyPlayingLevel(exoPlayer = exoPlayer, level = it[0]) }
        }

    }

    Column{
        TopAppBar(
            title = { Text(text = "Skin Details") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Rounded.ArrowBack, "")
                }
            }
        )
        state.skin?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 8.dp),
                text = it.displayName,
                style = MaterialTheme.typography.h1
            )

            ChromaDisplayForSkinDetail(chromaList = it.chromas, imageLoader = imageLoader) { idx ->
                currentlyPlayingChroma.value = state.skin.chromas[idx]
            }
        }

        //Actual Exoplayer in AndroidView
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(4.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Box(
                    modifier = Modifier.defaultMinSize(minHeight = 200.dp)
                ) {
                    AndroidView(
                        factory = { context ->
                            PlayerView(context).apply {
                                player = exoPlayer
                            }
                        }
                    )
                }
            }

            //Composable to show list of levels
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                state.levels?.forEach { level ->
                    when (level.levelItemType) {
                        is LevelItemType.VFX -> {
                            Button(onClick = {
                                //check should always pass
                                currentlyPlayingLevel.value = level
                            }
                            ) {
                                Text(text = LevelItemType.VFX.uiValue)
                            }
                        }
                        is LevelItemType.Animation -> {
                            Button(onClick = {
                                //check should always pass
                                currentlyPlayingLevel.value = level
                            }) {
                                Text(text = LevelItemType.Animation.uiValue)
                            }

                        }
                        is LevelItemType.Finisher -> {
                            Button(onClick = {
                                //check should always pass
                                currentlyPlayingLevel.value = level
                            }) {
                                Text(text = LevelItemType.Finisher.uiValue)
                            }

                        }
                        is LevelItemType.KillCounter -> {
                            Button(onClick = {
                                //check should always pass
                                currentlyPlayingLevel.value = level
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
        }
    }
}

@Composable
private fun updateCurrentlyPlayingChroma(exoPlayer: SimpleExoPlayer, chroma:Chroma?){

    if (chroma?.streamedVideo == null) return

    val context = LocalContext.current
    LaunchedEffect(chroma) {
        exoPlayer.apply {
            chroma?.let {
                val dataSourceFactory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(chroma.streamedVideo)))

                setMediaSource(source)
                prepare()
                playWhenReady = true
            } ?: stop()
        }
    }

}


@Composable
private fun updateCurrentlyPlayingLevel(exoPlayer: SimpleExoPlayer, level: Level?) {
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

