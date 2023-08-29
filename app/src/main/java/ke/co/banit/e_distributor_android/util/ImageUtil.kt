package ke.co.banit.e_distributor_android.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ke.co.banit.e_distributor_android.R
import timber.log.Timber

/**
 * @Author: Angatia Benson
 * @Date: 27/08/2022
 * Copyright (c) 2022 Bantechnis
 */

fun getProgressDrawable(_context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(_context).apply {
        strokeWidth = 2f
        centerRadius = 20f
        start()
    }
}

fun CircleImageView.loadImage(url: String?, progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE

    Picasso.get()
        .load(url)
        .placeholder(R.color.grey_500)
        .into(this, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
                Timber.i("SUCCESS LOADING IMAGE")
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.GONE
                Timber.i("ERROR LOADING IMAGE : $url")
                e?.printStackTrace()
                this@loadImage.setImageResource(R.color.grey_500)
            }
        })
}



fun ImageView.loadImage(
    url: String?,
    action: (ImageView) -> Unit = {}
) {
    Picasso.get()
        .load(url)
        .placeholder(R.color.grey_500)
        .into(this, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                action(this@loadImage)
            }

            override fun onError(e: Exception?) {
                Timber.i("ERROR LOADING IMAGE : $url")
                e?.printStackTrace()
                this@loadImage.setImageResource(R.color.grey_500)
            }
        })
}