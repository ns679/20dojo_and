package jp.co.cyberagent.dojo2020.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.cyberagent.dojo2020.DI
import jp.co.cyberagent.dojo2020.data.model.Memo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
    private val repository = DI.injectLocalDataSource(context)

    //case A
    private val mutableLiveData: MutableLiveData<List<Memo>> = MutableLiveData()
    val liveData: LiveData<List<Memo>>
        get() = mutableLiveData

    //case B
    //val liveData = liveData<List<Memo>> {
    //    emitSource(repository.fetchAll().asLiveData())
    //}

    fun reload() = viewModelScope.launch {
        repository.fetchAll().collect {
            mutableLiveData.value = it
        }
    }

    fun save(memo: Memo) = viewModelScope.launch {
        repository.save(memo)
    }
}