import java.io.File
import kotlin.math.abs

fun lists(): Pair<List<Int>, List<Int>> {
    val (listA, listB) = File("src/main/resources/input1").readLines()
        .map {
            val parts = it.split("   ").map(String::toInt)
            parts[0] to parts[1]
        }
        .unzip()

    return listA.sorted() to listB.sorted()
}

fun firstA(): Int {
    return lists().first.zip(lists().second).sumOf { (a, b) -> abs(a - b) }
}

fun firstB(): Int {
    return lists().first.sumOf { it * lists().second.count { second -> second == it } }
}
