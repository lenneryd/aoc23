import java.io.File
import java.math.BigInteger

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
    val seedRanges: List<LongRange>,
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
    val sub = this.drop(start + 1).let {
        if (it.indexOf("") != -1) {
            it.subList(0, it.indexOf(""))
        } else {
            it
        }
    }
    sub.map { str ->
        str.split(" ").let { list ->
            RangeMap(
                identifier,
                list[1].toLong() until list[1].toLong() + list[2].toLong(),
                list[0].toLong() until list[0].toLong() + list[2].toLong(),
                list[2].toLong()
            )
        }
    }
}

fun List<String>.mapInput(): Input {
    val str = this.subList(2, this.lastIndex)
    return Input(
        seeds = this[0].substringAfter("seeds: ").trim().split(" ").map { it.toLong() },
        seedRanges = this[0].substringAfter("seeds: ").trim().split(" ").windowed(2, 2, false).map {
            it[0].toLong() until it[0].toLong() + it[1].toLong()
        },
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
            } ?: acc
        }
    }.min()
}
fun List<String>.bigInts() = map { it.toBigInteger() }
fun List<String>.splitBy(predicate: (String) -> Boolean): List<List<String>> {
    return this.fold(mutableListOf<MutableList<String>>() to 0) { (splits, idx), current ->
        if (!predicate(current)) {
            splits.getOrElse(idx) {
                mutableListOf<String>().also { splits.add(it) }
            }.add(current)
            splits to idx
        } else {
            splits to idx + 1
        }
    }.first
}

fun solutionPart2(input: List<String>): Long {
    val seedRanges = input.first().drop(6).trim().split(" ").bigInts()
        .chunked(2).map { it[0] to it[1] }
    val categories = input
        .drop(2)
        .splitBy { it == "" }
        .map { maps ->
            maps.drop(1).map {
                it.split(" ").bigInts()
            }
        }

    return seedRanges.map { seedRange ->
        categories.fold(listOf(seedRange)) { ranges, mappings ->

            val rangesToMap = ranges.toMutableList()
            val mappedRanges = mutableListOf<Pair<BigInteger, BigInteger>>()

            while (rangesToMap.isNotEmpty()) {
                val range = rangesToMap.removeFirst()
                val start = range.first
                val end = start + range.second

                val mapping = mappings.find { (_, mapStart, mapRange) ->
                    val mapEnd = mapStart + mapRange
                    start < mapEnd && end > mapStart
                }

                if (mapping == null) {
                    mappedRanges += range
                    continue
                }

                val (dest, mapStart, mapRange) = mapping
                val mapEnd = mapStart + mapRange
                mappedRanges += (dest + (start.max(mapStart) - mapStart)) to end.min(mapEnd) - start.max(mapStart)
                if (start < mapStart) {
                    rangesToMap += start to (mapStart - start)
                }
                if (end > mapEnd) {
                    rangesToMap += mapEnd to (end - mapEnd)
                }
            }
            mappedRanges
        }
    }.flatten().minOf { it.first }.toLong()
}


