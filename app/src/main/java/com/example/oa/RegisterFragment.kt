package com.example.oa

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*
import kotlin.collections.ArrayList


class RegisterFragment : Fragment() {

    //EditText
    private lateinit var editTextUsername : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var editTextConfirmPassword : EditText
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPhone : EditText
    private lateinit var editTextBio : EditText
    //TextView
    private lateinit var textViewDob : TextView
    //DatePicker
    private lateinit var datePicker: DatePicker
    //button
    private lateinit var buttonContinueRegister : Button


    //global user information
    private lateinit var stringUsername : String
    private lateinit var stringPassword : String
    private lateinit var stringConfirmPassword : String
    private lateinit var stringEmail : String
    private lateinit var stringPhone : String
    private var stringAge : Int = 18
    private lateinit var stringBio : String





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //the whole view
        val root : View = inflater.inflate(R.layout.fragment_register, container, false)

        //edit text
        editTextUsername  = root.findViewById(R.id.editTextUsernameRegister)
        editTextPassword  = root.findViewById(R.id.editTextPasswordRegister)
        editTextConfirmPassword = root.findViewById(R.id.editTextConfirmPasswordRegister)
        editTextEmail = root.findViewById(R.id.editTextEmailRegister)
        editTextPhone = root.findViewById(R.id.ediTextPhoneNumRegister)
        editTextBio = root.findViewById(R.id.editTextDescriptionRegister)

        //text view
        textViewDob = root.findViewById(R.id.textViewDob)

        //date picker
        datePicker = root.findViewById(R.id.datePicker)

        //button
        buttonContinueRegister = root.findViewById(R.id.buttonContinueRegister)

        //calender instance
        val today = Calendar.getInstance()

        //initialized
        val dobList : ArrayList<Int> = ArrayList(3)
        dobList.add(0)
        dobList.add(0)
        dobList.add(0)

        val todayList : ArrayList<Int> = ArrayList(3)
        todayList.add(today.get(Calendar.MONTH) + 1)
        todayList.add(today.get(Calendar.DAY_OF_MONTH))
        todayList.add(today.get(Calendar.YEAR))


        datePicker.init(2003,0,1)
        {
                _, year, month, day ->


            dobList[0] = month + 1
            dobList[1] = day
            dobList[2] = year

            //calls function to get userAge
            textViewDob.text = userAge(dobList,todayList)

        }





        buttonContinueRegister.setOnClickListener(View.OnClickListener {


            if(checksForValidInputs())
            {

                val userObject = User(stringUsername,stringPassword,stringEmail,stringPhone,stringAge.toString(),stringBio)

                val userParse  = ParseUser()
                userParse.username = userObject.userName
                userParse.setPassword(userObject.password)
                userParse.email = userObject.email
                userParse.put("Phone", userObject.phone)
                userParse.put("Age", userObject.age)
                userParse.put("Bio", userObject.bio)

                userParse.signUpInBackground { e->(

                        if(e == null)
                        {

                            Log.i(TAG,"Successfully signed up user ${userObject.userName}")
                            goToConfigureWorkoutFragment()

                        }

                        else
                        {

                            val errorSegment = e.toString().split(':')
                            val error : String = errorSegment[errorSegment.size - 1]
                            //Do some sort of builder to take care of error
                            //typically error will be 209
                            //if error 209 then send the user to reset there password
                            Log.i(TAG,"Unable to register user, error: $error")
                            Toast.makeText(context,"ERROR: $e", Toast.LENGTH_LONG).show()
                        }
                        )}
            }

            else
            {
                Toast.makeText(context,"Please correct errors above", Toast.LENGTH_SHORT).show()
            }

        })




       // Inflate the layout for this fragment
        return root

        }


    private fun goToConfigureWorkoutFragment()
    {

        //need to destroy or pop the current fragment that I am in


    }



    private fun userAge(dobList: ArrayList<Int>, todayList: ArrayList<Int> ) : String
    {
        val ageString = "Age: "
        var age = todayList[2] - dobList[2]
        val monthDifference = todayList[0] - dobList[0]
        val dayDifference = todayList[1] - todayList[1]

        if(dayDifference < 0 || monthDifference < 0)
        {
            age--
            stringAge =  age
            return ageString + age
        }

        else
        {
            stringAge = age
            return ageString + age
        }
    }



    private fun checksForValidInputs(): Boolean
    {


        stringUsername = editTextUsername.text.toString().trim()
        stringPassword = editTextPassword.text.toString().trim()
        stringConfirmPassword = editTextConfirmPassword.text.toString().trim()
        stringEmail = editTextEmail.text.toString().trim()
        stringPhone = editTextPhone.text.toString().trim()
        stringBio = editTextBio.text.toString().trim()


        //false
        if(stringUsername.isEmpty())
        {

            editTextUsername.error = "Username is required"
            return false
        }

        //false
        if(stringPassword.isEmpty())
        {

            editTextPassword.error = "Password is required"
            return false
        }



        if(checksPassword(stringPassword) < 4)
        {

            editTextPassword.error = "Password is too weak"
            return false
        }


        //false
        if(stringConfirmPassword.isEmpty())
        {

            editTextConfirmPassword.error = "Confirm Password is required"
            return false
        }


        //true
        if(stringPassword.isNotEmpty() && stringConfirmPassword != stringPassword) {

            this.editTextConfirmPassword.error = "Passwords do not match"
            return false
        }


        if(stringEmail.isEmpty())
        {

            editTextEmail.error = "Email is required"
            return false
        }


        if(stringEmail.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches())
        {

            editTextEmail.error = "Valid email required"
            return false
        }

        if(stringAge < 16)
        {

            textViewDob.error = "User must be at least 16 years old"
            return false

        }


        if(stringPhone.isEmpty())
        {

            editTextPhone.error = "Phone is required "
            return false
        }

        if(stringPhone.isNotEmpty() && !Patterns.PHONE.matcher(stringPhone).matches())
        {

            editTextPhone.error = "Valid phone number is required"
            return false
        }


        return true
    }


    //Must be of length size 8
    //Must contain uppercase letter
    //Must contain number : 0 1 2 3 4 5 6 7 8 9
    //Must contain special character: ! @ # $ %
    private fun checksPassword(userPassword:String) : Int
    {

        if(userPassword.length < 8)
        {

            return 0

        }

        val loopTo = userPassword.length - 1
        var counterSecurity = 1


        for(i in 0..loopTo) {

        if(userPassword[i].isDigit()
            || userPassword[i].isUpperCase()
            || checksForSpecialChar(userPassword[i]))
            counterSecurity++


        }



    return counterSecurity

    }

    private fun checksForSpecialChar(aChar : Char) : Boolean
    {

        val specialCharacter : ArrayList<Char> = ArrayList()
        specialCharacter.add('!')
        specialCharacter.add('@')
        specialCharacter.add('#')
        specialCharacter.add('$')
        specialCharacter.add('%')

        val sizeOfList = 4

        for(i in 0..sizeOfList)
        {
            if(aChar == specialCharacter[i])
                return true
        }

        return false



    }















}







