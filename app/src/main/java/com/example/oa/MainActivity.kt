package com.example.oa

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {



    private lateinit var quoteTextView:TextView



    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAllUI()
        setText()

        if(!checkIfUserExists())
        {
           Log.i(TAG,"User does not exists")
            val goSignUpOrSignIn = Intent(this, SignInSignUp::class.java)
            startActivity(goSignUpOrSignIn)

        }

        else
        {
            Log.i(TAG,"User exists")
            val goMainExperience = Intent (this,MainExperience::class.java)
            startActivity(goMainExperience)

        }

    }



    private fun setText()
    {


        var myQuotes: ArrayList<String> = ArrayList()

        myQuotes.add("The glory days are behind, but we still work ever after no one cares.")
        myQuotes.add("Remember to always work hard regardless of where you are at in life.")
        myQuotes.add("You were an athlete once remember the grind continues")

        var randomVar : Int

        var random  = Random()

        randomVar = random.nextInt(3)

        quoteTextView.text = myQuotes[randomVar]


    }


    //void function
    private fun  setAllUI()
    {

        quoteTextView = findViewById(R.id.textViewQuotesMain)



    }

    //function will return a boolean
    private fun checkIfUserExists() : Boolean
    {
        val currentUser = ParseUser.getCurrentUser();
        return currentUser != null
    }




}