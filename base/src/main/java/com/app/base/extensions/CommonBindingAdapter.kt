package com.app.base.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType.*
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.app.base.utils.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import java.io.File


@BindingAdapter(
    value = ["android:src", "placeholderRes", "errorRes", "android:scaleType", "isCircle", "isRoundCorners", "cornerType"],
    requireAll = false
)
fun loadImageDrawable(
    imageView: ImageView,
    image: Any?,
    placeholderResId: Drawable? = null,
    errorResId: Drawable? = null,
    scaleType: ImageView.ScaleType? = null,
    isCircle: Boolean = false,
    isRoundCorners: Boolean = false,
    cornerType: RoundedCornersTransformation.CornerType? = RoundedCornersTransformation.CornerType.ALL
) {
    val requestManager = Glide.with(imageView)
    var requestBuilder: RequestBuilder<*>? = null
    if (image != null && image != 0) {
        requestBuilder = when (image) {
            is String -> if (image.startsWith("http://") || image.startsWith("https://")) {
                requestManager.load(image)
            } else {
                requestManager.load("http://100.25.202.132/upm-api/public/$image")
            }
            is Uri -> requestManager.load(image as Uri?)
            is Drawable -> requestManager.load(image as Drawable?)
            is Bitmap -> requestManager.load(image as Bitmap?)
            is Int -> requestManager.load(image as Int?)
            is File -> requestManager.load(image as File?)
            is Array<*> -> requestManager.load(image as Array<*>?)
            else -> requestManager.load(image)
        }
    }
    var options = RequestOptions()
    options = options.override(imageView.width, imageView.height)
    if (placeholderResId != null) {
        if (requestBuilder == null) {
            requestBuilder = requestManager.load(placeholderResId)
        }
    }
    if (scaleType != null) {
        when (scaleType) {
            CENTER_CROP -> options = options.centerCrop()
            FIT_CENTER -> options = options.fitCenter()
            CENTER_INSIDE -> options = options.centerInside()
            MATRIX -> TODO()
            FIT_XY -> TODO()
            FIT_START -> TODO()
            FIT_END -> TODO()
            CENTER -> TODO()
        }
    }
    if (isCircle) {
        options = options.circleCrop()
    }
    if (isRoundCorners) {
        options =
            RequestOptions.bitmapTransform(
                MultiTransformation(
                    CenterCrop(), RoundedCornersTransformation(
                        20, 0, cornerType
                            ?: RoundedCornersTransformation.CornerType.ALL
                    )
                )
            )
    }
    // ?.transition(withCrossFade())
    requestBuilder?.apply(options.placeholder(placeholderResId).error(errorResId))
        ?.into(imageView)
}


@BindingAdapter(
    value = ["drawableSrc"]
)
fun setImageDrawable(
    iv: ImageView,
    image: Any?
) {
    if (image != null)
        when (image) {
            is Drawable -> iv.setImageDrawable(image)
            is Int -> iv.setImageResource(image)
        }
}


@BindingAdapter("htmlText")
fun setHtmlText(textView: TextView, text: String?) {
    text?.let {
        if (!TextUtils.isEmpty(text)) {
            textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

}

@BindingAdapter("android:background")
fun setBackground(view: View, resId: Int?) {
    if (resId != null) {
        view.setBackgroundResource(resId)
    } else {
        view.background = null
    }
}

@BindingAdapter("htmlTextRes")
fun setHtmlText(textView: TextView, resId: Int?) {
    resId?.let {
        textView.text =
            HtmlCompat.fromHtml(textView.context.getString(resId), HtmlCompat.FROM_HTML_MODE_LEGACY)

    }
}

@BindingAdapter(value = ["errorMessage"], requireAll = true)
fun setError(et: TextInputLayout, errorMessage: String?) {
    et.error = errorMessage
}

@BindingAdapter("android:text")
fun setSpannableStringBuilder(textView: TextView, spannable: SpannableStringBuilder?) {
    spannable?.let {
        textView.text = it
    }
}

@BindingAdapter("bgColor")
fun changeBackgroundDrawableColor(view: View, @ColorInt color: Int?) {
    if (color != null) {
        view.background?.overrideColor(color)
    }
}


@BindingAdapter("app:checkedChip")
fun checkedChip(cg: ChipGroup, @IdRes id: Int) {
    cg.check(id)
}

enum class BgType {
    ALL, TOP, BOTTOM, CENTER
}