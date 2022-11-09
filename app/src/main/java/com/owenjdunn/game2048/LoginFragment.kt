package com.owenjdunn.game2048

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 * Use the LoginFragment.newInstance factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private val emailRegex = Pattern.compile(
        "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}",
        Pattern.CASE_INSENSITIVE
    )

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    // shared instance of ViewModel with all fragments; used with viewModel line in onActivityCreated
    // declare a ref to viewmodel instance and then initialize it with delegate
    // use a property delegate to init class;
    // use viewModels with fragment or activityViewModels with an activity like NavigationActivity (this app)
    private val viewModel by activityViewModels<UserDataViewModel>()

    /**
     * Inflate the login layout.
     *
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup?]
     * @param savedInstanceState [Bundle?]
     * @return [View?]
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate (convert layout to view) the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /**
     * Get shared instance of ViewModel. A factory class is informed of the the lifecycle owner,
     * [NavigationActivity] first.
     *
     * Make sure that [NavigationActivity] exists when the viewmodel is initialized. Initializing
     * within this method after the super call assures that.
     *
     * Deprecated
     *
     * @param savedInstanceState [Bundle?]
     */
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
            // initialize ref to viewmodel instance
////        viewModel = ViewModelProvider(requireActivity()).get(UserDataViewModel::class.java)
//    }

    /**
     * Handle view listeners for login_fragment layout. Similar use as [onCreate()], but View methods
     * must be called from the passed view object.
     *
     * Handles user login data and transition to other fragments with the navigation controller. User
     * data is handled locally with the ViewModel shared instance.
     *
     * @param view [View]
     * @param savedInstanceState [Bundle?]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = view.findViewById<EditText>(R.id.editTextEmailAddress)
        val password = view.findViewById<EditText>(R.id.editTextTextPassword)
        val startFAB = view.findViewById<FloatingActionButton>(R.id.floatingActionButtonStart)
        val signInButton = view.findViewById<Button>(R.id.signInButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)
        // widget animation from xml file; need this.context to refer to fragment associated with
        val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)

        // init fields for testing
//        email.text.insert(0, "dunnow@mail.gvsu.edu")
//        password.text.insert(0, "123456")

        // auto move to main screen if user is logged in
        viewModel.userId.observe(viewLifecycleOwner) {
            if (viewModel.userId.value != null) findNavController().navigate(R.id.action_login2main)
        }

        // Set onClick(v: View?) function for button view generated click events.
        // This works because OnClickListener interface only has one method.
        // _ represents unused parameter by lambda function
        // launch a standard game on new activity
        startFAB.setOnClickListener { _ ->   // may start lambda with (var1, ..., varn) -> or leave out if no parameter
            val emailStr = email.text.toString()
            val passStr = password.text.toString()

            if (emailStr.isNotBlank()) {
                if (isValidLoginData(email, emailStr, passStr)) {   // is data valid?
                    // attempt to sign in to Firebase with data
                    // view passed to give context
//                    viewModel.signInWithEmailAndPassword(view, emailStr, passStr) // Firebase
                    viewModel.userId = MutableLiveData<String?>()   // replace with signin function above when Database active
                    viewModel.userId.value = emailStr   // replace with signin function above when Database active
                    viewModel.userId.observe(viewLifecycleOwner) { uid ->
                        if (uid != null) {
                            findNavController().navigate(R.id.action_login2main)
                        } else {
                            startFAB.startAnimation(shake)
                        }
                    }
                }
            } else {  // play as guest in main game UI with local data use
                findNavController().navigate(R.id.action_login2main)
            }
        }
        // Attempt to log in by checking user email and password.
        signInButton.setOnClickListener { _ ->
            val emailStr = email.text.toString()
            val passStr = password.text.toString()

            if (isValidLoginData(email, emailStr, passStr)) {
//                viewModel.signInWithEmailAndPassword(email, emailStr, passStr)    // Firebase
                viewModel.userId = MutableLiveData<String?>()   // replace with signin function above when Database active
                viewModel.userId.value = emailStr   // replace with signin function above when Database active
                viewModel.userId.observe(viewLifecycleOwner) { uid ->
                    if (uid != null) {
                        findNavController().navigate(R.id.action_login2main)
                    } else {
                        signInButton.startAnimation(shake)
                    }
                }
            } else {    // invalid login data, give some visual feedback
                signInButton.startAnimation(shake)
            }
        }
        // Sign up for a new user account.
        registerButton.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_login2register)    // goto register UI
        }

        super.onViewCreated(view, savedInstanceState)
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment LoginFragment.
//         */
//        // Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            LoginFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    /**
     * Check if login data is valid. A layout view reference is passed to give Snackbar messages
     * a layout reference.
     *
     * Checks if an email is given, if the email fits a regex pattern and if the password matches a
     * hardcoded value.
     *
     * @param view - a view to find the parent view from
     * @param emailStr - user entered email to [EditText]
     * @param passStr - user entered password to [EditText]
     * @return [Boolean] - is email valid?
     */
    private fun isValidLoginData(view: EditText, emailStr: String, passStr: String): Boolean {
        when {  // 3 invalid conditions to check for first before 'else' acceptance
            emailStr.isEmpty() -> {
                Snackbar
                        .make(view, getString(R.string.email_required), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            !emailRegex.matcher(emailStr).find() -> {
                Snackbar
                        .make(view, getString(R.string.invalid_email), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            passStr.length < 6 -> {     // firebase requires 6 or more chars
                Snackbar
                        .make(view, getString(R.string.password_length_fail), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            else -> { // valid email and password found
//                    Snackbar
//                            .make(signInButton, getString(R.string.login_verified),
//                                  Snackbar.LENGTH_SHORT)
//                            .show()
                return true
            }
        }
    }
}