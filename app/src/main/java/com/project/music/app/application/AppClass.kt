package com.project.music.app.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        sApplication = this
    }

    companion object {
        private var sApplication: Application? = null
        private var instance: AppClass? = null

        fun getApplication(): Application? {
            return sApplication
        }

        fun getContext(): Context {
            return getApplication()!!.applicationContext
        }

        @Synchronized
        fun getInstance(): AppClass? {
            return instance
        }
    }


}