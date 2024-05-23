package udesc.eso.ddm.infra.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import udesc.eso.ddm.model.TranslationRequest
import udesc.eso.ddm.model.TranslationResponse

class TranslationHttpDAO(private val httpClient: HttpClient) {
    suspend fun translateWord(translateRequest: TranslationRequest): TranslationResponse {
        val response = httpClient.post {
            url(HttpRoutes.TRANSLATE_ROUTE)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(translateRequest)
        }
        return response.body()
    }
}