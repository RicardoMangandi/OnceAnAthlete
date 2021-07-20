package com.example.oa

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseUser


//.server("http://18.118.189.127/parse/")
//.server("http://18.191.42.67/parse/")


class ParseServer : Application() {
    override fun onCreate() {
        super.onCreate()


        Parse.enableLocalDatastore(this)


        Parse.initialize(Parse.Configuration.Builder(applicationContext)
                .applicationId("myappID")
                .clientKey("31YGHf7Vq90O")
                .server("http://18.221.96.0/parse/")
                .build())

        ParseUser.enableRevocableSessionInBackground()


        val defaultACL : ParseACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)
    }
}