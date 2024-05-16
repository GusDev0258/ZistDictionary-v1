package udesc.eso.ddm.infra.http

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import udesc.eso.ddm.model.TranslationRequest

class TranslationHttpDAO(private val httpClient: HttpClient) {
    public suspend fun translateWord(translateRequest: TranslationRequest): HttpResponse {
        return httpClient.post {
            url(HttpRoutes.TRANSLATE_ROUTE)
            contentType(ContentType.Application.Json)
            setBody(translateRequest)
        }
    }
}