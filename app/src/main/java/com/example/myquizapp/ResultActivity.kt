package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityResultBinding

    private var mCorrectAnswers: Int = 0
    private var totalQuestions: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCorrectAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        binding.apply {
            tvName.text = intent.getStringExtra(Constants.USER_NAME)
            tvScore.text = resources.getString(R.string.final_score, mCorrectAnswers.toString(), totalQuestions.toString())
            btnFinish.setOnClickListener(this@ResultActivity)

        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_finish -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}