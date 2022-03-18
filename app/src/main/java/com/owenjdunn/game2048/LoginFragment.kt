package com.owenjdunn.game2048

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /**
     * Handle view listeners for login_fragment layout. Similar use as [onCreate()], but View methods
     * must be called from the passed view object.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = view.findViewById<EditText>(R.id.editTextEmailAddress)
        val password = view.findViewById<EditText>(R.id.editTextTextPassword)
        val startFOB = view.findViewById<FloatingActionButton>(R.id.floatingActionButtonStart)
        val signInButton = view.findViewById<Button>(R.id.signInButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        // Set onClick(v: View?) function for button view generated click events.
        // This works because OnClickListener interface only has one method.
        // _ represents unused parameter by lambda function
        // launch a standard game on new activity
        startFOB.setOnClickListener { _ ->   // may start lambda with (var1, ..., varn) -> or leave out if no parameter
            val emailStr = email.text.toString()
            val passStr = password.text.toString()
            val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)

            if (emailStr.isNotBlank()) {
                if (isValidLogin(email, emailStr, passStr)) { // goto main game UI with passed email data
                    findNavController().navigate(R.id.action_login2main,
                        bundleOf("userEmail" to emailStr))
                } else {    // invalid email or password; give some visual feedback
                    startFOB.startAnimation(shake)
                }
            } else {  // play as guest in main game UI with local data use
                findNavController().navigate(R.id.action_login2main,
                    bundleOf("userEmail" to "Guest"))
            }
//                when {  // 3 invalid conditions to check for first before 'else' acceptance
//                    emailStr.isEmpty() ->
//                        Snackbar
//                            .make(
//                                email, getString(R.string.email_required),
//                                Snackbar.LENGTH_LONG
//                            )
//                            .show()
//                    !emailRegex.matcher(emailStr).find() ->
//                        Snackbar
//                            .make(
//                                email, getString(R.string.invalid_email),
//                                Snackbar.LENGTH_LONG
//                            )
//                            .show()
//                    !passStr.contains("2048") ->
//                        signInButton.startAnimation(shake)
////                    Snackbar
////                            .make(password, getString(R.string.invalid_password),
////                                  Snackbar.LENGTH_LONG)
////                            .show()
//                    else -> {
////                    Snackbar
////                            .make(signInButton, getString(R.string.login_verified),
////                                  Snackbar.LENGTH_SHORT)
////                            .show()
//                        findNavController().navigate(
//                            R.id.action_login2main,
//                            bundleOf("userEmail" to emailStr)
//                        )  // goto main game UI with passed email data
//                    }
//                }
//            } else {  // play as guest
//                findNavController().navigate(
//                    R.id.action_login2main,
//                    bundleOf("userEmail" to "local")
//                )    // goto main game UI with local data use
//            }
        }
        // Attempt to log in by checking user email and password.
        signInButton.setOnClickListener { _ ->
            val emailStr = email.text.toString()
            val passStr = password.text.toString()
            // widget animation from xml file; need this.context to refer to fragment associated with
            val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)

            if (isValidLogin(email, emailStr, passStr)) { // goto main game UI with passed email data))
                findNavController().navigate(R.id.action_login2main,
                    bundleOf("userEmail" to emailStr))
            } else {    // invalid login data, give some visual feedback
                signInButton.startAnimation(shake)
            }
//            when {  // 3 invalid conditions to check for first before 'else' acceptance
//                emailStr.isEmpty() ->
//                    Snackbar
//                        .make(
//                            email, getString(R.string.email_required),
//                            Snackbar.LENGTH_LONG
//                        )
//                        .show()
//                !emailRegex.matcher(emailStr).find() ->
//                    Snackbar
//                        .make(
//                            email, getString(R.string.invalid_email),
//                            Snackbar.LENGTH_LONG
//                        )
//                        .show()
//                !passStr.contains("2048") ->
//                    signInButton.startAnimation(shake)
////                    Snackbar
////                            .make(password, getString(R.string.invalid_password),
////                                  Snackbar.LENGTH_LONG)
////                            .show()
//                else -> {
////                    Snackbar
////                            .make(signInButton, getString(R.string.login_verified),
////                                  Snackbar.LENGTH_SHORT)
////                            .show()
//                    findNavController().navigate(
//                        R.id.action_login2main,
//                        bundleOf("userEmail" to emailStr)
//                    )  // goto main game UI with passed email data
//                }
//            }
        }
        // Sign up for a new user account. Might add database services later.
        registerButton.setOnClickListener { _ ->
//            Snackbar.make(registerButton, "Register not available yet", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_login2register)    // goto register UI
        }
        // Options - Action menu choice TODO
        //
//        optionsAction.setOnClickListener { _ ->
//            Snackbar.make(optionsAction, "No Options available yet", Snackbar.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_login2options)    // goto register UI

//        }
        // Statistics UI - TODO
//        statsAction.setOnClickListener { _ ->
//
//        }
        // Log out - Action menu choice TODO likely will be in another method
//        logOutAction.setOnClickListener { _ ->
//            Snackbar.make(logOutAction, "Register not available yet, so no log out either", Snackbar.LENGTH_SHORT).show()
//        }

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
//        // TODO: Rename and change types and number of parameters
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
     */
    private fun isValidLogin(email: EditText, emailStr: String, passStr: String): Boolean {
        when {  // 3 invalid conditions to check for first before 'else' acceptance
            emailStr.isEmpty() -> {
                Snackbar
                        .make(email, getString(R.string.email_required), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            !emailRegex.matcher(emailStr).find() -> {
                Snackbar
                        .make(email, getString(R.string.invalid_email), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            !passStr.contains("2048") -> {
                Snackbar
                        .make(email, getString(R.string.invalid_password), Snackbar.LENGTH_LONG)
                        .show()
                return false
            }
            else -> { // valid email and password found
//                    Snackbar
//                            .make(signInButton, getString(R.string.login_verified),
//                                  Snackbar.LENGTH_SHORT)
//                            .show()
//                findNavController().navigate(
//                    R.id.action_login2main,
//                    bundleOf("userEmail" to emailStr)
                return true
            }
        }
    }
}