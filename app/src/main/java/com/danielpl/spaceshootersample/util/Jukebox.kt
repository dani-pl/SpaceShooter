package com.danielpl.spaceshootersample.util

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.danielpl.spaceshootersample.R
import java.io.IOException

object SFX{
    var crash = 0
    var starting = 1
    var death = 2
    var boost = 3
}
const val MAX_STREAMS = 3
class Jukebox(assetManager: AssetManager) {
    private val assetManager = assetManager
    private val soundPool: SoundPool
    init {
        val attr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attr)
            .setMaxStreams(MAX_STREAMS)
            .build()
        Log.d(R.string.jukebox_tag.toString(), "soundPool created!")
        SFX.crash = loadSound("crash.wav")
        SFX.starting = loadSound("starting.wav")
        SFX.death = loadSound("death.wav")
        SFX.boost = loadSound("boost.wav")
    }

    private fun loadSound(fileName: String): Int{
        try {
            val descriptor: AssetFileDescriptor = assetManager.openFd(fileName)
            return soundPool.load(descriptor, 1)
        }catch(e: IOException){
            Log.d(R.string.jukebox_tag.toString(), "Unable to load $fileName! Check the filename, and make sure it's in the assets-folder.")
        }
        return 0
    }

    fun play(soundID: Int) {
        val leftVolume = 1f
        val rightVolume = 1f
        val priority = 0
        val loop = 0
        val playbackRate = 1.0f
        if (soundID > 0) {
            soundPool.play(soundID, leftVolume, rightVolume, priority, loop, playbackRate)
        }
    }

    fun destroy() {
        soundPool.release()
        //the soundPool can no longer be used! you have to create a new soundPool.
    }
}