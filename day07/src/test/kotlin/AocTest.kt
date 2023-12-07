import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AocTest {

    @Test
    internal fun `solutionPart1 should return this sum`() {
        assertEquals(
            solutionPart1(
                listOf(
                    "32T3K 765",
                    "T55J5 684",
                    "KK677 28",
                    "KTJJT 220",
                    "QQQJA 483",
                )
            ), 6440L
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "32T3K 765",
                    "T55J5 684",
                    "KK677 28",
                    "KTJJT 220",
                    "QQQJA 483",
                )
            ), 5905L
        )
    }
}
