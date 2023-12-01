import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AocTest {

    @Test
    internal fun `solutionPart1 should return this sum`() {
        assertEquals(
            solutionPart1(
                listOf(
                    "1abc2",
                    "pqr3stu8vwx",
                    "a1b2c3d4e5f",
                    "treb7uchet"
                )
            ), 142
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "two1nine",
                    "eightwothree",
                    "abcone2threexyz",
                    "xtwone3four",
                    "4nineeightseven2",
                    "zoneight234",
                    "7pqrstsixteen",
                )
            ), 281
        )
    }
}
