package com.sk.ui_weapondetail

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sk.core.domain.ProgressBarState
import com.sk.ui_weapondetail.ui.WeaponDetailState
import com.sk.ui_weaponlist.R
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin
import java.lang.Math.abs

@Composable
fun WeaponDetail(
    state: WeaponDetailState,
    imageLoader: ImageLoader
) {
    Box {
        state.weapon?.let { weapon ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {


                item {
                    Column {
                        val painter = rememberImagePainter(
                            weapon.displayIcon,
                            imageLoader,
                            builder = {
                                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                            }
                        )

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp),
                            painter = painter,
                            contentDescription = weapon.displayName,
                            contentScale = ContentScale.Fit
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp),
                                    text = weapon.displayName,
                                    style = MaterialTheme.typography.h1
                                )
                                val iconPainter = rememberImagePainter(
                                    weapon.killStreamIcon,
                                    imageLoader = imageLoader,
                                    builder = {
                                        placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                                    }
                                )

                                Image(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = iconPainter,
                                    contentDescription = weapon.displayName,
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Text(
                                modifier = Modifier.padding(bottom = 4.dp),
                                text = weapon.category.uiValue,
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                    state.skins?.forEach {
                        WeaponSkinCollection(it, imageLoader = imageLoader)
                    }
                }
            }
            if (state.progressBarState == ProgressBarState.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun WeaponBaseStats(
    weapon: Weapon,
    padding: Dp,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {

        }
    }
}

@Composable
fun WeaponSkinCollection(
    skin: Skin,
    imageLoader: ImageLoader
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = skin.displayName
    )
    ChromaDisplay(chromaList = skin.chromas, imageLoader = imageLoader)
    //LevelDisplay(levelList = skin.levels, imageLoader = imageLoader)
}


//Interactive selection and viewing for chromas
@Composable
fun ChromaDisplay(chromaList: List<Chroma>, imageLoader: ImageLoader) {
    val selectedChroma = remember { mutableStateOf(chromaList[0]) }

    val painter = rememberImagePainter(
        selectedChroma.value.fullRender,
        imageLoader,
        builder = {
            placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
        }
    )
    Column {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .defaultMinSize(minHeight = 160.dp),
            painter = painter,
            contentDescription = selectedChroma.value.displayName,                 //Todo - change to skin's displayname
            contentScale = ContentScale.Fit
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            chromaList.forEachIndexed { index, chroma ->
                chroma.swatch?.let {
                    Swatch(
                        swatchIcon = it,
                        imageLoader = imageLoader,
                        index = index,
                        onSwatchSelected = { idx ->
                            selectedChroma.value = chromaList[idx]
                        },
                        showBorder = selectedChroma.value == chromaList[index]
                    )
                }
            }
        }
    }
}

@Composable
private fun Swatch(
    swatchIcon: String,
    imageLoader: ImageLoader,
    index: Int,
    onSwatchSelected: (Int) -> Unit,
    showBorder: Boolean
) {

    val painter = rememberImagePainter(
        swatchIcon,
        imageLoader,
        builder = {
            placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
        }
    )
    Image(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .padding(5.dp)
            .border(
                if (showBorder) BorderStroke(4.dp, Color.Black) else BorderStroke(
                    0.dp,
                    Color.Black
                )
            )
            .clickable {
                onSwatchSelected(index)
            },
        painter = painter,
        contentDescription = "",
        contentScale = ContentScale.Fit
    )
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


    LazyRow(state = listState) {
        items(levelList) {
            Box {
                AndroidView(factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                })
            }
        }

    }
}

@Composable
private fun determineCurrentlyPLayingItem(listState: LazyListState, items: List<Level>): Level? {
    val layoutInfo = listState.layoutInfo
    val visibleLevels = layoutInfo.visibleItemsInfo.map { items[it.index] }
    val levelsWithVideo = visibleLevels.filter { it.streamedVideo != null }
    return if (levelsWithVideo.size == 1) {
        levelsWithVideo.first()
    } else {
        val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
        val itemsFromCenter =
            layoutInfo.visibleItemsInfo.sortedBy { abs((it.offset + it.size) / 2) - midPoint }
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


