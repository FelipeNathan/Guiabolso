package com.felipenathan.guiabolso.service

import java.sql.Timestamp
import java.util.Calendar
import kotlin.random.Random

object MockService {
    fun generateRandomDate(ano: Int, mes: Int): Timestamp {
        val offset = Calendar.getInstance()
        offset.set(Calendar.MONTH, mes - 1)
        offset.set(Calendar.YEAR, ano)
        offset.set(Calendar.DAY_OF_MONTH, offset.getActualMinimum(Calendar.DAY_OF_MONTH))

        val end = Calendar.getInstance()
        end.set(Calendar.MONTH, mes - 1)
        end.set(Calendar.YEAR, ano)
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH))

        val diff = end.timeInMillis - offset.timeInMillis + 1
        return Timestamp((offset.timeInMillis + (Math.random() * diff)).toLong())
    }

    fun generateRandomDescription(): String {
        val upperCasePattern = 'A'..'Z'
        val vowel = arrayOf('a', 'e', 'i', 'o', 'u')
        val lowerCasePattern = ('a'..'z') - vowel

        var description = upperCasePattern.random().toString()

        for (i in 1..Random.nextInt(10, 60)) {

            val randomSpace = Random.nextInt(5, 10)
            val lastSpace = description.lastIndexOf(" ")
            if (lastSpace > -1) {
                if (description.substring(lastSpace + 1).length > randomSpace) {
                    description += " "
                }
            } else if (description.length >= randomSpace) {
                description += " "
            }

            if (description.length >= 60)
                break

            description += vowel.random()
            if (description.length >= 60)
                break

            description += lowerCasePattern.random().toString()
            if (description.length >= 60)
                break
        }

        return description
    }
}