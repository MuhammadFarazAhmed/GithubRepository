package com.app.base.extensions

import android.content.ContentResolver
import android.content.Context
import android.content.res.TypedArray
import android.database.Cursor
import android.graphics.*
import android.graphics.drawable.*
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.app.base.R
import com.app.base.ui.ProgressDialogFragment
import com.app.base.utils.Constants
import org.joda.time.*
import java.io.File
import java.util.*
import kotlin.math.sqrt


fun Fragment.getColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(requireContext(), color)
}

fun View.closeKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.openKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(
        this.windowToken,
        InputMethodManager.SHOW_FORCED, 0
    )
}

fun Fragment.getDrawable(@DrawableRes res: Int) = ContextCompat.getDrawable(requireContext(), res)

fun Fragment.showProgress(title: String = "", message: String = "") {
    if (!isAdded) return
    val fragment = childFragmentManager.findFragmentByTag("showProgress")
    if (fragment == null)
        ProgressDialogFragment.newInstance(title, message)
            .show(childFragmentManager, "showProgress")
}

fun Fragment.hideProgress() {
    try {
        if (isAdded) {
            childFragmentManager.fragments.filter {
                it.tag == "showProgress"
            }.forEach {
                (it as DialogFragment).dismissAllowingStateLoss()
            }
        }
    } catch (e: Exception) {
    }
}

fun Fragment.onLoadingProgress(): (Boolean) -> Unit {
    return {
        if (isAdded)
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
    }
}

/*fun FragmentActivity.doLightStatusbar() {
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.grey_bg_on_dark_bg)
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        Configuration.UI_MODE_NIGHT_NO -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = getColor(android.R.color.white)
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            }
        }
    }
}

fun FragmentActivity.doNormalStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}*/

fun getMimeType(uri: Uri, contentResolver: ContentResolver): String {
    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    return (if (cursor != null) {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
        val f = cursor.getString(idx)
        cursor.close()
        f
    } else {
        when {
            uri.scheme.equals(ContentResolver.SCHEME_CONTENT) -> {
                contentResolver.getType(uri)
            }
            else -> {
                val ext =
                    MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
                    ?: tryGetMimeTypeFromExtension(uri)
            }
        } ?: ""
    }).toString()
}

fun tryGetMimeTypeFromExtension(uri: Uri): String {
    val fileName = uri.toString()

    val dotIndex = fileName.lastIndexOf(".")
    val extension = if (dotIndex >= 0) {
        fileName.substring(dotIndex + 1)
    } else null

    return if (extension != null) {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: ""
    } else ""
}

fun getExtensionFromMimeType(uri: Uri, contentResolver: ContentResolver): String? {
    val mime = MimeTypeMap.getSingleton()
    return mime.getExtensionFromMimeType(contentResolver.getType(uri))
}

fun String.isUrl(): Boolean {
    return this.contains("http://") || this.contains("https://")
}


fun getOffersFormattedTime(context: Context, createdAt: DateTime): String {
    val difference = Duration(createdAt, DateTime.now())
    return when {
        difference.toStandardSeconds().isLessThan(Seconds.seconds(60)) -> {
            context.getString(R.string.sec_ago, difference.standardSeconds.toInt())
        }
        difference.toStandardMinutes().isLessThan(Minutes.minutes(60)) -> {
            context.getString(R.string.min_ago, difference.standardMinutes.toInt())
        }
        difference.toStandardHours().isLessThan(Hours.hours(24)) -> {
            context.resources.getQuantityString(
                R.plurals.numOfHours,
                difference.standardHours.toInt(),
                difference.standardHours.toInt()
            )
        }
        difference.toStandardDays().days <= 7 -> {
            context.resources.getQuantityString(
                R.plurals.numOfDays,
                difference.standardDays.toInt(),
                difference.standardDays.toInt()
            )
        }
        difference.toPeriod().toStandardWeeks().isLessThan(Weeks.THREE) -> {
            context.resources.getQuantityString(
                R.plurals.numOfWeeks,
                difference.toPeriod().toStandardWeeks().weeks,
                difference.toPeriod().toStandardWeeks().weeks
            )
        }
        else -> createdAt.toString(Constants.DATE_TIME_VIEW_FORMAT_5)
    }
}

