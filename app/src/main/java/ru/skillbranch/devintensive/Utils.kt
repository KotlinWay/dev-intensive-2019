package ru.skillbranch.devintensive

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

}