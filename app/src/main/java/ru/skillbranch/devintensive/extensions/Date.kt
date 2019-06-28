package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND*60
const val HOUR = MINUTE*60
const val DAY = HOUR*24

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String{
    val simpleDateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return simpleDateFormat.format(this)
}

fun Date.add(value:Int, timeUnits: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(timeUnits){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}
fun Date.humanizeDiff(): String {
return ""
}


enum class TimeUnits{
    SECOND, MINUTE, HOUR, DAY
}