fun getFormattedDateTime(context: Context, createdAt: DateTime): String {
    return when {
        isToday(createdAt) -> {
            context.resources.getString(
                R.string.today,
                createdAt.toString(Constants.TIME_VIEW_FORMAT_1).toUpperCase()
            )
        }
        isYesterday(createdAt) -> {
            context.resources.getString(
                R.string.yesterday,
                createdAt.toString(Constants.TIME_VIEW_FORMAT_1).toUpperCase()
            )
        }
        else -> createdAt.toString(Constants.DATE_TIME_VIEW_FORMAT_2).toUpperCase()
    }
}

fun isToday(time: DateTime?) = LocalDate.now().compareTo(LocalDate(time)) == 0

fun isYesterday(time: DateTime?) = LocalDate.now().minusDays(1).compareTo(LocalDate(time)) == 0


/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Float.toPixel(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Float.toDP(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


fun Drawable.overrideColor(@ColorInt colorInt: Int) {
    when (this) {
        is GradientDrawable -> setColor(colorInt)
        is ShapeDrawable -> paint.color = colorInt
        is ColorDrawable -> color = colorInt
    }
}


fun Context.getMatColor(typeColor: String, index: Int): Int {
    var returnColor = Color.BLACK
    val arrayId: Int = this.resources.getIdentifier(
        "mdcolor_$typeColor",
        "array",
        this.packageName
    )
    if (arrayId != 0) {
        val colors: TypedArray = this.resources.obtainTypedArray(arrayId)
        //val index = (Math.random() * colors.length()).toInt()
        returnColor = colors.getColor(index, Color.BLACK)
        colors.recycle()
    }
    return returnColor
}

fun Context.getMatColor(typeColor: String): Int {
    var returnColor = Color.BLACK
    val arrayId: Int = this.resources.getIdentifier(
        "mdcolor_$typeColor",
        "array",
        this.packageName
    )
    if (arrayId != 0) {
        val colors: TypedArray = this.resources.obtainTypedArray(arrayId)
        val index = (Math.random() * colors.length()).toInt()
        returnColor = colors.getColor(index, Color.BLACK)
        colors.recycle()
    }
    return returnColor
}

fun Context.isColorLight(color: Int): Boolean {
    if (android.R.color.transparent == color) return true
    val rgb = intArrayOf(Color.red(color), Color.green(color), Color.blue(color))
    val brightness = sqrt(
        rgb[0] * rgb[0] * .241 + rgb[1] * rgb[1] * .691 + rgb[2] * rgb[2] * .068
    ).toInt()
    return brightness > 214
}

fun Context.writeOnDrawable(text: String, isAlive: Boolean): Drawable? {
    ResourcesCompat.getDrawable(resources, R.drawable.circle_blue, null)?.let {
        val paintText = Paint()
        it.overrideColor(
            ContextCompat.getColor(
                this,
                if (isAlive) R.color.blue else R.color.red
            )
        )
        val b = it.toBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(b)
        // val d: Drawable =
        paintText.style = Paint.Style.FILL
        paintText.color = ContextCompat.getColor(this, R.color.white)
        paintText.isAntiAlias = true
        paintText.textSize = 16f.toPixel(this)
        paintText.typeface = Typeface.DEFAULT_BOLD
        paintText.textAlign = Paint.Align.CENTER
        val xPos = (canvas.width / 2).toFloat()
        val yPos = (canvas.height / 2 - (paintText.descent() + paintText.ascent()) / 2)
        canvas.drawText(text, xPos, yPos, paintText)
        // d.draw(canvas)

        return BitmapDrawable(this.resources, b)
    }
    return null
}

