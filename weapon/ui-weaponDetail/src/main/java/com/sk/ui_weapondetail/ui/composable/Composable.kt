package com.sk.ui_weapondetail.ui.composable

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ChromaDisplay(chromaList: List<Chroma>, imageLoader: ImageLoader, showVideoOverlay: Boolean) {
    val selectedChroma = remember { mutableStateOf(chromaList[0]) }

    val context = LocalContext.current

    val chromaExoPlayer = if (showVideoOverlay) {
        remember {
            SimpleExoPlayer.Builder(context).build()
        }
    } else {
        null
    }

    val isExoPlayerVisible = remember {
        mutableStateOf(false)
    }

    //Updating chroma item
    chromaExoPlayer?.let {
        val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)

        updateCurrentlyPlayingItem(
            exoPlayer = it,
            chroma = selectedChroma.value,
            isExoPlayerVisible
        )

        DisposableEffect(lifecycleOwner) {
            val lifecycle = lifecycleOwner.lifecycle
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.playWhenReady = false
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        it.playWhenReady = true
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        it.run {
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


    }


    val painter = rememberImagePainter(
        selectedChroma.value.fullRender,
        imageLoader,
        builder = {
            placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
        }
    )
    Column {
        Box {
            if (!isExoPlayerVisible.value) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .defaultMinSize(minHeight = 160.dp),
                    painter = painter,
                    contentDescription = selectedChroma.value.displayName,                 //Todo - change to skin's displayname
                    contentScale = ContentScale.Fit
                )
            } else {
                AndroidView(
                    factory = { context ->
                        PlayerView(context).apply {
                            player = chromaExoPlayer
                        }
                    }
                )
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
    chroma: Chroma?,
    isExoPlayerVisible: MutableState<Boolean>,
) {

    chroma?.streamedVideo?.let {
        isExoPlayerVisible.value = true
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
                    playWhenReady = true
                }
            }
        }
    } ?: run { isExoPlayerVisible.value = false }
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
