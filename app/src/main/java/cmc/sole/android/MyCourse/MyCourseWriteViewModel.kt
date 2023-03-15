package cmc.sole.android.MyCourse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCourseWriteViewModel: ViewModel() {
    var date: MutableLiveData<String> = MutableLiveData<String>()

    init {
        date.value = ""
    }

    fun setDate(value: String) {
        date.value = value
    }

    fun getDate(): String {
        return date.value.toString()
    }
}