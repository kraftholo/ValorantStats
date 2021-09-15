package com.sk.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sk.ui.test.TAG_WEAPON_NAME
import com.sk.weapon_domain.Weapon

@Composable
fun WeaponListItem(
    weapon: Weapon,
    onSelectWeapon: (String) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colors.surface)
            .clickable {
                onSelectWeapon(weapon.UUID)
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                // TODO(Replace with Image)
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .background(Color.LightGray),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f) // fill 80% of remaining width
                    .padding(start = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .testTag(TAG_WEAPON_NAME),
                    text = weapon.displayName,
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .testTag(""),
                    text = weapon.category.uiValue,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Fill the rest of the width (100% - 80% = 20%)
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                val fireMode = weapon.weaponStats?.fireMode
                Text(
                    text = fireMode?.uiValue ?: "No fire mode" ,
                    style = MaterialTheme.typography.caption,
                    //color = if (proWR > 50) Color(0xFF009a34) else MaterialTheme.colors.error,
                )

            }
        }
    }
}