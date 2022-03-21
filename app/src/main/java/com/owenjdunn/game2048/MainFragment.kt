package com.owenjdunn.game2048

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

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
//    lateinit var emailAddr: String  // don't init in constructor
//    lateinit var viewModel: UserDataViewModel
    // use property delegate to init viewmodel class or gain shared reference
    private val viewModel by activityViewModels<UserDataViewModel>()

    /**
     * Get passed data if available.
     */
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

        setHasOptionsMenu(true) // signal to fragment it has an options menu to display

//        emailAddr = try {     // handled by viewmodel?
//                requireArguments().getString("userEmail").toString()
//            } catch (e: IllegalStateException) { "Guest" /* don't use account, use local data*/ }

    }

    /**
     * Display the layout.
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
     *
     * Deprecated - move code elsewhere
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(UserDataViewModel::class.java)
        viewModel.userId.observe(this.viewLifecycleOwner) { z ->    // Observer defined lambda
            val userId = view?.findViewById<TextView>(R.id.user_id)
            userId?.text = z    // update TextView text
        }
    }

    /**
     * Make some changes to the UI.
     *
     * Not currently in use.
     */
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // change text of TextView widget
////        val userId = view.findViewById<TextView>(R.id.user_id)    // handled only by viewmodel Observer now?
////        userId.text = emailAddr   // handled only by viewmodel Observer now?
//
//        // create a basic test game board programatically (to later allow different sizes and changes during play)
////        val gameTableLayout: TableLayout = view.findViewById(R.id.game_table_layout)
////        var rows = 8
////        var cols = 8
////        var gameBoardArray: Array<Array<TextView>> // might want to use an array of TextViews to access them
////        val cell: TextView =
//
//        // create each table row programmatically
//        // may need to define layout and textview params to display properly
////        for (row in 0 until rows) {
////            // create a new TableRow: fill screen, streach and condense,
////            val tr = TableRow(view.context, )
////            // add TableRow to TableLayout
////            gameTableLayout.addView(tr)
////            for (col in 0 until cols) {
////                // create textview: take up area of 4-5 digit number with a little extra, color background,
////                // border of some kind, bold, large text
////                val tx = TextView(view.context)
////                tx.text = "2048"   // don't leave 0 print in final result; view doesn't show up without it for now though
////                // assign it to (row, col) cell
////                tr.addView(tx)
//////                if (col == cols - 1) {
////////                    tr.forEach { gameBoardArray[++] =   }
////////                    gameBoardArray.set(row, tr)
//////                    for (i in 0 until cols) {
//////                        gameBoardArray[i] =
//////                    }
//////                }
////            }
////        }
////        gameTableLayout.getChildAt()
//    }

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
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    /**
     * Called when an option item is selected and what is done when this happens.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {    // goto a new instance of login screen, TODO logout of user account, data recording
            findNavController().navigate(R.id.action_main2login)
            return true
        }
//        else if (item.itemId == R.id.action_options) {    // TODO goto options UI
//        findNavController().navigate(R.id.action_main2options)
//           return true
//        }
//        else if (item.itemId == R.id.action_stats) {    // TODO goto stats UI
//        findNavController().navigate(R.id.action_main2stats)
//           return true
//        }
        return false
    }
}