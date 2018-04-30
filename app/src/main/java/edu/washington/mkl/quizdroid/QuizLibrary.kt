package edu.washington.mkl.quizdroid

class QuizLibrary () {
    // Setup Quizes HERE

    val q1_1 = Question("1+1", arrayOf("2","10", "11", "16"), "2")
    val q1_2 = Question("10/10", arrayOf("1","10", "15", "16"), "1")
    val q1_3 = Question("11 * 22", arrayOf("242","0", "11", "16"), "242")

    val q2_1 = Question("Which of the following is a physical quantity that has a magnitude but no direction?",
            arrayOf("Vector","Frame of reference", "Resultant", "Scalar"), "Scalar")
    val q2_2 = Question("Multiplying or dividing vectors by scalars results in",
            arrayOf("Vectors if multiplied or scalars if divided","Scalars if multiplied scalars", "Scalars", "Vectors"), "Vectors")
    val q2_3 = Question("Identify the following quantities as scalar or vector: the mass of an object, the number of leaves on a tree, wind velocity.",
            arrayOf("Vector, scalar, scalar","Vector, scalar, vector", "Scalar, scalar, vector", "Scalar, vector, vector"), "Scalar, scalar, vector")
    val q2_4 = Question("Which of the following is an example of a vector quantity?",
            arrayOf("Temperature","Velocity", "Volume", "Mass"), "Velocity")

    val q3_1 = Question("'Guardians of the Galaxy' director James Gunn confirmed via Twitter that the Orb holds which Infinity Stone?",
            arrayOf("The space stone","The mind stone", "The power stone", "The time stone"), "The power stone")
    val q3_2 = Question("What line of dialogue reveals Senator Stern to be a villain in 'Captain America: The Winter Soldier'?",
            arrayOf("Death to Shield","I am issuing a direct order to kill Captain America on sight", "Hail Hydra", "Cut one off. Two shall take its place."),
            "Hail Hydra")
    val q3_3 = Question("What's Abomination's real name?", arrayOf("Brock Rumlow","Thunderbolt Ross", "Georges Batroc", "Emil Blonsky"), "Emil Blonsky")

    val Quiz1 = Quiz("Math", "Math Quiz!", arrayOf(q1_1,q1_2,q1_3));
    val Quiz2 = Quiz("Physics", "This is a simple physics quiz!", arrayOf(q2_1,q2_2,q2_3,q2_4));
    val Quiz3 = Quiz("Marvel", "Math Quiz!", arrayOf(q3_1,q3_2,q3_3));

    val Quizes:Map<String, Quiz> = mapOf("Math" to Quiz1, "Physics" to Quiz2, "Marvel" to Quiz3)

    class Question (val question:String, val choices:Array<String>, val answer:String)

    class Quiz (val title:String, val description:String, val questions:Array<Question>)
}

