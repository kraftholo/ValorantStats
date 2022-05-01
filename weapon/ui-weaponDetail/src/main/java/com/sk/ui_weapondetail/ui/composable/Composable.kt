package com.sk.ui_weapondetail.ui.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
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
                            selectedChroma.value = chromaList[idx]              // value change causes recomposition
                        },
                        showBorder = selectedChroma.value == chromaList[index]
                    )
                }
            }
        }
    }
}


//Todo : Remove later
/*@ExperimentalAnimationApi
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

    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

    //Using transition to animate multiple values
    val transition = updateTransition(targetState = isPlayerVisible.value)

    val imageWidthF by transition.animateFloat() { playerIsVisible ->
        if (playerIsVisible) 0.3f else 0.7f
    }
    val imageHeight by transition.animateDp() { playerIsVisible ->
        if (playerIsVisible) 200.dp else 100.dp
    }

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

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()                      //This makes the Arrangement.SpaceAround work
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(imageWidthF)
                    .height(imageHeight)
                    .padding(8.dp),
                painter = painter,
                contentDescription = selectedChroma.value.displayName,
                contentScale = ContentScale.Fit
            )

            AnimatedVisibility(visible = isPlayerVisible.value) {
                AndroidView(
                    factory = { context ->
                        PlayerView(context).apply {
                            useController = false
                            hideController()
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                            player = exoPlayer
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(1.0f - imageWidthF)
                        .height(imageHeight / 2f)
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
                            println("onSwatchSelected(): chromaList[idx].streamedVideo.isNullOrEmpty() = ${chromaList[idx].streamedVideo.isNullOrEmpty()} ")
                            isPlayerVisible.value = !chromaList[idx].streamedVideo.isNullOrEmpty()
                        },
                        showBorder = selectedChroma.value == chromaList[index]
                    )
                }
            }
        }

    }
}*/


@Composable
fun ChromaDisplayForSkinDetail(
    chromaList: List<Chroma>,
    imageLoader: ImageLoader,
    playChromaVideo: (idx: Int) -> Unit
) {
    val selectedChroma = remember { mutableStateOf(chromaList[0]) }
    val painter = rememberImagePainter(
        selectedChroma.value.fullRender,
        imageLoader = imageLoader,
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
            contentDescription = selectedChroma.value.displayName,
            contentScale = ContentScale.Fit
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                            playChromaVideo(idx)                               //Will trigger exoplayer in WeaponSkinDetail
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

@Composable
fun StatBox(
    title: String,
    value: String,
    units: String
) {
    Card(
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(5.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = Color.LightGray
            ) {
                Text(
                    style = MaterialTheme.typography.h6,
                    text = title
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = units,
                style = MaterialTheme.typography.h6
            )
        }
    }
}


