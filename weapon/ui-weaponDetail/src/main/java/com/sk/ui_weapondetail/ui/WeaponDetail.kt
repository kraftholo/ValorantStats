package com.sk.ui_weapondetail

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.sk.ui_weapondetail.ui.WeaponDetailState
import com.sk.ui_weaponlist.R
import com.sk.weapon_domain.Weapon
import com.sk.weapon_domain.skin.Chroma
import com.sk.weapon_domain.skin.Level
import com.sk.weapon_domain.skin.Skin

@Composable
fun WeaponDetail(
    state: WeaponDetailState,
    imageLoader: ImageLoader
) {

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
    LevelDisplay(levelList = skin.levels, imageLoader = imageLoader)
}


//Interactive selection and viewing for chromas
@Composable
fun ChromaDisplay(chromaList: List<Chroma>, imageLoader: ImageLoader) {
    val selectedChroma = remember { mutableStateOf(chromaList[0]) }

    var painter = rememberImagePainter(
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

}


