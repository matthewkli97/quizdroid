package edu.washington.mkl.quizdroid

class QuizLibrary () {
    // Setup Quizes HERE

    val q1_1 = Question("1+1", arrayOf("2","10", "11", "16"), "2")
    val q1_2 = Question("10/10", arrayOf("1","10", "15", "16"), "1")
    val q1_3 = Question("11 * 22", arrayOf("242","0", "11", "16"), "242")

    val Quiz1 = Quiz("Math", "Math Quiz!", arrayOf(q1_1,q1_2,q1_3));
    val Quiz2 = Quiz("Physics", "Math Quiz!", arrayOf(q1_1,q1_2,q1_3));
    val Quiz3 = Quiz("Marvel", "Math Quiz!", arrayOf(q1_1,q1_2,q1_3));

    val Quizes:Map<String, Quiz> = mapOf("Math" to Quiz1, "Physics" to Quiz2, "Marvel" to Quiz3)
}

class Question (val question:String, val choices:Array<String>, val answer:String)

class Quiz (val title:String, val description:String, val questions:Array<Question>)
