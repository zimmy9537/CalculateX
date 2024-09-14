package com.best.zimmy.calculatex.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.best.zimmy.calculatex.data.CalculatorRepository
import java.util.Stack

class CalculatorViewModel(repository: CalculatorRepository) : ViewModel() {

    val lastInput: MutableState<String?> = mutableStateOf(null)
    val expression: MutableState<String> = mutableStateOf("")
    val result: MutableState<String?> = mutableStateOf(null)

    fun onButtonPressed(button: String) {
        expression.value += button
        if (isValidExpression(expression.value)) {
            lastInput.value = button
            result.value = "${evaluatePostfix(infixToPostfix(expression.value))}"
        } else {
            if (isOperator(expression.value.last())){
                expression.value = expression.value.dropLast(1)
                onButtonPressed(button)
            }
            // else if +/- or % is pressed
            else if (button == "E") {
                expression.value = expression.value.dropLast(1)
                onButtonPressed("=")
            }
            else if (button == "+/-") {
                expression.value = expression.value.dropLast(1)
                expression.value += "*-1"
                onButtonPressed("=")
            }
            else {
                expression.value = ""
                result.value = "Invalid Expression"
            }
        }
    }

    val gridItems = listOf(
        "C", "+/-", "%", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        ".", "0", "E", "="
    )

    fun isOperator(char: Char) = char in "+-*/"

    fun infixToPostfix(expression: String): String {
        val result = StringBuilder()
        val stack = Stack<Char>()
        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '(' to 0)

        for (char in expression) {
            when {
                char.isDigit() -> result.append(char) // Append number directly to the result
                char == '(' -> stack.push(char) // Push opening parentheses onto the stack
                char == ')' -> {
                    // Pop till the corresponding '(' is found
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(stack.pop())
                    }
                    stack.pop() // Pop '('
                }

                char in precedence.keys -> {
                    // Pop operators of higher or equal precedence
                    while (!stack.isEmpty() && precedence[stack.peek()]!! >= precedence[char]!!) {
                        result.append(stack.pop())
                    }
                    stack.push(char)
                }
            }
        }

        // Pop the remaining operators from the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop())
        }

        return result.toString()
    }

    fun evaluatePostfix(expression: String): Double {
        val stack = Stack<Double>()

        for (char in expression) {
            when {
                char.isDigit() -> stack.push(char.toString().toDouble())
                char == '+' -> stack.push(stack.pop() + stack.pop())
                char == '-' -> {
                    val second = stack.pop()
                    val first = stack.pop()
                    stack.push(first - second)
                }

                char == '*' -> stack.push(stack.pop() * stack.pop())
                char == '/' -> {
                    val second = stack.pop()
                    val first = stack.pop()
                    stack.push(first / second)
                }
            }
        }
        return stack.pop()
    }

    private fun isValidExpression(expression: String): Boolean {
        var balance = 0
        var lastChar: Char? = null

        for (char in expression) {
            when (char) {
                in '0'..'9' -> {} // Numbers are valid
                '+', '-', '*', '/' -> {
                    // Operators should not be at the start or after another operator
                    if (lastChar == null || lastChar in "+-*/(") return false
                }

                '(' -> balance++ // Open parenthesis increases balance
                ')' -> {
                    balance-- // Close parenthesis decreases balance
                    // Balance should never be negative, and it shouldn't follow an operator
                    if (balance < 0 || lastChar!! in "+-*/(") return false
                }

                else -> return false // Invalid characters
            }
            lastChar = char
        }

        // Expression must end with a digit or close parenthesis and have balanced parentheses
        return balance == 0 && (lastChar?.isDigit() == true || lastChar == ')')
    }
}