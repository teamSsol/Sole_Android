package cmc.sole.android.Signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupAgreeViewModel: ViewModel() {
    var all: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var location: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var service: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var personal: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var marketing: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        all.value = false
        location.value = false
        service.value = false
        personal.value = false
        marketing.value = false
    }

    fun setAll() {
        all.value = all.value == false
    }

    fun setLocation() {
        location.value = location.value != true
    }

    fun setService() {
        service.value = service.value != true
    }

    fun setPersonal() {
        personal.value = personal.value != true
    }

    fun setMarketing() {
        marketing.value = marketing.value != true
    }

    fun getAll(): Boolean? {
        return all.value
    }

    fun getLocation(): Boolean? {
        return location.value
    }

    fun getService(): Boolean? {
        return service.value
    }

    fun getPersonal(): Boolean? {
        return personal.value
    }

    fun getMarketing(): Boolean? {
        return marketing.value
    }
}