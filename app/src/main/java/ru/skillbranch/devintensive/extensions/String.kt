package ru.skillbranch.devintensive.extensions

import java.util.*


//    *String.truncate
//    Необходимо реализовать метод truncate усекающий исходную строку до указанного числа символов и добавляющий заполнитель "..." в конец строки
//    +1
//    Реализуй extension усекающий исходную
//    строку до указанного числа символов (по умолчанию 16) и возвращающий усеченную ст
//    року с заполнителем "..." если последний символ усеченной строки является пробелом - удалить его и добавить заполнитель
//    Пример:
//    "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending Ro...
//    "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(14) //Bender Bending...


fun String.truncate(value: Int = 16): String {
    return if (this.trimEnd().length <= value) {
        this
    } else {
        var result = this.substring(0, value).trim()
        return when (result) {
            "" -> result
            else -> "${result}..."
        }
    }
}

//    *String.stripHtml
//    Необходимо реализовать метод stripHtml для очистки строки от лишних пробелов, html тегов, escape последовательностей
//    +1
//    Реализуй extension позволяющий очистить строку от html тегов и html
//    escape последовательностей ("& < > ' ""), а так же удалить пустые символы (пробелы)
//    между словами если их больше 1. Необходимо вернуть модифицированную строку
//    Пример:
//    "<p class="title">Образовательное IT-сообщество Skill Branch</p>".stripHtml()
// Образовательное IT-сообщество Skill Branch
//    "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml()
// Образовательное IT-сообщество Skill Branch

fun String.stripHtml(): String {
    val temp = this.trim().replace("\\s+".toRegex(), " ")
    var result = StringBuilder("")
    val stack = Stack<Char>()
    var flagNext = true
    for (c in temp) {
        when (c) {
            '<' -> {
                flagNext = false
                stack.push(c)
            }
            '>' -> {
                flagNext = true
                stack.pop()
            }
        }
        if(flagNext){
            if(c !in "&<>'\"") result.append(c)
        }
    }
    return result.toString()
}