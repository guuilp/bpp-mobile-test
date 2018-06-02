package guuilp.com.github.bppmobiletest.data.remote

import android.arch.lifecycle.LiveData
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.network.ApiResponse
import retrofit2.http.*

interface BPPService {

    @POST("/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String, @Field("password") password: String): LiveData<ApiResponse<LoginResponse>>

    @GET("/invoice")
    fun getInvoiceList(): LiveData<ApiResponse<List<Invoice>>>

}