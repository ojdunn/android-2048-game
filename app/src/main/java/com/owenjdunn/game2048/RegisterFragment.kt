package com.owenjdunn.game2048

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.fragment.app.activityViewModels
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
 * Use the RegisterFragment.newInstance factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private val emailRegex = Pattern.compile(
        "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}",
        Pattern.CASE_INSENSITIVE)
    // use a property delegate to init viewmodel class or get shared ref to it if already init (this app)
    // use viewModels with fragment or activityViewModels with an activity like NavigationActivity (this app)
    private val viewModel by activityViewModels<UserDataViewModel>()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerFOB= view.findViewById<FloatingActionButton>(R.id.register_fob)
        val email = view.findViewById<EditText>(R.id.edit_text_email_register)
        val password =  view.findViewById<EditText>(R.id.enter_pass_register)
        val passwordConfirm =  view.findViewById<EditText>(R.id.confirm_pass_register)

        registerFOB.setOnClickListener { _ ->   // may start lambda with (var_1, ..., var_n) -> or leave out if no parameter
            // first check for valid email and password
            val emailStr = email.text.toString()
            val passStr = password.text.toString()
            val passConfirmStr = passwordConfirm.text.toString()
            // widget animation from xml file; need this.context to refer to fragment associated with
            val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)

            when {  // 3 invalid conditions to check for first before 'else' acceptance
                emailStr.isEmpty() ->
                    Snackbar
                            .make(email, getString(R.string.invalid_login),
                                  Snackbar.LENGTH_LONG)
                            .show()
                !emailRegex.matcher(emailStr).find() ->   // email doesn't fit regex pattern?
                    Snackbar
                            .make(email, getString(R.string.invalid_email_register),
                                  Snackbar.LENGTH_LONG)
                            .show()
                passStr != passConfirmStr -> {            // strings are not the same?
                    registerFOB.startAnimation(shake)
                    Snackbar
                            .make(password, getString(R.string.verify_password_fail),
                                  Snackbar.LENGTH_LONG)
                            .show()
                }
//                passStr  -> {    // check if pass meets criteria TODO
//                    registerFOB.startAnimation(shake)
//                        Snackbar
//                                .make(password, getString(R.string.invalid_password),
//                                      Snackbar.LENGTH_LONG)
//                                .show()
//                }
                else -> {   // save email and pass info TODO Firebase?
//                    Snackbar
//                            .make(signInButton, getString(R.string.login_verified),
//                                  Snackbar.LENGTH_SHORT)
//                            .show()
                    // Pass user email to main UI

                    // goto main game UI with account data use; pass user login data
                    viewModel.userId.value = emailStr   // store data into viewmodel
                    findNavController().navigate(R.id.action_register2main)
                }
            }
        }
    }
}