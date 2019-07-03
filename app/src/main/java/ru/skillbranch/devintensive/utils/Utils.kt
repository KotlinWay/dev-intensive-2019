package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.extensions.TimeUnits
import java.lang.StringBuilder
import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        if (fullName?.trim()?.split(" ")?.size != null && fullName.isNotBlank()) {
            var fullNameList = fullName.trim().split(" ")
            fullNameList = fullNameList.filter { it.isNotEmpty() }
            return if (fullNameList.size > 1) {
                Pair(fullNameList[0], fullNameList[1])
            } else {
                fullNameList[0] to null
            }
        } else {
            return when (fullName?.trim()) {
                null, "" -> null to null
                else -> null to null
            }
        }
    }


    fun toInitials(firstName:String?, lastName:String?):String?{
        val firstChar = firstName?.trim()?.firstOrNull()?.toUpperCase()
        val secondChar = lastName?.trim()?.firstOrNull()?.toUpperCase()
        return when(firstChar to secondChar){
            null to null -> null
            null to secondChar -> secondChar.toString()
            firstChar to null -> firstChar.toString()
            else -> firstChar.toString() + secondChar.toString()
        }
    }



    fun transliteration(payload:String, divider:String = " "): String{
        var result = StringBuilder()

        for (c in payload){
            result.append(when(c){
                'а' -> "a"
                'б' -> "b"
                'в' -> "v"
                'г' -> "g"
                'д' -> "d"
                'е' -> "e"
                'ё' -> "e"
                'ж' -> "zh"
                'з' -> "z"
                'и' -> "i"
                'й' -> "i"
                'к' -> "k"
                'л' -> "l"
                'м' -> "m"
                'н' -> "n"
                'о' -> "o"
                'п' -> "p"
                'р' -> "r"
                'с' -> "s"
                'т' -> "t"
                'у' -> "u"
                'ф' -> "f"
                'х' -> "h"
                'ц' -> "c"
                'ч' -> "ch"
                'ш' -> "sh"
                'щ' -> "sh'"
                'ъ' -> ""
                'ы' -> "i"
                'ь' -> ""
                'э' -> "e"
                'ю' -> "yu"
                'я' -> "ya"
                'А' -> "A"
                'Б' -> "B"
                'В' -> "V"
                'Г' -> "G"
                'Д' -> "D"
                'Е' -> "E"
                'Ё' -> "E"
                'Ж' -> "Zh"
                'З' -> "Z"
                'И' -> "I"
                'Й' -> "I"
                'К' -> "K"
                'Л' -> "L"
                'М' -> "M"
                'Н' -> "N"
                'О' -> "O"
                'П' -> "P"
                'Р' -> "R"
                'С' -> "S"
                'Т' -> "T"
                'У' -> "U"
                'Ф' -> "F"
                'Х' -> "H"
                'Ц' -> "C"
                'Ч' -> "Ch"
                'Ш' -> "Sh"
                'Щ' -> "Sh'"
                'Ъ' -> ""
                'Ы' -> "I"
                'Ь' -> ""
                'Э' -> "E"
                'Ю' -> "Yu"
                'Я' -> "Ya"
                ' ' -> divider
                else -> c.toString()
            })
        }
        return result.toString()
    }

}