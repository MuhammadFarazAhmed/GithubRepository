package com.app.interfaces.models.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Message
 */

@Parcelize
data class Message(
    var code: Int = 0,
    var message: String = ""
) : Parcelable



