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
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.sk.core.domain.ProgressBarState
import com.sk.ui_weapondetail.R
import com.sk.ui_weapondetail.ui.composable.ChromaDisplay
import com.sk.ui_weapondetail.ui.composable.StatBox

import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Skin

@ExperimentalFoundationApi
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

                            //Main Weapon Image
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
                                    WeaponBaseStats(state.weapon)
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
    weapon: Weapon
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weapon.weaponStats?.fireRate?.let {
                StatBox(title = "FIRE RATE", value = "$it", units = "RDS/SEC")
            }
            weapon.weaponStats?.magazineSize?.let {
                StatBox(title = "MAGAZINE", value = "$it", units = "RDS")
            }
            val adsAcc = weapon.weaponStats?.adsStats?.firstBulletAccuracy
            val hipAcc = weapon.weaponStats?.firstBulletAccuracy

            if (adsAcc != null && hipAcc != null) {
                StatBox(
                    title = "1st SHOT SPREAD",
                    value = "${hipAcc}/${adsAcc}",
                    units = "DEG (HIP/ADS)"
                )
            }
            weapon.weaponStats?.adsStats?.zoomMultiplier?.let {
                StatBox(
                    title = "ZOOM",
                    value = "${it}x",
                    units = ""
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weapon.weaponStats?.equipTimeSeconds?.let {
                StatBox(title = "EQUIP SPEED", value = "$it", units = "SEC")
            }
            weapon.weaponStats?.reloadTimeSeconds?.let {
                StatBox(title = "RELOAD SPEED", value = "$it", units = "SEC")
            }
            weapon.weaponStats?.equipTimeSeconds?.let {
                StatBox(title = "EQUIP SPEED", value = "$it", units = "SEC")
            }
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
        Column() {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = skin.displayName
            )
            ChromaDisplay(chromaList = skin.chromas, imageLoader = imageLoader)
            if (skin.hasLevels) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onSelectSkin(skin.uuid) }
                    ) {
                        Text("Show Skin Levels")
                    }
                }
            }
        }
    }
}







