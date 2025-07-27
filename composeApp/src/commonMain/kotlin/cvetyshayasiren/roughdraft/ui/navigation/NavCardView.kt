package cvetyshayasiren.roughdraft.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun NavCardView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                navController.navigate(RoughDraftDestination.DraftPane)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Domain,
                contentDescription = ""
            )
        }
        IconButton(
            onClick = {
                navController.navigate(RoughDraftDestination.MapDraftList)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = ""
            )
        }
        IconButton(
            onClick = {
                navController.navigate(RoughDraftDestination.Settings)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = ""
            )
        }
    }
}