import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AocTest {

    @Test
    internal fun `solutionPart1 should return this sum`() {
        assertEquals(
            solutionPart1(
                listOf(
                    "...#......",
                    ".......#..",
                    "#.........",
                    "..........",
                    "......#...",
                    ".#........",
                    ".........#",
                    "..........",
                    ".......#..",
                    "#...#.....",
                )
            ), 374L
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "...#......",
                    ".......#..",
                    "#.........",
                    "..........",
                    "......#...",
                    ".#........",
                    ".........#",
                    "..........",
                    ".......#..",
                    "#...#.....",
                ), expansion = 10
            ), 1030
        )
    }

    @Test
    internal fun `solutionPart2 should return this sum too`() {
        assertEquals(
            solutionPart2(
                listOf(
                    "...#......",
                    ".......#..",
                    "#.........",
                    "..........",
                    "......#...",
                    ".#........",
                    ".........#",
                    "..........",
                    ".......#..",
                    "#...#.....",
                ), expansion = 100
            ), 8410
        )
    }
}
