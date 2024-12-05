import java.io.File
import java.util.*

private val input = File("src/main/resources/input5").readLines()
private val emptyLine = input.indexOf("")
private val rules = input.take(emptyLine).map { it.split('|')[0].toInt() to it.split('|')[1].toInt() }
private val updates = input.drop(emptyLine + 1).map { update -> update.split(',').map { it.toInt() } }

fun fifthA(): Int {
    return updates
        .filter { isUpdateCorrect(it) }
        .sumOf { getMiddleNumber(it) }
}

fun fifthB(): Int {
    return updates
        .filter { !isUpdateCorrect(it) }
        .map { sortUpdate(it) }
        .sumOf { getMiddleNumber(it) }
}

private fun isUpdateCorrect(update: List<Int>): Boolean {
    val ruleMatches = update.indices.count { i ->
        rules
            .filter { it.first in update && it.second in update }
            .filter { it.first == update[i] || it.second == update[i] }
            .all { isRuleValidForPosition(update, it, i) }
    }

    return update.size == ruleMatches
}

private fun isRuleValidForPosition(update: List<Int>, rule: Pair<Int, Int>, position: Int): Boolean {
    return when (update[position]) {
        rule.first -> return update.drop(position + 1).contains(rule.second)
        rule.second -> update.take(position).contains(rule.first)
        else -> false
    }
}

private fun getMiddleNumber(update: List<Int>): Int {
    require(update.size % 2 != 0)
    return update[(update.size - 1) / 2]
}

private fun sortUpdate(update: List<Int>): List<Int> {
    val relevantRules = rules.filter { it.first in update && it.second in update }
    var sortedUpdate = update
    while (!isUpdateCorrect(sortedUpdate)) {
        relevantRules.forEach {
            for (i in sortedUpdate.indices) {
                if ((it.first == sortedUpdate[i] || it.second == sortedUpdate[i]) && !isRuleValidForPosition(sortedUpdate, it, i)) {
                    sortedUpdate = swapRule(sortedUpdate, it)
                }
            }
        }
    }

    return sortedUpdate
}

private fun swapRule(update: List<Int>, rule: Pair<Int, Int>): List<Int> {
    return update.toMutableList().apply {
        Collections.swap(this, indexOf(rule.first), indexOf(rule.second))
    }
}
