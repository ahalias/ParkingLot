package minesweeper

import kotlin.random.Random
import java.util.*

class MineSweeper (var mines: Int) {
    val mineField = MutableList(9) {MutableList(9) {"."}}
    val mineFieldHidden = MutableList(9) {MutableList(9) {"."}}
    var minesFound: Int = 0

    fun setField () {
        var minesSetted = 0
        while (minesSetted < mines) {
            var positionX = Random.nextInt(0, 8)
            var positionY = Random.nextInt(0, 8)
            if (mineField[positionX][positionY] == ".") {
                mineField[positionX][positionY] = "X"
                minesSetted++
            }
        }
        for (x in 0..8) {
            for (y in 0..8) {
                if (mineField[x][y] == "X") {
                    for (xPos in listOf(x, x - 1, x + 1)) {
                        for (yPos in listOf(y, y - 1, y + 1)) {
                            if (xPos in 0..8 && yPos in 0..8) {
                                if (mineField[xPos][yPos] == ".") {
                                    mineField[xPos][yPos] = "1"
                                } else if (mineField[xPos][yPos] != "X") {
                                    mineField[xPos][yPos] = (mineField[xPos][yPos].toInt() + 1).toString()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun printField () {
        val coordinatesH = " │123456789│"
        val delimiter = "—│—————————│"
        println(coordinatesH)
        println(delimiter)
        for (h in 1..this.mineFieldHidden.size) {
            println("$h|${this.mineFieldHidden[h-1].joinToString("")}|")
        }
        println(delimiter)
    }

    fun setMark (y: Int, x: Int, status: String): String {
        var xPos = x - 1
        var yPos = y - 1



        if (status == "free") {
            if (mineField[xPos][yPos].toIntOrNull() != null) {mineFieldHidden[xPos][yPos] = mineField[xPos][yPos]}
            else if (mineField[xPos][yPos] == "X") {return "false"}
            else if (mineField[xPos][yPos] == ".") {

                for (x1 in -8..8) {
                    for (y1 in -8..8) {
                        var x1 = x1+xPos
                        var y1 = y1+yPos
                        if (x1 in 0..8 && y1 in 0..8) {
                            if (mineField[x1][y1] == ".") {
                                mineFieldHidden[x1][y1] = "/"


                                for (x2 in -8..8) {
                                    for (y2 in -8..8) {
                                        if (x2 in 0..8 && y2 in 0..8) {
                                            if (mineField[x2][y2].toIntOrNull() != null) {
                                                mineFieldHidden[x2][y2] = mineField[x2][y2]
                                            } else if (mineField[x2][y2] == ".")
                                            {
                                                mineFieldHidden[x2][y2] = "/"
                                            } else if (mineFieldHidden[x2][y2] == "/") {break}

                                        }
                                    }
                                }




                            } else if (mineField[x1][y1].toIntOrNull() != null) {
                                mineFieldHidden[x1][y1] = mineField[x1][y1]
                                break
                            }
                        }
                    }
                }
            }
        }


        if (status == "mine") {
            if (mineFieldHidden[xPos][yPos] == "*") {
                mineFieldHidden[xPos][yPos] = "."
            } else {
                mineFieldHidden[xPos][yPos] = "*"
                if (mineField[xPos][yPos] == "X") minesFound++
            }
        }
        if (minesFound == mines) {return "true"}
        return "goon"

    }

    fun firstMove (x: Int, y: Int, status: String) {
        var xPos1 = x - 1
        var yPos1 = y - 1
        while (mineField[xPos1][yPos1] =="X" && status =="free") {
            setField()
        }
        if (mineField[xPos1][yPos1] !="X" && status =="free") {setMark(x, y, status)}
    }

}


fun main() {
    print("How many mines do you want on the field?")
    val minesCount = readln().toInt()
    val scanner = Scanner(System.`in`)
    var mineSweeper = MineSweeper(minesCount)
    mineSweeper.setField()
    mineSweeper.printField()


    while (true) {
        print("Set/unset mines marks or claim a cell as free:")
        var x = scanner.nextInt()
        var y = scanner.nextInt()
        var status = scanner.next()
        mineSweeper.firstMove(x, y, status) //infinite loop
        var result = mineSweeper.setMark(x, y, status)
        mineSweeper.printField()
        if (result == "true") {
            println("Congratulations! You found all the mines!")
            break
        } else if (result == "false") {println("You stepped on a mine and failed!")
            break}
    }

}