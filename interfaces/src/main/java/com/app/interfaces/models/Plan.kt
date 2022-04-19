package com.app.interfaces.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val collaborators: Int,
    val name: String,
    val private_repos: Int,
    val space: Int
) : Parcelable