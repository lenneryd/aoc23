import java.io.File

fun main() {
    val input = File("input.txt").readLines()
    val answer = when (System.getenv("part")) {
        "part2" -> solutionPart2(input)
        else -> solutionPart1(input)
    }
    println(answer)
}

data class RangeMap(val identifier: String, val src: LongRange, val dest: LongRange, val length: Long)
data class Input(
    val seeds: List<Long>,
    val seedSoil: List<RangeMap>,
    val soilFertilizer: List<RangeMap>,
    val fertilizerWater: List<RangeMap>,
    val waterLight: List<RangeMap>,
    val lightTemp: List<RangeMap>,
    val tempHumidity: List<RangeMap>,
    val humidityLocation: List<RangeMap>
)

fun Input.toOrder(): List<List<RangeMap>> = listOf(seedSoil, soilFertilizer, fertilizerWater, waterLight, lightTemp, tempHumidity, humidityLocation)

fun List<String>.toRangeMap(identifier: String) = this.indexOf(identifier).let { start ->
    val sub = this.drop(start+1).let {
        if(it.indexOf("") != -1) {
            it.subList(0, it.indexOf(""))
        } else {
            it
        }
    }
    sub.map { str ->
        str.split(" ").let { list ->
            RangeMap(
                identifier,
                list[1].toLong()until list[1].toLong()+list[2].toLong(),
                list[0].toLong()until list[0].toLong()+list[2].toLong(),
                list[2].toLong()
            )
        }
    }
}

fun List<String>.mapInput(): Input {
    val str = this.subList(2, this.lastIndex)
    return Input(
        seeds = this[0].substringAfter("seeds: ").trim().split(" ").map { it.toLong() },
        seedSoil = str.toRangeMap("seed-to-soil map:"),
        soilFertilizer = str.toRangeMap("soil-to-fertilizer map:"),
        fertilizerWater = str.toRangeMap("fertilizer-to-water map:"),
        waterLight = str.toRangeMap("water-to-light map:"),
        lightTemp = str.toRangeMap("light-to-temperature map:"),
        tempHumidity = str.toRangeMap("temperature-to-humidity map:"),
        humidityLocation = str.toRangeMap("humidity-to-location map:"),
    )
}

fun solutionPart1(input: List<String>): Long = input.mapInput().let { inputs ->
    inputs.seeds.map { seed ->
        inputs.toOrder().fold(seed) { acc, ranges ->
            val range = ranges.firstOrNull { acc in it.src }

            range?.let {
                range.dest.first + range.src.indexOf(acc)
            }?: acc
        }
    }.min()
}

fun solutionPart2(input: List<String>): Long = 0

