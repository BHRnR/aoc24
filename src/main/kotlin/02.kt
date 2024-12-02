import java.io.File
import kotlin.math.abs

fun secondA(): Int {
    return File("src/main/resources/input2").readLines()
        .map { line -> line.split(" ").map { it.toInt() } }
        .map { isValid(it) }
        .filter { it }
        .size
}

fun secondB(): Int {
    return File("src/main/resources/input2").readLines()
        .map { line -> line.split(" ").map { it.toInt() } }
        .map { isValidWithDamp(it) }
        .filter { it }
        .size
}

private fun isValidWithDamp(list: List<Int>): Boolean {
    if (isValid(list)) {
        return true
    }
    for (i in list.indices) {
        val copiedList = ArrayList(list.toList())
        copiedList.removeAt(i)
        if (isValid(copiedList)) {
            return true
        }
    }
    return false
}

private fun isValid(list: List<Int>): Boolean {
    val isIncreasing = (list[1] - list[0]) >= 0;
    for (i in 0..<list.size - 1) {
        val difference = (list[i + 1] - list[i])
        if ((difference > 0 && !isIncreasing) || (difference < 0 && isIncreasing) || difference == 0 || abs(difference) > 3) {
            return false
        }
    }
    return true
}
