import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

val digits = listOf("1" to 1, "2" to 2, "3" to 3, "4" to 4, "5" to 5, "6" to 6, "7" to 7, "8" to 8, "9" to 9, "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

fun solutionPart1(input: List<String>) = input.sumOf { line -> Integer.valueOf("${line.first { it.isDigit() }}${line.last { it.isDigit() }}") }

fun solutionPart2(input: List<String>) = input.sumOf { line ->
    Integer.valueOf("${
        line.findAnyOf(digits.map { it.first })!!.let { found -> digits.find { it.first ==  found.second}!!.second }
    }${
        line.findLastAnyOf(digits.map { it.first })!!.let { found -> digits.find { it.first ==  found.second}!!.second }
    }")
}



