import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

data class Position(val x: Int, val y: Int)
data class Symbol(val type: Char, val x: Int, val y: Int)
data class PartNumber(val num: Int, val length: Int, val x: Int, val y: Int)

fun Int.toLength() = when {
    this < 10 -> 1
    this < 100 -> 2
    else -> 3
}

fun Char.isSymbol() = this != '.' && !this.isDigit()
fun Char.toSymbolOrNull(x: Int, y: Int) = this.takeIf { it.isSymbol() }?.let { Symbol(it, x, y) }
fun List<String>.mapSymbols(): Map<Position, Symbol> =
    mapIndexed { y, line -> line.mapIndexedNotNull { x, c -> c.toSymbolOrNull(x, y)?.let { Position(x, y) to it } } }.flatten().toMap()

fun List<String>.mapNumbers(): List<PartNumber> = mapIndexed { y, line ->
    buildList {
        var x = 0
        while (x < line.length) {
            if (line[x].isDigit()) {
                val endX = kotlin.math.min(x + 3, line.length)
                val nr = line.substring(x, endX).filter { it.isDigit() }.toInt()
                add(PartNumber(nr, nr.toLength(), x, y))
                x += nr.toLength()
            }
            x++
        }
    }
}.flatten()

fun Map<Position, Symbol>.filterAdjacent(part: PartNumber): List<Symbol> {
    val positions = buildList {
        val xRange = part.x - 1..part.x + part.length
        val yRange = part.y - 1..part.y + 1
        for (x in xRange) {
            for (y in yRange) {
                add(Position(x, y))
            }
        }
    }
    return positions.mapNotNull { this[it] }
}

fun solutionPart1(input: List<String>) = input.mapNumbers().let { numbers ->
    val symbols = input.mapSymbols()
    numbers.map { number -> number to symbols.filterAdjacent(number) }
}.let { list ->

    val (validParts, _) = list.partition { (_, symbols) -> symbols.isNotEmpty() }
    validParts.sumOf { (nr, _) -> nr.num }
}

fun solutionPart2(input: List<String>) = input.mapNumbers().let { numbers ->
    val symbols = input.mapSymbols()
    val symbolsToNumbers = buildMap<Symbol, MutableList<PartNumber>> {
        val possibleGears = symbols.filter { it.value.type == '*' }
        possibleGears.forEach {
            put(it.value, mutableListOf())
        }
        numbers.forEach { nr ->
            possibleGears.filterAdjacent(nr).forEach { symbol ->
                get(symbol)!!.add(nr)
            }
        }
    }

    symbolsToNumbers.filter { it.value.size == 2 }.map { it.value.first().num * it.value.last().num }.sum()
}


