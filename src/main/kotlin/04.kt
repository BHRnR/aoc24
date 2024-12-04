import java.io.File

enum class Direction {
    N, NE, E, SE, S, SW, W, NW
}

class CoordDir(val coordinates: Pair<Int, Int>, val direction: Direction)

val inputLines = File("src/main/resources/input4").readLines()
val validSolutions = listOf(
    listOf('M', 'M', 'S', 'S'), listOf('S', 'M', 'M', 'S'), listOf('S', 'S', 'M', 'M'), listOf('M', 'S', 'S', 'M')
)

fun fourthA(): Int {
    return inputLines.indices.flatMap { i ->
        inputLines[0].indices.flatMap { j ->
            if (inputLines[i][j] == 'X') {
                Direction.entries.filter { isCharInDirection(i to j, it, 'M') }.map { CoordDir(i to j, it) }
            } else {
                emptyList()
            }
        }
    }.asSequence().filter { isCharInDirection(it.coordinates, it.direction, 'M') }
        .map { CoordDir(coordsInDirection(it.coordinates, it.direction)!!, it.direction) }
        .filter { isCharInDirection(it.coordinates, it.direction, 'A') }
        .map { CoordDir(coordsInDirection(it.coordinates, it.direction)!!, it.direction) }
        .filter { isCharInDirection(it.coordinates, it.direction, 'S') }.count()
}

fun fourthB(): Int {
    return inputLines.indices.flatMap { i ->
        inputLines[0].indices.mapNotNull { j ->
            if (inputLines[i][j] == 'A') (i to j) else null
        }
    }.map { coord -> listOf(Direction.NE, Direction.SE, Direction.SW, Direction.NW).map { dir -> charInDirection(coord, dir) } }
        .count { validSolutions.contains(it) }
}

private fun isCharInDirection(coordinates: Pair<Int, Int>, direction: Direction, char: Char): Boolean {
    val newCoords = coordsInDirection(coordinates, direction) ?: return false
    return inputLines[newCoords.first][newCoords.second] == char
}

private fun charInDirection(coordinates: Pair<Int, Int>, direction: Direction): Char? {
    val newCoords = coordsInDirection(coordinates, direction) ?: return null
    return inputLines[newCoords.first][newCoords.second]
}

private fun coordsInDirection(coordinates: Pair<Int, Int>, direction: Direction): Pair<Int, Int>? {
    val newCoordinates = when (direction) {
        Direction.N -> coordinates.first - 1 to coordinates.second
        Direction.NE -> coordinates.first - 1 to coordinates.second + 1
        Direction.E -> coordinates.first to coordinates.second + 1
        Direction.SE -> coordinates.first + 1 to coordinates.second + 1
        Direction.S -> coordinates.first + 1 to coordinates.second
        Direction.SW -> coordinates.first + 1 to coordinates.second - 1
        Direction.W -> coordinates.first to coordinates.second - 1
        Direction.NW -> coordinates.first - 1 to coordinates.second - 1
    }
    return if (newCoordinates.first < 0 || newCoordinates.second < 0 || newCoordinates.first > inputLines.size - 1 || newCoordinates.second > inputLines[0].length - 1) {
        null
    } else {
        newCoordinates
    }
}



