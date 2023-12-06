import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

fun List<String>.mapInput() = map { it.substringAfter(":").trim().split(Regex("\\s+")) }.let { lists ->
    listOfNotNull(lists[0][0].toLong() to lists[1][0].toLong(),
        lists[0][1].toLong() to lists[1][1].toLong(),
        lists[0][2].toLong() to lists[1][2].toLong(),
        lists[0].getOrNull(3)?.let { lists[0][3].toLong() to lists[1][3].toLong() }
    )
}

fun List<String>.mapAsSingle(): Pair<Long, Long> = map { it.substringAfter(":").trim().filter { c -> c != ' ' } }.let {
    it[0].toLong() to it[1].toLong()
}

fun Pair<Long, Long>.calculate(): Long {
    var boosting = first / 2
    var racing = 0
    while ((first - boosting) * boosting > second) {
        racing++
        boosting--
    }
    return (racing * 2) + (first % 2 - 1)
}

fun solutionPart1(input: List<String>): Long = input.mapInput().fold(1) { acc, (race, dist) ->
    acc * (race to dist).calculate()
}

fun solutionPart2(input: List<String>): Long = input.mapAsSingle().let { race ->
    race.calculate()
}


