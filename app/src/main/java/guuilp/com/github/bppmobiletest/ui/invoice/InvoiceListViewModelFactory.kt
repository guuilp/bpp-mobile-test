package guuilp.com.github.bppmobiletest.ui.invoice

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import guuilp.com.github.bppmobiletest.data.BPPRepository

@Suppress("UNCHECKED_CAST")
class InvoiceListViewModelFactory(private val repository: BPPRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InvoiceListViewModel(repository) as T
    }
}