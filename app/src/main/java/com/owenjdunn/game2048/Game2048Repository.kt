package com.owenjdunn.game2048

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

/**
 * Repository class for use with MVVM architecture as recommended by Android. This class handles
 * the details of interfacing with the Firebase backend.
 *
 * The ViewModel should only have a reference to this class.
 */
class Game2048Repository {
    private val auth = FirebaseAuth.getInstance()

    /**
     * Use an email and password to attempt authentication on Firebase to sign in a user.
     *
     * Data passed by ViewModel class, which has a reference to containing [Game2048Repository]
     * class of this function.
     *
     * @param email
     * @param password
     * @return uidResponse - returns uid as value of [MutableLiveData] if verified, null as value
     * if not verified
     */
    fun firebaseSignInWithEmail(email: String, password: String) : MutableLiveData<String?> {
        val uidResponse = MutableLiveData<String?>()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
                uidResponse.value = auth.currentUser?.uid
            else
                uidResponse.value = null
        }

        return uidResponse
    }

    /**
     * Use Firebase to create a new user with passed email and password.
     *
     * Data passed by ViewModel class, which has a reference to containing [Game2048Repository]
     * class of this function.
     *
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
     */
    fun firebaseSignUpWithEmail(email: String, password: String) : MutableLiveData<String?> {
        val uidResponse = MutableLiveData<String?>()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
                uidResponse.value = auth.currentUser?.uid
            else
                uidResponse.value = null
        }

        return uidResponse
    }
}