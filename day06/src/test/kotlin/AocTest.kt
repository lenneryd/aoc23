import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AocTest {

    @Test
    internal fun `solutionPart1 should return this sum`() {
        assertEquals(
            solutionPart1(
                listOf(
                    "Time:      7  15   30",
                    "Distance:  9  40  200"
                )
            ), 288
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "Time:      7  15   30",
                    "Distance:  9  40  200"
                )
            ), 71503
        )
    }
}
