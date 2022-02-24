package com.bcr.moviejetpackcompose

import android.app.Application
import com.bcr.moviejetpackcompose.core.data.Session

val session: Session by lazy {
    App.session!!
}

class App: Application() {
    companion object {
        var session: Session? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        session = Session(applicationContext)
    }
}