package guuilp.com.github.bppmobiletest.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import guuilp.com.github.bppmobiletest.data.BPPRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: BPPRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}