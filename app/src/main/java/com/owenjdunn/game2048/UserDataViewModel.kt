package com.owenjdunn.game2048

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * The ViewModel of the application. Used to store and retrieve data from various fragments of the
 * app. Acts between the model and the passive view. An additional repository class acts between
 * ViewModel and Firebase backend.
 */
class UserDataViewModel : ViewModel() {
    lateinit var userId: MutableLiveData<String?> // the backing store for property userId
    private val repo = Game2048Repository() // repository object

//    init {
//        userId.value = "Guest"  // init value if not logged in
//    }

    /**
     * Pass email and password to repository to interface with the Firebase backend to authenticate
     * the user.
     *
     * The [UserDataViewModel.userId] parameter is set based on the results of the attempted
     * authentication.
     *
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
     */
    fun signInWithEmailAndPassword(email: String, password: String) {
        userId = repo.firebaseSignInWithEmail(email, password)
    }

    /**
     * Pass email and password to repository to interface with the Firebase backend to create a new
     * user account.
     *
     * The [UserDataViewModel.userId] parameter is set based on the results of the attempted
     * authentication.
     *
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
     */
    fun signUpWithEmailAndPassword(email: String, password: String) {
        userId = repo.firebaseSignUpWithEmail(email, password)
    }
}