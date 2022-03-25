package com.owenjdunn.game2048

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * The ViewModel of the application. Used to store and retrieve data from various fragments of the
 * app. Acts between the model and the passive view.
 */
class UserDataViewModel : ViewModel() {
    @Suppress("PropertyName")
    private var _usr = MutableLiveData<String>()    // the backing store for property userId

    // Declare a property userId and its getter
    val userId
        get() = _usr
}