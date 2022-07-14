package com.sirius.test_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sirius.test_app.databinding.ItemReviewBinding

class ReviewListAdapter(
    private var reviews: List<ReviewModel> = listOf()
) : RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int = reviews.size


    fun updateData(data: List<ReviewModel>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = reviews.size

            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                return reviews[oldPosition] == data[newPosition]
            }

            override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                return reviews[oldPosition].hashCode() == data[newPosition].hashCode()
            }

        }
    }

    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewModel) {
            with(binding) {
                tvAuthor.text = review.userName
                tvDate.text = review.date
                tvReview.text = review.message
            }
            Glide.with(itemView)
                .load(review.userImage)
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }
}
