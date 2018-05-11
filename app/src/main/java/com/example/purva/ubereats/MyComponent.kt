package com.example.purva.ubereats

import com.example.purva.ubereats.ui.address.AddressActivity
import javax.inject.Singleton

import dagger.Component

@Component(modules = arrayOf(SharedPrefencesModule::class))
@Singleton
interface MyComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: AddressActivity)

}