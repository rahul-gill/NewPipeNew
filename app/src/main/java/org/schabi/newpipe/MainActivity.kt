package org.schabi.newpipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.OnSwipe
import androidx.constraintlayout.compose.SwipeDirection
import androidx.constraintlayout.compose.SwipeMode
import androidx.constraintlayout.compose.SwipeSide
import androidx.constraintlayout.compose.SwipeTouchUp
import androidx.constraintlayout.compose.layoutId
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import org.schabi.newpipe.ui.theme.NewPipePreviews
import org.schabi.newpipe.ui.theme.NewPipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewPipeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Something()
                }
            }
        }
    }
}

@NewPipePreviews
@Composable
fun Something() {
    //basic setup
    val viewModel = viewModel<PlayerViewModel>()
    val videoItems by viewModel.mediaItems.collectAsState()
    val selectVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let(viewModel::addVideoUri)
        }
    )
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    var playingSongName by remember {
        mutableStateOf("")
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    //swipe gestures setup
    MotionLayout(
        MotionScene {
            val player = createRefFor("player")
            val expandedContent = createRefFor("expandedContent")
            val miniPlayer = createRefFor("miniPlayer")
            defaultTransition(
                from = constraintSet {
                    constrain(player) {
                        top.linkTo(parent.top)
                        height = Dimension.value(200.dp)
                    }
                    constrain(expandedContent){
                        top.linkTo(player.bottom)
                    }
                    constrain(miniPlayer) {
                        top.linkTo(player.top)
                        bottom.linkTo(player.bottom)
                        alpha = 0f
                    }
                },
                to = constraintSet {
                    constrain(player) {
                        bottom.linkTo(anchor = parent.bottom)
                        height = Dimension.value(50.dp)
                    }
                    constrain(expandedContent){
                        top.linkTo(player.bottom)
                    }
                    constrain(miniPlayer){
                        top.linkTo(player.top)
                        bottom.linkTo(player.bottom)
                        alpha = 1f
                    }
                }
            ) { // this: TransitionScope
                onSwipe = OnSwipe(
                    anchor = player,
                    side = SwipeSide.Top,
                    direction = SwipeDirection.Up,
                    mode = SwipeMode.Velocity,
                    onTouchUp = SwipeTouchUp.AutoComplete,
                    dragScale = 2f
                )
            }
        },
        progress = 0f,
        Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                //TODO: fix this onTouchEvent
                object: PlayerView(context){
                    @SuppressLint("ClickableViewAccessibility")
                    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
                    override fun  onTouchEvent(event: MotionEvent): Boolean {
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                showController()
                            }
                        }
                        return false
                    }
                }.also {
                    it.player = viewModel.player
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .layoutId("player")
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .layoutId("expandedContent")
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = {
                selectVideoLauncher.launch("video/mp4")
            }) {
                Icon(
                    imageVector = Icons.Default.FileOpen,
                    contentDescription = "Select video"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(videoItems) { item ->
                    Text(
                        text = item.third,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.playVideo(item.first)
                                playingSongName = item.third
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
        Text(text = playingSongName, modifier = Modifier.layoutId("miniPlayer"))
    }
}