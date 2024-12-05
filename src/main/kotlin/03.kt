import java.io.File

private val regex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
private val input = File("src/main/resources/input3").readText()

fun thirdA(): Int {
    return regex.findAll(input)
        .map { it.value }
        .map { productFromMatch(it) }
        .sum()
}

fun thirdB(): Int {
    val muls = regex.findAll(input)
    val doPositions = """do\(\)""".toRegex().findAll(input).map { it.range.first }
    val dontPositions = """don't\(\)""".toRegex().findAll(input).map { it.range.first }

    return muls.filter { (lastPosition(it.range.first, doPositions) >= lastPosition(it.range.first, dontPositions)) }
        .map { productFromMatch(it.value) }
        .sum()
}

private fun productFromMatch(match: String): Int {
    return match
        .removePrefix("mul(")
        .removeSuffix(")")
        .split(",")
        .let { (a, b) -> a.toInt() * b.toInt() }
}

private fun lastPosition(toCheck: Int, positions: Sequence<Int>): Int {
    return positions.filter { it < toCheck }.maxOrNull() ?: 0;
}
