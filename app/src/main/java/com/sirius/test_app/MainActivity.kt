package com.sirius.test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.chip.Chip
import com.sirius.test_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val data = DataModel()

        binding.tvName.text = data.name
        binding.tvReviewCountSmall.text = data.gradeCnt
        binding.tvDescription.text = data.description

        with(binding.cgTagsList) {
            removeAllViews()

            addView(Chip(this@MainActivity, null, R.attr.tagChipStyle).also {
                it.text = data.tags[0]
            })

            addView(Chip(this@MainActivity, null, R.attr.tagChipStyle).also {
                it.text = data.tags[1]
            })

            addView(Chip(this@MainActivity, null, R.attr.tagChipStyle).also {
                it.text = data.tags[2]
            })
        }


        binding.tvRating.text = data.rating.toString()
        val reviews = "${data.gradeCnt} Reviews"
        binding.tvReviewCount.text = reviews

        binding.tvReviewAuthor1.text = data.reviews[0].userName
        binding.tvReviewDate1.text = data.reviews[0].date
        binding.tvReview1.text = data.reviews[0].message

        binding.tvReviewAuthor2.text = data.reviews[1].userName
        binding.tvReviewDate2.text = data.reviews[1].date
        binding.tvReview2.text = data.reviews[1].message
    }
}