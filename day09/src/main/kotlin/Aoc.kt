import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

fun List<String>.mapInput(): List<List<Long>> = map { it.split(" ").map { num -> num.toLong() } }

fun List<Long>.derivate(): List<Long> = this.windowed(2, 1, false).map { pair ->
    pair.last() - pair.first()
}
fun List<List<Long>>.extrapolateNext(input: List<Long>): Long = this.reversed().fold(0L) { acc, iter ->
    acc + iter.last()
} + input.last()

fun List<List<Long>>.extrapolatePrevious(input: List<Long>): Long = input.first() - this.reversed().fold(0L) { acc, iter ->
    iter.first() - acc
}

fun List<Long>.isAllZeros(): Boolean = this.all { it == 0L }

fun List<Long>.generateDerivates(): Pair<List<Long>, List<List<Long>>> {
    val first = this.derivate()
    return this to buildList {
        add(first)
        while (!this.last().isAllZeros()) {
            add(this.last().derivate())
        }
    }
}

fun solutionPart1(input: List<String>): Long = input.mapInput().map { history ->
    history.generateDerivates()
}.sumOf { (input, derivates) ->
    derivates.extrapolateNext(input)
}

fun solutionPart2(input: List<String>): Long = input.mapInput().map { history ->
    history.generateDerivates()
}.sumOf { (input, derivates) ->
    derivates.extrapolatePrevious(input)
}
