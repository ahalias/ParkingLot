import kotlin.system.exitProcess

class ParkingLot {
    var listInfo = mutableListOf<String>()
    var nextLot: Int = 0
    var lotsTakenAmount = 0
    var carInfo: MutableList<String> = mutableListOf()
    val takenLotsReverse: MutableMap<Int, String> = mutableMapOf()

    fun createLot(lotsAmount: Int) {
        (1..lotsAmount).map { takenLotsReverse[it] = "free" }
        carInfo = mutableListOf()
        lotsTakenAmount = 0
    }

    fun assignLot(input: List<String>, lotsAmount: Int) {
        var takenLots = takenLotsReverse.toSortedMap(reverseOrder())
        var iterator = takenLots.iterator()
        iterator.forEach { (key, value) ->
            if (value == "free") {
                nextLot = key
                value
            }
        }
        when {
            input[0] == "spot_by_reg" -> {
                listInfo = mutableListOf<String>()
                for (car in carInfo) {
                    var inquiry = car.split(" ")
                    if (inquiry[1].toUpperCase() == input[1].toUpperCase()) {
                        listInfo.add(inquiry[0])
                    }
                }
                if (listInfo.isEmpty() != true) {
                    println(listInfo.joinToString()) } else { println("No cars with registration number ${input[1]} were found.") }
            }
            input[0] == "spot_by_color" -> {
                listInfo = mutableListOf<String>()
                for (car in carInfo) {
                    var inquiry = car.split(" ")
                    if (inquiry[2].toUpperCase() == input[1].toUpperCase()) {
                        listInfo.add(inquiry[0])
                    }
                }
                if (listInfo.isEmpty() != true) {
                    println(listInfo.joinToString()) } else { println("No cars with color ${input[1]} were found.") }
            }
            input[0] == "reg_by_color" -> {
                listInfo = mutableListOf<String>()
                for (car in carInfo) {
                    var inquiry = car.split(" ")
                    if (inquiry[2].toUpperCase() == input[1].toUpperCase()) {
                        listInfo.add(inquiry[1])
                    }
                }
                if (listInfo.isEmpty() != true) {
                    println(listInfo.joinToString()) } else { println("No cars with color ${input[1]} were found.") }
            }
            input[0] == "park" -> {
                if (lotsTakenAmount >= lotsAmount) { println("Sorry, the parking lot is full.") }
                else if (takenLots[nextLot] == "free") {
                    println("${input.last()} car parked in spot $nextLot.")
                    takenLotsReverse.set(nextLot, "taken")
                    lotsTakenAmount++
                    carInfo.add(nextLot - 1, "$nextLot ${input[1]} ${input[2]}")

                }
            }
            input[0] == "exit" -> exitProcess(1)
            input[0] == "status" -> {
                when {
                    carInfo.isEmpty() -> println("Parking lot is empty.")
                    else ->
                        for (entry in carInfo.indices) {
                            println("${carInfo[entry]}")
                        }
                }
            }
            input[0] == "leave" -> {
                try {
                    println("Spot ${input.get(1)} is free.")
                    lotsTakenAmount--
                    takenLotsReverse[input.get(1).toInt()] = "free"
                    carInfo.removeAt(input.get(1).toInt() - 1)

                } catch(e: Exception) { println("There is no car in spot ${input.get(1)}.") }
            }
        }
    }
}

fun main() {
    var lotsAmount: Int = 0
    val parkingLot = ParkingLot()
    var createParkLot: Boolean = false
    while (true) {
        val inquiry = readln()
        val input = inquiry.split(" ")
        if (input[0] == "create" && input[1].toIntOrNull() != null) {
            println("Created a parking lot with ${input.get(1)} spots.")
            createParkLot = true
            lotsAmount = input.get(1).toInt()
            parkingLot.createLot(lotsAmount)
        } else if (input[0] == "exit") { exitProcess(1) }
        else if (createParkLot == false) {
            println("Sorry, a parking lot has not been created.")
            continue
        }
        parkingLot.assignLot(input, lotsAmount)
    }
}