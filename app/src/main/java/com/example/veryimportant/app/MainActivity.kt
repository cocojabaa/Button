package com.example.veryimportant.app

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.veryimportant.R
import com.example.veryimportant.databinding.ActivityMainBinding
import com.example.veryimportant.domain.CustomMediaPlayer

class MainActivity : AppCompatActivity(), AnimationListener {
    val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var customMediaPlayer: CustomMediaPlayer

    lateinit var keydownAnim: Animation
    lateinit var keyupAnim: Animation
    lateinit var randomTopsideKeydownAnim: Animation
    lateinit var randomTopsideKeyupAnim: Animation
    lateinit var randomSideKeydownAnim: Animation
    lateinit var randomSideKeyupAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.getInsetsController(window,window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.statusBars())
        }
        initAnimations()

        customMediaPlayer = CustomMediaPlayer(this)

        binding.playButtonFrameLayout.setOnClickListener {
            binding.buttonTopSide.startAnimation(keydownAnim)
            customMediaPlayer.playSound()
        }

        binding.randomButtonFrameLayout.setOnClickListener {
            binding.randomTopSide.startAnimation(randomTopsideKeydownAnim)
            binding.randomSideRect.startAnimation(randomSideKeydownAnim)
            customMediaPlayer.setRandomSound()
        }
    }

    private fun initAnimations() {
        keydownAnim = AnimationUtils.loadAnimation(this, R.anim.keydown)
        keyupAnim = AnimationUtils.loadAnimation(this, R.anim.keyup)

        randomTopsideKeydownAnim = AnimationUtils.loadAnimation(this, R.anim.random_keydown_topside)
        randomTopsideKeyupAnim = AnimationUtils.loadAnimation(this, R.anim.random_keyup_topside)
        randomSideKeydownAnim = AnimationUtils.loadAnimation(this, R.anim.random_keydown_side)
        randomSideKeyupAnim = AnimationUtils.loadAnimation(this, R.anim.random_keyup_side)

        randomTopsideKeydownAnim.setAnimationListener(this)
        randomSideKeydownAnim.setAnimationListener(this)
        keydownAnim.setAnimationListener(this)
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