package com.sk.ui_weapondetail.ui.composable

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sk.ui_weaponlist.R
import com.sk.weapon_domain.skin.Chroma


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


@ExperimentalAnimationApi
@Composable
fun ChromaDisplayWithVideo(
    chromaList: List<Chroma>,
    imageLoader: ImageLoader
) {
    val selectedChroma = remember { mutableStateOf(chromaList[0]) }
    val isPlayerVisible =
        remember { mutableStateOf((!chromaList[0].streamedVideo.isNullOrEmpty())) }
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)

    println("ChromaDisplayWithVideo: View Recomposing! selectedChroma = ${selectedChroma.value.displayName} , isPlayerVisible = ${isPlayerVisible.value}")

    val context = LocalContext.current
    val exoPlayer = remember { SimpleExoPlayer.Builder(context).build() }

    val imageSize by animateSizeAsState(
        targetValue =
        if (isPlayerVisible.value) {
            Size(150f, 100f)
        } else {
            Size(300f, 150f)
        }
    )

    updateCurrentlyPlayingItem(
        exoPlayer = exoPlayer,
        chroma = selectedChroma.value,
        selectedChroma.value == chromaList[0]
    )

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
    val painter = rememberImagePainter(
        selectedChroma.value.fullRender,
        imageLoader,
        builder = {
            placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
        }
    )

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                modifier = Modifier
                    .size(imageSize.width.dp, imageSize.height.dp)
                    .padding(8.dp),
                painter = painter,
                contentDescription = selectedChroma.value.displayName,
                contentScale = ContentScale.Fit
            )

            AnimatedVisibility(visible = isPlayerVisible.value) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(imageSize.height.dp),
                    horizontalAlignment = Alignment.End
                ) {

                    AndroidView(factory = { context ->
                        PlayerView(context).apply {
                            player = exoPlayer
                        }
                    }
                    )
                }
            }
        }

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
                            println("onSwatchSelected(): chromaList[idx].streamedVideo.isNullOrEmpty() = ${chromaList[idx].streamedVideo.isNullOrEmpty()} ")
                            isPlayerVisible.value = !chromaList[idx].streamedVideo.isNullOrEmpty()
                        },
                        showBorder = selectedChroma.value == chromaList[index]
                    )
                }
            }
        }

    }
}


@Composable
private fun updateCurrentlyPlayingItem(
    exoPlayer: SimpleExoPlayer,
    chroma: Chroma,
    playOnReady: Boolean
) {
    chroma.streamedVideo?.let {
        exoPlayer.let { player ->
            val context = LocalContext.current
            LaunchedEffect(chroma) {
                player.apply {
                    val dataSourceFactory = DefaultDataSourceFactory(
                        context,
                        Util.getUserAgent(context, context.packageName)
                    )
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(Uri.parse(it)))

                    setMediaSource(source)
                    prepare()
                    playWhenReady = playOnReady
                }
            }
        }
    } ?: run { exoPlayer.stop() }
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
