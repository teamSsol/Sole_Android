package cmc.sole.android.MyCourse.Write

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cmc.sole.android.MyCourse.TagButton

class MyCourseWriteViewModel: ViewModel() {
    var date: MutableLiveData<String> = MutableLiveData<String>()
    var time: MutableLiveData<String> = MutableLiveData<String>()
    var tag: MutableLiveData<List<TagButton>> = MutableLiveData<List<TagButton>>()
    var checkTag: MutableList<Boolean> = mutableListOf()

    init {
        date.value = ""
        time.value = ""
        checkTag.add(0, false)
        checkTag.add(1, false)
        checkTag.add(2, false)
        checkTag.add(3, false)
        checkTag.add(4, false)
        checkTag.add(5, false)
        checkTag.add(6, false)
        checkTag.add(7, false)
        checkTag.add(8, false)
        checkTag.add(9, false)
        checkTag.add(10, false)
        checkTag.add(11, false)
        checkTag.add(12, false)
        checkTag.add(13, false)
        checkTag.add(14, false)
        checkTag.add(15, false)
        checkTag.add(16, false)
        checkTag.add(17, false)
    }

    fun setDate(value: String) {
        date.value = value
    }

    fun setTime(value: String) {
        time.value = value
    }

    fun setTag(value: List<TagButton>) {
        tag.value = value
    }

    fun setCheckTag(position: Int) {
        checkTag[position] = !checkTag[position]
    }

    fun getDate(): String {
        return date.value.toString()
    }

    fun getTime(): String {
        return time.value.toString()
    }

    fun getTag(): List<TagButton>? {
        return tag.value
    }

    fun getCheckTagAll(): MutableList<Boolean> {
        return checkTag
    }

    fun getCheckTag(position: Int): Boolean {
        return checkTag[position]
    }
}