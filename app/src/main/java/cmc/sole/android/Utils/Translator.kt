package cmc.sole.android.Utils

import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.R

object Translator {
    fun tagEngToKor(activity: AppCompatActivity, tag: String): String {
        return when(tag) {
            "TASTY_PLACE" -> activity.getString(R.string.place_tag1)
            "CAFE" -> activity.getString(R.string.place_tag2)
            "CULTURE_ART" -> activity.getString(R.string.place_tag3)
            "ACTIVITY" -> activity.getString(R.string.place_tag4)
            "HEALING" -> activity.getString(R.string.place_tag5)
            "NATURE" -> activity.getString(R.string.place_tag6)
            "NIGHT_VIEW" -> activity.getString(R.string.place_tag7)
            "HISTORY" -> activity.getString(R.string.place_tag8)
            "THEME_PARK" -> activity.getString(R.string.place_tag9)

            "ALONE" -> activity.getString(R.string.with_tag10)
            "FRIEND" -> activity.getString(R.string.with_tag11)
            "COUPLE" -> activity.getString(R.string.with_tag12)
            "KID" -> activity.getString(R.string.with_tag13)
            "PET" -> activity.getString(R.string.with_tag14)

            "WALK" -> activity.getString(R.string.trans_tag15)
            "BIKE" -> activity.getString(R.string.trans_tag16)
            "CAR" -> activity.getString(R.string.trans_tag17)
            "PUBLIC_TRANSPORTATION" -> activity.getString(R.string.trans_tag18)
            else -> "."
        }
    }

    fun tagKorToEng(activity: AppCompatActivity, tag: String): String {
        return when(tag) {
            activity.getString(R.string.place_tag1) -> "TASTY_PLACE"
            activity.getString(R.string.place_tag2) -> "CAFE"
            activity.getString(R.string.place_tag3) -> "CULTURE_ART"
            activity.getString(R.string.place_tag4) -> "ACTIVITY"
            activity.getString(R.string.place_tag5) -> "HEALING"
            activity.getString(R.string.place_tag6) -> "NATURE"
            activity.getString(R.string.place_tag7) -> "NIGHT_VIEW"
            activity.getString(R.string.place_tag8) -> "HISTORY"
            activity.getString(R.string.place_tag9) -> "THEME_PARK"

            activity.getString(R.string.with_tag10) -> "ALONE"
            activity.getString(R.string.with_tag11) -> "FRIEND"
            activity.getString(R.string.with_tag12) -> "COUPLE"
            activity.getString(R.string.with_tag13) -> "KID"
            activity.getString(R.string.with_tag14) -> "PET"

            activity.getString(R.string.trans_tag15) -> "WALK"
            activity.getString(R.string.trans_tag16) -> "PUBLIC_TRANSPORTATION"
            activity.getString(R.string.trans_tag17) -> "BIKE"
            activity.getString(R.string.trans_tag18) -> "CAR"
            else -> "."
        }
    }

    fun returnTagKorStr(activity: AppCompatActivity, position: Int): String {
        return when(position) {
            1 -> activity.getString(R.string.place_tag1)
            2 -> activity.getString(R.string.place_tag2)
            3 -> activity.getString(R.string.place_tag3)
            4 -> activity.getString(R.string.place_tag4)
            5 -> activity.getString(R.string.place_tag5)
            6 -> activity.getString(R.string.place_tag6)
            7 -> activity.getString(R.string.place_tag7)
            8 -> activity.getString(R.string.place_tag8)
            9 -> activity.getString(R.string.place_tag9)

            10 -> activity.getString(R.string.with_tag10)
            11 -> activity.getString(R.string.with_tag11)
            12 -> activity.getString(R.string.with_tag12)
            13 -> activity.getString(R.string.with_tag13)
            14 -> activity.getString(R.string.with_tag14)

            15 -> activity.getString(R.string.trans_tag15)
            16 -> activity.getString(R.string.trans_tag16)
            17 -> activity.getString(R.string.trans_tag17)
            18 -> activity.getString(R.string.trans_tag18)
            else -> "."
        }
    }

    fun returnTagEngStr(position: Int): Categories {
        return when(position) {
            1 -> Categories.TASTY_PLACE
            2 -> Categories.CAFE
            3 -> Categories.CULTURE_ART
            4 -> Categories.ACTIVITY
            5 -> Categories.HEALING
            6 -> Categories.NATURE
            7 -> Categories.NIGHT_VIEW
            8 -> Categories.HISTORY
            9 -> Categories.THEME_PARK

            10 -> Categories.ALONE
            11 -> Categories.FRIEND
            12 -> Categories.COUPLE
            13 -> Categories.KID
            14 -> Categories.PET

            15 -> Categories.WALK
            16 -> Categories.PUBLIC_TRANSPORTATION
            17 -> Categories.CAR
            18 -> Categories.BIKE
            else -> Categories.BIKE
        }
    }

    fun tagEngPosition(activity: AppCompatActivity, tag: String): Int {
        return when(tag) {
            "TASTY_PLACE" -> 1
            "CAFE" -> 2
            "CULTURE_ART" -> 3
            "ACTIVITY" -> 4
            "HEALING" -> 5
            "NATURE" -> 6
            "NIGHT_VIEW" -> 7
            "HISTORY" -> 8
            "THEME_PARK" -> 9

            "ALONE" -> 10
            "FRIEND" -> 11
            "COUPLE" -> 12
            "KID" -> 13
            "PET" -> 14

            "WALK" -> 15
            "BIKE" -> 16
            "CAR" -> 17
            "PUBLIC_TRANSPORTATION" -> 18
            else -> -1
        }
    }
}