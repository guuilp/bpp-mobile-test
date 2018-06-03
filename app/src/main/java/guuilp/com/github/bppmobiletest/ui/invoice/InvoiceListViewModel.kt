package guuilp.com.github.bppmobiletest.ui.invoice

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import guuilp.com.github.bppmobiletest.data.BPPRepository
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.network.Resource
import guuilp.com.github.bppmobiletest.utils.AbsentLiveData

class InvoiceListViewModel(private val repository: BPPRepository): ViewModel(){

    val invoiceList: LiveData<Resource<List<Invoice>>>

    private val userLogged: MutableLiveData<Boolean> = MutableLiveData()

    init {
        invoiceList = Transformations.switchMap(userLogged, {
            if(it) repository.getInvoiceList()
            else AbsentLiveData.create()
        })
    }

    fun setUserLogged(logged: Boolean) {
        userLogged.value = logged
    }
}