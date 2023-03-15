package cmc.sole.android.MyCourse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCourseWriteViewModel: ViewModel() {
    var date: MutableLiveData<String> = MutableLiveData<String>()
    var time: MutableLiveData<String> = MutableLiveData<String>()

    init {
        date.value = ""
        time.value = ""
    }

    fun setDate(value: String) {
        date.value = value
    }

    fun setTime(value: String) {
        time.value = value
    }

    fun getDate(): String {
        return date.value.toString()
    }

    fun getTime(): String {
        return time.value.toString()
    }
}