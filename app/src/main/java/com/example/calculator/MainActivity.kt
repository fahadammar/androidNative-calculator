package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    // Represent whether the lastly pressed key is numeric or not
    var lastNumeric: Boolean = false

    // If true, do not allow to add another DOT
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // OnClick functions

    fun onDigit(view: View){
    // view is the parent class of all the widgets
      opText.append((view as Button).text)
     /*
     * A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
     * */
        flagSetter(true, null)
    }

    fun onClear(view: View){
        opText.text = ""
        flagSetter(false, false)
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            opText.append(".")
            flagSetter(false, true)
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(opText.text.toString())){
            opText.append((view as Button).text)
            flagSetter(false, false)
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            try {
                var opValue : String = opText.text.toString()
                var prefix: String = ""

                if(opValue.startsWith("-")){
                    prefix = "-"
                    // if the minus is at the start then index[0] = "-",
                    // so we skip minus and get the rest of string which contains the number and arithmetic operator
                    opValue = opValue.substring(1)
                }

                if(opValue.contains("-")){
                    var splitValue = opValue.split("-")
                    arithmeticOperation(splitValue,prefix, "-")
                }
                else if(opValue.contains("+")) {
                    var splitValue = opValue.split("+")
                    arithmeticOperation(splitValue,prefix, "+")
                }
                else if(opValue.contains("*")) {
                    var splitValue = opValue.split("*")
                    arithmeticOperation(splitValue,prefix, "*")
                }
                else if(opValue.contains("/")) {
                    var splitValue = opValue.split("/")
                    arithmeticOperation(splitValue,prefix, "/")
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                Log.e("ERROR", "MSG -> " + e.message)
            }
        }

    }


    // Private Functions //

    private fun flagSetter(flag1: Boolean, flag2 : Boolean?) {
        lastNumeric =  flag1
        if(flag2 != null)
            lastDot =  flag2
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
                value.contains("/") || value.contains("+")
            || value.contains("*") || value.contains("-")
        }
    }

    private fun removeEndZero(value : String) : String {
        var result : String = ""
        if(value.contains(".0")){
            // start from 0 index and goes till end before end 2 characters
            result = value.substring(0, value.length - 2)
        }
        return result;
    }

    private fun arithmeticOperation(value:List<String>, prefix: String, operator: String){
        var one = value[0]
        var two = value[1]

        var result : Double = 0.0

        if(!prefix.isEmpty()){
            one = "-$one"
        }

        when(operator){
            "-" -> result = one.toDouble() - two.toDouble()
            "+" -> result = one.toDouble() + two.toDouble()
            "*" -> result = one.toDouble() * two.toDouble()
            "/" -> result = one.toDouble() / two.toDouble()
        }

        // sets the arithmetic operation result to Output Text (TextView)
        opText.text = removeEndZero(result.toString())
    }

}