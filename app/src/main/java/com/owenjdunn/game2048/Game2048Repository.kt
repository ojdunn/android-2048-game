package com.owenjdunn.game2048

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

/**
 * Repository class for use with MVVM architecture as recommended by Android. This class handles
 * the details of interfacing with the Firebase backend.
 *
 * Only the ViewModel should have a reference to this class.
 */
class Game2048Repository {
    private var auth: FirebaseAuth = Firebase.auth   // create shared instance of auth object
    private var user: FirebaseUser? = null // user profile class related to user's Firebase database data
    private val dbStore = Firebase.firestore    // ref to Firebase Firestore database instance
    private var docRef: DocumentReference? = null   // ref to top doc assoc with current user

    /**
     * Use an email and password to attempt authentication on Firebase to sign in a user.
     *
     * Data passed by ViewModel class, which has a reference to containing [Game2048Repository]
     * class of this function.
     *
     * @param view - any view from current context used for a Snackbar message in case of error
     * @param email
     * @param password
     * @return uidResponse - returns uid as value of [MutableLiveData] if verified, null as value
     * if not verified
     */
    fun firebaseSignInWithEmail(view: View, email: String, password: String) :
            MutableLiveData<String?> {
        val uidResponse = MutableLiveData<String?>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {    // sign in success, update UI
                    Log.d(TAG, "firebaseSignInWithEmail:success")
                    // save current user profile class and save uid value to uidResponse
                    user = auth.currentUser?.also {
                        uidResponse.value = it.uid
                        docRef = dbStore.collection("users").document(it.uid)
                    }
                }
                else {    // sign in fail
                    Log.w(TAG, "firebaseSignInWithEmail:failed", task.exception)
                    Snackbar
                            .make(view, R.string.firebase_auth_failed, Snackbar.LENGTH_LONG)
                            .show()
                    user = null
                    uidResponse.value = null
                }
            }

        return uidResponse
    }

    /**
     * Use Firebase to create a new user with passed email and password. The user is also signed in
     * automatically after account creation.
     *
     * Firebase itself requires password to have >=6 chars,
     *
     * Data passed by ViewModel class, which has a reference to containing [Game2048Repository]
     * class of this function.
     *
     * @param view - any view from current context used for a Snackbar message in case of error
     * @param email
     * @param password
     * @return uidResponse - returns uid if verified, null if not
     */
    fun firebaseSignUpWithEmail(view: View, email: String, password: String) : MutableLiveData<String?> {
        val uidResponse = MutableLiveData<String?>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { // created account and sign in success, update UI
                    Log.d(TAG, "firebaseSignUpWithEmail:success")
                    user = auth.currentUser
                    // set value to unique uid
                    uidResponse.value = user?.uid
                }
                else {  // sign up fail
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {    // tell user why password rejected
                        e.reason?.let {
                            Snackbar
                                    .make(view, it, Snackbar.LENGTH_LONG)
                                    .show()
                        }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(TAG, it) }
                    } finally {
                        Log.w(TAG, "firebaseSignUpWithEmail:failure", task.exception)
                    }
                    user = null
                    uidResponse.value = null
                }
            }

        return uidResponse
    }

    /**
     * Check if user is already signed in. Update UI in passive view if logged in.
     *
     * @return Boolean - is user logged in already?
     */
    fun firebaseIsUserSignedIn() : Boolean = auth.currentUser != null

    /**
     * Sign a user out of Firebase. Clear current user data after sign out.
     */
    fun firebaseSignOut() {
        auth.signOut()
        user = null
    }

    /**
     * Save user data to Firebase Firestore. Add if no data or update if data already present.
     *
     * @param s - user game data
     */
    fun firebaseUpdateUserData(s: UserData) {
        val userData = hashMapOf(
            "uid"  to user?.uid,    // key
            "highScore" to s.highScore,
            "wins" to s.wins,
            "losses" to s.losses,
            "totalMoves" to s.totalMoves,
            "timePlayed" to s.timePlayed
        )
        // create new document with uid as key
        if (user != null) {
            dbStore.collection("users")
                .add(userData)
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${docRef.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
        // add to uid document with their game stats
//        docRef?.update(gData as Map<String, Any>)
//        if () {      // Add data if none present
//            docRef?.collection("gameData")?.let {
//                it.add(gData)
//            }
//        }
//        else {      // Update data if present
//
//        }
    }
}