package guuilp.com.github.bppmobiletest.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "invoice")
data class Invoice(
		@PrimaryKey val transactionId: String,
		val mccCode: String,
		val transactionCurrency: String,
		val billingAmount: Double,
		val mccDescription: String,
		val billingCurrency: String,
		val transactionStatus: String,
		val transactionAmount: Double,
		val transactionFormattedDate: String,
		val transactionDate: String,
		val transactionName: String,
		val merchantName: String
)
