package org.schabi.newpipe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.schabi.newpipe.R
import org.schabi.newpipe.ui.theme.MAX_CONTENT_WIDTH
import org.schabi.newpipe.ui.theme.NewPipePreviews
import org.schabi.newpipe.ui.theme.NewPipeTheme
import org.schabi.newpipe.ui.theme.PreviewWrapper

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onShowOptionItems: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.go_back))
        }

        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .padding(8.dp)
                .height(40.dp)
                .weight(1f),
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_text),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(100f),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor  = Color.Transparent,
                unfocusedIndicatorColor  = Color.Transparent
            )
        )
        IconButton(onClick = onShowOptionItems) {
            Icon(imageVector = Icons.Default.Tune, contentDescription = stringResource(R.string.search_filters))
        }
    }
}

@Composable
fun HistorySearchSuggestionItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onFillClick: () -> Unit
){
    SearchSuggestionItem(modifier, text, onClick, onFillClick, icon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSuggestionItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onFillClick: () -> Unit,
    icon: @Composable () -> Unit = {
        Icon(imageVector = Icons.Default.History, contentDescription = null)
    },
){
    Card(
        onClick = onClick,
        modifier = modifier.widthIn(max = MAX_CONTENT_WIDTH),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().widthIn(max = MAX_CONTENT_WIDTH)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Text(
                text = text,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = onFillClick) {
                Icon(imageVector = Icons.Default.NorthWest, contentDescription = stringResource(R.string.enter_search_suggestion))
            }
        }
    }
}

@Composable
@NewPipePreviews
private fun SearchViewPreview(){
    PreviewWrapper{
        val text = remember {
            mutableStateOf("")
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SearchAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = text.value,
                    onSearchQueryChange = { text.value = it },
                    onBack = {},
                    onShowOptionItems = {}
                )
            }
        ) {
            Column(Modifier.padding(it).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                for(i in 0 until 5){
                    SearchSuggestionItem(
                        text = "Some search suggestion",
                        onFillClick = {},
                        onClick = {}
                    )
                    HistorySearchSuggestionItem(
                        text = "Search suggestion from history",
                        onFillClick = {},
                        onClick = {}
                    )
                }
            }
        }
    }
}