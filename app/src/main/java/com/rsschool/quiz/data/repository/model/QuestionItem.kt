package com.rsschool.quiz.data.repository.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes

data class QuestionItem(
    @StringRes val questionRes: Int,
    val answerOptions: IntArray?,
    val answerNumber: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createIntArray(),
        parcel.readInt()
    ) {
    }

    companion object CREATOR : Parcelable.Creator<QuestionItem> {
        override fun createFromParcel(parcel: Parcel): QuestionItem {
            return QuestionItem(parcel)
        }

        override fun newArray(size: Int): Array<QuestionItem?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(questionRes)
        parcel.writeIntArray(answerOptions)
        parcel.writeInt(answerNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionItem

        if (questionRes != other.questionRes) return false
        if (answerOptions != null) {
            if (other.answerOptions == null) return false
            if (!answerOptions.contentEquals(other.answerOptions)) return false
        } else if (other.answerOptions != null) return false
        if (answerNumber != other.answerNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = questionRes
        result = 31 * result + (answerOptions?.contentHashCode() ?: 0)
        result = 31 * result + answerNumber
        return result
    }
}