package guuilp.com.github.bppmobiletest.data.local

import android.arch.lifecycle.LiveData

class BPPLocal(private val bppDb: BPPDatabase){
    fun saveTweetsFromUser(listTweet: List<Invoice>){
        bppDb.bppDao().insertInvoiceList(listTweet)
    }

    fun getTweetsFromUser(): LiveData<List<Invoice>> {
        return bppDb.bppDao().getInvoiceList()
    }
}