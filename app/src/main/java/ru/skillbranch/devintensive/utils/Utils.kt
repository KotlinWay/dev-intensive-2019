package ru.skillbranch.devintensive.utils

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


    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstChar = firstName?.trim()?.firstOrNull()?.toUpperCase()
        val secondChar = lastName?.trim()?.firstOrNull()?.toUpperCase()
        return when (firstChar to secondChar) {
            null to null -> null
            null to secondChar -> secondChar.toString()
            firstChar to null -> firstChar.toString()
            else -> firstChar.toString() + secondChar.toString()
        }
    }


    fun transliteration(payload: String, divider: String = " "): String {
        if (payload.trim().isEmpty()) return ""
        var result = StringBuilder()

        for (c in payload) {
            result.append(when (c) {
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

    fun validLinkToGithub(s: String): Boolean {
        var isValid = true
        val setExceptions = setOf("enterprise", "features", "topics", "collections", "trending", "events", "marketplace", "pricing", "nonprofit", "customer-stories", "security", "login", "join")
        when {
            s.trim() == "" -> isValid = true
            s.startsWith("https://github.com") -> {
                val tempString = s.substring("https://github.com".length, s.length)
                if (tempString.contains("/")) isValid = false
                if (setExceptions.contains(tempString)) isValid = false
                if (tempString.trim() == "") isValid = false
            }
            s.startsWith("www.github.com") -> {
                val tempString = s.substring("www.github.com".length, s.length)
                if (tempString.contains("/")) isValid = false
                if (setExceptions.contains(tempString)) isValid = false
                if (tempString.trim() == "") isValid = false
            }
            s.startsWith("https://www.github.com") -> {
                val tempString = s.substring("https://www.github.com".length,  s.length)
                if (tempString.contains("/")) isValid = false
                if (setExceptions.contains(tempString)) isValid = false
                if (tempString.trim() == "") isValid = false
            }
            else -> isValid = false

        }
        return isValid
    }
}