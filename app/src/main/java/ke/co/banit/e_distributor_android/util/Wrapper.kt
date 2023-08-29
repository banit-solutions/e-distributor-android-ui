package ke.co.banit.e_distributor_android.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.MediaStore
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.stfalcon.imageviewer.StfalconImageViewer
import ke.co.banit.e_distributor_android.App
import ke.co.banit.e_distributor_android.R
import java.io.Serializable
import java.security.SecureRandom
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

fun AppCompatActivity.loadActivity(
    cls: Class<*>?,
    dataObject: HashMap<String, Serializable>? = null,
    dataString: HashMap<String, String>? = null
) {
    val intent = Intent(this, cls)
    if (!dataObject.isNullOrEmpty()) {
        for (key in dataObject.keys) {
            intent.putExtra(key, dataObject[key])
        }
    }

    if (!dataString.isNullOrEmpty()) {
        for (key in dataString.keys) {
            intent.putExtra(key, dataString[key])
        }
    }
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Fragment.loadActivity(
    cls: Class<*>?,
    dataObject: HashMap<String, Serializable>? = null,
    dataString: HashMap<String, String>? = null
) {
    val intent = Intent(requireContext(), cls)
    if (!dataObject.isNullOrEmpty()) {
        for (key in dataObject.keys) {
            intent.putExtra(key, dataObject[key])
        }
    }

    if (!dataString.isNullOrEmpty()) {
        for (key in dataString.keys) {
            intent.putExtra(key, dataString[key])
        }
    }
    startActivity(intent)
    requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun AppCompatActivity.showSnackBar(mMessage: String) {
    window.decorView.rootView.snackbar(mMessage)
}

fun Fragment.showSnackBar(mMessage: String) {
    requireView().snackbar(mMessage)
}

fun AppCompatActivity.openNotificationSettings() {
    val intent = Intent()

    when {
        SDK_INT >= Build.VERSION_CODES.O -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("android.provider.extra.APP_PACKAGE", this.packageName)
        }

        SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", this.packageName)
            intent.putExtra("app_uid", this.applicationInfo.uid)
        }
        else -> {
            // For older versions, you can open the generic Apps settings page
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
        }
    }

    startActivity(intent)
}


fun addZeroBefore(number: Int): String {
    return if (number < 10) {
        "0$number"
    } else {
        number.toString()
    }
}

fun checkAndRequestPermissions(context: Context?, activity: Activity?) {
    val permissionStorage =
        ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val permissionReadStorage =
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    val permissionFineLocation =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    val permissionCoarseLocation =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)


    val listPermissionsNeeded: MutableList<String> = ArrayList()
    if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    if (listPermissionsNeeded.isNotEmpty()) {
        ActivityCompat.requestPermissions(activity!!, listPermissionsNeeded.toTypedArray(), 1001)
    }
}

fun formatCurrency(number: String): String {
    val doubleNumber = number.toDouble()
    val formatter = DecimalFormat("#,###.00")
    return formatter.format(doubleNumber)
}

@SuppressLint("SimpleDateFormat")
fun calculateTimeIntervals(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val previousDate = dateFormat.parse(date)
    val currentDate = Date()

    val diffInMillis = currentDate.time - previousDate?.time!!

    val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
    val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
    val diffInWeeks = diffInDays / 7
    // Check if the year is the same
    val currentCalendar = Calendar.getInstance().apply { time = currentDate }
    val previousCalendar = Calendar.getInstance().apply { time = previousDate }
    val isSameYear = currentCalendar.get(Calendar.YEAR) == previousCalendar.get(Calendar.YEAR)

    return when {
        diffInSeconds < 3 -> "now"
        diffInSeconds < 60 -> "$diffInSeconds s"
        diffInMinutes < 60 -> "$diffInMinutes m"
        diffInHours < 24 -> "$diffInHours h"
        diffInDays < 7 -> "$diffInDays d"
        diffInWeeks < 8 -> "$diffInWeeks w"
        isSameYear -> SimpleDateFormat("d MMM").format(previousDate)
        else -> SimpleDateFormat("d MMM yy").format(previousDate)
    }
}
@SuppressLint("SimpleDateFormat")
fun calculateDateDifference(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val previousDate = dateFormat.parse(date)
    val currentDate = Date()

    val days = ((previousDate?.time!! - currentDate.time) / (24 * 60 * 60 * 1000)).toInt()

    return if (days < 0)
        "0"
    else
        return days.toString()
}

