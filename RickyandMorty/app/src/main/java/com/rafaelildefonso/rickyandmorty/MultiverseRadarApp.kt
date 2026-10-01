package com.rafaelildefonso.rickyandmorty

import android.app.Application
import com.rafaelildefonso.rickyandmorty.data.AppContainer

class MultiverseRadarApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}