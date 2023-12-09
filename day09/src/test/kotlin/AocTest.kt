import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AocTest {

    @Test
    internal fun `solutionPart1 should return this sum`() {
        assertEquals(
            solutionPart1(
                listOf(
                    "0 3 6 9 12 15",
                    "1 3 6 10 15 21",
                    "10 13 16 21 30 45",
                )
            ), 114
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "0 3 6 9 12 15",
                    "1 3 6 10 15 21",
                    "10 13 16 21 30 45",
                )
            ), 2
        )
    }
}
