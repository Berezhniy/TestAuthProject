package ru.startandroid.testauthproject

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.multidex.MultiDex



class App : Application () {

    override fun onCreate() {
        super.onCreate()
    }
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}