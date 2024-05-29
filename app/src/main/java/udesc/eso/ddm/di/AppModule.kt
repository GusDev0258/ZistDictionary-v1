package udesc.eso.ddm.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import udesc.eso.ddm.infra.http.TranslationHttpDAO
import udesc.eso.ddm.infra.repository.AuthRepository
import udesc.eso.ddm.infra.repository.DictionaryRepository
import udesc.eso.ddm.infra.repository.UserRepository
import udesc.eso.ddm.infra.repository.WordRepository
import udesc.eso.ddm.viewmodel.RegisterViewModel
import udesc.eso.ddm.viewmodel.LoginViewModel
import udesc.eso.ddm.viewmodel.UserViewModel
import udesc.eso.ddm.viewmodel.DictionaryViewModel
import udesc.eso.ddm.viewmodel.WordViewModel

val appModule = module {
    singleOf(::TranslationHttpDAO)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    single {
        UserRepository(get())
    }
    single {
        DictionaryRepository(get(), get())
    }
    single {
        WordRepository(get(), get())
    }
    viewModelOf(::UserViewModel)
    viewModelOf(::DictionaryViewModel)
    viewModelOf(::WordViewModel)
}

val httpModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 480000
                connectTimeoutMillis = 480000
                socketTimeoutMillis = 480000
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