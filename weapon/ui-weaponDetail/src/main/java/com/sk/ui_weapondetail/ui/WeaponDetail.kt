package com.sk.ui_weapondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.sk.ui_weapondetail.ui.WeaponDetailState
import com.sk.ui_weaponlist.R

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

//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(1.dp)
//                            .background(color = if (isSystemInDarkTheme()) Color.White else Color.Black)
//                    )

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
            }
        }
    }
}
