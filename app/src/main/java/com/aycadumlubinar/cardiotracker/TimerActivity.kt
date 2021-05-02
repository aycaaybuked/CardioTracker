package com.aycadumlubinar.cardiotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aycadumlubinar.cardiotracker.utils.convertMinutesToSeconds
import com.aycadumlubinar.cardiotracker.utils.convertSecondsToMinutes
import com.aycadumlubinar.cardiotracker.utils.getTimeFromStr
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_timer.*


class TimerActivity : AppCompatActivity() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var timeTV: TextView
    private lateinit var stepTV: TextView
    private lateinit var stepCountTV: TextView
    private lateinit var playPauseB: ImageButton
    private lateinit var replayB: ImageButton
    private lateinit var stopB: ImageButton
    private lateinit var soundB: ImageButton
    private var setNumberIni: Int = 0
    private var workSecondsIni: Int = 0
    private var restSecondsIni: Int = 0
    private var currentSetNumber: Int = 0
    private var currentStep: Int = 0
    private var currentTime: Int = 0
    private var timer: CountDownTimer? = null
    private var isPaused: Boolean = false
    private var isMuted: Boolean = false
    private lateinit var mpGetReady: MediaPlayer
    private lateinit var mpStart: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        constraintLayout = a_timer_constraint_layout_main
        timeTV = a_timer_text_view_time
        stepTV = a_timer_text_view_step
        stepCountTV = a_timer_text_view_step_count
        playPauseB = a_timer_image_button_play_pause
        stopB = a_timer_image_button_stop
        replayB = a_timer_image_button_replay
        soundB = a_timer_image_button_sound

        iniActionButtons()
        getValues()
        iniGetReady()
    }

    private fun iniActionButtons() {
        soundB.setOnClickListener {
            isMuted = if (isMuted) {
                soundB.setImageResource(R.drawable.ic_baseline_volume_up_24)
                false
            } else {
                soundB.setImageResource(R.drawable.ic_baseline_volume_off_24)
                true
            }
        }
        stopB.setOnClickListener {
            cancelTimer()
            this.finish()
        }
        replayB.setOnClickListener {
            cancelTimer()
            currentSetNumber = setNumberIni
            iniGetReady()
        }
        playPauseB.setOnClickListener {
            if (!isPaused) {
                cancelTimer()
                playPauseB.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                isPaused = true
            } else {
                if (currentStep != -1) {
                    currentTime = convertMinutesToSeconds(
                            getTimeFromStr(timeTV.text.toString()).first,
                            getTimeFromStr(timeTV.text.toString()).second
                    )
                    playPauseB.setImageResource(R.drawable.ic_baseline_pause_24)
                    startTimer(currentTime)
                    isPaused = false
                }
            }
        }
    }

    private fun iniGetReady() {
        currentStep = 0
        playPauseB.setImageResource(R.drawable.ic_baseline_pause_24)
        stepTV.isVisible = true
        stepCountTV.isVisible = true
        constraintLayout.setBackgroundResource(R.drawable.green_gradient)
        stepCountTV.text =
                resources.getString(R.string.upper_set) + " " + currentSetNumber.toString()
        stepTV.text = resources.getString(R.string.upper_get_ready)
        timeTV.text = resources.getString(R.string.ini_time)
        startTimer(6)
    }


}


