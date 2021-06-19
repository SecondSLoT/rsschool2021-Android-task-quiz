package com.rsschool.quiz.data.repository.model

import android.os.Parcel
import android.os.Parcelable

data class QuestionItem(
    val question: String?, // @StringRes
    val answerOptions: Array<String>?,
    val answerNumber: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArray(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringArray(answerOptions)
        parcel.writeInt(answerNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionItem> {
        override fun createFromParcel(parcel: Parcel): QuestionItem {
            return QuestionItem(parcel)
        }

        override fun newArray(size: Int): Array<QuestionItem?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionItem

        if (question != other.question) return false
        if (!answerOptions.contentEquals(other.answerOptions)) return false
        if (answerNumber != other.answerNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + answerOptions.contentHashCode()
        result = 31 * result + answerNumber
        return result
    }
}