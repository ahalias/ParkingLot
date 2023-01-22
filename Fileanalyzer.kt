package analyzer

import java.io.File

fun main(args: Array<String>) {
    val file = File(args[0])
    if (file.readText().contains(args[1])) println(args[2]) else println("Unknown file type")
}