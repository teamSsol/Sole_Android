package cmc.sole.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupAgreeViewModel: ViewModel() {
    var all: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var service: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var personal: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var marketing: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun setAll() {
        all.value = all.value == false
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