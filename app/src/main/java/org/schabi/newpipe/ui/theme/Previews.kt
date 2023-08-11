package org.schabi.newpipe.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Phone Portrait Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
@Preview(
    name = "Phone Landscape Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = "spec:id=reference_phone,shape=Normal,width=891,height=411,unit=dp,dpi=420"
)
@Preview(
    name = "Tablet Portrait Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240"
)
@Preview(
    name = "Tablet Landscape Night Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = "spec:id=reference_tablet,shape=Normal,width=800,height=1280,unit=dp,dpi=240"
)
annotation class NewPipePreviews

@Composable
fun PreviewWrapper(
    content: @Composable () -> Unit
){
    NewPipeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}