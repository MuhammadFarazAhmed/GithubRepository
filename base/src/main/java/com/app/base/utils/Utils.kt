package com.app.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import java.lang.reflect.InvocationTargetException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object Utils {

    fun getFacebookKeyHash(context: Context): String {
        var keyhash = ""

        try {
            @SuppressLint("PackageManagerGetSignatures") val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                Log.d("KeyHash:", keyhash)
            }
        } catch (ignored: PackageManager.NameNotFoundException) {

        } catch (ignored: NoSuchAlgorithmException) {
        }

        return keyhash
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length >= 8
    }

    fun isValidPhone(target: String): Boolean {
        return !TextUtils.isEmpty(target) && TextUtils.isDigitsOnly(target) && target.length == 12
    }

    fun mergeNames(firstName: String, lastName: String): String {
        return String.format("%s %s", firstName, lastName)
    }

    inline fun <T : Any> ifLet(vararg elements: T?, closure: (List<T>) -> Unit) {
        if (elements.all { it != null }) {
            closure(elements.filterNotNull())
        }
    }

    fun fitDrawable(resources: Resources, drawable: Drawable, sizePx: Int): Drawable {
        var drawable = drawable
        if (drawable.intrinsicWidth != sizePx || drawable.intrinsicHeight != sizePx) {

            if (drawable is BitmapDrawable) {

                drawable =
                    BitmapDrawable(resources, Bitmap.createScaledBitmap(getBitmap(drawable), sizePx, sizePx, true))
            }
        }
        drawable.setBounds(0, 0, sizePx, sizePx)

        return drawable
    }

    fun getBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else (drawable as? VectorDrawable)?.let { getBitmap(it) }
            ?: throw IllegalArgumentException("unsupported drawable type")
    }


    fun getDeviceCountryCode(context: Context): String {
        var countryCode: String?

        // try to get country code from TelephonyManager service
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        // query first getSimCountryIso()
        countryCode = tm.simCountryIso
        if (countryCode != null && countryCode.length == 2)
            return countryCode.toLowerCase()

        if (tm.phoneType == TelephonyManager.PHONE_TYPE_CDMA) {
            // special case for CDMA Devices
            countryCode = getCDMACountryIso()
        } else {
            // for 3G devices (with SIM) query getNetworkCountryIso()
            countryCode = tm.networkCountryIso
        }

        if (countryCode != null && countryCode.length == 2)
            return countryCode.toLowerCase()

        // if network country not available (tablets maybe), get country code from Locale class
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            countryCode = context.resources.configuration.locales.get(0).country
        } else {
            countryCode = context.resources.configuration.locale.country
        }

        return if (countryCode != null && countryCode.length == 2) countryCode.toLowerCase() else "us"

        // general fallback to "us"
    }

    fun getCountryZipCode(context: Context, countryCode: String): String? {
        val rl = context.resources.getStringArray(com.app.base.R.array.CountryCodes)
        rl.forEach { element ->
            val value = element.split(",")
            if (countryCode == value[1].toLowerCase()) {
                return value[0]
            }
        }
        return null
    }


    @SuppressLint("PrivateApi")
    private fun getCDMACountryIso(): String? {
        try {
            // try to get country code from SystemProperties private class
            val systemProperties = Class.forName("android.os.SystemProperties")
            val get = systemProperties.getMethod("get", String::class.java)

            // get homeOperator that contain MCC + MNC
            val homeOperator = get.invoke(
                systemProperties,
                "ro.cdma.home.operator.numeric"
            ) as String

            // first 3 chars (MCC) from homeOperator represents the country code
            val mcc = Integer.parseInt(homeOperator.substring(0, 3))

            // mapping just countries that actually use CDMA networks
            when (mcc) {
                330 -> return "PR"
                310 -> return "US"
                311 -> return "US"
                312 -> return "US"
                316 -> return "US"
                283 -> return "AM"
                460 -> return "CN"
                455 -> return "MO"
                414 -> return "MM"
                619 -> return "SL"
                450 -> return "KR"
                634 -> return "SD"
                434 -> return "UZ"
                232 -> return "AT"
                204 -> return "NL"
                262 -> return "DE"
                247 -> return "LV"
                255 -> return "UA"
            }
        } catch (ignored: ClassNotFoundException) {
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: IllegalAccessException) {
        } catch (ignored: InvocationTargetException) {
        } catch (ignored: NullPointerException) {
        }

        return null
    }
}
