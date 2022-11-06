package com.owenjdunn.game2048

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import kotlin.random.Random

/**
 * Extends the Application class. It's the best place for making one-time calls in its onCreate()
 * method.
 *
 * May convert old java code to Kotlin. Use this file for that.
 */
class KGame2048(/*private val rows: Int = 6,
               private val cols: Int = 6,
               private val board: Array<Int> = Array(size = rows * cols){0},    // array values init all 0
               private val boardCells: Array<Cell2048> = ,     // TODO: use one or other?
               private val winVal: Int = 2048,  // winning game cell value*/
): Application() {

    private val random: Random = Random(0)  // use Kotlin random class (better than java.util); seed set to 0 for testing; random seed: System.currentTimeMillis()
    
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)    // init a good api for date data
    }

    /**
     * Get a random power of two. You can set the range of the power of two by choosing the min
     * and max possible shifting of the bits left (multiply by 2 each shift if no overflow of a
     * two's complement number).
     * Gives one of [2, 4, 8, .., 64] by default. Set the min and max left
     * shift to deviate. Allowed range of [2, 2^n], n = [0, 11] for n shifts left.
     */
    fun getRandomPowerTwo(minShiftLeft: Int = 1, maxShiftLeft: Int = 6): Int { // 2^6 = 64, 2^11 = 2048; 2, 4, 8, .., 2048
        val maxAllowed = 11         // max left shift of bits allowed
        val defaultMaxShift = 6     // 2^6 = 64

        // First check for acceptable shifts. Use default range if a problem.
        if ( minShiftLeft < 0 || minShiftLeft > maxAllowed ||
             maxShiftLeft < 0 || maxShiftLeft > maxAllowed ||
             minShiftLeft > maxShiftLeft
        ) return 1 shl random.nextInt(1,defaultMaxShift+1)

        return 1 shl random.nextInt(minShiftLeft, maxShiftLeft+1)
    }
}