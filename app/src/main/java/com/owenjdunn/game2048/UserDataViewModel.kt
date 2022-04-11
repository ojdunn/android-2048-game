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
//    lateinit var userId: MutableLiveData<String?> // the backing store for property userId
//    lateinit var userName: MutableLiveData<String?> // display name for user
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
     * @param view - any view from current context used for a Snackbar message in case of error
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
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
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
     */
    fun signUpWithEmailAndPassword(view: View, email: String, password: String) {
        userId = repo.firebaseSignUpWithEmail(view, email, password)
    }

    /**
     * Check if signed in. Update UI in Passive View if logged in.
     *
     * Calls [Game2048Repository.firebaseIsUserSignedIn].
     *
     * @return Boolean - is user signed in?
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
}