package cvetyshayasiren.roughdraft.domain.audioPlayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import kotlinx.coroutines.launch


object AudioPlayerInteractions: ViewModel() {
    val player: AudioPlayer = AudioPlayer()
    private val currentPage = DraftBookInteractions.currentPage
    private val settings = SettingsState.settings

    init {
        DraftBookInteractions.viewModelScope.launch {
            player.songIsOver().collect { isOver ->
                if(isOver) {
                    when(settings.value.playbackOptions) {
                        PlaybackOptions.Stoppable -> player.stop()
                        PlaybackOptions.RepeatOne -> { setProgress(0.0); player.play() }
                        PlaybackOptions.RepeatNext -> { DraftBookInteractions.nextPage() }
                    }
                }
            }
        }
    }

    private fun prepare() {
        val audioUri = currentPage.value.audioUri
        player.prepare(audioUri)
    }

    private fun prepareAndPlay() {
        prepare()
        player.play()
    }

    fun firstPlayInteraction() {
        when(player.state.value.firstInteractionDone) {
            true -> player.swapPause()
            false -> prepareAndPlay()
        }
    }

    fun swapPause() = player.swapPause()

    fun pageSwapped() {
        when(player.state.value.isPlaying) {
            true -> prepareAndPlay()
            false -> prepare()
        }
    }

    fun setProgress(value: Double) = player.setProgress(value)

    fun setVolume(value: Double) = player.setVolume(value)

    fun nextPlaybackOption() = SettingsState.setSettings(playbackOptions = settings.value.playbackOptions.next())
}