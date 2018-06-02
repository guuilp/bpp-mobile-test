package guuilp.com.github.bppmobiletest.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import guuilp.com.github.bppmobiletest.data.local.BPPLocal
import guuilp.com.github.bppmobiletest.data.remote.BPPService
import guuilp.com.github.bppmobiletest.data.remote.LoginRequest
import guuilp.com.github.bppmobiletest.data.remote.LoginResponse
import guuilp.com.github.bppmobiletest.network.Resource
import guuilp.com.github.bppmobiletest.utils.RateLimiter
import guuilp.com.github.bppmobiletest.utils.mainThread
import java.util.concurrent.TimeUnit

class BPPRepository(val local: BPPLocal,
                    val remote: BPPService){

    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun login(email: String, password: String): LiveData<Resource<LoginResponse>> {
        val result = MediatorLiveData<Resource<LoginResponse>>()

        Resource.loading(null)

        val call = remote.login(email, password)

        result.addSource(call){ response ->
            if(response != null && response.isSuccessful && response.body!!.code == "200"){
                mainThread {
                    result.value = Resource.success(response.body)
                }
            } else {
                mainThread {
                    result.value = response?.body?.let { Resource.error(it.message.toString(), null) }
                }
            }
        }

        return result
    }
}