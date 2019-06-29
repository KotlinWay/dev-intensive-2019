package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60L
const val HOUR = MINUTE * 60L
const val DAY = HOUR * 24L
const val MONTH = DAY * 30
const val YEAR = MONTH * 12

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return simpleDateFormat.format(this)
}

fun Date.add(value: Int, timeUnits: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (timeUnits) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.MONTH -> value * MONTH
        TimeUnits.YEAR -> value * YEAR
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(diffDate: Date = Date()): String {

    var delta = diffDate.time - this.time

    var isDatePositive = delta >= 0

    if (!isDatePositive) delta *= -1

//    if(this.time < )

    val (value, timeUnit) = when (delta) {
        in 0 until MINUTE -> delta / SECOND to TimeUnits.SECOND
        in MINUTE until HOUR -> delta / MINUTE to TimeUnits.MINUTE
        in HOUR until DAY -> delta / HOUR to TimeUnits.HOUR
        in DAY until MONTH -> delta / DAY to TimeUnits.DAY
        in MONTH until YEAR -> delta / MONTH to TimeUnits.MONTH
        else -> delta / YEAR to TimeUnits.YEAR
    }
    return timeUnit.toStringDeltaValue(value, isDatePositive)
}

fun Date.humanizeDiff(): String {
    var delta = this.time - Date().time

    var isDatePositive = delta >= 0

    if (!isDatePositive) delta *= -1

//    if(this.time < )

    val (value, timeUnit) = when (delta) {
        in 0 until MINUTE -> delta / SECOND to TimeUnits.SECOND
        in MINUTE until HOUR -> delta / MINUTE to TimeUnits.MINUTE
        in HOUR until DAY -> delta / HOUR to TimeUnits.HOUR
        in DAY until MONTH -> delta / DAY to TimeUnits.DAY
        in MONTH until YEAR -> delta / MONTH to TimeUnits.MONTH
        else -> delta / YEAR to TimeUnits.YEAR
    }
    return timeUnit.toStringDeltaValue(value, isDatePositive)
}


enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY, MONTH, YEAR;

    override fun toString(): String {
        return this.ordinal.toString()
    }

    fun toStringDeltaValue(value: Long, datePositive: Boolean): String {
        if (value == 0L) return "только что"
        var result = when (this) {
            SECOND -> getNormalFormSecond(value)
            MINUTE -> getNormalFormMinute(value)
            HOUR -> getNormalFormHour(value)
            DAY -> getNormalFormDay(value)
            MONTH -> getNormalFormMonth(value)
            YEAR -> getNormalFormYear(value)
        }


        if (datePositive) {
            if (this.ordinal == SECOND.ordinal) {
                result = "через несколько секунд"
            } else if (this.ordinal == YEAR.ordinal) {
                result = "более чем через год"
            } else {
                result = "через $result"
            }
        } else {
            if (this.ordinal == SECOND.ordinal) {
                result = "несколько секунд назад"
            } else if (this.ordinal == YEAR.ordinal) {
                result = "более года назад"
            } else {
                result = "$result назад"
            }
        }

        return result
    }

    private fun getNormalFormSecond(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L

        return when (lv) {
            1L -> "$lv секунду"
            in 2..4 -> "$lv секунды"
            in 11..19 -> "$lv секунд"
            else -> {
                if (lv % 10 == 1L) {
                    "$lv секунду"
                } else if (lv % 10 in 2..4) {
                    "$lv секунды"
                } else if (lv % 10 == 0L) {
                    "$lv секунд"
                } else {
                    "$lv секунд"
                }
            }
        }
    }

    private fun getNormalFormMinute(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L
        return when (lv) {
            1L -> "$lv минуту"
            in 2..4 -> "$lv минуты"
            in 11..19 -> "$lv минут"
            else -> {
                if (lv % 10 == 1L) {
                    "$lv минуту"
                } else if (lv % 10 in 2..4) {
                    "$lv минуты"
                } else if (lv % 10 == 0L) {
                    "$lv минут"
                } else
                    "$lv минут"
            }
        }
    }

    private fun getNormalFormHour(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L
        return when (lv) {
            1L -> "$lv час"
            in 2..4 -> "$lv часа"
            in 11..19 -> "$lv часов"
            else -> {
                if (lv % 10 == 1L) {
                    "$lv часов"
                } else if (lv % 10 in 2..4) {
                    "$lv часа"
                } else if (lv % 10 == 0L) {
                    "$lv часов"
                } else
                    "$lv часов"
            }
        }
    }

    private fun getNormalFormDay(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L
        return when (lv) {
            1L -> "$lv день"
            in 2..4 -> "$lv дня"
            in 11..19 -> "$lv дней"
            else -> {
                if (lv % 10 == 1L) {
                    "$lv день"
                } else if (lv % 10 in 2..4) {
                    "$lv дня"
                } else if (lv % 10 == 0L) {
                    "$lv дней"
                } else
                    "$lv дней"
            }
        }
    }

    private fun getNormalFormMonth(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L
        return when (lv) {
            1L -> "$lv месяц"
            in 2..4 -> "$lv месяца"
            in 11..19 -> "$lv месяцев"
            else -> {
                if (lv / 10 == 1L) {
                    "$lv месяцев"
                } else if (lv / 10 in 2..4) {
                    "$lv месяца"
                } else if (lv / 10 == 0L) {
                    "$lv месяцев"
                } else
                    "$lv месяцев"
            }
        }
    }

    private fun getNormalFormYear(value: Long): String {
        var lv = value
        if (value < 0) lv = value * -1L
        return when (lv) {
            1L -> "$lv год"
            in 2..4 -> "$lv года"
            in 11..19 -> "$lv лет"
            else -> {
                if (lv % 10 == 1L) {
                    "$lv лет"
                } else if (lv % 10 in 2..4) {
                    "$lv года"
                } else if (lv % 10 == 0L) {
                    "$lv лет"
                } else
                    "$lv лет"
            }
        }
    }
}