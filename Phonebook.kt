package phonebook


import java.io.File




open class Search() {


    var entries: Int = 0
    val dataSet = File("/Users/ahalias/demo/Phone Book/src/directory.txt").readLines()
    val findFile = File("/Users/ahalias/demo/Phone Book/src/find.txt").readLines()
    val list = mutableListOf<DataBase>()
    var list1 = listOf<DataBase>()
    var sortingTime: Long = 0
    var timeTaken: Long = 0
    var timeSum: Long = 0






    data class DataBase(val phoneNum: String, val name: String, val surName: String)


    fun linearSearch() {
        val start = System.currentTimeMillis()
        var end: Long = 0
        entries = 0
        for (line in findFile) {
            for (unit in dataSet) {
                if (unit.contains(line)) {
                    entries++
                    break
                }
            }
        }
        end = System.currentTimeMillis()
        timeTaken = end - start
    }


    fun sortList() {
        val startSorting = System.currentTimeMillis()
        var endSorting: Long = 0
        var stopSorting = timeTaken * 10
        for (x in 0..dataSet.lastIndex) {
            while (sortingTime < stopSorting) {
                val unit = dataSet[x].split(" ")
                list.add(DataBase(unit[0], unit[1], unit.last()))
                list1 = list.sortedBy { it.name }
                endSorting = System.currentTimeMillis()
                sortingTime = endSorting - startSorting
                timeSum = sortingTime + timeTaken
            }
            if (sortingTime >= stopSorting) {
                linearSearch()
                break
            }
        }
    }
}


fun main() {
    val search = Search()
    println("Start searching (linear search)...")
    search.linearSearch()
    val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", search.timeTaken)
    println("Found ${search.entries} / ${search.findFile.size} entries. Time taken: $time\n")


    search.sortList()
    println("Start searching (bubble sort + jump search)...")
    val timeToSort = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", search.sortingTime)
    val overAllTime = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", search.timeSum)
    println("Found ${search.entries} / ${search.findFile.size} entries. Time taken: $overAllTime")
    println("Sorting time: $timeToSort - STOPPED, moved to linear search")
    println("Searching time: $time")
}
