package com.owenjdunn.game2048

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * The ViewModel of the application. Used to store and retrieve data from various fragments of the
 * app. Acts between the model and the passive view. An additional repository class acts between
 * ViewModel and Firebase backend.
 */
class UserDataViewModel : ViewModel() {
    var userId = MutableLiveData<String?>() // the backing store for property userId
    private val repo = Game2048Repository() // repository object
    // player game stats mutable live data
//    val highScore = MutableLiveData<Int?>()
    var wins = MutableLiveData<Int>(0)
    var losses = MutableLiveData<Int>(0)
    var totalMoves = MutableLiveData<Int>(0)

    /**
     * Pass email and password to repository to interface with the Firebase backend to authenticate
     * the user.
     *
     * The [UserDataViewModel.userId] parameter is set based on the results of the attempted
     * authentication.
     *
     * @param view [View] any view from current context used for a Snackbar message in case of error
     * @param email [String]
     * @param password [String]
     */
    fun signInWithEmailAndPassword(view: View, email: String, password: String) {
        userId = repo.firebaseSignInWithEmail(view, email, password)
    }

    /**
     * Pass email and password to repository to interface with the Firebase backend to create a new
     * user account.
     *
     * The [UserDataViewModel.userId] parameter is set based on the results of the attempted
     * authentication.
     *
     * @param view - any view from current context
     * @param email [String]
     * @param password [String]
     */
    fun signUpWithEmailAndPassword(view: View, email: String, password: String) {
        userId = repo.firebaseSignUpWithEmail(view, email, password)
    }

    /**
     * Check if signed in. Update UI in Passive View if logged in.
     *
     * Calls [Game2048Repository.firebaseIsUserSignedIn].
     *
     * @return [Boolean] is user signed in?
     */
    fun isUserSignedIn() : Boolean = repo.firebaseIsUserSignedIn()

    /**
     * Sign a user out of their account. The repository class is used to sign the user out. The user
     * id is set to null.
     */
    fun signOut() {
        repo.firebaseSignOut()
        userId.value = null
    }

    /**
     * Add user statistics to database.
     *
     * @param d - user game statistics
     */
    fun updateUserStats(d: UserData) {
//        repo.firebaseUpdateUserData(d)    // Firebase
    }
}