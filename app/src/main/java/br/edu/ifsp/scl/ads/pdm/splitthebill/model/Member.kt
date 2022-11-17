package br.edu.ifsp.scl.ads.pdm.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Member(
    val id: Int,
    var name: String,
    var valuePaid: Double,
    var description: String,
): Parcelable