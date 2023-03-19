package cmc.sole.android.Utils

import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.R

object TagTranslator {
    fun tagTranslate(activity: AppCompatActivity, tag: String): String {
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
            else -> "알 수 없는 태그"
        }
    }
}