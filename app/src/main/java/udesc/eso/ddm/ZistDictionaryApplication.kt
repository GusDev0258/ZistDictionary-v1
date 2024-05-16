package udesc.eso.ddm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import udesc.eso.ddm.di.appModule
import udesc.eso.ddm.di.authRepositoryModule
import udesc.eso.ddm.di.firebaseAuthModule
import udesc.eso.ddm.di.firebaseStoreModule
import udesc.eso.ddm.di.httpModule

class ZistDictionaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ZistDictionaryApplication)
            modules(
                appModule,
                httpModule,
                firebaseAuthModule,
                firebaseStoreModule,
                authRepositoryModule
            )
        }
    }
}