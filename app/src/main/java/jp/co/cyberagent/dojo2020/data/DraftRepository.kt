package jp.co.cyberagent.dojo2020.data

import jp.co.cyberagent.dojo2020.data.local.DraftDataSource
import jp.co.cyberagent.dojo2020.data.model.Draft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    suspend fun saveDraft(draft: Draft)

    suspend fun fetchAllDraft(): Flow<List<Draft>>

    suspend fun fetchFilteredDraftsByCategory(category: String): Flow<List<Draft>?>

    suspend fun fetchDraftById(id: String): Flow<Draft?>

    suspend fun deleteDraftById(id: String)
}

class DefaultDraftRepository(private val draftDataSource: DraftDataSource) : DraftRepository {
    override suspend fun saveDraft(draft: Draft) {
        draftDataSource.saveDraft(draft)
    }

    override suspend fun fetchAllDraft(): Flow<List<Draft>> {
        return draftDataSource.fetchAllDraft()
    }

    override suspend fun fetchFilteredDraftsByCategory(category: String): Flow<List<Draft>?> {
        return draftDataSource.fetchFilteredDraftsByCategory(category)
    }

    override suspend fun fetchDraftById(id: String): Flow<Draft?> {
        return draftDataSource.fetchDraftById(id)
    }

    override suspend fun deleteDraftById(id: String) {
        return draftDataSource.deleteDraftById(id)
    }
}