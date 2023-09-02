package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private val operator_list = listOf("+", "-", "/", "x", "^")
    private var bracket_available_pairs = 0


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    fun inputButton(view: View) {
        with(binding){
            when((view as Button).text.toString()){
                "1" -> {
                    num_add("1")
                }
                "2" -> {
                    num_add("2")
                }
                "3" -> {
                    num_add("3")
                }
                "4" -> {
                    num_add("4")
                }
                "5" -> {
                    num_add("5")
                }
                "6" -> {
                    num_add("6")
                }
                "7" -> {
                    num_add("7")
                }
                "8" -> {
                    num_add("8")
                }
                "9" -> {
                    num_add("9")
                }
                "0" -> {
                    num_add("0")
                }
                "%" -> {
                    num_add("%")
                }
                "/" -> {
                    opr_add("/")
                }
                "x" -> {
                    opr_add("x")
                }
                "-" -> {
                    opr_add("-")
                }
                "+" -> {
                    opr_add("+")
                }
                "^" -> {
                    opr_add("^")
                }
                "." -> {
                    num_add(".")
                }

                "()" -> {
                    if (bracket_available_pairs == 0
                        && hasil.text.toString().takeLast(1) != ")"){
                        hasil.text = hasil.text.toString() + "("
                        bracket_available_pairs++
                    } else if (bracket_available_pairs > 0
                            && hasil.text.toString().takeLast(1) != "("
                            && !operator_list.contains(hasil.text.toString().takeLast(1))) {
                        hasil.text = hasil.text.toString() + ")"
                        bracket_available_pairs--
                    } else {}
                }
                "C" -> {
                    hasil.text = ""
                    bracket_available_pairs= 0
                }
                "=" -> {
                    if (!operator_list.contains(hasil.text.toString().takeLast(1)) ) {
                        hasil.text = solve(hasil.text.toString() + '=')
                    } else {}
                }
                else -> {
                    hasil.text = "NaN"
                }

            }
        }
    }

    private fun solve(infix : String): String {
        val stack = ArrayList<Char>()
        val postfix = ArrayList<String>()
        var temp : String = ""
        fun operationPriority(x : Char) : Int {
            return when (x) {
                '^' -> {3}
                'x' -> {2}
                '/' -> {2}
                '%' -> {2}
                '+' -> {1}
                '-' -> {1}
                else -> {0}
            }
        }


        // Infix to Postfix
        for (chr in infix) {
            if (chr in listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')) {
                temp += chr
            }
            else {
                if (temp.isNotEmpty()) {
                    temp += "d"
                    postfix.add(temp)
                    temp = ""
                }

                if (chr == '(') {
                    stack.add(chr)
                }
                else if (chr == ')') {
                    while (stack.last() != '(') {
                        postfix.add(stack.removeLast().toString())
                    }
                    stack.removeLast()
                }
                else {
                    if (stack.isEmpty()) {
                        stack.add(chr)
                    }
                    else if (operationPriority(chr) > operationPriority(stack.last())) {
                        stack.add(chr)
                    }
                    else {
                        while (stack.isNotEmpty() && operationPriority(chr) <= operationPriority(stack.last())) {
                            postfix.add(stack.removeLast().toString())

                        }
                        if (chr != '=') {
                            stack.add(chr)
                        }
                    }
                }

            }
        }

        // Calculate Postfix
        try {
            while (postfix.size > 1) {
                var i: Int = 0
                while (postfix[i].toDoubleOrNull() != null) {
                    i++
                }
                var _operator = postfix[i]
                when (_operator) {
                    "^" -> {
                        postfix[i] = "${postfix[i - 2].toDouble().pow(postfix[i - 1].toDouble())}d"
                        postfix.removeAt(i - 2)
                        postfix.removeAt(i - 2)
                    }
                    "x" -> {
                        postfix[i] = "${postfix[i-2].toDouble() * postfix[i-1].toDouble()}d"
                        postfix.removeAt(i - 2)
                        postfix.removeAt(i - 2)
                    }
                    "/" -> {
                        postfix[i] = "${postfix[i-2].toDouble() / postfix[i-1].toDouble()}d"
                        postfix.removeAt(i - 2)
                        postfix.removeAt(i - 2)
                    }
                    "%" -> {
                        postfix[i] = "${postfix[i-1].toDouble() / 100}d"
                        postfix.removeAt(i - 1)
                    }
                    "+" -> {
                        postfix[i] = "${postfix[i-2].toDouble() + postfix[i-1].toDouble()}d"
                        postfix.removeAt(i - 2)
                        postfix.removeAt(i - 2)
                    }
                    "-" -> {
                        postfix[i] = "${postfix[i-2].toDouble() - postfix[i-1].toDouble()}d"
                        postfix.removeAt(i - 2)
                        postfix.removeAt(i - 2)
                    }
                    else -> {
                        //TODO: Something
                    }
                }
            }
            val hasil = postfix.removeLast().toString().toDouble()
            if (hasil % 1 == 0.0) {
                return hasil.toInt().toString()
            }
            else {
                return hasil.toString()
            }
        }
        catch (e: ArithmeticException) {
            return "Maaf belum bisa"
        }
    }


    private fun num_add(x: String){
        with(binding){
            hasil.text = hasil.text.toString() + x
        }
    }

    private fun opr_add(x: String){
        with(binding){
            if (!operator_list.contains(hasil.text.toString().takeLast(1))){
                hasil.text = hasil.text.toString() + x
            }
        }
    }

    fun backspace(view: View) {
        with(binding){
            if (hasil.text.toString().takeLast(1) == ")") {
                bracket_available_pairs++
            } else if (hasil.text.toString().takeLast(1) == "(") {
                bracket_available_pairs--
            }
            hasil.text = hasil.text.toString().dropLast(1)
        }
    }



}