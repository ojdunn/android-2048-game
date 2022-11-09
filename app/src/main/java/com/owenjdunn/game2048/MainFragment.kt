package com.owenjdunn.game2048

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.common.math.IntMath.log2
import java.math.RoundingMode


// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * Contains the game layout.
 * A simple [Fragment] subclass.
 * Use the MainFragment.newInstance factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
//    // Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    // use property delegate to init viewmodel class or gain shared reference
    private val viewModel by activityViewModels<UserDataViewModel>()
//    private val s = UserData()     // init with database values, if available, write to DB before leaving app
    private var numRandomVals = 3  // how many random values start the game with TODO allow user to choose
//    private val colors: Array<Int> = arrayOf(R.color.dark_orange_a100_0, R.color.light_orange_a100_1,
//        R.color.dark_yellow_a100_2, R.color.light_green_a100_4, R.color.blue_a100_8,
//        R.color.purple_a100_16, R.color.dark_purple_a100_32, R.color.pink_a100_64,
//        R.color.orange_a100_128, R.color.yellow_a100_256, R.color.light_teal_a100_512,
//        R.color.blue_a100_1024, R.color.dark_blue_a100_2048, R.color.dark_pink_a100_4096,
//        R.color.dark_yellow_a100_8192)
    // backgrounds array has same color in background with an black border around cell
    private val backgrounds: Array<Int> = arrayOf(R.drawable.text_color0, R.drawable.text_color1,
        R.drawable.text_color2, R.drawable.text_color4, R.drawable.text_color8,
        R.drawable.text_color16, R.drawable.text_color32, R.drawable.text_color64,
        R.drawable.text_color128, R.drawable.text_color256, R.drawable.text_color512,
        R.drawable.text_color1024, R.drawable.text_color2048, R.drawable.text_color4096,
        R.drawable.text_color8192)

    /**
     * Get passed data if available.
     *
     * @param savedInstanceState [Bundle?]
     */
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

        setHasOptionsMenu(true) // signal to fragment it has an options menu to display
    }

    /**
     * Display the layout.
     *
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup?]
     * @param savedInstanceState [Bundle?]
     * @return [View?] the main fragment layout
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    /**
     * Get shared instance of viewmodel by giving the lifecycle owner [NavigationActivity]. To retrieve
     * data from the viewmodel the passive view or [MainFragment] uses the observer pattern. The
     * observer automatically updates the TextView with new user_id.
     */
