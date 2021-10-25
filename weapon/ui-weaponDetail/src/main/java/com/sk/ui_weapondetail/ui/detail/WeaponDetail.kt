package com.sk.ui_weapondetail.ui.detail

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
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
import com.sk.ui_weapondetail.ui.composable.ChromaDisplay
import com.sk.ui_weaponlist.R
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin
import java.lang.Math.abs

@Composable
fun WeaponDetail(
    state: WeaponDetailState,
    imageLoader: ImageLoader,
    onSelectSkin: (String) -> Unit
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
                        WeaponSkinCollection(it, imageLoader = imageLoader, onSelectSkin)
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
    imageLoader: ImageLoader,
    onSelectSkin: (String) -> Unit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = skin.displayName
    )
    ChromaDisplay(chromaList = skin.chromas, imageLoader = imageLoader)
    if (skin.hasLevels) {
        Button(onClick = { onSelectSkin(skin.uuid) }) {
            Text("Show Skin Levels")
        }
    }
}







