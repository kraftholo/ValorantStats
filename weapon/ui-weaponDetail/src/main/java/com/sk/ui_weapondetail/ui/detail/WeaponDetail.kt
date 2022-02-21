package com.sk.ui_weapondetail.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.sk.core.domain.ProgressBarState
import com.sk.ui_weapondetail.R
import com.sk.ui_weapondetail.ui.composable.ChromaDisplay

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Skin

@Composable
fun WeaponDetail(
    state: WeaponDetailState,
    imageLoader: ImageLoader,
    onSelectSkin: (String) -> Unit,
    onBack: () -> Unit
) {
    Column() {
        TopAppBar(
            title = { Text(text = state.weapon?.displayName ?: "") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Rounded.ArrowBack, "")
                }
            }
        )
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

                            //Todo -> 2 colors for light and dark
                            Surface(
                                elevation = 10.dp,
                                color = MaterialTheme.colors.primarySurface,
                                shape = MaterialTheme.shapes.large
                            ) {
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
                                        Row(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .weight(6f)
                                        ) {
                                            Text(
                                                modifier = Modifier
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
                                                    .width(40.dp),
                                                painter = iconPainter,
                                                contentDescription = weapon.displayName,
                                                contentScale = ContentScale.Fit
                                            )
                                        }

                                        weapon.shopData?.let {
                                            Text(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .weight(2f),
                                                text = it.cost.toString(),
                                                style = MaterialTheme.typography.h2
                                            )
                                        }
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(
                                                bottom = 4.dp,
                                                start = 4.dp
                                            ),
                                            text = weapon.category.uiValue,
                                            style = MaterialTheme.typography.subtitle1
                                        )

                                        Text(
                                            modifier = Modifier.padding(
                                                bottom = 4.dp,
                                                start = 8.dp
                                            ),
                                            text = "(${weapon.weaponStats?.fireMode?.uiValue ?: "Unknown"})",
                                            style = MaterialTheme.typography.subtitle1
                                        )
                                    }

                                    weapon.weaponStats?.let { weaponStats ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painterResource(id = if (isSystemInDarkTheme()) R.drawable.wall_penetration_dark else R.drawable.wall_penetration_light),
                                                "wall_penetration",
                                                tint = Color.Unspecified,
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(MaterialTheme.shapes.medium)
                                            )
                                            Text(
                                                modifier = Modifier.padding(
                                                    bottom = 4.dp,
                                                    start = 2.dp
                                                ),
                                                text = weaponStats.wallPenetration?.uiValue
                                                    ?: "Unknown",
                                                style = MaterialTheme.typography.subtitle1
                                            )
                                        }
                                    }

                                }
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
}

@ExperimentalFoundationApi
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
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            cells = GridCells.Fixed(6)
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
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(4.dp),
        backgroundColor = MaterialTheme.colors.surface
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
}







