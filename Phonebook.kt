package phonebook
import java.io.File
import java.lang.System.currentTimeMillis
import java.util.*
open class Search() {
    val dataSet = File("/Users/ahalias/demo/Phone Book/src/directory.txt").readLines().toMutableList()
    val findFile = File("/Users/ahalias/demo/Phone Book/src/find.txt").readLines().toMutableList()
    var timeTaken: Long = 0
    var timeSum: Long = 0
    var time = 0
    var entries = 0
    var entriesB = 0
    var entry = 0
    fun linearSearch() {
        val start = currentTimeMillis()
        var end: Long = 0
        findFile.forEach {
            for (unit in dataSet) {
                if (unit == it) {
                    continue
                }
            }
            ++entry
        }
        end = currentTimeMillis()
        timeTaken = end - start
    }
    fun bubbleJump() {
        println("Start searching (bubble sort + jump search)...")
        val startBJ = currentTimeMillis()
        bubbleSort(dataSet, startBJ)
    }
    fun bubbleSort(a: MutableList<String>, startBJ: Long) {
        val startSorting = currentTimeMillis()
        var changed: Boolean
        loop@ do {
            changed = false
            for (i in 0..a.size - 2) {
                var sortingTime = currentTimeMillis() - startSorting
                if (sortingTime >= timeTaken * 2) {
                    entry = 0
                    linearSearch()
                    break@loop
                }
                if (a[i] > a[i + 1]) {
                    val tmp = a[i]
                    a[i] = a[i + 1]
                    a[i + 1] = tmp
                    changed = true
                }
            }
        } while (changed)
        jumpSearch(a, findFile, startBJ)
        val sortingTime = currentTimeMillis() - startSorting
        val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeTaken)
        val overAllTimeX = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", sortingTime)
        val overAllTime = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", sortingTime + timeTaken)
        println("Found $entry / ${findFile.size} entries. Time taken: $overAllTime")
        println("Sorting time: $overAllTimeX")
        println("Searching time: $time\n")
    }
    fun jumpSearch(arr: MutableList<String>, findText: MutableList<String>, startBJ: Long) {
        val startJumping = currentTimeMillis()
        var left = 0
        var right = 0
        var entries = 0
        // Finding block size to be jumped
        for(value in findText) {
            val jump = Math.sqrt(arr.size.toDouble()).toInt()
            while (left < arr.size && arr[left] <= value) {
                right = Math.min(arr.size - 1, left + jump)
                if (arr[right] >= value) {
                    break
                }
                left += jump
            }
            // Doing a linear search for x in block
            // beginning with left.
            while (left <= right && arr[left] <= value) {
                // If element is found
                if (arr[left] == value) {
                    continue
                }
                left++
            }
            entries++
        }
        val searchTime = currentTimeMillis() - startJumping
        val bjTime = currentTimeMillis() - startBJ
    }
    fun quickBin() {
        val start = currentTimeMillis()
        println("Start searching (quick sort + binary search)...")
        quickSort(dataSet)
        val end = currentTimeMillis()
        timeTaken = end - start
        val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeTaken)
        println("Found $entriesB / ${findFile.size} entries. Time taken: $time\n")
    }
    fun quickSort(arr: MutableList<String>) {
        val start = currentTimeMillis()
        quicksort(arr, 0, arr.size - 1)
        val end = currentTimeMillis()
        val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", end - start)
        println("Sorting time: $time")
        val startS = currentTimeMillis()
        binarySearch(arr, findFile, startS)
    }
    // Take Left (first) Index of the array and Right (last) Index of the array
    fun quicksort(arr: MutableList<String>, left: Int, right: Int) {
        // If the first index less or equal than the last index
        if (left <= right) {
            // Create a Key/Pivot Element
            val key = arr[(left + right) / 2]
            // Create temp Variables to loop through array
            var i = left
            var j = right
            while (i <= j) {
                while (arr[i] < key)
                    i++
                while (arr[j] > key)
                    j--
                if (i <= j) {
                    arr[i] = arr[j].also { arr[j] = arr[i] }
                    i++
                    j--
                }
            }
            // Recursion to the smaller partition in the array after sorted above
            if (left < j) {
                quicksort(arr, left, j)
            }
            if (right > i) {
                quicksort(arr, i, right)
            }
        }
    }
    fun binarySearch(arrays: MutableList<String>, element: MutableList<String>, start: Long) {
        element.forEach {
            arrays.binarySearch(it)
        }
        entriesB = element.size
        val end = currentTimeMillis()
        val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", end - start)
        println("Searching time: $time")
    }
    fun hashMap() {
        println("Start searching (hash table)...")
        val hashMap = mutableMapOf<String, String>()
        var timeSort = 0L
        for (x in 0..dataSet.lastIndex) {
            val unit = dataSet[x].split(" ")
            unit.forEach {
                val phone = unit[0]
                var name =""
                try {
                    name = "${unit[1]} ${unit[2]}"
                } catch(e: Exception) { name =  unit[1] }
                val sort1 = currentTimeMillis()
                hashMap.put (name, phone)
                val sort2 = currentTimeMillis() - sort1
                timeSort += sort2
            }
        }
        val overAllTime = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", timeSort)
        var entr = 0
        var srt = 0L
        for (line in findFile) {
            val startS = currentTimeMillis()
            if (hashMap.contains(line)) {
                entr++
            }
            srt += currentTimeMillis() - startS
        }
        val tm = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", srt)
        val end = srt + timeSort
        val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", end)
        println("Found $entr / ${findFile.size}  entries. Time taken: $time.")
        println("Creating time: $overAllTime")
        println("Searching time: $tm\n")
    }
}
fun main() {
    val search = Search()
    println("Start searching (linear search)...")
    search.linearSearch()
    val time = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", search.timeTaken)
    println("Found ${search.entry} / ${search.findFile.size} entries. Time taken: $time\n")
    search.bubbleJump()
    search.quickBin()
    search.hashMap()
}