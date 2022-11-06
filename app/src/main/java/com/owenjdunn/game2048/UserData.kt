package com.owenjdunn.game2048

import com.google.firebase.firestore.DocumentId

/**
 * Data related to a player's game play. Used to store in the cloud between sessions and/or locally
 * while playing.
 *
 * Firebase requires class used to read data to have a default constructor. You can fit this by
 * having default values.
 */
data class UserData(
    // Firestore auto fill in the key field with the doc ID assoc with each UserData doc in Cloud Firestore
    val uid: String? = null,    // key
//    @DocumentId
//    val key: String = uid,
    var highScore: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var totalMoves: Int = 0,
    var timePlayed: Int = 0
)