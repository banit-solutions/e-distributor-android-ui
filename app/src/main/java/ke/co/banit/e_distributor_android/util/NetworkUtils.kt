@file:OptIn(ExperimentalSerializationApi::class)

package ke.co.banit.e_distributor_android.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.processphoenix.ProcessPhoenix
import ke.co.banit.e_distributor_android.App
import ke.co.banit.e_distributor_android.core.ErrorResponse
import ke.co.banit.e_distributor_android.data.PrefManager
import ke.co.banit.e_distributor_android.data.remote.Resource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun AppCompatActivity.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> window.decorView.rootView.snackbar(
            "Please check your internet connection",
            retry
        )

        failure.errorCode == 401 -> {
            DialogUtils.errorDialog(
                message = "Unauthorized access. You'll be logged out for security reasons.",
                action = {
                    PrefManager().logout()
                    ProcessPhoenix.triggerRebirth(App.application.applicationContext)
                }, context = this
            )
        }

        else -> {
            val errorRaw = failure.errorBody?.string().toString()

            // Check if the error response is JSON
            val isJson = try {
                Json.decodeFromString<ErrorResponse>(errorRaw)
                true
            } catch (e: SerializationException) {
                false
            }

            if (isJson) {
                val errorObj = Json.decodeFromString<ErrorResponse>(errorRaw)
                window.decorView.rootView.snackbar(errorObj.message)
            } else {
                window.decorView.rootView.snackbar(errorRaw)
            }
        }
    }
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )

        failure.errorCode == 401 -> {
            DialogUtils.errorDialog(
                message = "Another device just logged into your account. If it is not you, you're advised to login again and change your password immediately",
                action = {
                    PrefManager().logout()
                    ProcessPhoenix.triggerRebirth(App.application.applicationContext)
                }, context = requireContext()
            )
        }

        else -> {
            val errorRaw = failure.errorBody?.string().toString()

            // Check if the error response is JSON
            val isJson = try {
                Json.decodeFromString<ErrorResponse>(errorRaw)
                true
            } catch (e: SerializationException) {
                false
            }

            if (isJson) {
                val errorObj = Json.decodeFromString<ErrorResponse>(errorRaw)
                requireView().snackbar(errorObj.message)
            } else {
                requireView().snackbar(errorRaw)
            }
        }
    }
}