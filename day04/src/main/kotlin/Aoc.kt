import java.io.File
import java.lang.Math.pow
import kotlin.math.pow

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

data class Card(val id: Int, val winning: List<Int>, val numbers: List<Int>)

fun List<String>.mapInput() = this.map { str ->
    val id = str.drop(4).substringBefore(":").trim().toInt()
    val winning = str.substringAfter(":").substringBefore(" | ").trim().split(Regex("\\s+")).map { it.trim().toInt() }
    val numbers = str.substringAfter(" | ").trim().split(Regex("\\s+")).map { it.trim().toInt() }
    Card(id, winning, numbers)
}

fun solutionPart1(input: List<String>): Int = input.mapInput().sumOf { card ->
    card.winning.toSet().intersect(card.numbers.toSet()).size.let { size ->
        if (size == 0) {
            0
        } else {
            1 * 2.0.pow(size - 1.0).toInt()
        }
    }
}

fun Map<Int, Card>.getWinnings(card: Card): List<Card> = card.winning.toSet().intersect(card.numbers.toSet()).size.let { winnings ->
    (card.id+1 .. card.id+winnings).map { this@getWinnings[it]!! }
}

fun solutionPart2(input: List<String>): Int = input.mapInput().let { cards ->
    var results = 0
    val allCards = cards.associateBy { it.id }
    val queue = ArrayDeque(cards)
    while(queue.isNotEmpty()) {
        val card = queue.removeFirst()
        val winnings = allCards.getWinnings(card)
        queue.addAll(winnings)
        results += winnings.size
    }

    return results + cards.size
}


