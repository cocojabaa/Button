package com.example.veryimportant

import kotlin.random.Random
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.veryimportant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AnimationListener {
    val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var buttonMediaPlayer: MediaPlayer
    lateinit var soundMediaPlayer: MediaPlayer

    lateinit var keydownAnim: Animation
    lateinit var keyupAnim: Animation

    lateinit var randomTopsideKeydownAnim: Animation
    lateinit var randomTopsideKeyupAnim: Animation
    lateinit var randomSideKeydownAnim: Animation
    lateinit var randomSideKeyupAnim: Animation

    lateinit var sounds: MutableList<Int>
    var currentSound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.getInsetsController(window,window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.statusBars())
        }

        keydownAnim = AnimationUtils.loadAnimation(this, R.anim.keydown)
        keyupAnim = AnimationUtils.loadAnimation(this, R.anim.keyup)

        randomTopsideKeydownAnim = AnimationUtils.loadAnimation(this, R.anim.random_keydown_topside)
        randomTopsideKeyupAnim = AnimationUtils.loadAnimation(this, R.anim.random_keyup_topside)
        randomSideKeydownAnim = AnimationUtils.loadAnimation(this, R.anim.random_keydown_side)
        randomSideKeyupAnim = AnimationUtils.loadAnimation(this, R.anim.random_keyup_side)

        randomTopsideKeydownAnim.setAnimationListener(this)
        randomSideKeydownAnim.setAnimationListener(this)
        keydownAnim.setAnimationListener(this)

        // Инициализация списка звуков
        val fields = R.raw::class.java.fields
        sounds = mutableListOf()
        fields.forEach {
            if (it.name != "button") sounds.add(it.getInt(it))
            Log.i("My", "${it.name} - ${it.getInt(it)}")
        }

        // Выбор случайного звука
        currentSound = sounds[Random.nextInt(0, sounds.size)]
        sounds.remove(currentSound)

        soundMediaPlayer = MediaPlayer.create(this, currentSound)
        buttonMediaPlayer = MediaPlayer.create(this, R.raw.button)
        binding.playButtonFrameLayout.setOnClickListener {
            binding.buttonTopSide.startAnimation(keydownAnim)

            buttonMediaPlayer.start()
            if (soundMediaPlayer.isPlaying) {
                soundMediaPlayer.stop()
                soundMediaPlayer.reset()
                soundMediaPlayer = MediaPlayer.create(this, currentSound)
                soundMediaPlayer.start()
            }
            else {
                soundMediaPlayer.start()
            }
        }
        binding.randomButtonFrameLayout.setOnClickListener {
            binding.randomTopSide.startAnimation(randomTopsideKeydownAnim)
            binding.randomSideRect.startAnimation(randomSideKeydownAnim)

            buttonMediaPlayer.start()
            setRandomSound()
        }
    }

    private fun setRandomSound() {
        val newSound = sounds[Random.nextInt(0, sounds.size)]
        sounds.remove(newSound)
        sounds.add(currentSound)
        currentSound = newSound
        soundMediaPlayer.stop()
        soundMediaPlayer.reset()
        soundMediaPlayer = MediaPlayer.create(this, currentSound)
    }

    override fun onAnimationEnd(p0: Animation?) {
        if (p0 == keydownAnim) {
            binding.buttonTopSide.startAnimation(keyupAnim)
        } else if (p0 == randomTopsideKeydownAnim) {
            binding.randomTopSide.startAnimation(randomTopsideKeyupAnim)
        } else if (p0 == randomSideKeydownAnim) {
            binding.randomSideRect.startAnimation(randomSideKeyupAnim)
        }
    }


    override fun onAnimationStart(p0: Animation?) { }
    override fun onAnimationRepeat(p0: Animation?) { }
}