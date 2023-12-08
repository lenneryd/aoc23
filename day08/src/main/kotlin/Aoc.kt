import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

data class Node(val id: String, val left: String, val right: String)
data class MapGraph(val instructions: String, val lookup: Map<String, Node>)

fun List<String>.mapInput(): MapGraph = this.drop(2).map { line ->
    val str = line.split(" = ")
    val leftRight = str.last().split(",")
    Node(id = str.first().trim(), leftRight.first().drop(1).trim(), leftRight.last().dropLast(1).trim())
}.associateBy { it.id }.let { lookup ->
    MapGraph(this.first(), lookup)
}

fun solveFor(graph: MapGraph, id: String, end: String): Long {
    var current = id
    var iterations = 0L
    val instructions = mutableListOf(*graph.instructions.toCharArray().toTypedArray())
    while (!current.endsWith(end)) {
        iterations++
        val instruction = instructions.removeFirst()
        current = if (instruction == 'L') graph.lookup[current]!!.left else graph.lookup[current]!!.right

        if (instructions.isEmpty()) {
            instructions.addAll(graph.instructions.toList())
        }
    }

    return iterations
}

fun solutionPart1(input: List<String>): Long = input.mapInput().let { graph ->
    return solveFor(graph, "AAA", "ZZZ")
}

fun solutionPart2(input: List<String>): Long = input.mapInput().let { graph ->
    val starts = graph.lookup.entries.filter { it.key.endsWith("A") }.map { it.value }

    return starts.map { solveFor(graph, it.id, "Z") }.reduce { acc, current ->
        lowestCommonMultiple(acc, current)
    }
}

