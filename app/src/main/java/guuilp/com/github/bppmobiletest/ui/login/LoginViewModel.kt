package guuilp.com.github.bppmobiletest.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import guuilp.com.github.bppmobiletest.data.BPPRepository
import guuilp.com.github.bppmobiletest.data.local.User
import guuilp.com.github.bppmobiletest.data.remote.LoginResponse
import guuilp.com.github.bppmobiletest.network.Resource
import guuilp.com.github.bppmobiletest.utils.AbsentLiveData
import guuilp.com.github.bppmobiletest.utils.encode

class LoginViewModel(private val repository: BPPRepository): ViewModel(){

    val response: LiveData<Resource<LoginResponse>>

    private val user: MutableLiveData<User> = MutableLiveData()

    init {
        response = Transformations.switchMap(user, {
            when {
                it == null -> AbsentLiveData.create()
                else -> repository.login(it.email, it.password)
            }
        })
    }

    fun setUser(email: String?, password: String?, force:Boolean) {
        if(email == null || password == null) return
        if ((email == user.value?.email || password == user.value?.password) && !force) {
            return
        }

        user.value = User(email, password.encode())
    }
}