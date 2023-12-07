import java.io.File
import java.util.PriorityQueue

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

val ranks = "AKQJT98765432".reversed()
val ranks2 =  "AKQT98765432J".reversed()

data class Hand(val cards: String, val bid: Int)

fun List<String>.mapInput(): List<Hand> = map { it.split(" ").let { split -> Hand(split[0], split[1].toInt()) } }
fun Char.rank(ranks: String): Int = ranks.indexOf(this) + 1
fun String.strength(): Int = when {
    count { it == this[0] } == 5 -> 10 // 5 of a Kind
    any { c -> count { it == c } == 4 } -> 9 // Four of a Kind
    any { c -> count { it == c } == 3 } && toSet().size == 2 -> 8 // Full House
    any { c -> count { it == c } == 3 } -> 7 // Three of a Kind
    any { c -> count { it == c } == 2 } && toSet().size == 3 -> 6 // Two Pairs
    any { c -> count { it == c } == 2 } && toSet().size == 4 -> 5 // Pair
    else -> 0 // High Card
}

fun String.improvedHand(): String {
    var improved = this
    ranks2.forEach { c ->
        val newHand = this.replace('J', c)
        if(newHand.strength() > improved.strength()) {
            improved = newHand
        }
    }
    return improved
}

val handComparator = Comparator<Hand> { hand1, hand2 ->
    val strength1 = hand1.cards.strength()
    val strength2 = hand2.cards.strength()
    when {
        strength1 > strength2 -> return@Comparator 1
        strength1 < strength2 -> return@Comparator -1
        else -> repeat(5) { idx ->
            if (hand1.cards[idx].rank(ranks) > hand2.cards[idx].rank(ranks)) {
                return@Comparator 1
            }

            if (hand1.cards[idx].rank(ranks) < hand2.cards[idx].rank(ranks)) {
                return@Comparator -1
            }
        }
    }
    0
}

val handComparator2 = Comparator<Hand> { hand1, hand2 ->
    val strength1 = hand1.cards.improvedHand().strength()
    val strength2 = hand2.cards.improvedHand().strength()
    when {
        strength1 > strength2 -> return@Comparator 1
        strength1 < strength2 -> return@Comparator -1
        else -> repeat(5) { idx ->
            if (hand1.cards[idx].rank(ranks2) > hand2.cards[idx].rank(ranks2)) {
                return@Comparator 1
            }

            if (hand1.cards[idx].rank(ranks2) < hand2.cards[idx].rank(ranks2)) {
                return@Comparator -1
            }
        }
    }
    0
}

fun solutionPart1(input: List<String>): Long = input.mapInput().let { list ->
    val queue = PriorityQueue(handComparator)
    queue.addAll(list)

    var sum = 0L
    for (i in 1..list.size) {
        queue.poll().let { hand ->
            sum += (hand.bid * i).toLong()
        }
    }
    return sum
}

fun solutionPart2(input: List<String>): Long = input.mapInput().let { list ->
    val queue = PriorityQueue(handComparator2)
    queue.addAll(list)

    var sum = 0L
    for (i in 1..list.size) {
        queue.poll().let { hand ->
            sum += (hand.bid * i).toLong()
        }
    }
    return sum
}

