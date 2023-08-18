package io.amntoppo.githubpr.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType>NetworkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveRequest: suspend(RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = {true}
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveRequest(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, null) }
        }
    }
    else {
        query().map { Resource.Success(it)}
    }
    emitAll(flow)
}
    .catch {
        emit(Resource.Error(it, null))
    }