fun formatPhoneNumber(phoneNumber: String): String {
    if (!isKenyanNumber(phoneNumber))
        return ""

    if (phoneNumber.length == 10 && phoneNumber.startsWith("0")) {
        return phoneNumber.replaceFirst("^0".toRegex(), "254")
    }

    return if (phoneNumber.length == 13 && phoneNumber.startsWith("+")) phoneNumber.replaceFirst(
        "^+".toRegex(),
        ""
    ) else phoneNumber
}

fun isKenyanNumber(number: String): Boolean {
    //(\+254|^){1}[ ]?[7]{1}([0-3]{1}[0-9]{1})[ ]?[0-9]{3}[ ]?[0-9]{3}\z
    val pattern = Regex("^(\\+254|254|0)[1-9]\\d{8}\$")
    return pattern.matches(number)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

fun getFileNameFromUri(uri: Uri, contentResolver: ContentResolver): String? {
    val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
    val cursor = contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            return it.getString(displayNameIndex)
        }
    }
    return null
}

fun showImageViewer(
    context: Context,
    sourceImageView: ImageView,
    images: List<Any>,  // This list can be URLs, file paths, or drawables
    startPosition: Int = 0  // Default position to start viewing from
) {
    StfalconImageViewer.Builder(context, images, ::loader)
        .withStartPosition(startPosition)
        .withBackgroundColorResource(R.color.black)
        .withImageMarginPixels(8)
        .withContainerPaddingPixels(24)
        .withHiddenStatusBar(false)
        .allowZooming(true)
        .allowSwipeToDismiss(true)
        .withTransitionFrom(sourceImageView)
        .show()
}

private fun loader(imageView: ImageView, image: Any) {
    imageView.setColorFilter(Color.TRANSPARENT)
    Glide.with(imageView.context)
        .load(image)
        .listener(object: RequestListener<Drawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

        })
        .into(imageView)
}



fun AppCompatActivity.bindMenu(
    @MenuRes menuRes: Int,
    onMenuItemSelected: (MenuItem) -> Unit
): MenuProvider {
    val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onMenuItemSelected(menuItem)
            return true
        }
    }
    addMenuProvider(menuProvider)
    return menuProvider
}

fun FragmentActivity.bindMenu(
    menuHost: MenuHost,
    @MenuRes menuRes: Int,
    lifecycleOwner: LifecycleOwner,
    onMenuItemSelected: (MenuItem) -> Unit
): MenuProvider {
    val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onMenuItemSelected(menuItem)
            return true
        }
    }
    menuHost.addMenuProvider(menuProvider, lifecycleOwner, Lifecycle.State.RESUMED)
    return menuProvider
}

fun formatDate(input: String?): String? {
    // Define the input date formats
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Define the output date format
    val outputFormat = SimpleDateFormat("MMMM dd yyyy", Locale.getDefault())

    // Try to parse the input string to a Date object using both formats
    val date = when {
        input == null -> Date()
        input.contains(":") -> dateTimeFormat.parse(input)
        else -> dateFormat.parse(input)
    }

    // Format the Date object to the desired output format
    return date?.let { outputFormat.format(it) }
}

private fun generateKey(): String {
    val random = SecureRandom()
    val bytes = ByteArray(16)
    random.nextBytes(bytes)

    return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP).trim()
}

@SuppressLint("GetInstance")
fun encryptKey(): String {
    val secret = "bo6s8t5qKAiWwWL2"
    val plainKey = generateKey()
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKey = SecretKeySpec(secret.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    return android.util.Base64.encodeToString(
        cipher.doFinal(plainKey.toByteArray()),
        android.util.Base64.NO_WRAP
    )
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

fun Fragment.openBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun openFeedPopupmenu(
    view: View,
    @MenuRes menuRes: Int,
    onMenuItemSelected: (MenuItem) -> Unit
) {
    val popUpMenu = PopupMenu(App.application, view)
    popUpMenu.menuInflater.inflate(menuRes, popUpMenu.menu)
    popUpMenu.setOnMenuItemClickListener {
        onMenuItemSelected(it!!)
        return@setOnMenuItemClickListener true
    }
    popUpMenu.show()
}