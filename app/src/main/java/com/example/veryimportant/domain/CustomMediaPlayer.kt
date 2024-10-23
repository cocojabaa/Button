package com.example.veryimportant.domain

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.veryimportant.R
import kotlin.random.Random

class CustomMediaPlayer(val context: Context) {
    private var soundIds = getAllSoundsId()
    private var currentSoundId = getRandomSound(true)
    private var soundMediaPlayer = MediaPlayer.create(context, currentSoundId)
    private val buttonMediaPlayer = MediaPlayer.create(context, R.raw.button)

    init {
        Log.i("My", "MEDIA PLAYER INIT")
    }

    fun playSound() {
        buttonMediaPlayer.start()
        if (soundMediaPlayer.isPlaying) {
            soundMediaPlayer.stop()
            soundMediaPlayer.reset()
            soundMediaPlayer = MediaPlayer.create(context, currentSoundId)
            soundMediaPlayer.start()
        }
        else {
            soundMediaPlayer.start()
        }
    }

    fun setRandomSound() {
        buttonMediaPlayer.start()
        currentSoundId = getRandomSound()
        setSound()
    }


    private fun getAllSoundsId(): MutableList<Int> {
        val fields = R.raw::class.java.fields
        var sounds = mutableListOf<Int>()
        fields.forEach {
            if (it.name != "button") sounds.add(it.getInt(it))
            Log.i("Sounds", "${it.name} - ${it.getInt(it)}")
        }
        return sounds
    }

    private fun setSound() {
        soundMediaPlayer.stop()
        soundMediaPlayer.reset()
        soundMediaPlayer = MediaPlayer.create(context, currentSoundId)
    }

    private fun getRandomSound(firstGet: Boolean = false): Int {
        var newSound = soundIds[Random.nextInt(0, soundIds.size)]
        soundIds.remove(newSound)
        if (!firstGet) {
            soundIds.add(currentSoundId)
        }
        return newSound
    }


}