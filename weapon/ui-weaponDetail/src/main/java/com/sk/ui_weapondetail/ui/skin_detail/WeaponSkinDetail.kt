package com.sk.ui_weapondetail.ui.skin_detail

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.google.android.exoplayer2.Player
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

    val streamedVideo = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val exoPLayer = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)

    Column {
        state.skin?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 8.dp),
                text = it.displayName,
                style = MaterialTheme.typography.h1
            )
            ChromaDisplay(chromaList = it.chromas, imageLoader = imageLoader)
        }

        Row(
            modifier = Modifier.fillMaxWidth()
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
                            level.streamedVideo?.let {
                                streamedVideo.value = it
                            }
                        }) {
                            Text(text = LevelItemType.VFX.uiValue)
                        }
                    }
                    is LevelItemType.Animation -> {
                        Button(onClick = {
                            //check should always pass
                            level.streamedVideo?.let {
                                streamedVideo.value = it
                            }
                        }) {
                            Text(text = LevelItemType.Animation.uiValue)
                        }

                    }
                    is LevelItemType.Finisher -> {
                        Button(onClick = {
                            //check should always pass
                            level.streamedVideo?.let {
                                streamedVideo.value = it
                            }
                        }) {
                            Text(text = LevelItemType.Finisher.uiValue)
                        }

                    }
                    is LevelItemType.KillCounter -> {
                        Button(onClick = {
                            //check should always pass
                            level.streamedVideo?.let {
                                streamedVideo.value = it
                            }
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


        state.levels?.let {
            //LevelDisplay(levelList = it, imageLoader = imageLoader)
            AndroidView(factory = {
                playerView
            })
            LevelDisplay2(levelList = it, streamedVideo.value,exoPLayer,playerView)
        }
    }

}

@Composable
fun LevelDisplay2(levelList: List<Level>, streamedVideo: String, exoPLayer: SimpleExoPlayer, playerView : PlayerView) {
    println("shobhit: leveldisplay2 recomposing!!!!")

    if(streamedVideo.isNotEmpty()){
        exoPLayer.setMediaItem(MediaItem.fromUri(streamedVideo))

        LaunchedEffect(exoPLayer) {
            exoPLayer.apply {
                prepare()
                playWhenReady = true
                playerView.player = exoPLayer
            }
        }
    }




}


//List of videos for all levels of skin ( eg: vfx,finisher etc.)
@Composable
fun LevelDisplay(levelList: List<Level>, imageLoader: ImageLoader) {

    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ALL
        }
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

    val listState = rememberLazyListState()
    val currentlyPlayingItem = determineCurrentlyPLayingItem(listState, levelList)
    updateCurrentlyPlayingItem(exoPlayer = exoPlayer, level = currentlyPlayingItem)


    //LazyRow(state = listState) {
    // items(levelList) {
    Box {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
        )
    }

}

// }
//}

@Composable
private fun determineCurrentlyPLayingItem(listState: LazyListState, items: List<Level>): Level? {
    Log.d("itemsForCurPlayItem", "items = $items")
    val layoutInfo = listState.layoutInfo
    //val visibleLevels = layoutInfo.visibleItemsInfo.map { items[it.index] }
    val levelsWithVideo = items.filter { !it.streamedVideo.isNullOrBlank() }
    return if (levelsWithVideo.size == 1) {
        levelsWithVideo.first()
    } else {
        null
    }

    return if (levelsWithVideo.size == 1) {
        levelsWithVideo.first()
    } else {
        val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
        val itemsFromCenter =
            layoutInfo.visibleItemsInfo.sortedBy { Math.abs((it.offset + it.size) / 2) - midPoint }
        itemsFromCenter.map { items[it.index] }.firstOrNull { it.streamedVideo != null }
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

