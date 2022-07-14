package com.sirius.test_app

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.google.android.material.chip.Chip
import com.sirius.test_app.databinding.ActivityMainBinding
import java.security.MessageDigest

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

        Glide.with(this)
            .load(data.image)
            .priority(Priority.HIGH)
            .dontAnimate()
            .into(binding.ivAppBarImage)

        Glide.with(this).asBitmap()
            .load(data.logo)
            .priority(Priority.HIGH)
            .transform(CropBitmap())
            .dontAnimate()
            .into(binding.ivLogo)

        binding.tvName.text = data.name
        binding.rbRating1.rating = data.rating
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
        binding.rbRating2.rating = data.rating
        val reviews = "${data.gradeCnt} Reviews"
        binding.tvReviewCount.text = reviews

        val reviewListAdapter = ReviewListAdapter(data.reviews)

        with(binding.rvReviewList) {
            adapter = reviewListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    inner class CropBitmap : BitmapTransformation() {

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            val centerX: Int = toTransform.width / 2
            val centerY: Int = toTransform.height / 2

            var startX = 0
            for (x in 0 until toTransform.width) {
                val a = toTransform.getPixel(x, centerY)
                if (a != -16777216) {
                    startX = x
                } else {
                    break
                }
            }
            var endX = toTransform.width
            for (x in toTransform.width - 1 downTo startX) {
                val a = toTransform.getPixel(x, centerY)
                if (a != -16777216) {
                    endX = x
                } else {
                    break
                }
            }
            var startY = 0
            for (y in 0 until toTransform.height) {
                val a = (toTransform.getPixel(centerX, y))
                if (a != -16777216) {
                    startY = y
                } else {
                    break
                }
            }
            var endY = toTransform.height
            for (y in toTransform.height - 1 downTo startY) {
                val a = (toTransform.getPixel(centerX, y))
                if (a != -16777216) {
                    endY = y
                } else {
                    break
                }
            }


            Log.d("Bounds:", "$startX, $startY, $endX, $endY")
            return Bitmap.createBitmap(
                toTransform,
                startX -5,
                startY -5,
                endX - startX + 10,
                endY - startY + 10
            )
        }

    }
}