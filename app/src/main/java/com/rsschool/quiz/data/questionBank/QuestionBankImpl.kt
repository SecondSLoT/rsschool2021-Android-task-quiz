package com.rsschool.quiz.data.questionBank

import com.rsschool.quiz.data.questionBank.model.QuestionEntity
import com.rsschool.quiz.data.repository.QuestionBank

object QuestionBankImpl : QuestionBank {

    private val questions: List<QuestionEntity> = listOf(
        QuestionEntity(
            "Территория какой из этих стран - наибольшая?",
            arrayOf("Германия", "Япония", "Италия", "Польша", "Финляндия"),
            1
        ),
        QuestionEntity(
            "Какой из этих городов - родина Казановы?",
            arrayOf("Неаполь", "Милан", "Верона", "Флоренция", "Венеция"),
            4
        ),
        QuestionEntity(
            "Кто считается основоположником кубизма?",
            arrayOf("В. Кандинский", "Ф. Леже", "К. Моне", "П. Пикассо", "К. Малевич"),
            3
        ),
        QuestionEntity(
            "Какая березка стояла во поле?",
            arrayOf("Высокая", "Кудрявая", "Зеленая", "Засохшая", "Красивая"),
            1
        ),
        QuestionEntity(
            "С какой фигуры начинаются соревнования по городошному спорту?",
            arrayOf("Пушка", "Пулемётное гнездо", "Ракета", "Артиллерия", "Часовые"),
            0
        ),
        QuestionEntity(
            "Благодаря какому животному Шурик познакомился с Ниной в к/ф \"Кавказская пленница\"?",
            arrayOf("Верблюд", "Ёжик", "Осёл", "Конь", "Бык"),
            2
        ),
        QuestionEntity(
            "За какую плату работал Балда у попа в сказке " +
                    "А.С. Пушкина \"Сказка о попе и работнике его Балде\"?",
            arrayOf("Три подзатыльника", "Три пинка", "Три рубля", "Три щелчка по лбу", "Три поцелуя"),
            3
        ),
        QuestionEntity(
            "Где муха-цокотуха нашла денежку?",
            arrayOf("На лугу", "В лесу", "В поле", "Во дворе", "На вписке"),
            2
        ),
        QuestionEntity(
            "Сколько баллов вы поставите за эту работу? ;)",
            arrayOf("100", "200", "500", "1000", "Все, что есть, поставлю!"),
            0
        ),
    )

    private var answers = IntArray(questions.size)

    override fun getQuestions(): List<QuestionEntity> {
        return questions
    }

    override fun getAnswers(): IntArray {
        return answers
    }

    override fun setAnswers(answers: IntArray) {
        this.answers = answers
    }
}