//    @Deprecated(message = "deprecated", replaceWith = ReplaceWith(
//        "super.onActivityCreated(savedInstanceState)",
//        "androidx.fragment.app.Fragment"
//    )
//    )
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProvider(requireActivity()).get(UserDataViewModel::class.java)   // use with lateinit approach to val viewModel
//        // use observer pattern to retrieve data from viewmodel and display on UI
//        // place game and/or user data here (time, score, high score, moves, etc.)
////        viewModel.userId.observe(this.viewLifecycleOwner) { z ->    // Observer defined lambda
////            val userId = view?.findViewById<TextView>(R.id.user_id)
////            userId?.text = z    // update TextView text
////        }
//
//        // set user text to Guest if not logged in
////        view?.findViewById<TextView>(R.id.user_id)?.text ?:
//    }

    /**
     * This function handles the UI, uses the model to play the game, and handles the
     * buttons and listeners. Combined, this allows a user to play the game.
     *
     * @param view [View]
     * @param savedInstanceState [Bundle?]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // define physical back button activity to log user out
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.signOut()
                findNavController().navigate(R.id.action_main2login)
            }
        })
        // hide toolbar back button
//        val actionBar = this.requireActivity().actionBar
//        actionBar?.setDisplayHomeAsUpEnabled(false)

        val gameTableLayout: TableLayout = view.findViewById(R.id.game_table_layout)
        val upBtn = view.findViewById<ImageButton>(R.id.upBtn)
        val downBtn = view.findViewById<ImageButton>(R.id.downBtn)
        val leftBtn = view.findViewById<ImageButton>(R.id.leftBtn)
        val rightBtn = view.findViewById<ImageButton>(R.id.rightBtn)
        val undoBtn = view.findViewById<ImageButton>(R.id.undoBtn)
        val btns: Array<ImageButton> = arrayOf(upBtn, downBtn, leftBtn, rightBtn, undoBtn)

        val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)

        val printData = view.findViewById<TextView>(R.id.user_id)

        // create a basic test game board programatically (to later allow different sizes and changes during play)
//        var rows = 8
//        var cols = 8
//        var gameBoardArray: Array<Array<TextView>> // might want to use an array of TextViews to access them
//        val cell: TextView =

        // create each table row programmatically
        // may need to define layout and textview params to display properly
//        for (row in 0 until rows) {
//            // create a new TableRow: fill screen, streach and condense,
//            val tr = TableRow(view.context, )
//            // add TableRow to TableLayout
//            gameTableLayout.addView(tr)
//            for (col in 0 until cols) {
//                // create textview: take up area of 4-5 digit number with a little extra, color background,
//                // border of some kind, bold, large text
//                val tx = TextView(view.context)
//                tx.text = "2048"   // don't leave 0 print in final result; view doesn't show up without it for now though
//                // assign it to (row, col) cell
//                tr.addView(tx)
////                if (col == cols - 1) {
//////                    tr.forEach { gameBoardArray[++] =   }
//////                    gameBoardArray.set(row, tr)
////                    for (i in 0 until cols) {
////                        gameBoardArray[i] =
////                    }
////                }
//            }
//        }
//        gameTableLayout.getChildAt()

        // Create game object that handle game logic
        val game = Game2048()

        // Create game board
        // TODO take user options to create new game
        createGameBoard(view, game, gameTableLayout)

        // init user data display
        printUserStats(printData)

        // listeners to define game play until a certain event happen: win, lose, logout, ..
        // add listeners to all buttons to play the game
        upBtn.setOnClickListener { _ ->
            // call game logic to slide cells up
            game.slide(SlideDirection.UP)
            // update board state
            updateGameBoard(view, game, gameTableLayout)
            // check game status
            checkGameStatus(view, game, btns)
            // update view with user statistics
            printUserStats(printData)
        }
        downBtn.setOnClickListener { _ ->
            game.slide(SlideDirection.DOWN)
            updateGameBoard(view, game, gameTableLayout)
            checkGameStatus(view, game, btns)
            printUserStats(printData)
        }
        leftBtn.setOnClickListener { _ ->
            game.slide(SlideDirection.LEFT)
            updateGameBoard(view, game, gameTableLayout)
            checkGameStatus(view, game, btns)
            printUserStats(printData)
        }
        rightBtn.setOnClickListener { _ ->
            game.slide(SlideDirection.RIGHT)
            updateGameBoard(view, game, gameTableLayout)
            checkGameStatus(view, game, btns)
            printUserStats(printData)
        }
        undoBtn.setOnClickListener { _ ->
            try {
                // balances to 1 less move after all function calls
                viewModel.totalMoves.value = viewModel.totalMoves.value?.minus(2)
                game.undo()
            } catch (e: java.lang.IllegalStateException) {
                // negate move decrease on fail
                viewModel.totalMoves.value = viewModel.totalMoves.value?.plus(1)

                // at starting move: give some feedback (shake btn, ..) that you can't go back any more moves
                undoBtn.startAnimation(shake)
                Snackbar.make(view, getString(R.string.undo_no_moves), Snackbar.LENGTH_LONG)
                        .show()
            }
            updateGameBoard(view, game, gameTableLayout)
            checkGameStatus(view, game, btns)
            printUserStats(printData)
        }

    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MainFragment.
//         */
//        // Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MainFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    /**
     * Display the menu created in an xml file within resources.
     *
     * @param menu [Menu] menu from xml file definition
     * @param inflater [MenuInflater]
     */
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    /**
     * Called when an option item is selected and what is done when this happens.
     *
     * @param item [MenuItem] menu item user touched
     * @return [Boolean] is menu item id action found?
     */
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) { // goto a new instance of login screen, log out of user account
            viewModel.signOut() // Firebase; must use to not auto go back to this screen from login fragment?
            findNavController().navigate(R.id.action_main2login)
            return true
        }
        else if (item.itemId == R.id.new_game) {    // start new game by reloading this fragment (user data are saved through ViewModel)
            findNavController().navigate(R.id.action_mainFragment_self)
            return true
        }
