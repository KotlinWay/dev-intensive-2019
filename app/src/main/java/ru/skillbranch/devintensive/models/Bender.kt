package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val trimAnswer = answer.trim()
        if (answer.trim().isEmpty()){
            return sendError()
        }
        if (question == Question.IDLE) {
            return "На этом все, вопросов больше нет" to status.color

        }
        return if (question.answers.contains(trimAnswer.toLowerCase())) {
            when (question){
                Question.NAME -> {
                    val first = trimAnswer.first()
                    val firstCapital = first.toUpperCase()
                    if (first != firstCapital) {
                        return "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
                    }
                }
                Question.PROFESSION -> {
                    val first = trimAnswer.first()
                    val firstCapital = first.toLowerCase()
                    if (first != firstCapital) {
                        return "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
                    }
                }

            }
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            when (question) {
                Question.MATERIAL -> {
                    val regex = "\\D*".toRegex()
                    if (!regex.matches(answer)) {
                        return "Материал не должен содержать цифр\n${question.question}" to status.color
                    }

                }
                Question.BDAY -> {
                    val regex = "\\d+".toRegex()
                    if (!regex.matches(answer)) {
                        return "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
                    }

                }
                Question.SERIAL -> {
                    val regex = "\\d{7}".toRegex()
                    if (!regex.matches(answer)) {
                        return "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
                    }
                }
            }

            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                sendError()
            }
        }
    }

    private fun sendError(): Pair<String, Triple<Int, Int, Int>> {
        status = status.nextStatus()
        return "Это неправильный ответ\n${question.question}" to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion() = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion() = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion() = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion() = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion() = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion() = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}