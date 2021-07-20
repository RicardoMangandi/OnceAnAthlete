package com.example.oa

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SignInSignUp : AppCompatActivity() {

    private lateinit var buttonSignUp: Button
    private lateinit var buttonSignIn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_sign_up)

        setAllViews()

        buttonSignIn.setOnClickListener(View.OnClickListener {

            Toast.makeText(this, "Sign In", Toast.LENGTH_LONG).show()

        })


        buttonSignUp.setOnClickListener(View.OnClickListener {

            //Toast.makeText(this, "Sign up", Toast.LENGTH_LONG).show()

            goToRegisterFragment()

        })

    }



    private fun setAllViews()
    {

        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignUp = findViewById(R.id.buttonSignUp)


    }


    private fun goToRegisterFragment()
    {

        val registerFragment: Fragment = RegisterFragment()
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.activity_sign_in_sign_up, registerFragment)
        mFragmentTransaction.addToBackStack(null)
        mFragmentTransaction.commit()



    }





}