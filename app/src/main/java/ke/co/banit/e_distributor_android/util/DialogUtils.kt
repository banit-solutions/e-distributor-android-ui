package ke.co.banit.e_distributor_android.util

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.FragmentManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener
import com.sanj.besid.info.BESIDInfoDialog
import ke.co.banit.e_distributor_android.App
import ke.co.banit.e_distributor_android.R
import ke.co.banit.e_distributor_android.data.PrefManager


/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */
object DialogUtils {
    private lateinit var progressDialog: ProgressDialogFragment
    fun showProgressDialog(
        fragmentManager: FragmentManager
    ) {
        progressDialog = ProgressDialogFragment.newInstance()
        progressDialog.isCancelable = false
        progressDialog.show(fragmentManager, "progress")
    }

    fun dismissProgressDialog() {
        progressDialog.dismiss()
    }


    fun toast(message: String) =
        Toast.makeText(App.application.applicationContext, message, Toast.LENGTH_LONG).show()

    fun errorDialog(title: String = "Oops! Error", message: String, context: Context) {
        PopupDialog.getInstance(context)
            .setStyle(Styles.FAILED)
            .setHeading(title)
            .setDescription(message)
            .setCancelable(false)
            .showDialog(object : OnDialogButtonClickListener() {
                override fun onDismissClicked(dialog: Dialog) {
                    super.onDismissClicked(dialog)
                }
            })
    }

    fun errorDialog(
        title: String = "Oops! Error",
        message: String,
        context: Context,
        action: (() -> Unit)
    ) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener {
                it.dismiss()
                action()
            }
        dialog.setCancelable(false)
        dialog.show()
    }

    fun warningDialog(title: String = "Something is not right", message: String, context: Context) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmText("Okay")
        dialog.setCancelable(false)
        dialog.show()
    }

    fun successDialog(
        title: String = "SUCCESS",
        message: String,
        context: Context,
        action: (() -> Unit)
    ) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmText("Okay")
            .setConfirmClickListener {
                it.dismiss()
                action()
            }
        dialog.setCancelable(false)
        dialog.show()
    }

    fun infoMessage(
        title: String = "Information",
        message: String,
        context: Context,
        listener: BESIDInfoDialog.BESIDInfoDialogInterface.OnClickListener
    ) {
        BESIDInfoDialog.Builder(context)
            .setMessage(message)
            .setTitle(title)
            .setCancelable(true)
            .setActionButton("Okay, I understand", listener)
            .build()
            .show()
    }

    fun confirmationDialog(
        title: String = "Confirm",
        message: String,
        mContext: Context,
        actionPositive: (() -> Unit),
        actionNegative: (() -> Unit) = { }
    ) {
        val themeId = getAppropriateTheme(mContext)
        val alertDialog: AlertDialog
        val alertBuilder: AlertDialog.Builder =
            AlertDialog.Builder(ContextThemeWrapper(mContext, themeId))
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton("Proceed") { dialog, _ ->
            run {
                dialog.dismiss()
                actionPositive()
            }
        }
        alertBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            actionNegative()
        }
        alertDialog = alertBuilder.create()
        alertDialog.show()
    }

    private fun isSystemInDarkMode(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun getAppropriateTheme(context: Context): Int {
        val appPrefDark = PrefManager().isDarkModeEnabled()
        val systemDark = isSystemInDarkMode(context)

        return when {
            appPrefDark -> R.style.MyAlertDialogTheme_Dark
            else -> {
                if (systemDark) R.style.MyAlertDialogTheme_Dark else R.style.MyAlertDialogTheme_Light
            }
        }
    }


}
