package org.schabi.newpipe.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.schabi.newpipe.ui.theme.MAX_CONTENT_WIDTH
import org.schabi.newpipe.ui.theme.NewPipePreviews
import org.schabi.newpipe.ui.theme.NewPipeTheme
import org.schabi.newpipe.ui.theme.PreviewWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: @Composable () -> Unit = {},
    isEnabled: Boolean,
    onClick: () -> Unit
){
    Card(
        onClick = onClick,
        modifier = modifier.widthIn(max = MAX_CONTENT_WIDTH),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        enabled = isEnabled
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ){
                icon()
            }
            Column(Modifier.weight(1f).padding(end = 8.dp)) {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if(description.isNotBlank()) Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemSwitch(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: @Composable () -> Unit = {},
    isEnabled: Boolean,
    isChecked: Boolean,
    onCheckedChange: () -> Unit
){
    Card(
        onClick = onCheckedChange,
        modifier = modifier.widthIn(max = MAX_CONTENT_WIDTH),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        enabled = isEnabled
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ){
                icon()
            }
            Column(Modifier.weight(1f).padding(end = 8.dp)) {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if(description.isNotBlank()) Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Switch(
                checked = isChecked,
                onCheckedChange = { onCheckedChange() },
                enabled = isEnabled
            )
        }
    }
}

@Composable
fun SettingsItemCategory(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true
){
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = if(isEnabled) LocalContentColor.current else LocalContentColor.current.copy(alpha = 0.5f)
    )
}

@Composable
@NewPipePreviews
fun SettingsItemPreview(){
    PreviewWrapper {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            SettingsItemCategory(title = "Simple Settings Items", modifier = Modifier.padding(vertical = 8.dp))
            SettingsItem(
                modifier = Modifier.padding(bottom = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = true,
                onClick = {}
            )
            SettingsItem(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = false,
                onClick = {}
            )
            SettingsItemCategory(title = "Switch Settings Items", modifier = Modifier.padding(vertical = 8.dp), isEnabled = false)
            SettingsItemSwitch(
                modifier = Modifier.padding(bottom = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = true,
                isChecked = true,
                onCheckedChange = {}
            )
            SettingsItemSwitch(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = false,
                isChecked = true,
                onCheckedChange = {}
            )
            SettingsItemSwitch(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = true,
                isChecked = false,
                onCheckedChange = {}
            )
            SettingsItemSwitch(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Some settings Item",
                description = "This settings does something which I'm describing here and making it long so that I can see how it looks",
                icon = {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                },
                isEnabled = false,
                isChecked = false,
                onCheckedChange = {}
            )

        }
    }
}

@Composable
fun DialogSingleChoice(
    modifier: Modifier,
    title: String,
    description: String
){
    //TODO
}


@Composable
fun DialogMultipleChoice(
    modifier: Modifier,
    title: String,
    description: String
){
    //TODO
}


@Composable
fun DialogTextField(
    modifier: Modifier,
    title: String,
    description: String,
    textValue: String,
    onTextValueChange: String
){
    //TODO
}


@Composable
fun DialogConfirm(
    modifier: Modifier,
    title: String,
    description: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
){
    //TODO
}
