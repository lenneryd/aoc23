import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

val Game1Rules = GameRules()

fun String.toColor(color: String): Int = substringBefore(color, "0").trim().toInt()

fun String.mapInput(): Game = Game(
    drop(5).substringBefore(":").toInt(),
    substringAfter(": ").split(";").map { group ->
        group.split(",").let { colors ->
            Group(
                colors.firstOrNull { it.contains("red") }?.toColor("red") ?: 0,
                colors.firstOrNull { it.contains("blue") }?.toColor("blue") ?: 0,
                colors.firstOrNull { it.contains("green") }?.toColor("green") ?: 0,
            )
        }

    }
)

fun solutionPart1(input: List<String>) = input.map { it.mapInput() }.filter { game ->
    game.list.maxOf { it.red } <= Game1Rules.red &&
            game.list.maxOf { it.green } <= Game1Rules.green &&
            game.list.maxOf { it.blue } <= Game1Rules.blue
}.sumOf { it.id }

fun solutionPart2(input: List<String>) = input.map { it.mapInput() }.map { game ->
    GameRules(game.list.maxOf { it.red }, game.list.maxOf { it.green }, game.list.maxOf { it.blue }).let {
        it.red * it.green * it.blue
    }
}.sum()

data class GameRules(val red: Int = 12, val green: Int = 13, val blue: Int = 14)
data class Group(val red: Int, val blue: Int, val green: Int)
data class Game(val id: Int, val list: List<Group>)


