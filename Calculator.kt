package calculator

class Calc {
    val inputString = ""
    var input = 0
    var result = 0

    object Operations {
        var operation = "no"

        fun sum(result: Int, input: Int): Int { return result + input }
        fun sub(result: Int, input: Int): Int  { return result - input }
    }
}


fun main() {
    val calc = Calc()
    val operations = Calc.Operations
    var initInput = calc.inputString
    var input = calc.input
    var result = calc.result
    var operation = operations.operation
        while (true) {
            initInput = readln()
            try {
                when {
                    initInput.isEmpty() -> continue
                    initInput == "/exit" -> break
                    initInput == "/help" -> {
                        println("The program calculates the sum of numbers")
                        continue
                    }

                    "\\/[a-zA-Z]*".toRegex().find(initInput) != null -> {
                        println("Unknown command")
                        continue
                    }

                    """[0-9]*""".toRegex().find(initInput) == null -> {
                        println("Invalid expression")
                        continue
                    }

                }
            val stringOfInput = initInput.toString().split(" ")
            result = stringOfInput[0].toInt()
            for (x in 1..stringOfInput.size - 1) {
                if (stringOfInput[x].toIntOrNull() == null) {
                    val inp = stringOfInput[x].substring(0).toList()
                    for (y in 0..inp.size - 1) {
                        if (operation == "no") {
                            when {
                                inp[y] == '+' ->
                                    operation = "sum"

                                inp[y] == '-' ->
                                    operation = "sub"
                            }
                        } else if (operation == "sum") {
                            when {
                                inp[y] == '+' -> operation = "sum"
                                inp[y] == '-' -> operation = "sub"
                            }
                        } else if (operation == "sub")
                            when {
                                inp[y] == '+' -> operation = "sub"
                                inp[y] == '-' -> operation = "sum"
                            }
                    }
                } else if (operation == "sum") {
                    result = operations.sum(result, stringOfInput[x].toInt())
                    operation = "no"
                } else if (operation == "sub") {
                    result = operations.sub(result, stringOfInput[x].toInt())
                    operation = "no"
                }
            }
            } catch (e:Exception) { println("Invalid expression") }
            println(result)
        }
    println("Bye")
}