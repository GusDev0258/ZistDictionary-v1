package udesc.eso.ddm.di

import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import udesc.eso.ddm.infra.http.TranslationHttpDAO
import udesc.eso.ddm.infra.repository.AuthRepository
import udesc.eso.ddm.viewmodel.RegisterViewModel

val appModule = module {
    singleOf(::TranslationHttpDAO)
    viewModelOf(::RegisterViewModel)
}

val httpModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}

val firebaseAuthModule = module {
    single {
         Firebase.auth
    }
}
val firebaseStoreModule = module {
    single {
        Firebase.firestore
    }
}

val authRepositoryModule = module {
    single {
        AuthRepository(get(), get())
    }
}