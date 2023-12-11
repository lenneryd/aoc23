import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

data class Galaxy(val x: Long, val y: Long, val id: Int)

fun List<String>.mapInput(): List<Galaxy> {
    var id = 0
    return mapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c == '#') Galaxy(x.toLong(), y.toLong(), id++) else null } }.flatten()
}

fun solve(input: List<String>, expansion: Long): Long {
    return input.mapInput().let { galaxies ->
        val rows: List<Int> = input.indices.filterNot {
            galaxies.map { galaxy -> galaxy.y }.toSet().contains(it.toLong())
        }
        val cols: List<Int> = input[0].indices.filterNot {
            galaxies.map { galaxy -> galaxy.x }.toSet().contains(it.toLong())
        }
        val adjustedGalaxies = galaxies.map { galaxy ->
            val addedCols = cols.count { it < galaxy.x } * (expansion - 1)
            val addedRows = rows.count { it < galaxy.y } * (expansion - 1)
            galaxy.copy(
                x = galaxy.x + addedCols,
                y = galaxy.y + addedRows
            )
        }

        val pairs = adjustedGalaxies.map { g1 ->
            adjustedGalaxies.mapNotNull { g2 ->
                if (g1.id == g2.id) {
                    null
                } else {
                    g1 to g2
                }
            }
        }.flatten()

        val distances = mutableMapOf<Pair<Int, Int>, Long>()
        pairs.forEach { (g1, g2) ->
            val ids = listOf(g1.id, g2.id).sorted()
            val current = ids[0] to ids[1]
            val currentDist = abs(g2.x - g1.x) + abs(g2.y - g1.y)
            val prev = distances[current] ?: Long.MAX_VALUE

            if (prev > currentDist) {
                distances[current] = currentDist
            }
        }

        distances.values.sum()
    }
}

fun solutionPart1(input: List<String>): Long = solve(input, expansion = 2L)

fun solutionPart2(input: List<String>, expansion: Long = 1_000_000L): Long = solve(input, expansion)
