package guuilp.com.github.bppmobiletest.data.local

import android.arch.lifecycle.LiveData

class BPPLocal(private val bppDb: BPPDatabase){
    fun saveInvoiceList(listTweet: List<Invoice>){
        bppDb.bppDao().insertInvoiceList(listTweet)
    }

    fun getInvoiceList(): LiveData<List<Invoice>> {
        return bppDb.bppDao().getInvoiceList()
    }
}