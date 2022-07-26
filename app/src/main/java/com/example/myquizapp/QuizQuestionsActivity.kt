package com.example.myquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myquizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        binding.apply {
            tvOptionOne.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionTwo.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionThree.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionFour.setOnClickListener(this@QuizQuestionsActivity)
            btnSubmit.setOnClickListener(this@QuizQuestionsActivity)
        }

        mQuestionsList = Constants.getQuestions()
        setQuestion()

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList[mCurrentPosition - 1]
        binding.apply {
            ivImage.setImageResource(question.image)
            progressBar.progress = mCurrentPosition
            tvProgress.text = "$mCurrentPosition/ ${progressBar.max}"
            tvQuestion.text = question.question
            tvOptionOne.text = question.optionOne
            tvOptionTwo.text = question.optionTwo
            tvOptionThree.text = question.optionThree
            tvOptionFour.text = question.optionFour

            if (mCurrentPosition == mQuestionsList.size) {
                btnSubmit.text = "FINISH"
            } else {
                btnSubmit.text = "SUBMIT"
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.apply {
            tvOptionOne.let {
                options.add(0, it)
            }
            tvOptionTwo.let {
                options.add(1, it)
            }
            tvOptionThree.let {
                options.add(2, it)
            }
            tvOptionFour.let {
                options.add(3, it)
            }

        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    private fun selectedOptionVIew(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one -> {
                selectedOptionVIew(binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionVIew(binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionVIew(binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionVIew(binding.tvOptionFour, 4)
            }
            R.id.btn_submit -> {

                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else {
                    val question = mQuestionsList[mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                    if (mCurrentPosition == mQuestionsList.size){
                        binding.btnSubmit.text = getString(R.string.finish)
                    }else{
                        binding.btnSubmit.text = getString(R.string.next)
                    }

                    mSelectedOptionPosition = 0

                }

            }

        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }

    }
}

