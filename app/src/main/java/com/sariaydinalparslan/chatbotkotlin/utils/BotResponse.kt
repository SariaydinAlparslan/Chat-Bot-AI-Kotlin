package com.sariaydinalparslan.chatbotkotlin.utils

import com.sariaydinalparslan.chatbotkotlin.utils.Constants.OPEN_GOOGLE
import com.sariaydinalparslan.chatbotkotlin.utils.Constants.OPEN_SEARCH

object BotResponse {

    fun basicResponse (_message : String): String{
        val random = (0..2).random()
        val message = _message.toLowerCase()
        return when{
            message.contains("hello") ->{
                when (random){
                    0->"Hello there"
                    1->"Sup"
                    2->"Boungiorno"
                    else -> "error"
                }
            }
            message.contains("how are you") ->{
                when (random){
                    0->"I'm doing fine,thans for asking "
                    1->"I'm hungry"
                    2->"Pretty good How About you"
                    else -> "error"
                }
            }
            message.contains("where are you") ->{
                when (random){
                    0->"I'm home"
                    1->"I'm your mind"
                    2->"You don't know about thatt"
                    else -> "error"
                }
            }
            message.contains("flip") && message.contains("coin")->{
                var r =(0..1).random()
                val result = if (r ==0) "heads" else "tails"
                "I flipped a coin and it landed on $result"
            }

            message.contains("solve") ->{
                val equation : String? = message.substringAfter("solve")
                return  try {
                    val answer = SolveMath.solveMath(equation ?:"0")
                    answer.toString()
                }catch (e : Exception){
                    "Sorry , I can't solve that"
                }
            }
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }

            message.contains("open") && message.contains("google") ->{
                OPEN_GOOGLE
            }
            message.contains("search") ->{
                OPEN_SEARCH
            }

            else ->{
                when (random){
                    0->"I dont understand"
                    1->"Idk"
                    2->"Try asking me something different!"
                    else -> "error"
                }
            }


        }
    }

}