//        else if (item.itemId == R.id.action_options) {    // TODO goto options UI
//        findNavController().navigate(R.id.action_main2options)
//           return true
//        }

        return false
    }

    /**
     * Create the game board according to the player's choices.
     *
     * @param view [View]
     * @param game [Game2048] model logic for number sliding game
     * @param gameTableLayout [TableLayout] root layout holding all game cells
     * @param rows [Int] how many rows of game table
     * @param cols [Int] how many columns of game table
     * @param winningValue [Int] what score must any cell be for the player to win a game?
     */
    private fun createGameBoard(view: View, game: Game2048, gameTableLayout: TableLayout,
                                rows: Int = 6, cols: Int = 6, winningValue: Int = 2048) {
        // TODO allow choice from options screen of game size (likely min of 3 to max 6 cell dimensions to fit phone screens), winning score, nRandomVals, ..
        game.resizeBoard(rows, cols, winningValue)

        // set board to all zeroes to start
        clearGameBoard(view, gameTableLayout)

        // add random cell values to board
        for (i in 1.. numRandomVals)
            game.placeRandomValue()

        // Update game board
        updateGameBoard(view, game, gameTableLayout)
    }

    /**
     * Clear all cell values on the board by setting them to 0 and its associated background. The
     * number and background color are set the same to make all cells the same width but also not
     * having "0" visible when cells have that value.
     *
     * This is called between updating the board
     * with the model's latest nonzero values after a player move.
     *
     * @param view [View] provides view context to access child views
     * @param gameTableLayout [TableLayout] layout for game board
     */
    private fun clearGameBoard(view: View, gameTableLayout: TableLayout) {
        var viewRow: TableRow
        var cellText: TextView

        // reset board
        for (i in 0 until gameTableLayout.childCount) { // each TableRow
            viewRow = gameTableLayout.getChildAt(i) as TableRow
            for (j in 0 until viewRow.virtualChildCount) {  // each cell (TextView) of row
                cellText = viewRow.getVirtualChildAt(j) as TextView
                cellText.text = "0000"
                // same color for text to not be visible (so columns all same width)
                cellText.setBackgroundResource(R.drawable.text_color0)
                cellText.setTextColor(getColor(view.context, R.color.dark_orange_a100_0))
            }
        }
    }

    /**
     * Update game board with model's latest cell values.
     *
     * @param view [View] provides view context to access views
     * @param game [Game2048] model game logic
     * @param gameTableLayout [TableLayout] 2D cell of TextViews to store game values
     */
    private fun updateGameBoard(view: View, game: Game2048, gameTableLayout: TableLayout) {
        var viewRow: TableRow
        var cellText: TextView
        var cellValue: Int

        // clear old TextView cells (doesn't effect 2d array game object values)
        clearGameBoard(view, gameTableLayout)

        // update view with all nonempty game cells (not value of 0)
        for(cell in game.nonEmptyTiles) {
            // get table row view
            viewRow = gameTableLayout.getChildAt(cell.getRow()) as TableRow
            cellText = viewRow.getVirtualChildAt(cell.getColumn()) as TextView
            cellValue = cell.getValue()

            if (cellValue == 0) {   // shouldn't have any 0 values, but just in case to avoid error
                cellText.text = "0000"
                cellText.setBackgroundResource(R.drawable.text_color0)
                cellText.setTextColor(getColor(view.context, R.color.dark_orange_a100_0))
            } else {
                cellText.text = "$cellValue"
                // for a given board value, call index = (log2(value) + 1); all values are powers of 2 except 0
                // 15 elements: 0 .. 14 index
                cellText.setBackgroundResource(backgrounds[ log2(cellValue, RoundingMode.UNNECESSARY) + 1 ])
                cellText.setTextColor(Color.WHITE)
            }
        }
    }

    /**
     * Check game status. Print message to user on a win or lose. Prevent continued play by deactivating
     * buttons on a win or lose condition.
     *
     * @param view [View] provides view context to access views
     * @param game [Game2048] model game logic
     * @param btns [Array<ImageButton>] array of image buttons making up the direction pad for game
     */
    private fun checkGameStatus(view: View, game: Game2048, btns: Array<ImageButton>) {
        game.updateStatus()
        when (game.status)  {
            GameStatus.IN_PROGRESS -> {
                // increment the totalMoves value handled by the shared ViewModel instance
                viewModel.totalMoves.value = viewModel.totalMoves.value?.plus(1)
            }
            GameStatus.USER_WON -> {
                viewModel.totalMoves.value = viewModel.totalMoves.value?.plus(1)
                viewModel.wins.value = viewModel.wins.value?.plus(1)
                // TODO add high score tracking - might want a more complex score tracking equation (points for moves, combines at certain values, less time to win, ..)
                // msg to user
                Snackbar.make(view, getString(R.string.win_message), Snackbar.LENGTH_LONG)
                        .show()
                // deactivate buttons and update user data
                endGame(btns)
            }
            GameStatus.USER_LOST -> {
                viewModel.totalMoves.value = viewModel.totalMoves.value?.plus(1)
                viewModel.losses.value = viewModel.losses.value?.plus(1)
                Snackbar.make(view, getString(R.string.lose_message), Snackbar.LENGTH_LONG)
                        .show()
                endGame(btns)
            }
        }
    }

    /**
     * Display updated user statistics on the game screen.
     *
     * @param textView [TextView] text box to display user statistics
     */
    private fun printUserStats(textView: TextView) {
        val s: String =
            if (viewModel.userId.value != null)
                "Player: ${viewModel.userId.value}, Wins: ${viewModel.wins.value} , Losses: ${viewModel.losses.value} , Moves: ${viewModel.totalMoves.value}"
            else
                "Player: Guest, Wins: ${viewModel.wins.value}, Losses: ${viewModel.losses.value} , Moves: ${viewModel.totalMoves.value}"

        textView.text = s   // change the user stats banner string
    }

    /**
     * End game by deactivating buttons.
     *
     * @param btns [Array<ImageButton>] array with all ImageButton buttons to make up the direction
     * controls for game.
     */
    private fun endGame(btns: Array<ImageButton>) {
        for (btn in btns) {
            btn.isVisible = false   // you can also make them unclickable
        }
    }

    /**
     * Start game by activating buttons and updating user statistics.
     *
     * @param btns [Array<ImageButton>] array with all ImageButton buttons to make up the direction
     * controls for game.
     */
//    private fun startGame(btns: Array<ImageButton>) {
//        for (btn in btns) {
//            btn.isVisible = true
//        }
//    }

    /**
     * Update game statistics for the View Model.
     *
     * @param s [UserData] a user data data class
     */
//    private fun updateStats(s: UserData) {
////        viewModel.highScore.value = s.highScore
//        viewModel.wins.value = s.wins
//        viewModel.losses.value = s.losses
//        viewModel.totalMoves.value = s.totalMoves
////        viewModel.timePlayed.value = s.timePlayed
//    }
}