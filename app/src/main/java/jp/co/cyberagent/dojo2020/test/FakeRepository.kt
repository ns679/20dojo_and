package jp.co.cyberagent.dojo2020.test

import android.util.Log
import jp.co.cyberagent.dojo2020.data.local.MemoDataSource
import jp.co.cyberagent.dojo2020.data.model.Memo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeRepository : MemoDataSource {
    override suspend fun saveMemo(memo: Memo) {
        Log.d(Constants.REPOSITORY, "insert: $memo")
    }

    override suspend fun fetchAllMemo() = flow {
        val dataList = MemoData.list

        dataList.forEach { Log.d(Constants.REPOSITORY, "fetchAll: $it") }
        emit(dataList)
    }

    override suspend fun fetchMemoById(id: String) = flow {
        emit(MemoData.list.firstOrNull { it.id == id })
    }

    override suspend fun fetchFilteredMemoByCategory(category: String) = flow {
        emit(MemoData.list.filter { it.category == category })
    }

    override suspend fun deleteMemoById(id: String) {
        MemoData.list.removeIf { it.id == id }
    }
}