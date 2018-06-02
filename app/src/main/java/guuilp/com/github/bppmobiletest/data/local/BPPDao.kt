package guuilp.com.github.bppmobiletest.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface BPPDao{

    @Query("SELECT * from invoice")
    fun getInvoiceList(): LiveData<List<Invoice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoiceList(invoiceList: List<Invoice>)
}