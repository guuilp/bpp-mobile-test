package guuilp.com.github.bppmobiletest.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.Nullable
import guuilp.com.github.bppmobiletest.data.local.BPPLocal
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.data.remote.BPPService
import guuilp.com.github.bppmobiletest.data.remote.LoginResponse
import guuilp.com.github.bppmobiletest.network.ApiResponse
import guuilp.com.github.bppmobiletest.network.NetworkBoundResource
import guuilp.com.github.bppmobiletest.network.Resource
import guuilp.com.github.bppmobiletest.utils.RateLimiter
import guuilp.com.github.bppmobiletest.utils.mainThread
import java.util.concurrent.TimeUnit

class BPPRepository(val local: BPPLocal,
                    val remote: BPPService){

    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun login(email: String, password: String?): LiveData<Resource<LoginResponse>> {
        val result = MediatorLiveData<Resource<LoginResponse>>()

        Resource.loading(null)

        val call = remote.login(email, password)

        result.addSource(call){ response ->
            if(response != null && response.isSuccessful && response.body!!.code == "200"){
                mainThread {
                    result.value = Resource.success(response.body)
                }
            } else {
                var resource: Resource<LoginResponse>? = null

                if(response?.body?.message != null) resource = Resource.error(response.body.message.toString(), null)
                else if(response?.errorMessage != null) resource = Resource.error(response.errorMessage, null)

                mainThread {
                    result.value = resource
                }
            }
        }

        return result
    }

    fun getInvoiceList(): LiveData<Resource<List<Invoice>>>{
        val key = "InvoiceList"

        return object : NetworkBoundResource<List<Invoice>, List<Invoice>>() {
            override fun saveCallResult(item: List<Invoice>) {
                local.saveInvoiceList(item)
            }

            override fun shouldFetch(@Nullable data: List<Invoice>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(key)
            }

            override fun loadFromDb(): LiveData<List<Invoice>> {
                return local.getInvoiceList()
            }

            override fun createCall(): LiveData<ApiResponse<List<Invoice>>> {
                return remote.getInvoiceList()
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(key)
            }
        }.asLiveData()
    }
}