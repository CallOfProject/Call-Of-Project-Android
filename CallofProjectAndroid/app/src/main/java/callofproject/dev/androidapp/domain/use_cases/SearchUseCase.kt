package callofproject.dev.androidapp.domain.use_cases

import android.content.Context
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.search.SearchUserAndProjectResponse
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val context: Context,
    private val pref: IPreferences,
    private val service: ICallOfProjectService,
) {

    suspend operator fun invoke(
        keyword: String,
        page: Int = 1
    ): Flow<Resource<MultipleResponseMessagePageable<SearchUserAndProjectResponse>>> {
        return flow {

            emit(Resource.Loading())

            try {
                val response = service.search(
                    keyword = keyword,
                    page = page,
                    token = pref.getToken()!!
                )
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}