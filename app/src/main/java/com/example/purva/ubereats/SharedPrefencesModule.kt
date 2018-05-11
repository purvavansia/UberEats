package com.example.purva.ubereats

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import dagger.Module
import dagger.Provides

@Module
class SharedPrefencesModule(context:Context) {

    private val context:Context
    init{
        this.context = context
    }
    @Provides
    fun provideContext(): Context {

        return context
    }

    @Provides
    fun provideSharedPref(): SharedPreferences {

        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
