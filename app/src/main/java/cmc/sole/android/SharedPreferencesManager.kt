package cmc.sole.android

import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import com.sole.android.ApplicationClass.Companion.mSharedPreferences

// AccessToken
fun removeAccessToken() {
    val editor = mSharedPreferences.edit()
    editor.remove("accessToken")
    editor.commit()
}

fun saveAccessToken(url: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("accessToken", url)
    editor.apply()
}

// FCMToken
fun removeFCMToken() {
    val editor = mSharedPreferences.edit()
    editor.remove("fcmToken")
    editor.commit()
}

fun saveFCMToken(url: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("fcmToken", url)
    editor.apply()
}

// MemberId
fun removeMemberId() {
    val editor = mSharedPreferences.edit()
    editor.remove("memberId")
    editor.commit()
}

fun saveMemberId(memberId: Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("memberId", memberId)
    editor.apply()
}

// Nickname
fun removeNickname() {
    val editor = mSharedPreferences.edit()
    editor.remove("nickname")
    editor.commit()
}

fun saveNickname(nickname: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("nickname", nickname)
    editor.apply()
}

// 프로필 이미지
fun removeProfileImgUrl() {
    val editor = mSharedPreferences.edit()
    editor.remove("profileImgUrl")
    editor.commit()
}

fun saveProfileImgUrl(url: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("profileImgUrl", url)
    editor.apply()
}

fun removePlaceCategories() {
    val editor = mSharedPreferences.edit()
    editor.remove("placeCategories")
    editor.commit()
}

fun savePlaceCategories(placeCategories: ArrayList<String>) {
    val editor = mSharedPreferences.edit()
    editor.putString("placeCategories", placeCategories.toString())
    editor.apply()
}

fun removeTransCategories() {
    val editor = mSharedPreferences.edit()
    editor.remove("transCategories")
    editor.commit()
}

fun saveTransCategories(transCategories: HashSet<Enum<transCategories>>) {
    val editor = mSharedPreferences.edit()
    editor.putString("transCategories", transCategories.toString())
    editor.apply()
}

fun removeWithCategories() {
    val editor = mSharedPreferences.edit()
    editor.remove("withCategories")
    editor.commit()
}

fun saveWithCategories(withCategories: HashSet<Enum<withCategories>>) {
    val editor = mSharedPreferences.edit()
    editor.putString("withCategories", withCategories.toString())
    editor.apply()
}

fun getAccessToken(): String? = mSharedPreferences.getString("accessToken", null)
fun getFCMToken(): String? = mSharedPreferences.getString("fcmToken", null)
fun getNickname(): String? = mSharedPreferences.getString("nickname", null)
fun getMemberId(): Int = mSharedPreferences.getInt("memberId", 0)
fun getProfileImgUrl(): String? = mSharedPreferences.getString("profileImgUrl", null)
fun getPlaceCategories(): String? = mSharedPreferences.getString("placeCategories", null)
fun getTransCategories(): String? = mSharedPreferences.getString("transCategories", null)
fun getWithCategories(): String? = mSharedPreferences.getString("withCategories", null)