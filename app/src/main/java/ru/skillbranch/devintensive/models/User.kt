package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date = Date(),
    var isOnline: Boolean = false
) {
    constructor(
        id: String,
        firstName: String?,
        lastName: String?
    ) : this(id = id, firstName = firstName, lastName = lastName, avatar = null)

    constructor(id:String) : this(id, "John", "Doe $id")

    init{
//        println("I'm alive!!")
    }

    companion object Factory{
        var countId = -1
        fun makeUser(fullname:String?): User {
            val (firstName, lastName) = Utils.parseFullName(fullname)
            return User(
                id = "${++countId}",
                firstName = firstName,
                lastName =  lastName
            )
        }
    }
}