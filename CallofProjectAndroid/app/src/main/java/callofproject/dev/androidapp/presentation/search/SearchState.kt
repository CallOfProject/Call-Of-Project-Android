package callofproject.dev.androidapp.presentation.search

import callofproject.dev.androidapp.domain.dto.search.SearchUserAndProjectResponse

data class SearchState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchResult: SearchUserAndProjectResponse = SearchUserAndProjectResponse()
)
