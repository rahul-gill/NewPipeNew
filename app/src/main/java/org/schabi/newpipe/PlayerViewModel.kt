package org.schabi.newpipe

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerViewModel(app: Application): AndroidViewModel(app) {
    val player by lazy {
        ExoPlayer.Builder(getApplication()).build()
    }
    private val videoUris = MutableStateFlow(emptyList<Uri>())
    val mediaItems = videoUris.map { uris ->
        uris.map { uri ->
            Triple(uri, MediaItem.fromUri(uri), getNameFromUri(uri))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addVideoUri(uri: Uri) {
        videoUris.value = videoUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(uri: Uri) {
        player.setMediaItem(
            mediaItems.value.find { it.first  == uri }?.second ?: return
        )
    }

    init {
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private fun getNameFromUri(contentUri: Uri): String {
        if(contentUri.scheme != "content") {
            return "No name"
        }
        val fileName = getApplication<Application>().contentResolver
            .query(
                contentUri,
                arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
                null,
                null,
                null,
            )
            ?.use { cursor ->
                val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(index)
            }
        return fileName?.let { fullFileName ->
                Uri.parse(fullFileName).lastPathSegment
        } ?: return "No name"
    }
}