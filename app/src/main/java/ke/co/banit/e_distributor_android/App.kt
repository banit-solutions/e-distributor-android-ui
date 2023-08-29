package ke.co.banit.e_distributor_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @Author: Angatia Benson
 * @Date: 18/08/2022
 * Copyright (c) 2022 Bantechnis
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var application: Application
            private set
    }
}