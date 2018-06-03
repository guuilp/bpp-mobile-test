package guuilp.com.github.bppmobiletest.ui.invoice

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import guuilp.com.github.bppmobiletest.R
import guuilp.com.github.bppmobiletest.data.local.Invoice
import guuilp.com.github.bppmobiletest.utils.formatCurrency
import guuilp.com.github.bppmobiletest.utils.formatDate
import kotlinx.android.synthetic.main.list_item_invoice.view.*

class InvoiceListAdapter(
        private var invoiceList: ArrayList<Invoice>,
        private val context: Context?,
        private var onItemClickListener: (tweet: Invoice, position: Int) -> Unit) : RecyclerView.Adapter<InvoiceListAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = invoiceList[position]
        holder.let {
            it.bindView(tweet, position, invoiceList.size)
            it.itemView.setOnClickListener {
                onItemClickListener(tweet, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_invoice, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return invoiceList.size
    }

    fun updateDataSet(newInvoiceList: ArrayList<Invoice>) {
        invoiceList = newInvoiceList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(invoice: Invoice, position: Int, listSize: Int) {
            val dateTime = itemView.tvDateTime
            val place = itemView.tvPlace
            val price = itemView.tvPrice
            val priceString = "${invoice.transactionAmount.formatCurrency()} (${invoice.transactionStatus})"

            dateTime.text = invoice.transactionFormattedDate.formatDate()
            place.text = invoice.merchantName
            price.text =  priceString

            if(position == 0) itemView.vTopLine.visibility = View.GONE
            else itemView.vTopLine.visibility = View.VISIBLE

            if(position == listSize - 1) itemView.vBottomLine.visibility = View.GONE
            else itemView.vBottomLine.visibility = View.VISIBLE
        }
    }
}